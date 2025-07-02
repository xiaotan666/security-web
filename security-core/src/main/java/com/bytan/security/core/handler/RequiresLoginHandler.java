package com.bytan.security.core.handler;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.annotation.RequiresLogin;
import com.bytan.security.core.exception.AuthenticationException;

/**
 * 登录认证注解处理器
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/18  22:55
 */
public class RequiresLoginHandler extends AuthenticationAnnotationHandler<RequiresLogin> {

    public RequiresLoginHandler(SecurityManager securityManager) {
        super(securityManager);
    }

    @Override
    public Class<RequiresLogin> getAnnotationClass() {
        return RequiresLogin.class;
    }

    @Override
    protected void execute(RequiresLogin annotation) {
        String subjectType = annotation.type();
        AuthenticationRealm authenticationRealm = this.getSubjectAuthenticationRealm(subjectType);

        try {
            authenticationRealm.isAuthentication(this.getRequestAccessToken(subjectType));
        } catch (AuthenticationException e) {
            if (annotation.required()) {
                throw new AuthenticationException(e.getMessage());
            }
        }
    }
}