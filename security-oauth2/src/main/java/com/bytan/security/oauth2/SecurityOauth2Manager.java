package com.bytan.security.oauth2;

import com.bytan.security.core.context.ThreadContext;
import com.bytan.security.core.exception.NoConfigException;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.oauth2.config.SecurityOauth2Config;
import com.bytan.security.oauth2.config.SecurityOauth2TokenConfig;
import com.bytan.security.oauth2.dataloader.Oauth2DataLoader;
import com.bytan.security.oauth2.grant.Oauth2Grant;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Oauth2核心管理器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/15 11:59
 */
public class SecurityOauth2Manager {

    private static final String Security_Oauth2Realm_KEY = "Security_Oauth2Realm_KEY";

    private final SecurityManager securityManager;
    private final SecurityOauth2Config securityOauth2Config;
    private final Map<String, Oauth2DataLoader> oauth2DataLoaderStorage = new ConcurrentHashMap<>();
    private final Map<String, Oauth2Grant> oauth2GrantStorage = new ConcurrentHashMap<>();

    public SecurityOauth2Manager(SecurityManager securityManager, SecurityOauth2Config securityOauth2Config) {
        this.securityManager = securityManager;
        this.securityOauth2Config = securityOauth2Config;
    }

    public SecurityOauth2Config getSecurityOauth2Config() {
        return securityOauth2Config;
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setOauth2DataLoader(Oauth2DataLoader oauth2DataLoader) {
        oauth2DataLoader.setSecurityDao(securityManager.getSecurityDao());
        this.oauth2DataLoaderStorage.put(oauth2DataLoader.getSubjectType(), oauth2DataLoader);
    }

    public Oauth2DataLoader getOauth2DataLoader(String subjectType) {
        Oauth2DataLoader oauth2DataLoader = this.oauth2DataLoaderStorage.get(subjectType);
        if (oauth2DataLoader == null) {
            throw new NoConfigException("获取Oauth2DataLoader主体类型为 [" + subjectType + "] 不存在");
        }

        return oauth2DataLoader;
    }

    public void addOauth2Grant(Oauth2Grant oauth2Grant) {
        this.oauth2GrantStorage.put(oauth2Grant.getSubjectType() + "-" + oauth2Grant.getGrantType(), oauth2Grant);
    }

    public Oauth2Grant getOauth2Grant(String subjectType, String grantType) {
        Oauth2Grant oauth2Grant = this.oauth2GrantStorage.get(subjectType + "-" + grantType);
        if (oauth2Grant == null) {
            throw new NoConfigException("获取oauth2Grant主体类型为 [" + subjectType + "]，grantType为 [" + grantType + "] 不存在");
        }

        return oauth2Grant;
    }

    public SecurityOauth2TokenConfig getOauth2TokenConfig(String subjectType) {
        List<SecurityOauth2TokenConfig> tokenList = securityOauth2Config.getToken();
        for (SecurityOauth2TokenConfig oauth2TokenConfig : tokenList) {
            if (oauth2TokenConfig.getSubjectType().equals(subjectType)) {
                return oauth2TokenConfig;
            }
        }

        throw new NullPointerException("Oauth2Token主体类型为 [" + subjectType + "] 尚未配置");
    }

    public Oauth2Realm getOauth2Realm(String subjectType) {
        String key = Security_Oauth2Realm_KEY + "_" + subjectType;
        Oauth2Realm oauth2Realm = ThreadContext.get(key);
        if (oauth2Realm == null) {
            oauth2Realm = new Oauth2Realm(getOauth2DataLoader(subjectType), getOauth2TokenConfig(subjectType));
            ThreadContext.set(key, oauth2Realm);
        }

        return oauth2Realm;
    }

}
