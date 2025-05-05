package com.bytan.security.oauth2.config;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Oauth2相关配置
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 14:25
 */
public class SecurityOauth2Config implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * oauth2 token配置
     */
    private List<SecurityOauth2TokenConfig> token = Collections.singletonList(new SecurityOauth2TokenConfig());

    public List<SecurityOauth2TokenConfig> getToken() {
        return token;
    }

    public void setToken(List<SecurityOauth2TokenConfig> token) {
        this.token = token;
    }
}
