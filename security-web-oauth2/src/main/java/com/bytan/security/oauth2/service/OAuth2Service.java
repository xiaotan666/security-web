package com.bytan.security.oauth2.service;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.config.AccessTokenConfig;
import com.bytan.security.core.exception.AuthenticationException;
import com.bytan.security.oauth2.Oauth2Realm;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.config.RefreshTokenConfig;
import com.bytan.security.oauth2.model.AuthorizationResponseModel;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.oauth2.dataloader.Oauth2DataLoader;
import com.bytan.security.oauth2.grant.Oauth2Grant;
import com.bytan.security.oauth2.model.RefreshTokenResponseModel;
import io.jsonwebtoken.JwtException;

import java.util.*;

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
     *
     * @param clientId     客户端id
     * @param clientSecret 客户端密钥
     * @param grantType    授权方式
     * @param scopes       授权范围
     * @param grantParam   授权参数
     * @return AuthorizationResponseModel 授权响应
     */
    public AuthorizationResponseModel authorization(String clientId, String clientSecret, String grantType, String[] scopes, Map<String, Object> grantParam) {
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("client_id不能为空");
        }
        if (clientSecret == null || clientSecret.isEmpty()) {
            throw new IllegalArgumentException("client_secret不能为空");
        }
        if (grantType == null || grantType.isEmpty()) {
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
        //授权成功生成访问令牌
        AuthenticationRealm authenticationRealm = securityManager.getAuthenticationRealm(subjectType);
        AuthorizationResponseModel model = new AuthorizationResponseModel();
        String accessToken = authenticationRealm.generateAccessToken(subjectId);
        model.setAccessToken(accessToken);
        //生成刷新令牌
        String refreshToken = oauth2Realm.generateRefreshToken(subjectId);
        model.setRefreshToken(refreshToken);

        //设置有效期
        AccessTokenConfig accessTokenConfig = securityManager.getAccessTokenConfig(subjectType);
        model.setExpiresIn(accessTokenConfig.getTimeout());
        RefreshTokenConfig refreshTokenConfig = securityOauth2Manager.getRefreshTokenConfig(subjectType);
        model.setRefreshExpiresIn(refreshTokenConfig.getTimeout());

        //设置令牌授权范围
        if (scopes != null && scopes.length > 0) {
            Set<String> scopeSet = new HashSet<>(Arrays.stream(scopes).toList());
            Oauth2DataLoader oauth2DataLoader = securityOauth2Manager.getOauth2DataLoader(subjectType);
            oauth2DataLoader.setSubjectScope(subjectId, scopeSet);
            model.setScope(scopeSet);
        }

        return model;
    }

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 访问令牌
     */
    public RefreshTokenResponseModel refreshToken(String refreshToken) {
        if (refreshToken != null && !refreshToken.isEmpty()) {
            throw new IllegalArgumentException("参数异常");
        }

        Oauth2Realm oauth2Realm = securityOauth2Manager.getOauth2Realm(subjectType);
        try {
            //校验刷新令牌是否合法
            String subjectId = oauth2Realm.parseRefreshToken(refreshToken);
            if (subjectId != null && !subjectId.isEmpty()) {
                Oauth2DataLoader oauth2DataLoader = securityOauth2Manager.getOauth2DataLoader(this.getSubjectType());
                Set<String> subjectTokenList = oauth2DataLoader.getSubjectRefreshToken(subjectId);
                boolean match = subjectTokenList.contains(refreshToken);
                if (!match) {
                    throw new AuthenticationException();
                }
            }
            //校验通过则重新生成访问令牌
            SecurityManager securityManager = securityOauth2Manager.getSecurityManager();
            AuthenticationRealm authenticationRealm = securityManager.getAuthenticationRealm(subjectType);
            RefreshTokenResponseModel model = new RefreshTokenResponseModel();
            model.setAccessToken(authenticationRealm.generateAccessToken(subjectId));
            AccessTokenConfig accessTokenConfig = securityManager.getAccessTokenConfig(subjectType);
            model.setExpiresIn(accessTokenConfig.getTimeout());

            return model;
        } catch (JwtException e) {
            throw new AuthenticationException();
        }
    }

    /**
     * 登出
     * @param accessToken 访问令牌
     * @param refreshToken 刷新令牌
     * @param subjectId 主体id
     */
    public void logout(String accessToken, String refreshToken, String subjectId) {
        Oauth2Realm oauth2Realm = securityOauth2Manager.getOauth2Realm(subjectType);
        oauth2Realm.recycleRefreshToken(subjectId, refreshToken);

        SecurityManager securityManager = securityOauth2Manager.getSecurityManager();
        AuthenticationRealm authenticationRealm = securityManager.getAuthenticationRealm(this.getSubjectType());
        authenticationRealm.recycleAccessToken(subjectId, accessToken);
    }
}
