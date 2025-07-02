package com.bytan.security.oauth2.springboot.starter.endpoint;

import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import com.bytan.security.core.subject.SubjectType;
import com.bytan.security.oauth2.SecurityOauth2Manager;
import com.bytan.security.oauth2.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OAuth2端点处理
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/11 10:36
 */
public abstract class OAuth2Endpoint implements SubjectType {

    @Qualifier
    protected SecurityOauth2Manager securityOauth2Manager;
    private OAuth2Service oAuth2Service;

    /**
     * 获取OAuth2业务 懒加载实现，直接拿securityOauth2Manager变量会报null
     *
     * @return AuthenticationService
     */
    private OAuth2Service getOAuth2Service() {
        if (oAuth2Service != null) {
            return oAuth2Service;
        }
        if (securityOauth2Manager != null) {
            return oAuth2Service = new OAuth2Service(securityOauth2Manager, this.getSubjectType());
        }

        throw new NullPointerException("securityOauth2Manager or oAuth2Service is null");
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

        return getOAuth2Service().authorization(
                clientId != null ? clientId[0] : null,
                clientSecret != null ? clientSecret[0] : null,
                grantType != null ? grantType[0] : null,
                scopes,
                new ConcurrentHashMap<String, Object>(parameterMap)
        );
    }
}
