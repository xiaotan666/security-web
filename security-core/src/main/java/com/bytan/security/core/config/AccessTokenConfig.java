package com.bytan.security.core.config;

import com.bytan.security.core.subject.SubjectType;

import java.io.Serial;
import java.io.Serializable;

/**
 * 访问令牌配置
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/22  18:09
 */
public class AccessTokenConfig implements Serializable, SubjectType {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主体类型
     */
    private String subjectType = USER;
    /**
     * 请求头名称
     */
    private String requestHeader = "x-authorization";
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
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

    @Override
    public String toString() {
        return "AccessTokenConfig{" +
                "subjectType='" + subjectType + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", prefix='" + prefix + '\'' +
                ", timeout=" + timeout +
                ", encryptKey='" + encryptKey + '\'' +
                ", signKey='" + signKey + '\'' +
                '}';
    }
}
