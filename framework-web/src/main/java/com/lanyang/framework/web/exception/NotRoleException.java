package com.lanyang.framework.web.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lanyang
 * @date 2025/8/8
 * @des
 */
public class NotRoleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotRoleException(String role) {
        super(role);
    }

    public NotRoleException(String[] roles) {
        super(StringUtils.join(roles, ","));
    }
}
