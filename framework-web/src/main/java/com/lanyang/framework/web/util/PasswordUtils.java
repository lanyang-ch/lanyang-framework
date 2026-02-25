package com.lanyang.framework.web.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lanyang
 * @date 2025/6/20
 * @des
 */
public class PasswordUtils {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordUtils() {}

    /**
     * 加密密码
     * @param rawPassword 明文密码
     * @return
     */
    public static String encryptPassword(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }


    /**
     *
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后密码
     * @return
     */
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }

}
