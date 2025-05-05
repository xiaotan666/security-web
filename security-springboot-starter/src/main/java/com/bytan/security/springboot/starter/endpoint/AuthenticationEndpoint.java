package com.bytan.security.springboot.starter.endpoint;

import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import com.bytan.security.core.service.AuthenticationService;
import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.subject.SubjectType;
import jakarta.annotation.Resource;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 身份鉴权端点处理
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/10  2:25
 */
public abstract class AuthenticationEndpoint implements SubjectType {

    @Resource
    protected SecurityManager securityManager;
    private AuthenticationService authenticationService;

    /**
     * 获取身份鉴权业务 懒加载实现，直接拿securityManager变量会报null
     * @return AuthenticationService
     */
    protected AuthenticationService getAuthenticationService() {
        if (authenticationService != null) {
            return authenticationService;
        }
        if (securityManager != null) {
            return authenticationService = new AuthenticationService(securityManager, this.getSubjectType());
        }

        throw new NullPointerException("核心管理器尚未加载...");
    }

    /**
     * 登录
     * @param request 当前请求
     * @param response 响应
     * @return Object
     */
    public Object login(SecurityRequest request, SecurityResponse response) {
        Map<String, Object> parameterMap = request.getParameterMap()
                .entrySet()
                .stream()
                .flatMap(entry -> Arrays.stream(entry.getValue()).map(value -> new AbstractMap.SimpleEntry<>(entry.getKey(), value)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> newVal));

        return getAuthenticationService().login(parameterMap);
    }

}