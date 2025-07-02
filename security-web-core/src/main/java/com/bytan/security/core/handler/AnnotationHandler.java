package com.bytan.security.core.handler;

import java.lang.annotation.Annotation;

/**
 * 注解处理器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/2/27 16:58
 */
public abstract class AnnotationHandler<A extends Annotation> {

    /**
     * 获取注解class
     * @return Class<注解>
     */
    public abstract Class<A> getAnnotationClass();

    /**
     * 执行处理方法
     * @param annotation 注解对象
     */
    public final void doExecute(Annotation annotation) {
        A a = this.getAnnotationClass().cast(annotation);
        this.execute(a);
    }

    /**
     * 执行处理方法
     * @param annotation 注解对象
     */
    protected abstract void execute(A annotation);

}
