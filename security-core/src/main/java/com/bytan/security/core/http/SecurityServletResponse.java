package com.bytan.security.core.http;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 默认基于Servlet响应实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/10 14:10
 */
public class SecurityServletResponse implements SecurityResponse {

    private final HttpServletResponse response;

    public SecurityServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SecurityServletResponse{" +
                "response=" + response +
                '}';
    }
}
