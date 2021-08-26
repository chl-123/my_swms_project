package com.fs.swms.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.excel.ExcelUtil;
import com.fs.swms.security.dto.*;
import com.fs.swms.security.entity.*;
import com.fs.swms.security.mapper.UserMapper;
import com.fs.swms.security.service.*;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户相关操作接口实现类
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午3:20:30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private IOrganizationService organizationService;
    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IOrganizationUserService organizationUserService;

    @Autowired
    private IDataPermissionService dataPermissionService;

    @Autowired
    private IRoleService roleService;

    @Value("${system.defaultPwd}")
    private String defaultPwd;

    @Value("${system.defaultRoleId}")
    private String defaultRoleId;

    @Value("${system.defaultOrgId}")
    private String defaultOrgId;

//    @Autowired
//    private CacheChannel cacheChannel;

    @Override
    public Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUser user) {
        Page<UserInfo> pageUserInfo = userMapper.selectUserList(page, user);
        return pageUserInfo;
    }

    @Override
    public boolean createUser(CreateUser user) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.and(e -> e.eq("user_account", user.getUserAccount()).or()
                .eq("user_account", user.getUserEmail()).or().eq("user_account", user.getUserMobile()).or()
                .eq("user_email", user.getUserAccount()).or()
                .eq("user_email", user.getUserEmail()).or().eq("user_email", user.getUserMobile()).or()
                .eq("user_mobile", user.getUserAccount()).or()
                .eq("user_mobile", user.getUserEmail()).or().eq("user_mobile", user.getUserMobile()));
        List<User> userList = this.list(ew);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("账号已经存在");
        }

        if(null == user.getOrganizationId())
        {
            user.setOrganizationId(defaultOrgId);
        }

        String roleId = user.getRoleId();
        List<String> roleIds = user.getRoleIds();
        if (null == roleId && CollectionUtils.isEmpty(roleIds)) {
            // 默认值，改成配置
            roleId = defaultRoleId;
        }

        //DTO转Entity
        User userEntity = new User();
        BeanCopier.create(CreateUser.class, User.class, false).copy(user, userEntity, null);

        String pwd = userEntity.getUserPassword();
        if (StringUtils.isBlank(pwd)) {
            // 默认密码，配置文件配置
            pwd = defaultPwd;
            // 初次登录需要修改密码
             userEntity.setUserStatus( "2" );
        }

        //密码加密
        String cryptPwd = BCrypt.hashpw(userEntity.getUserAccount() + pwd, BCrypt.gensalt());
        userEntity.setUserPassword(cryptPwd);
        boolean result = this.save(userEntity);
        if (result) {
            //保存用户和组织机构的关系
            String organizationId = user.getOrganizationId();
            OrganizationUser orgUser = new OrganizationUser();
            orgUser.setUserId(userEntity.getId());
            orgUser.setOrganizationId(organizationId);
            organizationUserService.save(orgUser);

            //默认增加用户所在机构数据权限值，但是否有操作权限还是会根据资源权限判断
            DataPermission dataPermission = new DataPermission();
            dataPermission.setUserId(userEntity.getId());
            dataPermission.setOrganizationId(organizationId);
            dataPermissionService.save(dataPermission);

            //保存用户角色信息
            user.setId(userEntity.getId());
            user.setUserPassword(cryptPwd);

            if(!CollectionUtils.isEmpty(roleIds))
            {
                for (String role : roleIds)
                {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userEntity.getId());
                    userRole.setRoleId(role);
                    result = userRoleService.save(userRole);
                }
            }
            else
            {
                UserRole userRole = new UserRole();
                userRole.setUserId(userEntity.getId());
                userRole.setRoleId(roleId);
                result = userRoleService.save(userRole);
            }
        }
        return result;
    }

    @Override
    @CacheEvict(value = "users", key = "'id_'.concat(#user.id)")
    public boolean updateUser(UpdateUser user) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.ne("id", user.getId())
                .and(e -> e.eq("user_account", user.getUserAccount()).or().eq("user_account", user.getUserEmail()).or().eq("user_account", user.getUserMobile()).or()
                        .or().eq("user_email", user.getUserAccount()).or().eq("user_email", user.getUserEmail()).or().eq("user_email", user.getUserMobile()).or()
                        .eq("user_mobile", user.getUserAccount()).or().eq("user_mobile", user.getUserEmail()).or().eq("user_mobile", user.getUserMobile()));
        List<User> userList = this.list(ew);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("账号已经存在");
        }

        User userEntity = new User();
        BeanCopier.create(UpdateUser.class, User.class, false).copy(user, userEntity, null);
        String pwd = userEntity.getUserPassword();
        User oldInfo = this.getById(userEntity.getId());
        if (!StringUtils.isEmpty(pwd)) {
            String cryptPwd = BCrypt.hashpw(oldInfo.getUserAccount() + pwd, BCrypt.gensalt());
            userEntity.setUserPassword(cryptPwd);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userEntity.getId());
        boolean result = this.update(userEntity, wrapper);

