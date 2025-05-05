package com.bytan.security.core.exception;

import java.io.Serial;

/**
 * 未启用异常
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/30 15:03
 */
public class NoEnableException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String OAUTH2_NO_ENABLE = "未启用Oauth2";

    private String message = "No Enable...";

    public NoEnableException() {}

    public NoEnableException(String message) {
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
