package com.bytan.security.oauth2.service;

import com.bytan.security.core.subject.SubjectType;
import com.bytan.security.oauth2.SecurityOauth2Manager;

/**
 * 抽象Oauth2业务类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/6 17:18
 */
public abstract class AbstractSecurityOauth2Service implements SubjectType {

    protected SecurityOauth2Manager securityOauth2Manager;

    public AbstractSecurityOauth2Service(SecurityOauth2Manager securityOauth2Manager) {
        this.securityOauth2Manager = securityOauth2Manager;
    }

}
