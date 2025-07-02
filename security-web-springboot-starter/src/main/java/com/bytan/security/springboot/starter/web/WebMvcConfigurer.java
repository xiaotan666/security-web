package com.bytan.security.springboot.starter.web;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.context.HttpContext;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

/**
 * WebMvc相关配置
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/10 18:05
 */
@ConditionalOnBean(SecurityManager.class)
@EnableWebMvc
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Resource
    private HttpContext httpContext;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HttpContextMethodArgumentResolver(httpContext));
    }

}
