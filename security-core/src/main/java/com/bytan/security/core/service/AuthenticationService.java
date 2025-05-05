package com.bytan.security.core.service;

import com.bytan.security.core.AuthenticationRealm;
import com.bytan.security.core.service.model.LoginModel;
import com.bytan.security.core.provider.AuthenticationProvider;
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
     * 登录业务
     * @param param 请求携带的参数
     * @return LoginModel
     */
    public LoginModel login(Map<String, Object> param) {
        AuthenticationProvider authenticationProvider = securityManager.getAuthenticationProvider(this.getSubjectType());
        String subjectId = authenticationProvider.loadLoginSubjectId(param);
        AuthenticationRealm authenticationRealm = securityManager.getAuthenticationRealm(this.getSubjectType());
        String token = authenticationRealm.getAccessToken(subjectId);

        LoginModel loginModel = new LoginModel();
        loginModel.setAccessToken(token);
        loginModel.setSubjectId(subjectId);

        return loginModel;
    }

//    public void logout(String subjectType, String subjectId) {
//
//    }

}
