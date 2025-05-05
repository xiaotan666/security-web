package com.bytan.security.oauth2.service;

import cn.hutool.core.util.StrUtil;
import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.config.SecurityTokenConfig;
import com.bytan.security.oauth2.Oauth2Realm;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.config.SecurityOauth2TokenConfig;
import com.bytan.security.oauth2.model.AuthorizationModel;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.oauth2.dataloader.Oauth2DataLoader;
import com.bytan.security.oauth2.grant.Oauth2Grant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * OAuth2业务类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/7 10:34
 */
public class OAuth2Service extends AbstractSecurityOauth2Service {

    private final String subjectType;

    public OAuth2Service(SecurityOauth2Manager securityOauth2Manager, String subjectType) {
        super(securityOauth2Manager);
        this.subjectType = subjectType;
    }

    @Override
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * 授权业务
     * @param clientId 客户端id
     * @param clientSecret 客户端密钥
     * @param grantType 授权方式
     * @param scopes 授权范围
     * @param grantParam 授权参数
     * @return AuthorizationModel
     */
    public AuthorizationModel authorization(String clientId, String clientSecret, String grantType, String[] scopes, Map<String, Object> grantParam) {
        if (StrUtil.isBlank(clientId)) {
            throw new IllegalArgumentException("client_id不能为空");
        }
        if (StrUtil.isBlank(clientSecret)) {
            throw new IllegalArgumentException("client_secret不能为空");
        }
        if (StrUtil.isBlank(grantType)) {
            throw new IllegalArgumentException("grant_type不能为空");
        }

        //校验应用是否合法
        Oauth2Realm oauth2Realm = securityOauth2Manager.getOauth2Realm(subjectType);
        if (!oauth2Realm.hasClientSecret(clientId, clientSecret)) {
            throw new IllegalArgumentException("应用id或secret有误");
        }

        //执行授权方法
        Oauth2Grant oauth2Grant = securityOauth2Manager.getOauth2Grant(subjectType, grantType);
        String subjectId = oauth2Grant.doMain(grantParam);

        SecurityManager securityManager = securityOauth2Manager.getSecurityManager();
        //授权成功生成访问密钥
        AuthenticationRealm authenticationRealm = securityManager.getAuthenticationRealm(subjectType);
        AuthorizationModel model = new AuthorizationModel();
        String accessToken = authenticationRealm.getAccessToken(subjectId);
        model.setAccessToken(accessToken);
        String refreshToken = oauth2Realm.getRefreshToken(subjectId);
        model.setRefreshToken(refreshToken);
        SecurityTokenConfig tokenConfig = securityManager.getTokenConfig(subjectType);
        model.setExpiresIn(tokenConfig.getTimeout());
        SecurityOauth2TokenConfig oauth2TokenConfig = securityOauth2Manager.getOauth2TokenConfig(subjectType);
        model.setRefreshExpiresIn(oauth2TokenConfig.getRefreshTimeout());

        //设置密钥权限
        List<String> scopeList = new ArrayList<>();
        if (scopes != null && scopes.length > 0) {
            scopeList.addAll(Arrays.stream(scopes).toList());
        }
        model.setScope(scopeList);

        //TODO 设置主体id的授权范围
        Oauth2DataLoader oauth2DataLoader = securityOauth2Manager.getOauth2DataLoader(subjectType);
        oauth2DataLoader.addSubjectScope(subjectId, scopeList);

        return model;
    }

    /**
     * 更新访问密钥
     * @param refreshToken 刷新密钥
     * @return 访问密钥
     */
    public String refreshToken(String refreshToken) {
        Oauth2Realm oauth2Realm = securityOauth2Manager.getOauth2Realm(subjectType);
        String subjectId = oauth2Realm.parseRefreshToken(refreshToken);

        return oauth2Realm.getRefreshToken(subjectId);
    }
}
