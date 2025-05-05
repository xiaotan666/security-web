package com.bytan.security.core.subject;

import com.bytan.security.core.context.ThreadContext;

/**
 * 主体上下文
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/10/14 15:54
 */
public class SubjectContext {

    private static final String SubjectId_Key = "SUBJECT_CONTEXT_HOLDER";

    private SubjectContext() {}

    /**
     * 获取主体Id
     * @return 主体Id
     */
    public static String getSubjectId() {
        return ThreadContext.get(SubjectId_Key);
    }

    /**
     * 设置主体ID
     * @param subjectId 主体Id
     */
    public static void setSubjectId(String subjectId) {
        ThreadContext.set(SubjectId_Key, subjectId);
    }

}
