package com.lanyang.framework.common.domain;

import lombok.Data;

/**
 * @author lanyang
 * @date 2025/8/8
 * @des
 */
@Data
public class LoginResult {

    /** TOKEN */
    private String accessToken;

    /** 过期时间 */
    private Long expiresIn;

    private String phoneNumber;
}
