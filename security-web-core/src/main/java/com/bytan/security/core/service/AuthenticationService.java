package com.bytan.security.core.service;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.service.model.LoginResponseModel;
import com.bytan.security.core.SecurityManager;

import java.util.Map;

/**
 * 身份鉴权业务
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/6 17:10
 */
public class AuthenticationService extends AbstractSecurityService {

    private final String subjectType;

    public AuthenticationService(SecurityManager securityManager, String subjectType) {
        super(securityManager);
        this.subjectType = subjectType;
    }

    @Override
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * 登录
     * @param param 请求携带的参数
     * @return LoginResponseModel 登录响应模型
     */
    public LoginResponseModel login(Map<String, Object> param) {
        String subjectId = securityManager.getAuthenticationProvider(this.getSubjectType())
                .loadLoginSubjectId(param);
        String accessToken = securityManager.getAuthenticationRealm(this.getSubjectType())
                .generateAccessToken(subjectId);

        LoginResponseModel model = new LoginResponseModel();
        model.setAccessToken(accessToken);
        model.setSubjectId(subjectId);
        return model;
    }

    /**
     * 登出
     * @param accessToken 访问token
     * @param subjectId 主体id
     */
    public void logout(String accessToken, String subjectId) {
        AuthenticationRealm authenticationRealm = securityManager.getAuthenticationRealm(this.getSubjectType());
        authenticationRealm.recycleAccessToken(subjectId, accessToken);
    }
}
