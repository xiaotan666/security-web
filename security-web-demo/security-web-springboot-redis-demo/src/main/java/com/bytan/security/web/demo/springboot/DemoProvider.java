package com.bytan.security.web.demo.springboot;

import com.bytan.security.core.provider.AuthenticationProvider;
import com.bytan.security.core.subject.SubjectType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义身份提供器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/6/30 16:27
 */
@Component
public class DemoProvider extends AuthenticationProvider {
    @Override
    public String loadLoginSubjectId(Map<String, Object> param) {
        //加载当前登录的主体id
        return "12345";
    }

    @Override
    public String getSubjectType() {
        //自定义主体类型
        return SubjectType.ADMIN;
    }
}
