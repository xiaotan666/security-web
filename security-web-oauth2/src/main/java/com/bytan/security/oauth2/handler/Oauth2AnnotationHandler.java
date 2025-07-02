package com.bytan.security.oauth2.handler;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.handler.AuthenticationAnnotationHandler;
import com.bytan.security.oauth2.Oauth2Realm;
import com.bytan.security.oauth2.SecurityOauth2Manager;

import java.lang.annotation.Annotation;

/**
 * Oauth2注解处理器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/2/27 17:00
 */
public abstract class Oauth2AnnotationHandler<T extends Annotation> extends AuthenticationAnnotationHandler<T> {

    private final SecurityOauth2Manager securityOauth2Manager;

    public Oauth2AnnotationHandler(SecurityManager securityManager, SecurityOauth2Manager securityOauth2Manager) {
        super(securityManager);
        if (securityOauth2Manager == null) {
            throw new NullPointerException("securityOauth2Manager must not be null");
        }
        this.securityOauth2Manager = securityOauth2Manager;
    }

    /**
     * 获取Oauth2功能类
     * @param subjectType 主体类型
     * @return Oauth2功能类
     */
    protected Oauth2Realm getSubjectOauth2Realm(String subjectType) {
        return securityOauth2Manager.getOauth2Realm(subjectType);
    }

}
