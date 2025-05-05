package com.bytan.security.springboot.starter.web;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.context.HttpContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * http上下文自动配置类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/13 15:50
 */
@ConditionalOnBean(SecurityManager.class)
@ConditionalOnMissingBean(HttpContext.class)
public class SecurityHttpContextAutoConfigure {

    @Bean
    public HttpContext httpContext() {
        return new DefaultHttpContext();
    }

}
