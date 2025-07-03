package com.bytan.security.oauth2;

import com.bytan.security.core.exception.NoConfigException;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.oauth2.config.RefreshTokenConfig;
import com.bytan.security.oauth2.config.SecurityOauth2Config;
import com.bytan.security.oauth2.dataloader.Oauth2DataLoader;
import com.bytan.security.oauth2.grant.Oauth2Grant;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Oauth2核心管理器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/15 11:59
 */
public class SecurityOauth2Manager {

    private final SecurityManager securityManager;
    private final SecurityOauth2Config securityOauth2Config;
    private final Map<String, Oauth2DataLoader> oauth2DataLoaderStorage = new ConcurrentHashMap<>();
    private final Map<String, Oauth2Grant> oauth2GrantStorage = new ConcurrentHashMap<>();
    private final Map<String, Oauth2Realm> oauth2RealmStorage = new ConcurrentHashMap<>();

    public SecurityOauth2Manager(SecurityManager securityManager, SecurityOauth2Config securityOauth2Config) {
        this.securityManager = securityManager;
        this.securityOauth2Config = securityOauth2Config;
    }

    /**
     * 核心管理器
     * @return SecurityManager
     */
    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    /**
     * 设置数据加载器
     * @param oauth2DataLoader 加载器
     */
    public void setOauth2DataLoader(Oauth2DataLoader oauth2DataLoader) {
        oauth2DataLoader.setSecurityDao(securityManager.getSecurityDao());
        oauth2DataLoaderStorage.put(oauth2DataLoader.getSubjectType(), oauth2DataLoader);
    }

    /**
     * 获取加载器
     * @param subjectType 主体类型
     * @return Oauth2DataLoader 加载器
     */
    public Oauth2DataLoader getOauth2DataLoader(String subjectType) {
        Oauth2DataLoader oauth2DataLoader = oauth2DataLoaderStorage.get(subjectType);
        if (oauth2DataLoader == null) {
            throw new NoConfigException("类型为 [" + subjectType + "] 的主体，尚未配置Oauth2加载器");
        }

        return oauth2DataLoader;
    }

    /**
     * 添加Grant处理器
     * @param oauth2Grant Grant处理器
     */
    public void addOauth2Grant(Oauth2Grant oauth2Grant) {
        oauth2GrantStorage.put(oauth2Grant.getSubjectType() + "-" + oauth2Grant.getGrantType(), oauth2Grant);
    }

    /**
     * 获取Grant处理器
     * @param subjectType 主体类型
     * @param grantType Grant处理器类型
     * @return Oauth2Grant
     */
    public Oauth2Grant getOauth2Grant(String subjectType, String grantType) {
        Oauth2Grant oauth2Grant = oauth2GrantStorage.get(subjectType + "-" + grantType);
        if (oauth2Grant == null) {
            throw new NoConfigException("类型为 [" + subjectType + "] 的主体，尚未配置Grant处理器");
        }

        return oauth2Grant;
    }

    /**
     * 获取刷新令牌配置
     * @param subjectType 主体类型
     * @return AccessTokenConfig 访问令牌配置类
     */
    public RefreshTokenConfig getRefreshTokenConfig(String subjectType) {
        Set<RefreshTokenConfig> refreshTokenConfigSet = securityOauth2Config.getRefreshToken();
        for (RefreshTokenConfig refreshTokenConfig : refreshTokenConfigSet) {
            if (refreshTokenConfig.getSubjectType().equals(subjectType)) {
                return refreshTokenConfig;
            }
        }

        RefreshTokenConfig refreshTokenConfig = new RefreshTokenConfig();
        refreshTokenConfig.setSubjectType(subjectType);
        securityOauth2Config.getRefreshToken().add(refreshTokenConfig);
        return refreshTokenConfig;
    }

    /**
     * 获取oauth2处理类
     * @param subjectType 主体类型
     * @return Oauth2Realm 处理类
     */
    public Oauth2Realm getOauth2Realm(String subjectType) {
        Oauth2Realm oauth2Realm = oauth2RealmStorage.get(subjectType);
        if (oauth2Realm == null) {
            oauth2Realm = new Oauth2Realm(getOauth2DataLoader(subjectType), getRefreshTokenConfig(subjectType));
            oauth2RealmStorage.put(oauth2Realm.getSubjectType(), oauth2Realm);
        }

        return oauth2Realm;
    }

}
