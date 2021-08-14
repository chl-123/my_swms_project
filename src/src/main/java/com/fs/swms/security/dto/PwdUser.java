package com.fs.swms.security.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 */
@Data
public class PwdUser implements Serializable
{

    /**
     * @Fields serialVersionUID :
     */

    private static final long serialVersionUID = 1L;

    /**
     * 电话
     */
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp="^[1][1-9][0-9]{9}$", message="请输入正确手机号。")
    private String userMobile;

    /**
     * 密码
     */
    @NotBlank(message="密码不能为空")
    @Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$", message="请输入6-18位，不含特殊字符密码。")
    private String userPassword;

    /**
     * 图片验证码
     */
//    @NotBlank(message="图片验证码不能为空")
//    private String vCode;

    @NotBlank(message="不能为空")
    private String verkey;

    /**
     * 短信验证码
     */
    @NotBlank(message="短信验证码不能为空")
    @Length(max=6, min=6, message="请输入6位数短信验证码。")
    private String smsCode;

    /**
     * '0'禁用，'1' 启用, '2' 密码过期或初次未修改
     */
    private String userStatus;

    private String roleId;
}
