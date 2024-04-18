package com.love.common.result;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final String SUCCESS = "0";
    private static final String FAILURE = "-1";
    /**
     * 编码
     */
    private String code = SUCCESS;
    /**
     * 信息
     */
    private String msg = "success";
    /**
     * 数据
     */
    private T data;

    private Result() {

    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data, String message) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMsg(message);
        return result;
    }

    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setMsg(message);
        result.setCode(FAILURE);
        return result;
    }

    public static <T> Result<T> fail(String code, String message) {
        Result<T> result = new Result<>();
        result.setMsg(message);
        result.setCode(code);
        return result;
    }

    public static <T> Result<T> fail(String code, String message, T data) {
        Result<T> result = new Result<>();
        result.setMsg(message);
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
