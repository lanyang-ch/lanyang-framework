package com.lanyang.framework.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lanyang
 * @date 2025/5/8
 * @des 16进制转换工具类
 */
public class HexConvertUtils {

    private static final Integer MIN_LENGTH_HEX = 2;

    /**
     * 将字符串分组
     * @param message 目标字符串
     * @param groupNumber 每几个字符一组
     * @return
     */
    public static List<String> convertString2List(String message, Integer groupNumber){

        if(StringUtils.isBlank(message)){
            return Collections.emptyList();
        }

        if(groupNumber <= 0){
            return Collections.emptyList();
        }

        return Stream.of(message.replaceAll("[\\s\\n]", StringUtils.EMPTY)
                .replaceAll("(.{" + groupNumber + "})", ",$1")
                .substring(1)
                .split(","))
                .collect(Collectors.toList());
    }

    /**
     * 将字符串分组
     * @param message 目标字符串
     * @param groupNumber 每几个字符一组
     * @return
     */
    public static String[] convertString2Array(String message, Integer groupNumber){
        if(StringUtils.isBlank(message)){
            return new String[0];
        }

        if(groupNumber <= 0){
            return new String[0];
        }
        return message.replaceAll("[\\s\\n]", StringUtils.EMPTY)
                .replaceAll("(.{" + groupNumber + "})", ",$1")
                .substring(1)
                .split(",");
    }

    /**
     * 将16进制字符串转换成Ascii
     * @param hexString 目标字符串 长度至少为2
     * @return
     */
    @Nullable
    public static String convertHex2Ascii(String hexString){

        if(StringUtils.isBlank(hexString) || hexString.length() < MIN_LENGTH_HEX){
            return null;
        }

        List<String> grouped = convertString2List(hexString, 2);

        StringBuilder result = new StringBuilder();
        for (String hex : grouped) {

            if("00".equals(hex)){
                continue;
            }
            char ch = (char) Integer.parseInt(hex, 16);
            result.append(ch);
        }

        return result.toString();
    }

    /**
     * 字符串按照16进制转int
     * @param message
     * @return
     */
    public static int parseHexInt(String message){
        return Integer.parseInt(message, 16);
    }

    /**
     * 数字转16进制字符串，
     * @param target 目标数字
     * @param length 长度，如果转换后长度不足则左补0
     * @return
     */
    public static String convert2HexString(int target, int length){
        return StringUtils.leftPad(Integer.toHexString(target).toUpperCase(), length,"0");
    }

}
