package com.bytan.security.springboot.web;

import com.bytan.security.core.context.HttpContext;
import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * HttpContext参数解析器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/10 17:59
 */
public class HttpContextMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpContext httpContext;

    public HttpContextMethodArgumentResolver(HttpContext httpContext) {
        this.httpContext = httpContext;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> paramType = parameter.getParameterType();
        if (paramType.equals(SecurityRequest.class)) {
            return httpContext.getRequest();
        } else if (paramType.equals(SecurityResponse.class)) {
            return httpContext.getResponse();
        }

        return null;
    }
}
