package com.fs.swms.common.annotation.auth;

import java.lang.annotation.*;

/**
 *
 * @ClassName: Pass
 * @Description: 在Controller方法上加入该注解不会验证身份
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuthentication {
}

