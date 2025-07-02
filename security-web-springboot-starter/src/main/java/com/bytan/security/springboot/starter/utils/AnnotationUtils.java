package com.bytan.security.springboot.starter.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 注解工具类
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2025/3/2  1:51
 */
public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils{

    /**
     * 获取目标方法多层里的注解
     * @param targetMethod 目标方法
     * @param annotationClass 注解class
     * @return 目标方法中（包含多层）的注解
     */
    public static Annotation getLevelAnnotation(Method targetMethod, Class<? extends Annotation> annotationClass) {
        //方法上的注解
        var annotation = targetMethod.getAnnotation(annotationClass);
        //当前类上的注解
        if (annotation == null) {
            annotation = targetMethod.getDeclaringClass().getAnnotation(annotationClass);
        }
        //父类上的注解
        if (annotation == null) {
            annotation = targetMethod.getDeclaringClass().getSuperclass().getAnnotation(annotationClass);
        }

        return annotation;
    }

}