//        //修改后更新缓存
//        cacheChannel.evict("users", "account_" + oldInfo.getUserAccount());
//        if (!StringUtils.isEmpty(oldInfo.getUserEmail()))
//        {
//            cacheChannel.evict("users", "account_" + oldInfo.getUserEmail());
//        }
//        if (!StringUtils.isEmpty(oldInfo.getUserMobile()))
//        {
//            cacheChannel.evict("users", "account_" + oldInfo.getUserMobile());
//        }

        String organizationId = user.getOrganizationId();
        QueryWrapper<OrganizationUser> organizationUserWrapper = new QueryWrapper<>();
        organizationUserWrapper.eq("user_id", userEntity.getId()).eq("organization_id", organizationId);
        OrganizationUser orgUserOld = organizationUserService.getOne(organizationUserWrapper);
        if (null == orgUserOld && null != organizationId)
        {
            QueryWrapper<OrganizationUser> organizationUserRemoveWrapper = new QueryWrapper<>();
            organizationUserRemoveWrapper.eq("user_id", userEntity.getId());
            OrganizationUser orgUserRemove = organizationUserService.getOne(organizationUserRemoveWrapper);
            QueryWrapper<DataPermission> dataPermissionRemoveWrapper = new QueryWrapper<>();
            dataPermissionRemoveWrapper.eq("user_id", userEntity.getId()).eq("organization_id", orgUserRemove.getOrganizationId());
            //删除旧机构的数据权限
            dataPermissionService.remove(dataPermissionRemoveWrapper);
            //删除旧机构的组织机构关系
            organizationUserService.remove(organizationUserRemoveWrapper);
            //保存用户和组织机构的关系
            OrganizationUser orgUser = new OrganizationUser();
            orgUser.setUserId(userEntity.getId());
            orgUser.setOrganizationId(organizationId);
            organizationUserService.save(orgUser);
            //默认增加用户所在机构数据权限值，但是否有操作权限还是会根据资源权限判断
            DataPermission dataPermission = new DataPermission();
            dataPermission.setUserId(userEntity.getId());
            dataPermission.setOrganizationId(organizationId);
            dataPermissionService.save(dataPermission);
        }

        List<String> roleIds = user.getRoleIds();
        if (result && (null != user.getRoleId() || !CollectionUtils.isEmpty(roleIds))) {
            if(!CollectionUtils.isEmpty(roleIds))
            {
                //删除不存在的权限
                QueryWrapper<UserRole> wp = new QueryWrapper<>();
                wp.eq("user_id", userEntity.getId());
                List<UserRole> urList = userRoleService.list(wp);
                if (!CollectionUtils.isEmpty(urList)) {
                    for (UserRole role : urList)
                    {
                        //如果这个权限不存在，则删除
                        if (!roleIds.contains(role.getRoleId()))
                        {
                            QueryWrapper<UserRole> wpd = new QueryWrapper<>();
                            wpd.eq("user_id", userEntity.getId()).eq("role_id", role.getRoleId());
                            userRoleService.remove(wpd);
                        }
                    }
                }

                //新增库里不存在的权限
                for (String role : roleIds)
                {
                    QueryWrapper<UserRole> oldWp = new QueryWrapper<>();
                    oldWp.eq("user_id", userEntity.getId()).eq("role_id", role);
                    UserRole oldUserRole = userRoleService.getOne(oldWp);
                    //查询出库中存在的角色列表，如果更新中的存在则不操作，如果不存在则新增
                    if(null == oldUserRole)
                    {
                        UserRole userRole = new UserRole();
                        userRole.setUserId(userEntity.getId());
                        userRole.setRoleId(role);
                        result = userRoleService.save(userRole);
                    }
                }
            }
            else if(null != user.getRoleId())
            {
                UserRole userRole = new UserRole();
                userRole.setUserId(userEntity.getId());
                userRole.setRoleId(user.getRoleId());
                QueryWrapper<UserRole> wp = new QueryWrapper<>();
                wp.eq("user_id", userEntity.getId()).eq("role_id", user.getRoleId());
                List<UserRole> urList = userRoleService.list(wp);
                //如果这个权限不存在，则删除其他权限，保存这个权限
                if (CollectionUtils.isEmpty(urList)) {
                    QueryWrapper<UserRole> wpd = new QueryWrapper<>();
                    wpd.eq("user_id", userEntity.getId());
                    userRoleService.remove(wpd);
                    result = userRoleService.save(userRole);
                }
            }
//            cacheChannel.evict("roles", "user_id_" + userEntity.getId());
//            cacheChannel.evict("resources", "user_id_" + userEntity.getId());
//            cacheChannel.evict("resources", "all_user_id_" + userEntity.getId());
        }
        return result;
    }


    @Override
    @CacheEvict(value = "users", key = "'id_'.concat(#userId)")
    public boolean deleteUser(String userId) {
        User oldInfo = this.getById(userId);
        boolean result = this.removeById(userId);
//        if (result) {
//            cacheChannel.evict("users", "account_" + oldInfo.getUserAccount());
//            if (!StringUtils.isEmpty(oldInfo.getUserEmail()))
//            {
//                cacheChannel.evict("users", "account_" + oldInfo.getUserEmail());
//            }
//            if (!StringUtils.isEmpty(oldInfo.getUserMobile()))
//            {
//                cacheChannel.evict("users", "account_" + oldInfo.getUserMobile());
//            }
//            cacheChannel.evict("roles", "user_id_" + userId);
//        }
        return result;
    }

    @Override
    public boolean batchDeleteUser(List<String> userIds) {
        List<User> userList = (List<User>) this.listByIds(userIds);
//        for (User oldInfo : userList)
//        {
//            cacheChannel.evict("users", "account_" + oldInfo.getUserAccount());
//            if (!StringUtils.isEmpty(oldInfo.getUserEmail()))
//            {
//                cacheChannel.evict("users", "account_" + oldInfo.getUserEmail());
//            }
//            if (!StringUtils.isEmpty(oldInfo.getUserMobile()))
//            {
//                cacheChannel.evict("users", "account_" + oldInfo.getUserMobile());
//            }
//            cacheChannel.evict("users", "id_" + oldInfo.getId());
//            cacheChannel.evict("roles", "user_id_" + oldInfo.getId());
//        }
        boolean result = this.removeByIds(userIds);
        return result;
    }

    @Override
    @Cacheable(value = "users", key = "'account_'.concat(#userAccount)")
    public User queryUserByAccount(String userAccount) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.and(e -> e.eq("user_account", userAccount).or().eq("user_email", userAccount).or().eq("user_mobile",
                userAccount));
        return this.getOne(ew);
    }

    @Override
    public boolean updateUserStatus(String userId, String status) {
        User userEntity = new User();
        userEntity.setId(userId);
        userEntity.setUserStatus(status);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userEntity.getId());
        return this.update(userEntity, wrapper);
    }

    @Override
    public boolean batchCreate(MyFile file) throws Exception {
        if (file.getFile()==null) {
            throw new BusinessException("文件不能为空，请选择文件上传");
        }
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        //读取Excel表格获取数据
        List<ReadExcelUser> dataList = ExcelUtil.read(file, ReadExcelUser.class);
        for(ReadExcelUser info:dataList){
            if (info.getOrganizationName()==null) {
                throw new BusinessException("部门名称不能为空");
            }
            if (info.getUserAccount()==null) {
                throw new BusinessException("用户账号不能为空");
            }
            if (info.getUserName()==null) {
                throw new BusinessException("用户姓名不能为空");
            }
            if (info.getRoleName()==null) {
                throw new BusinessException("角色不能为空");
            }
            if (info.getUserStatus()==null) {
                throw new BusinessException("用户状态不能为空");
            }
            //1:map.containsKey()   检测key是否重复
            if (map1.containsKey(info.getUserAccount())) {
                map1.clear();
                throw new BusinessException("文件中用户账号重复");
            } else {
                map1.put(info.getUserAccount(), 1);
            }
        }
        for(ReadExcelUser info:dataList){
            //查询从数据库中中查数据
            QueryWrapper<User> ew=new QueryWrapper<>();
            ew.eq("user_account",info.getUserAccount());
            List<User> userList=this.list(ew);
            //判断是否重复
            if(!CollectionUtils.isEmpty(userList)){
                throw new BusinessException("系统中用户账号"+info.getUserAccount()+"重复");
            }
            QueryWrapper<Organization> orgQueryWrapper=new QueryWrapper<>();
            orgQueryWrapper.eq("organization_name",info.getOrganizationName());
            List<Organization> organizationList=organizationService.list(orgQueryWrapper);
            //判断是否存在
            if(CollectionUtils.isEmpty(organizationList)){
                throw new BusinessException("系统中部门"+info.getOrganizationName()+"不存在");
            }
            QueryWrapper<Role> roleQueryWrapper=new QueryWrapper<>();
            roleQueryWrapper.eq("role_name",info.getRoleName());
            List<Role> roleList=roleService.list(roleQueryWrapper);
            //判断是否存在
            if(CollectionUtils.isEmpty(roleList)){
                throw new BusinessException("系统中角色"+info.getRoleName()+"不存在");
            }
        }
        String pwd = defaultPwd;
        boolean result=false;
        for(ReadExcelUser info:dataList){
            User user=new User();

            user.setUserAccount(info.getUserAccount());
            user.setUserStatus("2");
            //密码加密
            String cryptPwd = BCrypt.hashpw(user.getUserAccount() + pwd, BCrypt.gensalt());
            user.setUserPassword(cryptPwd);
            User userInfo = this.insertUser(user);
            if (userInfo!=null) {
                //保存用户和组织机构的关系
                QueryWrapper<Organization>  organizationQueryWrapper=new QueryWrapper<>();
                organizationQueryWrapper.eq("organization_name",info.getOrganizationName());
                List<Organization> list = organizationService.list(organizationQueryWrapper);

                OrganizationUser orgUser = new OrganizationUser();
                orgUser.setUserId(userInfo.getId());
                orgUser.setOrganizationId(list.get(0).getId());
                result = organizationUserService.save(orgUser);

                //默认增加用户所在机构数据权限值，但是否有操作权限还是会根据资源权限判断
                DataPermission dataPermission = new DataPermission();
                dataPermission.setUserId(userInfo.getId());
                dataPermission.setOrganizationId(list.get(0).getId());
                result=dataPermissionService.save(dataPermission);

                QueryWrapper<Role> roleQueryWrapper=new QueryWrapper<>();
                roleQueryWrapper.eq("role_name",info.getRoleName());
                List<Role> roleList=roleService.list(roleQueryWrapper);
                UserRole userRole=new UserRole();
                userRole.setRoleId(roleList.get(0).getId());
                userRole.setUserId(userInfo.getId());
                result=userRoleService.save(userRole);
            }
        }
        return result;
    }
    public User insertUser(User user) {
        baseMapper.insert(user);
        return user;
    }

    @Override
    public User getUserByOrganizationId(String organizationId) {
        QueryWrapper<OrganizationUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("organization_id",organizationId);
        List<OrganizationUser> organizationUserList = organizationUserService.list(queryWrapper);
        if (CollectionUtils.isEmpty(organizationUserList)) {
            throw new BusinessException("查询不到用户部门信息");
        }
        User user = this.getById(organizationUserList.get(0).getUserId());
        if (user == null) {
            throw new BusinessException("查询不到用户信息");
        }
        return user;
    }
}
