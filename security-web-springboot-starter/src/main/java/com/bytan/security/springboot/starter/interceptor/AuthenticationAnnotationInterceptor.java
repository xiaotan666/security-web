package com.bytan.security.springboot.starter.interceptor;

import com.bytan.security.core.handler.AuthenticationAnnotationHandler;
import com.bytan.security.core.handler.RequiresLoginHandler;
import com.bytan.security.core.handler.RequiresPermissionsHandler;
import com.bytan.security.core.handler.RequiresRolesHandler;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.springboot.interceptor.AnnotationInterceptor;

import java.lang.annotation.Annotation;

/**
 * 身份验证注解拦截器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/2/24 17:49
 */
public class AuthenticationAnnotationInterceptor extends AnnotationInterceptor {

    private final SecurityManager securityManager;

    public AuthenticationAnnotationInterceptor(SecurityManager securityManager) {
        this.securityManager = securityManager;
        registerInterceptor();
    }

    @Override
    protected void registerDefaultAnnotationHandler() {
        addAuthenticationAnnotationHandler(new RequiresLoginHandler(securityManager));
        addAuthenticationAnnotationHandler(new RequiresRolesHandler(securityManager));
        addAuthenticationAnnotationHandler(new RequiresPermissionsHandler(securityManager));
    }

    /**
     * 自定义校验注解处理器
     *
     * @param authenticationAnnotationHandler 校验处理器
     */
    public void addAuthenticationAnnotationHandler(AuthenticationAnnotationHandler<? extends Annotation> authenticationAnnotationHandler) {
        this.addAnnotationHandler(authenticationAnnotationHandler);
    }

}
