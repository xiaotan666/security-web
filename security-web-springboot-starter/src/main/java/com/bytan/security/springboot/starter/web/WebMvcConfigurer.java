package com.bytan.security.springboot.starter.web;

import com.bytan.security.core.context.HttpContext;
import com.bytan.security.springboot.starter.EnabledSecurityWeb;
import com.bytan.security.springboot.starter.SecurityHttpContextAutoConfigure;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

/**
 * WebMvc相关配置
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/10 18:05
 */
@AutoConfigureAfter(value = SecurityHttpContextAutoConfigure.class)
@ConditionalOnBean(annotation = EnabledSecurityWeb.class)
@EnableWebMvc
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Resource
    private HttpContext httpContext;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HttpContextMethodArgumentResolver(httpContext));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ContextInterceptor());
    }

}
