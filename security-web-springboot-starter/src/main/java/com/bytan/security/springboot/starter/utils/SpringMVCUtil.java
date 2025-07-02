package com.bytan.security.springboot.starter.utils;

import com.bytan.security.springboot.starter.exception.NotWebContextException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/26 16:06
 */
public class SpringMVCUtil {

    /**
     * 获取当前会话的 request
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null) {
            throw new NotWebContextException();
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前会话的 response
     * @return response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null) {
            throw new NotWebContextException();
        }
        return servletRequestAttributes.getResponse();
    }

    /**
     * 判断当前是否处于 Web 上下文中
     * @return request
     */
    public static boolean isWebContext() {
        return RequestContextHolder.getRequestAttributes() != null;
    }
}
