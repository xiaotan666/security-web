package com.bytan.security.springboot.starter;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.config.SecurityConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Security属性自动配置类
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/10  21:40
 */
@ConditionalOnBean(SecurityManager.class)
public class SecurityPropertiesAutoConfigure {

    @ConfigurationProperties(
            prefix = "security"
    )
    @Bean
    public SecurityConfig securityConfig() {
        return new SecurityConfig();
    }
}
