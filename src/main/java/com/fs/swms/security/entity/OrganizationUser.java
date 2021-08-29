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
