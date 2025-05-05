package com.bytan.security.oauth2;

import cn.hutool.core.util.StrUtil;
import com.bytan.security.oauth2.dataloader.Oauth2DataLoader;
import com.bytan.security.oauth2.config.SecurityOauth2TokenConfig;

import java.util.List;

/**
 * Oauth2相关核心功能
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 11:24
 */
public class Oauth2Realm extends Oauth2TokenRealm {

    private final Oauth2DataLoader oauth2DataLoader;

    public Oauth2Realm(Oauth2DataLoader oauth2DataLoader, SecurityOauth2TokenConfig oauth2TokenConfig) {
        super(oauth2TokenConfig);
        this.oauth2DataLoader = oauth2DataLoader;
    }

    /**
     * 校验clientId和secret是否正确
     * @param clientId 应用id
     * @param secret 应用密钥
     * @return ture正确
     */
    public boolean hasClientSecret(String clientId, String secret) {
        String clientSecret = oauth2DataLoader.getClientSecret(clientId);
        if (StrUtil.isBlank(clientSecret)) {
            return false;
        }

        return clientSecret.equals(secret);
    }

    /**
     * 是否拥有授权范围
     * @param subjectId 主体id
     * @param scope 授权范围
     * @return ture拥有
     */
    public boolean hasAuthScope(String subjectId, String scope) {
        List<String> authScopeList = oauth2DataLoader.getSubjectScope(subjectId);
        return authScopeList.stream().anyMatch(scope::equals);
    }

    /**
     * 是否拥有授权范围
     * @param subjectId 主体id
     * @param scopeList 授权范围列表
     * @return ture拥有
     */
    public boolean hasAuthScope(String subjectId, List<String> scopeList) {
        List<String> authScopeList = oauth2DataLoader.getSubjectScope(subjectId);
        for (String authScope : authScopeList) {
            scopeList.remove(authScope);
            if (scopeList.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getSubjectType() {
        return oauth2DataLoader.getSubjectType();
    }
}
