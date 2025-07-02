package com.bytan.security.oauth2.config;

import com.bytan.security.core.subject.SubjectType;

import java.io.Serial;
import java.io.Serializable;

/**
 * 刷新令牌配置类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/14 10:46
 */
public class RefreshTokenConfig implements Serializable, SubjectType {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主体类型
     */
    private String subjectType = USER;
    /**
     * 请求头名称
     */
    private String requestHeader = "x-refresh-token";
    /**
     * 自定义token前缀
     */
    private String prefix = "Bearer";
    /**
     *  有效期 (分钟)
     */
    private long timeout = 60 * 60 * 24 * 30;
    /**
     * 加密key
     */
    private String encryptKey;
    /**
     * 签名key
     */
    private String signKey;

    @Override
    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    @Override
    public String toString() {
        return "RefreshTokenConfig{" +
                "subjectType='" + subjectType + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", prefix='" + prefix + '\'' +
                ", timeout=" + timeout +
                ", encryptKey='" + encryptKey + '\'' +
                ", signKey='" + signKey + '\'' +
                '}';
    }
}
