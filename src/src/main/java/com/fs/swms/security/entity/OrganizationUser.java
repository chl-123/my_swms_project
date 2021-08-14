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
 *
 * </p>
 *
 */
@Data
@TableName("t_sys_organization_user")
@ApiModel(value="OrganizationUser对象", description="")
public class OrganizationUser extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("organization_id")
    private String organizationId;

    @TableField("user_id")
    private String userId;
}
