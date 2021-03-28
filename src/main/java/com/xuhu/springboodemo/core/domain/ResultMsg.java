package com.xuhu.springboodemo.core.domain;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class ResultMsg<T> implements Serializable {

    /**
     * 结果
     */
    private boolean result;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 请求唯一ID
     */
    private String requestId;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回数据
     */
    private T data;

    public ResultMsg() {
    }

    public ResultMsg(T object) {
        this.result = true;
        this.msg = "请求成功";
        this.data = object;
        this.code = 200;
    }

    public ResultMsg(boolean result, String msg) {
        this.result = result;
        this.msg = msg;
        this.data = null;
        this.code = 200;
    }

    public ResultMsg(String msg, T object) {
        this.result = true;
        this.msg = msg;
        this.data = object;
        this.code = 200;
    }

    public ResultMsg(boolean result, String msg, T object) {
        this.result = result;
        this.msg = msg;
        this.data = object;
        this.code = 200;
    }

    public ResultMsg(boolean result, Integer code, String msg, T object) {
        this.result = result;
        this.code = code;
        this.msg = msg;
        this.data = object;
    }

    public static <T> ResultMsg<T> error(String msg, T object) {
        return new ResultMsg<>(false, msg, object);
    }

    public static <T> ResultMsg<T> error(String msg) {
        return new ResultMsg<>(false, msg, null);
    }

    public static <T> ResultMsg<T> error(Integer code, String msg) {
        return new ResultMsg<>(false, code, msg, null);
    }

    public static <T> ResultMsg<T> ok(String msg, T object) {
        return new ResultMsg<>(true, 200, msg, object);
    }

    public static <T> ResultMsg<T> ok(String msg) {
        return new ResultMsg<>(true, 200, msg, null);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> ResultMsg<T> getSuccessReslut() {
        return new ResultMsg<>(Boolean.TRUE, "成功", null);
    }

    ;

    public static <T> ResultMsg<T> getSuccessReslut(T data) {
        return new ResultMsg<>(Boolean.TRUE, 200, "成功", data);
    }

    public static <T> ResultMsg<T> getErrorReslut(String message) {
        return new ResultMsg<>(Boolean.FALSE, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
