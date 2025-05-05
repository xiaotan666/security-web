package com.bytan.security.core.annotation;

import com.bytan.security.core.subject.SubjectType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要登录认证
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/6/6 14:59
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresLogin {

    /**
     * 是否强制
     * @return 是true
     */
    boolean required() default true;

    /**
     * 校验身份类型
     * @return 默认user
     */
    String type() default SubjectType.USER;
}
