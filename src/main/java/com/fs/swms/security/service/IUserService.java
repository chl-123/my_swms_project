package com.fs.swms.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.security.dto.CreateUser;
import com.fs.swms.security.dto.QueryUser;
import com.fs.swms.security.dto.UpdateUser;
import com.fs.swms.security.dto.UserInfo;
import com.fs.swms.security.entity.User;

import java.util.List;

/**
 * @ClassName: IUserService
 * @Description: 用户相关操接口
 */
public interface IUserService extends IService<User> {

    /**
     * 创建用户
     *
     * @param user
     * @return boolean
     */
    boolean createUser(CreateUser user);

    /**
     * 更新用户
     *
     * @param user
     * @return boolean
     */
    boolean updateUser(UpdateUser user);

    /**
     * 删除用户
     * @param userId
     * @return boolean
     */
    boolean deleteUser(String userId);

    /**
     * 批量删除用户
     * @param userIds
     * @return boolean
     */
    boolean batchDeleteUser(List<String> userIds);

    /**
     * 根据用户名查询用户
     *
     * @param userAccount 用户账号
     * @return User 用户
     */
    User queryUserByAccount(String userAccount);

    /**
     * 分页查询用户
     * @param page
     * @param user
     * @return Page<UserInfo>
     */
    Page<UserInfo> selectUserList(Page<UserInfo> page, QueryUser user);

    /**
     * 更新用户状态
     *
     * @param userId
     * @param status
     * @return boolean
     */
    boolean updateUserStatus(String userId,String status);
    /**
     * 批量添加用户
     * @param file
     * @return boolean
     */
    boolean batchCreate(MyFile file) throws Exception;
    /**
     * 添加并返回该用户信息
     * @param user
     * @return User
     */
    User insertUser(User user);
}
