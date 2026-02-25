package com.lanyang.framework.mp.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lanyang.framework.common.constant.SecurityConstants;
import com.lanyang.framework.common.context.TransmittableThreadLocalContextHolder;
import com.lanyang.framework.mp.config.TenantProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author lanyang
 * @date 2025/5/6
 * @des
 */
@Component
@RequiredArgsConstructor
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final TenantProperties properties;

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createBy = this.getFieldValByName(properties.getCreateUserColumn(), metaObject);

        if (!properties.getDefaultCreateUpdateUser().equals(createBy)
                && StringUtils.isNotBlank(TransmittableThreadLocalContextHolder.get(SecurityConstants.USER_ID))) {
            setFieldValByName(properties.getCreateUserColumn(), Long.parseLong(TransmittableThreadLocalContextHolder.get(SecurityConstants.USER_ID)), metaObject);
        }
        setFieldValByName(properties.getCreateTimeColumn(), DateUtil.date(), metaObject);
        setFieldValByName(properties.getUpdateTimeColumn(), DateUtil.date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateBy = this.getFieldValByName(properties.getUpdateUserColumn(), metaObject);
        if (!properties.getDefaultCreateUpdateUser().equals(updateBy)
                && StringUtils.isNotBlank(TransmittableThreadLocalContextHolder.get(SecurityConstants.USER_ID))) {
            setFieldValByName(properties.getUpdateUserColumn(), Long.parseLong(TransmittableThreadLocalContextHolder.get(SecurityConstants.USER_ID)), metaObject);
        }
        setFieldValByName(properties.getUpdateTimeColumn(), DateUtil.date(), metaObject);
    }
}
