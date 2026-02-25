package com.lanyang.framework.web.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lanyang
 * @date 2025/8/8
 * @des
 */
public class NotPermissionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotPermissionException(String permission) {
        super(permission);
    }

    public NotPermissionException(String[] permissions) {
        super(StringUtils.join(permissions, ","));
    }
}
