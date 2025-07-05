package com.bytan.security.oauth2.springboot.starter;

import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.config.SecurityOauth2Config;
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
    public SecurityOauth2Config securityOauth2Config() {
        return new SecurityOauth2Config();
    }
}
