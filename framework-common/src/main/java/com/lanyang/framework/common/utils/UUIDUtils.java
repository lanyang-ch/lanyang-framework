package com.lanyang.framework.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author lanyang
 * @date 2025/4/16
 * @des
 */
public class UUIDUtils {

    /**
     * 获取随机UUID
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
    }
}
