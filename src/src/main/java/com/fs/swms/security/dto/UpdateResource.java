package com.fs.swms.security.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限表
 * </p>
 */
@Data
public class UpdateResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String resourceName;

    private String parentId;

    private String resourceKey;

    private String resourceType;

    private String resourceIcon;

    private String resourcePath;

    private Boolean resourceShow;

    private Boolean resourceCache;

    private String resourceUrl;

    private Integer resourceLevel;

    private String resourcePageName;

    private String description;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
}
