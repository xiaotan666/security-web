package com.bytan.security.oauth2.handler;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.exception.AuthenticationException;
import com.bytan.security.oauth2.Oauth2Realm;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.annotation.RequiresScope;
import com.bytan.security.core.subject.SubjectContext;

import java.util.Set;

import static com.bytan.security.core.exception.AuthenticationException.NOT_PERMISSION;

/**
 * 授权范围处理器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/30 9:05
 */
public class RequiresScopeHandler extends Oauth2AnnotationHandler<RequiresScope> {

    public RequiresScopeHandler(SecurityManager securityManager, SecurityOauth2Manager securityOauth2Manager) {
        super(securityManager, securityOauth2Manager);
    }

    @Override
    public Class<RequiresScope> getAnnotationClass() {
        return RequiresScope.class;
    }

    @Override
    protected void execute(RequiresScope annotation) {
        String subjectType = annotation.type();
        Oauth2Realm oauth2Realm = getSubjectOauth2Realm(subjectType);
        AuthenticationRealm authenticationRealm = getSubjectAuthenticationRealm(subjectType);
        authenticationRealm.isAuthentication(this.getRequestAccessToken(subjectType));

        if (!oauth2Realm.hasAuthScope(SubjectContext.getSubjectId(), Set.of(annotation.value()))) {
            throw new AuthenticationException(NOT_PERMISSION);
        }
    }
}
