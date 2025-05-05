package com.bytan.security.oauth2.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 授权服务模型
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 17:49
 */
public class AuthorizationModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问密钥
     */
    private String accessToken;
    /**
     * 密钥类型
     */
    private String tokenType;
    /**
     * 密钥有效时间
     */
    private Long expiresIn;
    /**
     * 刷新密钥
     */
    private String refreshToken;
    /**
     * 刷新密钥有效时间
     */
    private Long refreshExpiresIn;
    /**
     * 权限范围
     */
    private List<String> scope;

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

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }
}
