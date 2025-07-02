package com.bytan.security.core.config;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 框架全局配置类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/14 9:40
 */
public class SecurityConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌配置
     */
    private Set<AccessTokenConfig> accessToken = new HashSet<>();

    public Set<AccessTokenConfig> getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Set<AccessTokenConfig> accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "SecurityConfig{" +
                "accessToken=" + accessToken +
                '}';
    }
}
