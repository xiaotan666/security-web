package com.bytan.security.core.context;

import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;

/**
 * Http信息传输上下文
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/10 11:54
 */
public interface HttpContext {

    /**
     * 获取当前请求
     * @return SecurityRequest
     */
    SecurityRequest getRequest();

    /**
     * 获取当前响应
     * @return SecurityResponse
     */
    SecurityResponse getResponse();
}
