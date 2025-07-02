package com.bytan.security.springboot.interceptor;

import com.bytan.security.core.handler.AnnotationHandler;
import com.bytan.security.springboot.utils.AnnotationUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解拦截器
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/2  2:06
 */
public abstract class AnnotationInterceptor extends StaticMethodMatcherPointcutAdvisor implements MethodInterceptor {

    private final Map<Class<? extends Annotation>, AnnotationHandler<? extends Annotation>> annotationHandlerMap = new ConcurrentHashMap<>();

    /**
     * 默认注解处理器注册
     */
    protected abstract void registerDefaultAnnotationHandler();

    /**
     * 添加注解处理器
     * @param annotationHandler 注解处理器
     */
    public void addAnnotationHandler(AnnotationHandler<? extends Annotation> annotationHandler) {
        this.annotationHandlerMap.put(annotationHandler.getAnnotationClass(), annotationHandler);
    }

    /**
     * 注册拦截器
     */
    protected void registerInterceptor() {
        this.registerDefaultAnnotationHandler();
        setAdvice(this);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        // 处理代理类的方法
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        for (Class<? extends Annotation> annotationClass : annotationHandlerMap.keySet()) {
            Annotation classAnnotation = AnnotationUtils.findAnnotation(targetClass, annotationClass);
            Annotation methodAnnotation = AnnotationUtils.findAnnotation(specificMethod, annotationClass);
            if (classAnnotation != null || methodAnnotation != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        for (Map.Entry<Class<? extends Annotation>, AnnotationHandler<? extends Annotation>> entry : this.annotationHandlerMap.entrySet()) {
            var authenticationAnnotation = AnnotationUtils.getLevelAnnotation(method, entry.getKey());
            if (authenticationAnnotation != null) {
                entry.getValue().doExecute(authenticationAnnotation);
            }
        }

        return methodInvocation.proceed();
    }

}
