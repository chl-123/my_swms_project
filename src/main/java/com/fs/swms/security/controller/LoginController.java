package com.fs.swms.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fs.swms.common.annotation.auth.CurrentUser;
import com.fs.swms.common.annotation.auth.NoAuthentication;
import com.fs.swms.common.base.Constant;
import com.fs.swms.common.base.ResponseConstant;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.captcha.GifCaptcha;
import com.fs.swms.common.component.JwtComponent;
import com.fs.swms.security.dto.LoginUser;
import com.fs.swms.security.dto.PwdUser;
import com.fs.swms.security.dto.UpdateUser;
import com.fs.swms.security.dto.UserInfo;
import com.fs.swms.security.entity.Resource;
import com.fs.swms.security.entity.Role;
import com.fs.swms.security.entity.User;
import com.fs.swms.security.service.IResourceService;
import com.fs.swms.security.service.IUserRoleService;
import com.fs.swms.security.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: LoginController
 * @Description: 登录相关前端控制器
 */
@RestController
@RequestMapping("/auth")
@Api(value = "LoginController|登录鉴权相关的前端控制器")
public class LoginController {

    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IResourceService resourceService;

//    @Autowired
//    private CacheChannel cacheChannel;

    @GetMapping("/vcode")
    @NoAuthentication
    @ApiOperation(value = "获取登录时的验证码")
    @ApiImplicitParam(paramType = "query", name = "codeKey", value = "验证码唯一标识", required = true, dataType = "String")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codeKey = request.getParameter("codeKey");
        if (codeKey != null && !codeKey.trim().isEmpty()) {
            response.setContentType("image/gif");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            GifCaptcha captcha = new GifCaptcha(130, 38, 5);
            ServletContext servletContext = request.getSession().getServletContext();
            servletContext.setAttribute("code_" + codeKey, captcha.text().toLowerCase());
            captcha.out(response.getOutputStream());
        }
    }

    /**
     * 登录，返回Token
     */
    @PostMapping("/login")
    @NoAuthentication
    @ApiOperation(value = "执行登录", notes = "返回token")
    public Result<String> login(@RequestBody LoginUser loginUser) throws Exception {
        String userAccount = loginUser.getUserAccount();
        String userPassword = loginUser.getUserPassword();

        if (StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(userPassword)) {
            return new Result<String>().error(ResponseConstant.PARAM_ERROR);
        }
        // 查询用户是否存在
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.eq("user_account", userAccount).or().eq("user_mobile", userAccount).or().eq("user_email", userAccount);
        User user = userService.getOne(ew);
        if (ObjectUtils.isEmpty(user) || !BCrypt.checkpw(user.getUserAccount() + userPassword, user.getUserPassword())) {
            return new Result<String>().error(ResponseConstant.INVALID_USERNAME_PASSWORD);
        }
        String token = jwtComponent.sign(user.getUserAccount(), user.getUserPassword(), Constant.ExpTimeType.WEB);
        return new Result<String>().success().put(token);
    }

    /**
     * 获取登录后的用户信息
     */
    @GetMapping("/user/info")
    @RequiresAuthentication
    @ApiOperation(value = "登录后获取用户个人信息")
    public Result<UserInfo> userInfo(HttpServletRequest request, @ApiIgnore @CurrentUser User currentUser) {
        String userId = currentUser.getId();
        UserInfo userInfo = new UserInfo();
        BeanCopier.create(User.class, UserInfo.class, false).copy(currentUser, userInfo, null);
        List<Role> userRole = userRoleService.queryRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(userRole)) {
            List<String> roles = new ArrayList<String>();
            StringBuffer roleNames = new StringBuffer();
            for (Role role : userRole) {
                roles.add(role.getRoleKey());
                roleNames.append(role.getRoleName());
            }
            userInfo.setRoles(roles);
            userInfo.setRoleName(roleNames.toString());
        }
        List<Resource> resourceList = resourceService.queryResourceByUserId(userId);
        userInfo.setResources(resourceList);

        List<String> resourceStringList = resourceService.queryResourceListByUserId(userId);
        userInfo.setStringResources(resourceStringList);
        return new Result<UserInfo>().success().put(userInfo);
    }

    /**
     * 刷新token
     */
    @GetMapping("/token/refresh")
    @RequiresAuthentication
    @ApiOperation(value = "刷新token")
    public Result<String> refreshToken( @ApiIgnore @CurrentUser User currentUser) {
        String userId = currentUser.getId();
        User user = userService.getById(userId);
        String token = jwtComponent.sign(user.getUserAccount(), user.getUserPassword(), Constant.ExpTimeType.WEB);
        return new Result<String>().success().put(token);
    }

    /**
     * 更新密码
     */
    @PostMapping("/pwd/update")
    @NoAuthentication
    @ApiOperation(value = "未登录用户找回密码，更新密码")
    public Result<?> changePwd(@Valid @RequestBody PwdUser user) {
        User userEntity = new User();
        BeanCopier.create(PwdUser.class, User.class, false).copy(user, userEntity, null);
        String pwd = userEntity.getUserPassword();
        if (!StringUtils.isEmpty(pwd)) {
            String cryptPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            userEntity.setUserPassword(cryptPwd);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_mobile", userEntity.getUserMobile());
        boolean result = userService.update(userEntity, wrapper);
        if (result) {
            return new Result<>().success("密码修改成功,请登录");
        } else {
            return new Result<>().error("密码修改失败，请重试");
        }
    }

    /**
     * 更新密码
     */
    @PostMapping("/pwd/personal/update")
    @RequiresAuthentication
    @ApiOperation(value = "登录用户，更新密码")
    public Result<?> changePwdPersonal(@Valid @RequestBody UpdateUser user, @ApiIgnore @CurrentUser User userc) {
        User userEntity = new User();
        userEntity.setUserMobile(userc.getUserMobile());
        String pwd = user.getUserPassword();
        if (!StringUtils.isEmpty(pwd)) {
            String cryptPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            userEntity.setUserPassword(cryptPwd);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_mobile", userEntity.getUserMobile());
        boolean result = userService.update(userEntity, wrapper);
        if (result) {
            return new Result<>().success("密码修改成功,请登录");
        } else {
            return new Result<>().error("密码修改失败，请重试");
        }
    }

    @PostMapping("/logout")
    @NoAuthentication
    @ApiOperation(value = "退出登录")
    public Result<?> logOut(HttpServletRequest request) throws Exception {
        return new Result<>().success();
    }

    @RequestMapping("401")
    @NoAuthentication
    @ApiIgnore
    public Result<?> unauthorized() {
        return new Result<>().error(ResponseConstant.USER_NO_PERMITION);
    }

    @RequestMapping("timeout")
    @NoAuthentication
    @ApiIgnore
    public Result<?> timeOut() {
        return new Result<>().error(ResponseConstant.UNAUTHORIZED);
    }
}

