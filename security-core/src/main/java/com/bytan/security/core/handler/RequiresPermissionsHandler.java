package com.bytan.security.core.handler;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.annotation.RequiresPermissions;
import com.bytan.security.core.subject.SubjectContext;
import com.bytan.security.core.exception.AuthenticationException;

import java.util.Set;

import static com.bytan.security.core.exception.AuthenticationException.NOT_PERMISSION;

/**
 * 权限校验处理器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/24 16:32
 */
public class RequiresPermissionsHandler extends AuthenticationAnnotationHandler<RequiresPermissions> {

    public RequiresPermissionsHandler(SecurityManager securityManager) {
        super(securityManager);
    }

    @Override
    public Class<RequiresPermissions> getAnnotationClass() {
        return RequiresPermissions.class;
    }

    @Override
    protected void execute(RequiresPermissions annotation) {
        String subjectType = annotation.type();
        AuthenticationRealm authentication = getSubjectAuthenticationRealm(subjectType);

        if (!authentication.hasAuthentication(this.getRequestAccessToken(subjectType))) {
            throw new AuthenticationException(AuthenticationException.USER_NOT_LOGIN);
        }
        if (!authentication.hasSubjectPermission(SubjectContext.getSubjectId(), Set.of(annotation.value()))) {
            throw new AuthenticationException(NOT_PERMISSION);
        }
    }

}
