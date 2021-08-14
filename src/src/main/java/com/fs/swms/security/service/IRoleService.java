package com.fs.swms.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.security.dto.CreateRole;
import com.fs.swms.security.dto.UpdateRole;
import com.fs.swms.security.entity.Role;

import java.util.List;

/**
 * @ClassName: IRoleService
 * @Description: 角色相关操作接口
 */
public interface IRoleService extends IService<Role> {

    /**
     * 分页查询角色列表
     * @param page
     * @param role
     * @return Page<Role>
     */
    Page<Role> selectRoleList(Page<Role> page, Role role);

    /**
     * 创建角色
     * @param role
     * @return boolean
     */
    boolean createRole(CreateRole role);

    /**
     * 更新角色
     * @param role
     * @return boolean
     */
    boolean updateRole(UpdateRole role);

    /**
     * 删除角色
     * @param roleId
     * @return boolean
     */
    boolean deleteRole(String roleId);

    /**
     * 批量删除角色
     * @param roleIds
     * @return boolean
     */
    boolean batchDeleteRole(List<String> roleIds);
}