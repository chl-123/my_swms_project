package com.fs.swms.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 */
@Data
public class CreateUser implements Serializable
{

    /**
     * @Fields serialVersionUID :
     */

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @NotBlank(message="账号不能为空")
    @Pattern(regexp = "^[a-z0-9_-]{3,16}$", message="账号格式不正确")
    private String userAccount;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @Size(min=2,max=16,message="姓名长度不正确")
    private String userName;

    /**
     * 1 : 男，0 : 女
     */
    @ApiModelProperty(value = "1 : 男，0 : 女， 2: 未知")
    private String userSex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @NotBlank
    @Email
    private String userEmail;

    /**
     * 电话
     */
    @ApiModelProperty(value = "手机号码")
    @NotBlank(message="手机号码不能为空")
    @Size(min=11,max=11,message="手机号码长度不正确")
    private String userMobile;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message="密码格式不正确")
    private String userPassword;

    /**
     * '0'禁用，'1' 启用, '2' 密码过期或初次未修改
     */
    @ApiModelProperty(value = "用户状态 '0'禁用,'1' 启用, '2' 密码过期或初次未修改")
    private String userStatus;

    private String roleId;

    @ApiModelProperty(value = "组织机构id")
    private String organizationId;

    /**
     * 角色数组
     */
    @ApiModelProperty(value = "用户角色ID数组")
    private List<String> roleIds;
}
