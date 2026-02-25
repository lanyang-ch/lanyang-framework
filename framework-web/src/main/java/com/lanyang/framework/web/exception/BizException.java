package com.lanyang.framework.web.exception;

/**
 * @author lanyang
 * @date 2025/3/27
 * @des
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    /**
     * 异常code
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public BizException(String message) {
        this.code = 500;
        this.message = message;
    }

    public BizException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
