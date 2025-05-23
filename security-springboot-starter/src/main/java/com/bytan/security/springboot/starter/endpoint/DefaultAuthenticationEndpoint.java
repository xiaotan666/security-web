package com.bytan.security.springboot.starter.endpoint;

import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 身份鉴权端点 默认实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/6 17:19
 */
@ConditionalOnProperty(
        prefix = "security.authentication",
        name = "endpoint",
        havingValue = "true"
)
@RestController
@RequestMapping("/authentication")
public class DefaultAuthenticationEndpoint extends AuthenticationEndpoint {

    @PostMapping("/login")
    @Override
    public Object login(SecurityRequest request, SecurityResponse response) {
        return super.login(request, response);
    }

    @Override
    public String getSubjectType() {
        return USER;
    }
}
