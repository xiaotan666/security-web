package com.bytan.security.springboot.starter.web;

import com.bytan.security.core.context.ThreadContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * 上下文拦截器
 */
public class ContextInterceptor implements AsyncHandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) {
        //释放上下文
        ThreadContext.clean();
    }
}
