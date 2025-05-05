package com.bytan.security.springboot.starter.oauth2.properties;

import com.bytan.security.oauth2.SecurityOauth2Manager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Oauth2属性自动配置类
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/10  21:59
 */
@ConditionalOnBean(value = SecurityOauth2Manager.class)
public class SecurityOauth2PropertiesAutoConfigure {

    @ConfigurationProperties(
            prefix = "security.oauth2"
    )
    @Bean
    public Oauth2Properties oauth2Properties() {
        return new Oauth2Properties();
    }
}
