package com.lanyang.framework.pay.exception;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public class PayException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public PayException() {
    }

    public PayException(String message) {
        this.message = message;
    }

    public PayException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}
