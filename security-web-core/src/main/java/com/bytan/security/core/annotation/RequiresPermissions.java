package com.bytan.security.core.annotation;

import com.bytan.security.core.subject.SubjectType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要权限码认证
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/9 18:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequiresPermissions {

    /**
     * 需要校验的权限码
     */
    String[] value();

    /**
     * 校验身份类型
     * @return 默认user
     */
    String type() default SubjectType.USER;
}