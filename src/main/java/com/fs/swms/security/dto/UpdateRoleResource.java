package com.fs.swms.security.dto;

import com.fs.swms.security.entity.RoleResource;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleResource {

    /**
     * 需要操作的角色id
     */
    private String roleId;

    /**
     * 添加的资源列表
     */
    private List<RoleResource> addResources;

    /**
     * 删除的资源列表
     */
    private List<RoleResource> delResources;
}
