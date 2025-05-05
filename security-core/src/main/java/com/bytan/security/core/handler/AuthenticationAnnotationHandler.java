package com.bytan.security.core.handler;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.config.SecurityTokenConfig;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.http.SecurityRequest;

import java.lang.annotation.Annotation;

/**
 * 身份验证注解处理器
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/18  22:58
 */
public abstract class AuthenticationAnnotationHandler<A extends Annotation> extends AnnotationHandler<A> {

    private final SecurityManager securityManager;

    public AuthenticationAnnotationHandler(SecurityManager securityManager) {
        if (securityManager == null) {
            throw new NullPointerException("securityManager must not be null");
        }
        this.securityManager = securityManager;
    }

    /**
     * 获取身份校验类
     * @param subjectType 主体类型
     * @return 身份校验类
     */
    protected AuthenticationRealm getSubjectAuthenticationRealm(String subjectType) {
        return securityManager.getAuthenticationRealm(subjectType);
    }

    /**
     * 获取访问token
     * @param subjectType 主体类型
     * @return AccessToken
     */
    protected String getRequestAccessToken(String subjectType) {
        SecurityRequest request = securityManager.getHttpContext().getRequest();
        SecurityTokenConfig tokenConfig = securityManager.getTokenConfig(subjectType);
        return request.getHeader(tokenConfig.getTokenName());
    }
}
