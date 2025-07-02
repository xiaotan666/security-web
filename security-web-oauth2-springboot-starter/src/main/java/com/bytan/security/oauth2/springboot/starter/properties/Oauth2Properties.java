package com.bytan.security.oauth2.springboot.starter.properties;

import com.bytan.security.oauth2.config.SecurityOauth2Config;

import java.io.Serial;
import java.io.Serializable;

/**
 * oauth2属性配置
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/15  13:31
 */
public class Oauth2Properties extends SecurityOauth2Config implements Serializable {

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
