package com.fs.swms.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 */
@Data
public class UpdateUser implements Serializable
{

    /**
     * @Fields serialVersionUID :
     */

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String id;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * '0'禁用，'1' 启用, '2' 密码过期或初次未修改
     */
    private String userStatus;

    private String roleId;

    @ApiModelProperty(value = "组织机构id")
    private String organizationId;

    /**
     * 角色数组
     */
    @ApiModelProperty(value = "用户角色ID数组")
    private List<String> roleIds;

    private String newPwd;

    private String oldPwd;

    private String smsCode;

}
