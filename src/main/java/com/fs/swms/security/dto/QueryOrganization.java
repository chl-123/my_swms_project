package com.fs.swms.security.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class QueryOrganization implements Serializable {
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
