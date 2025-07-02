package com.bytan.security.core.provider;

import com.bytan.security.core.subject.SubjectType;

import java.util.Map;

/**
 * 身份验证时所需要提供的实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/7 14:23
 */
public abstract class AuthenticationProvider implements SubjectType {

    /**
     * 加载登录主体id
     * @param param 请求携带的参数
     * @return 主体id
     */
    public abstract String loadLoginSubjectId(Map<String, Object> param);

}
