package com.rose.common.mp;


import lombok.Data;
/**
 * @author lihainuo * @description: 统一响应结果类
 */
@Data
public class Response<T> {
    // 状态信息
    private boolean success;
    // 响应消息
    private String message;
    // 状态码
    private Integer code;
    // 响应数据
    private T data;

    // 成功响应
    public static <T> Response<T> success() {
        return success(null);
    }

    public static <T> Response<T> success(T data) {
        Response<T> result = new Response<>();
        result.setSuccess(true);
        result.setCode(200);
        result.setData(data);
        return result;
    }

    // 失败响应
    public static <T> Response<T> fail() {
        Response<T> result = new Response<>();
        result.setSuccess(false);
        return result;
    }

    public static <T> Response<T> fail(String errorMessage) {
        Response<T> result = new Response<>();
        result.setSuccess(false);
        result.setMessage(errorMessage);
        return result;
    }

    public static <T> Response<T> fail(Integer errorCode, String errorMessage) {
        Response<T> result = new Response<>();
        result.setSuccess(false);
        result.setCode(errorCode);
        result.setMessage(errorMessage);
        return result;
    }

    // 需要实现全局异常管理
    public static <T> Response<T> fail(ResponseEnum responseEnum) {
        Response<T> result = new Response<>();
        result.setSuccess(false);
        result.setCode(responseEnum.getErrorCode());
        result.setMessage(responseEnum.getErrorMessage());
        return result;
    }
}