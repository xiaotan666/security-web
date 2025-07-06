package com.bytan.security.core;

import com.bytan.security.core.config.AccessTokenConfig;
import com.bytan.security.core.config.SecurityConfig;
import com.bytan.security.core.context.HttpContext;
import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.data.loader.DefaultAuthenticationDataLoader;
import com.bytan.security.core.exception.NoConfigException;
import com.bytan.security.core.provider.AuthenticationProvider;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 核心管理器
 * 用于管理全局处理器的唯一实例
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/22  15:52
 */
public class SecurityManager {

    private final HttpContext httpContext;
    private final SecurityConfig securityConfig;
    private final SecurityDao securityDao;
    private final Map<String, AuthenticationDataLoader> authenticationDataLoaderStorage = new ConcurrentHashMap<>();
    private final Map<String, AuthenticationProvider> authenticationProviderStorage = new ConcurrentHashMap<>();
    private final Map<String, AuthenticationRealm> authenticationRealmStorage = new ConcurrentHashMap<>();

    public SecurityManager(HttpContext httpContext, SecurityConfig securityConfig, SecurityDao securityDao) {
        this.httpContext = httpContext;
        this.securityConfig = securityConfig;
        this.securityDao = securityDao;
    }

    /**
     * http上下文处理器
     * @return HttpContext
     */
    public HttpContext getHttpContext() {
        return httpContext;
    }

    /**
     * 数据加载器
     * @return SecurityDao
     */
    public SecurityDao getSecurityDao() {
        return securityDao;
    }

    /**
     * 设置身份信息加载器
     * @param authenticationDataLoader 身份信息加载器
     */
    public void setAuthenticationDataLoader(AuthenticationDataLoader authenticationDataLoader) {
        authenticationDataLoader.setSecurityDao(this.securityDao);
        this.authenticationDataLoaderStorage.put(authenticationDataLoader.getSubjectType(), authenticationDataLoader);
    }

    /**
     * 获取身份信息加载器
     * @param subjectType 主体类型
     * @return 身份信息加载器
     */
    public AuthenticationDataLoader getAuthenticationDataLoader(String subjectType) {
        AuthenticationDataLoader authenticationDataLoader = this.authenticationDataLoaderStorage.get(subjectType);
        if (authenticationDataLoader == null) {
            authenticationDataLoader = new DefaultAuthenticationDataLoader(subjectType);
            authenticationDataLoader.setSecurityDao(this.securityDao);
            this.authenticationDataLoaderStorage.put(subjectType, authenticationDataLoader);
        }

        return authenticationDataLoader;

    }

    /**
     * 设置身份信息提供器
     * @param authenticationProvider 提供器
     */
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProviderStorage.put(authenticationProvider.getSubjectType(), authenticationProvider);
    }

    /**
     * 获取身份信息提供器
     * @param subjectType 主体类型
     * @return AuthenticationProvider 提供器
     */
    public AuthenticationProvider getAuthenticationProvider(String subjectType) {
        AuthenticationProvider authenticationProvider = this.authenticationProviderStorage.get(subjectType);
        if (authenticationProvider == null) {
            throw new NoConfigException("类型为 [" + subjectType + "] 的主体，尚未配置身份提供器");
        }

        return authenticationProvider;
    }

    /**
     * 获取访问令牌配置
     * @param subjectType 主体类型
     * @return AccessTokenConfig 访问令牌配置类
     */
    public AccessTokenConfig getAccessTokenConfig(String subjectType) {
        Set<AccessTokenConfig> accessTokenConfigSet = securityConfig.getAccessToken();
        for (AccessTokenConfig accessTokenConfig : accessTokenConfigSet) {
            if (accessTokenConfig.getSubjectType().equals(subjectType)) {
                return accessTokenConfig;
            }
        }

        AccessTokenConfig accessTokenConfig = new AccessTokenConfig();
        accessTokenConfig.setSubjectType(subjectType);
        securityConfig.getAccessToken().add(accessTokenConfig);
        return accessTokenConfig;
    }

    /**
     * 获取身份信息处理类
     * @param subjectType 主体类型
     * @return AuthenticationRealm 处理类
     */
    public AuthenticationRealm getAuthenticationRealm(String subjectType) {
        AuthenticationRealm authenticationRealm = this.authenticationRealmStorage.get(subjectType);
        if (authenticationRealm == null) {
            authenticationRealm = new AuthenticationRealm(getAuthenticationDataLoader(subjectType), getAccessTokenConfig(subjectType));
            this.authenticationRealmStorage.put(authenticationRealm.getSubjectType(), authenticationRealm);
        }

        return authenticationRealm;
    }
}
