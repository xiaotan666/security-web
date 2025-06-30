package com.bytan.security.demo.springboot;

import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.subject.SubjectType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/6/30 16:27
 */
@Component
public class DemoDataLoader extends AuthenticationDataLoader {
    @Override
    public List<String> doGetRolePermission(String role) {
        return List.of();
    }

    @Override
    public List<String> doGetSubjectRole(String subjectId) {
        return Collections.singletonList("admin");
    }

    @Override
    public String getSubjectType() {
        return SubjectType.ADMIN;
    }
}
