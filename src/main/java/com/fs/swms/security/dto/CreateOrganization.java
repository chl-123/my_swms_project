package com.fs.swms.security.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author jeebase
 * @since 2018-05-26
 */
@Data
public class CreateOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 父组织id
     */
    private String parentId;
    /**
     * 组织类型：1总公司，2分公司，3事业部
     */
    private String organizationType;
    /**
     * 组织名称
     */
    private String organizationName;
    /**
     * 组织编码
     */
    private String organizationKey;
    /**
     * 组织图标
     */
    private String organizationIcon;
    /**
     * 组织级别（排序）
     */
    private Integer organizationLevel;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 地区数组
     */
    private List<String> areas;

    /**
     * 街道详细地址
     */
    @ApiModelProperty(value = "街道详细地址")
    private String street;

    /**
     * 描述
     */
    private String description;
    /**
     * 创建日期
     */
    private Date createTime;

    private String creator;
    /**
     * 修改日期
     */
    private Date updateTime;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 1:删除 0:不删除
     */
    private String delFlag;
}
