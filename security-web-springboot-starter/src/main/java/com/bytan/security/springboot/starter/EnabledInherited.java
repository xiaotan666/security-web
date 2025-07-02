package com.bytan.security.springboot.starter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 继承注解效果
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/12 15:45
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface EnabledInherited {
}
