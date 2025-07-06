package com.bytan.security.springboot.starter;

import com.bytan.security.core.config.SecurityConfig;
import com.bytan.security.core.context.HttpContext;
import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.provider.AuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * Security自动配置类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/26 16:45
 */
@Import(value = { SecurityPropertiesAutoConfigure.class })
public class SecurityAutoConfigure {

    @Bean
    public SecurityManager securityManager(HttpContext httpContext,
                                           SecurityDao securityDao,
                                           SecurityConfig securityConfig,
                                           List<AuthenticationDataLoader> authenticationDataLoaderList,
                                           List<AuthenticationProvider> authenticationProviderList) {
        SecurityManager securityManager = new SecurityManager(httpContext, securityConfig, securityDao);
        authenticationDataLoaderList.forEach(securityManager::setAuthenticationDataLoader);
        authenticationProviderList.forEach(securityManager::setAuthenticationProvider);

        return securityManager;
    }

}
