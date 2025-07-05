package com.bytan.security.oauth2.springboot.starter.endpoint;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.config.AccessTokenConfig;
import com.bytan.security.core.exception.AuthenticationException;
import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import com.bytan.security.core.subject.SubjectContext;
import com.bytan.security.core.subject.SubjectType;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.config.RefreshTokenConfig;
import com.bytan.security.oauth2.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OAuth2端点服务
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/11 10:36
 */
@RestController
@RequestMapping("/oauth2")
public class OAuth2Endpoint implements SubjectType {

    private final SecurityOauth2Manager securityOauth2Manager;
    private final OAuth2Service oAuth2Service;

    @Autowired
    public OAuth2Endpoint(SecurityOauth2Manager securityOauth2Manager) {
        this.securityOauth2Manager = securityOauth2Manager;
        this.oAuth2Service = new OAuth2Service(securityOauth2Manager, this.getSubjectType());
    }

    /**
     * 授权访问
     *
     * @param securityRequest  当前请求
     * @param securityResponse 响应
     * @return Object
     */
    public Object authorization(SecurityRequest securityRequest, SecurityResponse securityResponse) {
        Map<String, String[]> parameterMap = securityRequest.getParameterMap();
        String[] clientId = parameterMap.computeIfPresent("client_id", (key, values) -> {
            parameterMap.remove(key);
            return values;
        });
        String[] clientSecret = parameterMap.computeIfPresent("client_secret", (key, values) -> {
            parameterMap.remove(key);
            return values;
        });
        String[] grantType = parameterMap.computeIfPresent("grant_type", (key, values) -> {
            parameterMap.remove(key);
            return values;
        });
        String[] scopes = parameterMap.computeIfPresent("scopes", (key, values) -> {
            parameterMap.remove(key);
            return values;
        });

        Assert.notNull(clientId, "client_id不能为空");
        Assert.notNull(clientSecret, "client_secret不能为空");
        Assert.notNull(grantType, "grant_type不能为空");

        return oAuth2Service.authorization(clientId[0], clientSecret[0], grantType[0], scopes, new ConcurrentHashMap<String, Object>(parameterMap));
    }

    /**
     * 刷新访问令牌
     *
     * @param securityRequest  当前请求
     * @param securityResponse 响应
     * @return 访问令牌
     */
    public Object refreshToken(SecurityRequest securityRequest, SecurityResponse securityResponse) {
        RefreshTokenConfig refreshTokenConfig = securityOauth2Manager.getRefreshTokenConfig(this.getSubjectType());
        String refreshToken = securityRequest.getHeader(refreshTokenConfig.getRequestHeader());
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new AuthenticationException();
        }

        return oAuth2Service.refreshToken(refreshToken);
    }

    /**
     * 登出
     * @param securityRequest  当前请求
     * @param securityResponse 响应
     */
    public Object logout(SecurityRequest securityRequest, SecurityResponse securityResponse) {
        RefreshTokenConfig refreshTokenConfig = securityOauth2Manager.getRefreshTokenConfig(this.getSubjectType());
        String refreshToken = securityRequest.getHeader(refreshTokenConfig.getRequestHeader());
        if (refreshToken != null && !refreshToken.isEmpty()) {
            SecurityManager securityManager = securityOauth2Manager.getSecurityManager();
            AccessTokenConfig accessTokenConfig = securityManager.getAccessTokenConfig(this.getSubjectType());
            String accessToken = securityRequest.getHeader(accessTokenConfig.getRequestHeader());
            if (accessToken != null && !accessToken.isEmpty()) {
                oAuth2Service.logout(accessToken, refreshToken, SubjectContext.getSubjectId());
            }
        }

        return "已退出";
    }

    @Override
    public String getSubjectType() {
        return SubjectType.USER;
    }
}
