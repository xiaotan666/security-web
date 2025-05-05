package com.bytan.security.springboot.starter.oauth2.endpoint;

import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * oauth2端点处理默认实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/11 10:42
 */
@ConditionalOnProperty(
        prefix = "security.oauth2",
        name = "endpoint",
        havingValue = "true"
)
@RestController
@RequestMapping("/oauth2")
public class DefaultOAuth2Endpoint extends OAuth2Endpoint {

    @Override
    public String getSubjectType() {
        return USER;
    }

    @Override
    public Object authorization(SecurityRequest securityRequest, SecurityResponse securityResponse) {
        return super.authorization(securityRequest, securityResponse);
    }
}
