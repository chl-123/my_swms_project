package com.fs.swms.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.security.dto.CreateRole;
import com.fs.swms.security.dto.UpdateRole;
import com.fs.swms.security.entity.Role;
import com.fs.swms.security.entity.RoleResource;
import com.fs.swms.security.entity.UserRole;
import com.fs.swms.security.mapper.RoleMapper;
import com.fs.swms.security.service.IRoleResourceService;
import com.fs.swms.security.service.IRoleService;
import com.fs.swms.security.service.IUserRoleService;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色相关操作接口实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    @Lazy
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleResourceService roleResourceService;

    @Override
    public Page<Role> selectRoleList(Page<Role> page, Role role) {
        Page<Role> roleInfoList = roleMapper.selectRoleList(page, role);
        return roleInfoList;
    }

    @Override
    public boolean createRole(CreateRole role) {
        QueryWrapper<Role> ew = new QueryWrapper<>();
        ew.eq("role_name", role.getRoleName()).or().eq("role_key", role.getRoleKey()).or()
                .eq("role_name", role.getRoleKey()).or().eq("role_key", role.getRoleName());
        List<Role> roleList = this.list(ew);
        if (!CollectionUtils.isEmpty(roleList)) {
            throw new BusinessException("角色名称或角色标识已经存在");
        }
        Role roleEntity = new Role();
        BeanCopier.create(CreateRole.class, Role.class, false).copy(role, roleEntity, null);
        boolean result = this.save(roleEntity);
        return result;
    }

    @Override
    @CacheEvict(value = "roles", allEntries = true)
    public boolean updateRole(UpdateRole role) {
        QueryWrapper<Role> ew = new QueryWrapper<>();
        ew.ne("id", role.getId()).and(e -> e.eq("role_name", role.getRoleName()).or().eq("role_key", role.getRoleKey())
                .or().eq("role_name", role.getRoleKey()).or().eq("role_key", role.getRoleName()));
        List<Role> roleList = this.list(ew);
        if (!CollectionUtils.isEmpty(roleList)) {
            throw new BusinessException("角色名称或角色标识已经存在");
        }
        Role roleEntity = new Role();
        BeanCopier.create(UpdateRole.class, Role.class, false).copy(role, roleEntity, null);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("id", roleEntity.getId());
        boolean result = this.update(roleEntity, wrapper);
        return result;
    }

    @Override
    @CacheEvict(value = "roles", allEntries = true)
    public boolean deleteRole(String roleId) {
        boolean result = this.removeById(roleId);
        if (result) {
            // 删除用户和角色的关联关系
            QueryWrapper<UserRole> wpd = new QueryWrapper<>();
            wpd.eq("role_id", roleId);
            userRoleService.remove(wpd);
            // 删除角色和权限的关联关系
            QueryWrapper<RoleResource> wpdr = new QueryWrapper<>();
            wpdr.eq("role_id", roleId);
            roleResourceService.remove(wpdr);
        }
        return result;
    }

    @Override
    @CacheEvict(value = "roles", allEntries = true)
    public boolean batchDeleteRole(List<String> roleIds) {
        boolean result = this.removeByIds(roleIds);
        if (result) {
            // 删除用户和角色的关联关系
            QueryWrapper<UserRole> wpd = new QueryWrapper<>();
            wpd.in("role_id", roleIds);
            userRoleService.remove(wpd);
            // 删除角色和权限的关联关系
            QueryWrapper<RoleResource> wpdr = new QueryWrapper<>();
            wpdr.in("role_id", roleIds);
            roleResourceService.remove(wpdr);
        }
        return result;
    }
}
