package com.bytan.security.demo.springboot;

import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.subject.SubjectType;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/6/30 16:27
 */
@Component
public class DemoDataLoader extends AuthenticationDataLoader {

    @Override
    public Set<String> doGetRolePermission(String role) {
        return Set.of("permissions_admin");
    }

    @Override
    public Set<String> doGetSubjectRole(String subjectId) {
        return Set.of("admin");
    }

    @Override
    public String getSubjectType() {
        return SubjectType.ADMIN;
    }
}
