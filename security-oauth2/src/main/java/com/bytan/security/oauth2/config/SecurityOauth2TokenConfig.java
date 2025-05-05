package com.bytan.security.oauth2.config;

import com.bytan.security.core.subject.SubjectType;

import java.io.Serial;
import java.io.Serializable;

/**
 * Oauth2 Token配置
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/14 10:46
 */
public class SecurityOauth2TokenConfig implements Serializable, SubjectType {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主体类型
     */
    private String subjectType = USER;
    /**
     * 刷新token名称
     */
    private String reTokenName = "re-token";
    /**
     *  刷新token有效期
     */
    private long refreshTimeout = 60 * 60 * 24 * 30;

    public long getRefreshTimeout() {
        return refreshTimeout;
    }

    public void setRefreshTimeout(long refreshTimeout) {
        this.refreshTimeout = refreshTimeout;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    @Override
    public String getSubjectType() {
        return subjectType;
    }

    public String getReTokenName() {
        return reTokenName;
    }

    public void setReTokenName(String reTokenName) {
        this.reTokenName = reTokenName;
    }
}
