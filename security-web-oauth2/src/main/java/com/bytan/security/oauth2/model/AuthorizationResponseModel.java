package com.bytan.security.oauth2.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 授权响应模型
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 17:49
 */
public class AuthorizationResponseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 令牌类型
     */
    private String tokenType;
    /**
     * 令牌有效时间
     */
    private Long expiresIn;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 刷新令牌有效时间
     */
    private Long refreshExpiresIn;
    /**
     * 权限范围
     */
    private Set<String> scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(Long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "AuthorizationResponseModel{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshExpiresIn=" + refreshExpiresIn +
                ", scope=" + scope +
                '}';
    }
}
