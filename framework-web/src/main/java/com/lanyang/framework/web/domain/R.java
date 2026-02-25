package com.lanyang.framework.web.domain;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author lanyang
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 失败
     */
    public static final int FAIL = 500;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(SUCCESS,null, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(SUCCESS, data,null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(SUCCESS, data, msg);
    }

    public static <T> R<T> fail() {
        return restResult(FAIL, null, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(FAIL,null, msg);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(FAIL, data, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(code,null,  msg);
    }

    private static <T> R<T> restResult(int code, T data,  String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS == ret.getCode();
    }
}
