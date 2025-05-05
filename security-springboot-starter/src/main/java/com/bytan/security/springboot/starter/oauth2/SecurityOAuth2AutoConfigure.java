package com.bytan.security.springboot.starter.oauth2;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.config.SecurityOauth2Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * OAuth2自动配置类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/2 17:20
 */
@ConditionalOnClass(value = SecurityOauth2Manager.class)
public class SecurityOAuth2AutoConfigure {

    /**
     * 注册Oauth2核心管理器
     * @param securityManager 核心管理器
     * @return SecurityOauth2Manager Oauth2核心管理器
     */
    @Bean
    public SecurityOauth2Manager securityOauth2Manager(SecurityManager securityManager,
                                                       SecurityOauth2Config securityOauth2Config) {
        return new SecurityOauth2Manager(securityManager, securityOauth2Config);
    }

}
