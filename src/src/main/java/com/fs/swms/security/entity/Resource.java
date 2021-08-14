package com.fs.swms.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fs.swms.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("t_sys_resource")
@ApiModel(value = "Resource对象", description = "权限表")
public class Resource extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("resource_name")
    private String resourceName;

    @TableField("parent_id")
    private String parentId;

    @TableField("resource_key")
    private String resourceKey;

    @ApiModelProperty(value = "资源类型：1、模块 2、菜单 3、按钮 4、链接")
    @TableField("resource_type")
    private String resourceType;

    @TableField("resource_icon")
    private String resourceIcon;

    @TableField("resource_path")
    private String resourcePath;

    @TableField("resource_url")
    private String resourceUrl;

    @TableField("resource_level")
    private Integer resourceLevel;

    @ApiModelProperty(value = "是否显示")
    @TableField("resource_show")
    private Boolean resourceShow;

    @ApiModelProperty(value = "是否缓存")
    @TableField("resource_cache")
    private Boolean resourceCache;

    @ApiModelProperty(value = "页面name")
    @TableField("resource_page_name")
    private String resourcePageName;

    @TableField("description")
    private String description;

    /**
     * 子菜单，必须初始化否则vue新增不展示树子菜单
     */
    @TableField(exist = false)
    private List<Resource> children = new ArrayList<>();
}