package com.bytan.security.oauth2.annotation;

/**
 * 校验授权范围
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/30 9:03
 */
public @interface RequiresScope {

    /**
     * 需要校验的权限码
     */
    String[] value();

    /**
     * 校验类型
     * @return 默认为空
     */
    String type() default "";
}
