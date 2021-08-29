package com.fs.swms.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fs.swms.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 角色和权限关联表
 * </p>
 */
@Data
@TableName("t_sys_role_resource")
@ApiModel(value="RoleResource对象", description="角色和权限关联表")
public class RoleResource  extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("role_id")
    private String roleId;

    @TableField("resource_id")
    private String resourceId;
}
