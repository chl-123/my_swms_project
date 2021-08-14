package com.fs.swms.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fs.swms.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@TableName("t_sys_role")
@ApiModel(value="Role对象", description="角色表")
public class Role extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("parent_id")
    private String parentId;

    @TableField("role_name")
    private String roleName;

    @TableField("role_key")
    private String roleKey;

    @ApiModelProperty(value = "1有效，0禁用")
    @TableField("role_status")
    private String roleStatus;

    @TableField("description")
    private String description;
}
