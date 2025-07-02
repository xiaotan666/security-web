package com.bytan.security.oauth2.grant;

import com.bytan.security.core.subject.SubjectType;

import java.util.Map;


/**
 * Oauth2授权方式接口 （自定义授权方法需要实现该接口）
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 17:31
 */
public interface Oauth2Grant extends SubjectType {

    /**
     * 获取授权类型
     * @return GrantType授权类型
     */
    String getGrantType();

    /**
     * 授权方法
     * @param param 参数
     * @return 主体id
     */
    String doMain(Map<String, Object> param);
}
