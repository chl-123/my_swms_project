package com.fs.swms.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fs.swms.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 组织表
 * </p>
 *
 */
@Data
@TableName("t_sys_organization")
@ApiModel(value = "Organization对象", description = "组织表")
public class Organization extends BaseEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父组织id")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "组织类型：1总公司，2分公司，3事业部")
    @TableField("organization_type")
    private String organizationType;

    @ApiModelProperty(value = "组织名称")
    @TableField("organization_name")
    private String organizationName;

    @ApiModelProperty(value = "组织编码")
    @TableField("organization_key")
    private String organizationKey;

    @ApiModelProperty(value = "组织图标")
    @TableField("organization_icon")
    private String organizationIcon;

    @ApiModelProperty(value = "组织级别（排序）")
    @TableField("organization_level")
    private Integer organizationLevel;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    /**
     * 是否是叶子节点
     */
    @TableField(exist = false)
    private Integer isLeaf;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Organization> children;
}