package com.bytan.security.springboot.starter;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.handler.AuthenticationAnnotationHandler;
import com.bytan.security.springboot.starter.interceptor.AuthenticationAnnotationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

/**
 * Security拦截器自动配置类
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/10  21:48
 */
@EnableAspectJAutoProxy(proxyTargetClass = true) // 启用CGLIB代理
public class SecurityInterceptorAutoConfigure {

    /**
     * 注入注解校验拦截器
     * @param securityManager 核心管理器
     * @return 注解校验拦截器
     */
    @Bean
    public AuthenticationAnnotationInterceptor authenticationAnnotationInterceptor(SecurityManager securityManager) {
        return new AuthenticationAnnotationInterceptor(securityManager);
    }

    /**
     * 注入自定义注解校验处理器
     *
     * @param handlerList 自定义注解校验处理器
     */
    @Autowired(required = false)
    public void setAuthenticationAnnotationHandler(List<AuthenticationAnnotationHandler<?>> handlerList,
                                                   AuthenticationAnnotationInterceptor authenticationAnnotationInterceptor) {
        handlerList.forEach(authenticationAnnotationInterceptor::addAuthenticationAnnotationHandler);
    }

}
