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

    public static final String USER_NOT_LOGIN = "账号未登录";
    public static final String NOT_PERMISSION = "账号未授权";

    private String message = "身份信息已过期，请重新登录";

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
