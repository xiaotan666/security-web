package com.bytan.security.oauth2.config;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Oauth2全局配置
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 14:25
 */
public class SecurityOauth2Config implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * oauth2令牌配置
     */
    private Set<RefreshTokenConfig> refreshToken = new HashSet<>();

    public Set<RefreshTokenConfig> getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Set<RefreshTokenConfig> refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "SecurityOauth2Config{" +
                "refreshToken=" + refreshToken +
                '}';
    }
}
