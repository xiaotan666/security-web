package com.bytan.security.springboot.starter.endpoint;

import com.bytan.security.core.config.AccessTokenConfig;
import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import com.bytan.security.core.service.AuthenticationService;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.subject.SubjectContext;
import com.bytan.security.core.subject.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 身份鉴权端点服务
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/10  2:25
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationEndpoint implements SubjectType {

    private final SecurityManager securityManager;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationEndpoint(SecurityManager securityManager) {
        this.authenticationService = new AuthenticationService(securityManager, getSubjectType());
        this.securityManager = securityManager;
    }

    /**
     * 登录用户
     * @param request 当前请求
     * @param response 响应
     * @return Object
     */
    @GetMapping("/login")
    public Object login(SecurityRequest request, SecurityResponse response) {
        Map<String, Object> parameterMap = request.getParameterMap()
                .entrySet()
                .stream()
                .flatMap(entry -> Arrays.stream(entry.getValue()).map(value -> new AbstractMap.SimpleEntry<>(entry.getKey(), value)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> newVal));

        return authenticationService.login(parameterMap);
    }

    /**
     * 用户登出
     * @param request 当前请求
     * @param response 响应
     * @return Object
     */
    @GetMapping("/logout")
    public Object logout(SecurityRequest request, SecurityResponse response) {
        AccessTokenConfig tokenConfig = securityManager.getAccessTokenConfig(getSubjectType());
        String accessToken = request.getHeader(tokenConfig.getRequestHeader());
        authenticationService.logout(accessToken, SubjectContext.getSubjectId());

        return "success";
    }

    @Override
    public String getSubjectType() {
        return SubjectType.USER;
    }
}