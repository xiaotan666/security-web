package com.bytan.security.core.service.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录模型封装
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/6 18:01
 */
public class LoginModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主体id
     */
    private String subjectId;
    /**
     * 访问token
     */
    private String accessToken;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
