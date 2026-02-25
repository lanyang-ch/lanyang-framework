package com.lanyang.framework.web.exception;

/**
 * @author lanyang
 * @date 2025/6/24
 * @des
 */
public class InnerAuthException extends BizException {
    private static final long serialVersionUID = 1L;

    public InnerAuthException(Integer code,String message) {
        super(code, message);
    }
}
