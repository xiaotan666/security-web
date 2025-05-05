package com.bytan.security.core.config;

import com.bytan.security.core.subject.SubjectType;

import java.io.Serial;
import java.io.Serializable;

/**
 * token配置相关
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/22  18:09
 */
public class SecurityTokenConfig implements Serializable, SubjectType {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主体类型
     */
    private String subjectType = USER;
    /**
     * token名称
     */
    private String tokenName = "Authorization";
    /**
     *  有效期
     */
    private long timeout = 60 * 60 * 24 * 30;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    @Override
    public String getSubjectType() {
        return subjectType;
    }
}
