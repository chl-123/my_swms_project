package com.fs.swms.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.security.dto.UpdateRoleResource;
import com.fs.swms.security.entity.Resource;
import com.fs.swms.security.entity.RoleResource;

import java.util.List;

/**
 * @ClassName: IRoleService
 * @Description: 角色相关操作接口
 */
public interface IRoleResourceService extends IService<RoleResource> {

    /**
     * 根据角色查询菜单
     *
     * @param roleId
     *            角色主键
     * @return List<Resource>
     *            资源列表
     */
    List<Resource> queryResourceByRoleId(String roleId);

    /**
     * 更新角色的权限
     * @param updateRoleResource
     * @return boolean
     */
    boolean updateList(UpdateRoleResource updateRoleResource);
}