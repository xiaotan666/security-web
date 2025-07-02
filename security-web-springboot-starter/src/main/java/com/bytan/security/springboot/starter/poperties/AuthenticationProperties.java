package com.bytan.security.springboot.starter.poperties;

import java.io.Serial;
import java.io.Serializable;

/**
 * 身份鉴权属性配置
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/10 11:09
 */
public class AuthenticationProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否开启鉴权端点
     */
    private boolean endpoint = false;

    public boolean isEndpoint() {
        return endpoint;
    }

    public void setEndpoint(boolean endpoint) {
        this.endpoint = endpoint;
    }
}
