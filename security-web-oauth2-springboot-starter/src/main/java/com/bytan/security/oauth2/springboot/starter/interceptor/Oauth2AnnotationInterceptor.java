package com.bytan.security.oauth2.springboot.starter.interceptor;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.handler.Oauth2AnnotationHandler;
import com.bytan.security.oauth2.handler.RequiresScopeHandler;
import com.bytan.security.springboot.starter.interceptor.AnnotationInterceptor;

import java.lang.annotation.Annotation;

/**
 * Oauth2注解拦截器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/2/24 17:49
 */
public class Oauth2AnnotationInterceptor extends AnnotationInterceptor {

    private final SecurityManager securityManager;
    private final SecurityOauth2Manager securityOauth2Manager;

    public Oauth2AnnotationInterceptor(SecurityManager securityManager, SecurityOauth2Manager securityOauth2Manager) {
        this.securityManager = securityManager;
        this.securityOauth2Manager = securityOauth2Manager;
        registerInterceptor();
    }

    @Override
    protected void registerDefaultAnnotationHandler() {
        addOauth2AnnotationHandler(new RequiresScopeHandler(securityManager, securityOauth2Manager));
    }

    /**
     * 自定义oauth2注解处理器
     *
     * @param oauth2AnnotationHandler oauth2注解处理器
     */
    public void addOauth2AnnotationHandler(Oauth2AnnotationHandler<? extends Annotation> oauth2AnnotationHandler) {
        this.addAnnotationHandler(oauth2AnnotationHandler);
    }

}
