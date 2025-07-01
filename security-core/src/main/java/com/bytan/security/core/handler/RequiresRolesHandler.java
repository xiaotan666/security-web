package com.bytan.security.core.handler;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.annotation.RequiresRoles;
import com.bytan.security.core.subject.SubjectContext;
import com.bytan.security.core.exception.AuthenticationException;

import java.util.Set;

import static com.bytan.security.core.exception.AuthenticationException.NOT_PERMISSION;

/**
 * 主体角色校验处理器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/24 16:32
 */
public class RequiresRolesHandler extends AuthenticationAnnotationHandler<RequiresRoles> {

    public RequiresRolesHandler(SecurityManager securityManager) {
        super(securityManager);
    }

    @Override
    public Class<RequiresRoles> getAnnotationClass() {
        return RequiresRoles.class;
    }

    @Override
    protected void execute(RequiresRoles annotation) {
        String subjectType = annotation.type();
        AuthenticationRealm authentication = getSubjectAuthenticationRealm(subjectType);

        if (!authentication.hasAuthentication(this.getRequestAccessToken(subjectType))) {
            throw new AuthenticationException(AuthenticationException.USER_NOT_LOGIN);
        }
        if (!authentication.hasSubjectRole(SubjectContext.getSubjectId(), Set.of(annotation.value()))) {
            throw new AuthenticationException(NOT_PERMISSION);
        }
    }
}
