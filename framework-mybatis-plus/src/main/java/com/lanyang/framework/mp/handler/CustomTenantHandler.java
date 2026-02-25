package com.lanyang.framework.mp.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.lanyang.framework.common.context.TransmittableThreadLocalContextHolder;
import com.lanyang.framework.mp.aspect.MybatisTenantContext;
import com.lanyang.framework.mp.config.TenantProperties;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import com.lanyang.framework.common.constant.SecurityConstants;

/**
 * @author lanyang
 * @date 2025/4/30
 * @des
 */
@RequiredArgsConstructor
public class CustomTenantHandler implements TenantLineHandler {

    private final TenantProperties properties;

    @Override
    public Expression getTenantId() {
        //自定义逻辑，业务中如何获取租户id
        //可以通过拦截器将租户信息放到 ThreadLocal
        return new LongValue(TransmittableThreadLocalContextHolder.get(SecurityConstants.TENANT_ID));
    }

    @Override
    public String getTenantIdColumn() {
        //租户在数据库表中的列名
        return properties.getColumn();
    }

    @Override
    public boolean ignoreTable(String tableName) {

        //忽略租户的判断逻辑
        if(MybatisTenantContext.get() == null){
            return properties.getIgnoreTables().contains(tableName);
        }

        return MybatisTenantContext.get();
    }
}
