package com.bytan.security.core.data.loader;

import java.util.HashSet;
import java.util.Set;

/**
 * 身份数据加载器默认实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/16 18:07
 */
public class DefaultAuthenticationDataLoader extends AuthenticationDataLoader {

    @Override
    public Set<String> doGetRolePermission(String role) {
        return new HashSet<>();
    }

    @Override
    public Set<String> doGetSubjectRole(String subjectId) {
        return new HashSet<>();
    }

    @Override
    public String getSubjectType() {
        return USER;
    }
}
