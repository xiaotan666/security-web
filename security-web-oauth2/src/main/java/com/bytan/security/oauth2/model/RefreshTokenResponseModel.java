package com.bytan.security.oauth2.model;

/**
 * 刷新令牌响应封装
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/7/3 10:07
 */
public class RefreshTokenResponseModel {
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 令牌有效时间
     */
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "RefreshTokenResponseModel{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
