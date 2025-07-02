package com.bytan.security.springboot.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 全局生效注解
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/12 10:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SecurityAutoConfigure.class)
public @interface EnabledSecurity {
}
