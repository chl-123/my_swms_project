package com.fs.swms.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.security.entity.Role;
import com.fs.swms.security.entity.UserRole;

import java.util.List;

/**
 * @ClassName: IUserRoleService
 * @Description: 用户角色相关操接口
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 根据用户ID查询人员角色
     *
     * @param userId
     *            用户ID
     * @return UserRole
     *            结果
     */
    UserRole selectByUserId(String userId);

    /**
     * queryRolesByUserId
     *
     * @Title: queryRolesByUserId
     * @Description: 查询用户的角色
     * @param userId
     * @return List<Role>
     */
    List<Role> queryRolesByUserId(String userId);
}
