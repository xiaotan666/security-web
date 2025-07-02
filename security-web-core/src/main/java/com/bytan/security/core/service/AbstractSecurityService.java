package com.bytan.security.core.service;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.subject.SubjectType;

/**
 * 抽象安全业务类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/6 17:18
 */
public abstract class AbstractSecurityService implements SubjectType {

    protected SecurityManager securityManager;

    public AbstractSecurityService(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

}
