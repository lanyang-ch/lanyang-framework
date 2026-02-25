package com.lanyang.framework.web.annotation;


import com.lanyang.framework.web.enums.JdbcTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lanyang
 * @date 2025/6/12
 * @des 数据权限切点标记
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 数据权限维度
     * @return
     */
    String dimension() default "";

    /**
     * 数据库表列名
     * @return
     */
    String columnName() default "";

    /**
     * 数据库表列的数据类型
     * @return
     */
    JdbcTypeEnum columnJdbcType() default JdbcTypeEnum.STRING;

}
