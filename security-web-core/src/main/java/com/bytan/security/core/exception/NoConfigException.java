package com.bytan.security.core.exception;

import java.io.Serial;

/**
 * 未配置异常
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/30 15:03
 */
public class NoConfigException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String GrantType_No_Config = "未配置grantType类型";
    public static final String ResponseType_No_Config = "未配置responseType类型";

    private String message = "No Config...";

    public NoConfigException() {}

    public NoConfigException(String message) {
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
