package com.bytan.security.springboot.exception;

import java.io.Serial;

/**
 * 非web上下文异常
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/26 16:09
 */
public class NotWebContextException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message = "当前环境非web上下文";

    public NotWebContextException() {}

    public NotWebContextException(String message) {
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
