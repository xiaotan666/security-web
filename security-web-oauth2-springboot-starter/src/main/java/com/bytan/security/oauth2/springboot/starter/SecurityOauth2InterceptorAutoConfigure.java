package com.bytan.security.oauth2.springboot.starter;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.grant.Oauth2Grant;
import com.bytan.security.oauth2.springboot.starter.interceptor.Oauth2AnnotationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Oauth2拦截器自动配置类
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/10  21:48
 */
@ConditionalOnBean(value = {SecurityManager.class, SecurityOauth2Manager.class})
public class SecurityOauth2InterceptorAutoConfigure {

    /**
     * 注入oauth2注解校验拦截器
     * @param securityManager 核心管理器
     * @param securityOauth2Manager oauth2核心管理器
     * @return oauth2注解校验拦截器
     */
    @Bean
    public Oauth2AnnotationInterceptor oauth2AnnotationInterceptor(SecurityManager securityManager,
                                                                   SecurityOauth2Manager securityOauth2Manager) {
        return new Oauth2AnnotationInterceptor(securityManager, securityOauth2Manager);
    }

    /**
     * 注入自定义授权方式
     *
     * @param oauth2GrantList 授权方式列表
     */
    @Autowired(required = false)
    public void setGrantType(List<Oauth2Grant> oauth2GrantList, SecurityOauth2Manager securityOauth2Manager) {
        oauth2GrantList.forEach(securityOauth2Manager::addOauth2Grant);
    }

}
