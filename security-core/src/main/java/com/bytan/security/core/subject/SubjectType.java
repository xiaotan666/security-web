package com.bytan.security.core.subject;

/**
 * 主体类型
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/24  22:36
 */
public interface SubjectType {

    /**
     * 普通用户
     */
    String USER = "user";
    /**
     * 管理员
     */
    String ADMIN = "admin";

    /**
     * 主体类型
     */
    String getSubjectType();
}
