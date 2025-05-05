package com.bytan.security.core;

import com.bytan.security.core.config.SecurityConfig;
import com.bytan.security.core.config.SecurityTokenConfig;
import com.bytan.security.core.context.HttpContext;
import com.bytan.security.core.context.ThreadContext;
import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.exception.NoConfigException;
import com.bytan.security.core.provider.AuthenticationProvider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 核心管理器
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/22  15:52
 */
public class SecurityManager {

    private static final String Security_AuthenticationRealm_KEY = "Security_AuthenticationRealm_KEY";

    private final HttpContext httpContext;
    private final SecurityConfig securityConfig;
    private final SecurityDao securityDao;
    private final Map<String, AuthenticationDataLoader> authenticationDataLoaderStorage = new ConcurrentHashMap<>();
    private final Map<String, AuthenticationProvider> authenticationProviderStorage = new ConcurrentHashMap<>();

    public SecurityManager(HttpContext httpContext, SecurityConfig securityConfig, SecurityDao securityDao) {
        this.httpContext = httpContext;
        this.securityConfig = securityConfig;
        this.securityDao = securityDao;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public SecurityConfig getSecurityConfig() {
        return securityConfig;
    }

    public SecurityDao getSecurityDao() {
        return securityDao;
    }

    public void setAuthenticationDataLoader(AuthenticationDataLoader authenticationDataLoader) {
        authenticationDataLoader.setSecurityDao(this.securityDao);
        this.authenticationDataLoaderStorage.put(authenticationDataLoader.getSubjectType(), authenticationDataLoader);
    }

    public AuthenticationDataLoader getAuthenticationDataLoader(String subjectType) {
        AuthenticationDataLoader authenticationDataLoader = this.authenticationDataLoaderStorage.get(subjectType);
        if (authenticationDataLoader == null) {
            throw new NoConfigException("AuthenticationDataLoader主体类型为 [" + subjectType + "] 尚未配置");
        }

        return authenticationDataLoader;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProviderStorage.put(authenticationProvider.getSubjectType(), authenticationProvider);
    }

    public AuthenticationProvider getAuthenticationProvider(String subjectType) {
        AuthenticationProvider authenticationProvider = this.authenticationProviderStorage.get(subjectType);
        if (authenticationProvider == null) {
            throw new NoConfigException("AuthenticationProvider主体类型为 [" + subjectType + "] 尚未配置");
        }

        return authenticationProvider;
    }

    public SecurityTokenConfig getTokenConfig(String subjectType) {
        List<SecurityTokenConfig> securityTokenConfigList = securityConfig.getToken();
        for (SecurityTokenConfig securityTokenConfig : securityTokenConfigList) {
            if (securityTokenConfig.getSubjectType().equals(subjectType)) {
                return securityTokenConfig;
            }
        }

        throw new NullPointerException("Token主体类型为 [" + subjectType + "] 尚未配置");
    }

    public AuthenticationRealm getAuthenticationRealm(String subjectType) {
        String key = Security_AuthenticationRealm_KEY + "_" + subjectType;
        AuthenticationRealm authenticationRealm = ThreadContext.get(key);
        if (authenticationRealm == null) {
            authenticationRealm = new AuthenticationRealm(getAuthenticationDataLoader(subjectType), getTokenConfig(subjectType));
            ThreadContext.set(key, authenticationRealm);
        }

        return authenticationRealm;
    }
}
