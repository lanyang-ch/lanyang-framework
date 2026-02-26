package com.lanyang.framework.pay.util;

import com.lanyang.framework.pay.exception.PayException;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author lanyang
 * @date 2025/12/15
 * @des
 */
public class BigDecimalConvertUtils {

    private static final BigDecimal hundred = new BigDecimal("100");

    /**
     * 乘以100后不保留小数
     * 参数仅两位小数有效，第三位及以后直接舍弃
     * 如入参3.145 返回结果为314
     * @param number
     * @return
     */
    public static Integer convertYuan2Cent(BigDecimal number){

        if(Objects.isNull(number)){
            throw new PayException("数字不允许为空");
        }

        BigDecimal fen = number.multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_DOWN);
        return Integer.parseInt(fen.toString());
    }

    public static BigDecimal convertCent2Yuan(String cent){
        if(Objects.isNull(cent)){
            throw new PayException("金额格式不正确");
        }

        try{
            return new BigDecimal(cent).divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
        }catch (Exception e){
            throw new PayException("金额格式不正确");
        }
    }
}
