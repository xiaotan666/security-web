package com.bytan.security.core.data.loader;

import java.util.List;

/**
 * 身份数据加载器默认实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/16 18:07
 */
public class DefaultAuthenticationDataLoader extends AuthenticationDataLoader {

    @Override
    public List<String> doGetRolePermission(String role) {
        return List.of();
    }

    @Override
    public List<String> doGetSubjectRole(String subjectId) {
        return List.of();
    }

    @Override
    public String getSubjectType() {
        return USER;
    }
}
