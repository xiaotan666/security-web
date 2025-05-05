package com.bytan.security.core.exception;

import java.io.Serial;

/**
 * 身份鉴权异常
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/7/10 17:26
 */
public class AuthenticationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String USER_NOT_LOGIN = "用户未登录";
    public static final String NOT_PERMISSION = "没有权限";
    public static final String NOT_SCOPE = "用户未授权";

    private String message = "身份验证失败，请联系管理员";

    public AuthenticationException() {}

    public AuthenticationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
