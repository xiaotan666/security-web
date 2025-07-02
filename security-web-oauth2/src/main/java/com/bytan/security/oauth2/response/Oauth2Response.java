package com.bytan.security.oauth2.response;

import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.subject.SubjectType;

/**
 * 响应类型接口 （自定义响应类型需实现该接口）
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 17:44
 */
public interface Oauth2Response extends SubjectType {

    /**
     * 获取响应类型
     * @return ResponseType响应类型
     */
    String getResponseType();

    /**
     * 响应方法
     * @param request 当前请求线程
     * @return 主体id
     */
    void doMain(SecurityRequest request);

}
