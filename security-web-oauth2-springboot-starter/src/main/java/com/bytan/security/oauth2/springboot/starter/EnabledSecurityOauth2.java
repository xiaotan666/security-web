package com.bytan.security.oauth2.springboot.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 一键启用注解
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/12 10:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SecurityOAuth2AutoConfigure.class)
public @interface EnabledSecurityOauth2 {

    /**
     * 是否启用默认端点服务，默认关闭
     */
    boolean endpoint() default false;
}
