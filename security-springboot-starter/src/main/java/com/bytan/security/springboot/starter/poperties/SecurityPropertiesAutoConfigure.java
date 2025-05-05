package com.bytan.security.springboot.starter.poperties;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.config.SecurityConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Security属性自动配置类
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/10  21:40
 */
@ConditionalOnBean(SecurityManager.class)
@ComponentScan({"com.bytan.security.springboot.starter.endpoint"})
public class SecurityPropertiesAutoConfigure {

    @ConfigurationProperties(
            prefix = "security"
    )
    @Bean
    public SecurityConfig securityConfig() {
        return new SecurityConfig();
    }

    @ConfigurationProperties(
            prefix = "security.authentication"
    )
    @Bean
    public AuthenticationProperties endpointProperties() {
        return new AuthenticationProperties();
    }
}
