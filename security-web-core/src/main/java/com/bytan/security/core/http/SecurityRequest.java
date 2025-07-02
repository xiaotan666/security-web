package com.bytan.security.core.http;

import java.util.Map;

/**
 * 请求接口
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/20 15:09
 */
public interface SecurityRequest {

    /**
     * 获取请求头
     * @param headerName 请求头的name
     * @return 请求头参数值
     */
    String getHeader(String headerName);

    /**
     * 获取请求中参数值
     * @param paramName 请求的name
     * @return name对应的参数值
     */
    String getParameter(String paramName);

    /**
     * 获取请求中所有的参数值
     *
     * @return Map<String, String[]>
     */
    Map<String, String[]> getParameterMap();

}
