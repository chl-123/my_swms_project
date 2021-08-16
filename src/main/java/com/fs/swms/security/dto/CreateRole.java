package com.fs.swms.security.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
public class CreateRole
{

    private String id;

    private String parentId;

    private String roleName;

    private String roleKey;

    private String roleStatus;

    private String description;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
}
