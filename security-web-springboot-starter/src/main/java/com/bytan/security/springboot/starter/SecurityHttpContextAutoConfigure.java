package com.bytan.security.springboot.starter;

import com.bytan.security.core.context.HttpContext;
import com.bytan.security.springboot.starter.web.DefaultHttpContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * http上下文自动配置类
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/13 15:50
 */
@ConditionalOnBean(annotation = EnabledSecurityWeb.class)
public class SecurityHttpContextAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(value = HttpContext.class)
    public HttpContext httpContext() {
        return new DefaultHttpContext();
    }

}
