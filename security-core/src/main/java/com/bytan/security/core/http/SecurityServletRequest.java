package com.bytan.security.core.http;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 默认基于Servlet请求实现
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/22  16:01
 */
public class SecurityServletRequest implements SecurityRequest {

    private final HttpServletRequest request;

    public SecurityServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getHeader(String headerName) {
        return request.getHeader(headerName);
    }

    @Override
    public String getParameter(String paramName) {
        return request.getParameter(paramName);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return request.getParameterMap();
    }


    @Override
    public String toString() {
        return "SecurityServletRequest{" +
                "request=" + request +
                '}';
    }
}
