package com.bytan.security.web.demo.springboot;

import com.bytan.security.core.provider.AuthenticationProvider;
import com.bytan.security.core.subject.SubjectType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/6/30 16:27
 */
@Component
public class DemoProvider extends AuthenticationProvider {
    @Override
    public String loadLoginSubjectId(Map<String, Object> param) {
        return "12345";
    }

    @Override
    public String getSubjectType() {
        return SubjectType.ADMIN;
    }
}
