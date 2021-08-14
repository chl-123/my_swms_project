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
 * 用户和角色关联表
 * </p>
 */
@Data
@TableName("t_sys_user_role")
@ApiModel(value="UserRole对象", description="用户和角色关联表")
public class UserRole extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("role_id")
    private String roleId;
}
