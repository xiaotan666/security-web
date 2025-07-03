package com.bytan.security.core.handler;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.config.AccessTokenConfig;
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
            throw new NullPointerException("核心管理器未成功加载");
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
     * 获取请求中的访问token
     * @param subjectType 主体类型
     * @return AccessToken
     */
    protected String getRequestAccessToken(String subjectType) {
        SecurityRequest request = securityManager.getHttpContext().getRequest();
        AccessTokenConfig tokenConfig = securityManager.getAccessTokenConfig(subjectType);
        return request.getHeader(tokenConfig.getRequestHeader());
    }
}
