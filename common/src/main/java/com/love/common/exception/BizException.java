package com.love.common.exception;

public class BizException extends RuntimeException {
    private String code = "-1";

    public BizException(String message) {
        super(message);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static BizException build(String message, Object... params) {
        if (params.length > 0) {
            return new BizException(String.format(message, params));
        }
        return new BizException(message);
    }

    public static BizException buildWithCode(String code, String message, Object... params) {
        if (params.length > 0) {
            return new BizException(code, String.format(message, params));
        }
        return new BizException(code, message);
    }

    public String getCode() {
        return code;
    }
}
