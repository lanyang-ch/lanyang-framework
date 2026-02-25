package com.lanyang.framework.web.exception;


/**
 * @author lanyang
 * @date 2025/6/16
 * @des
 */
public class AuthException extends BizException {

    public AuthException(Integer code, String message) {
        super(code, message);
    }
}
