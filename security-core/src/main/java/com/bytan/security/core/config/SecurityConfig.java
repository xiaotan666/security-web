package com.bytan.security.core.config;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 框架核心配置
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/14 9:40
 */
public class SecurityConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * token配置
     */
    private List<SecurityTokenConfig> token = Collections.singletonList(new SecurityTokenConfig());

    public List<SecurityTokenConfig> getToken() {
        return token;
    }

    public void setToken(List<SecurityTokenConfig> token) {
        this.token = token;
    }
}
