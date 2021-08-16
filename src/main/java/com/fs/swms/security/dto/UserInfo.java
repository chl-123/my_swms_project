package com.fs.swms.security.dto;

import com.fs.swms.security.entity.Resource;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserInfo
{
    private String id;
    private String token;
    private String userAccount;
    private String userNickName;
    private String userName;
    private String userEmail;
    private String userMobile;
    private String userSex;
    private String userStatus;
    private Long time;
    private String roleId;
    private String organizationId;
    private String organizationName;
    private String roleIds;
    private String roleKey;
    private String roleName;
    private String dataPermission;
    private List<String> roles;
    private List<Resource> resources;
    private List<String> stringResources;
    private String headImgUrl;
    private String province;
    private String provinceName;
    private String city;
    private String cityName;
    private String area;
    private String street;
    private String description;
    private Date createTime;
}
