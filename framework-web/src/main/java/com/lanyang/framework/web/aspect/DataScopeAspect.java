package com.lanyang.framework.web.aspect;

import com.lanyang.framework.web.annotation.DataScope;
import com.lanyang.framework.web.domain.BaseQueryEntity;
import com.lanyang.framework.web.domain.DataAuth;
import com.lanyang.framework.web.domain.LoginUser;
import com.lanyang.framework.web.enums.JdbcTypeEnum;
import com.lanyang.framework.web.exception.AuthException;
import com.lanyang.framework.web.util.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lanyang
 * @date 2025/6/12
 * @des 数据权限处理
 */
@Aspect
@Component
public class DataScopeAspect {

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Pointcut("@annotation(com.lanyang.framework.web.annotation.DataScope) " +
            "|| @within(com.lanyang.framework.web.annotation.DataScope) " )
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint point){

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        DataScope dataScope = method.getAnnotation(DataScope.class);
        clearDataScope(point);
        handleDataScope(point, dataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScope dataScope) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();

        if(loginUser == null){
            throw new AuthException(401, "未登录");
        }
        if (loginUser != null) {
            List<DataAuth> dataAuthList = loginUser.getDataAuthList();
            dataScopeFilter(joinPoint, dataScope, dataAuthList);

        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint  切点
     * @param dataAuthList 用户权限
     */
    public static void dataScopeFilter(JoinPoint joinPoint, DataScope dataScope, List<DataAuth> dataAuthList) {
        StringBuilder sqlString = new StringBuilder();

        if(StringUtils.isBlank(dataScope.dimension())){
            // 未指定数据权限维度
            sqlString.append(" AND 1=0");
            addDataScope(joinPoint, sqlString.toString());
            return;
        }

        List<DataAuth> targetDataAuthDimension = dataAuthList.stream()
                .filter(dataAuth -> StringUtils.equals(dataAuth.getDimension(), dataScope.dimension()))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(targetDataAuthDimension) || CollectionUtils.isEmpty(targetDataAuthDimension.get(0).getAuthCodes())){
            //用户不拥有指定维度的数据权限
            sqlString.append(" AND 1=0");
            addDataScope(joinPoint, sqlString.toString());
            return;
        }

        if(JdbcTypeEnum.STRING.equals(dataScope.columnJdbcType())){
            sqlString.append(" AND ")
                    .append(dataScope.columnName())
                    .append(" IN (")
                    .append(targetDataAuthDimension.get(0).getAuthCodes().stream()
                            .map(e -> "'" + e + "'")
                            .collect(Collectors.joining(",")))
                    .append(" )");
        }else if(JdbcTypeEnum.NUMBER.equals(dataScope.columnJdbcType())){
            //数字类型的列，不加单引号，防止隐性类型转型，影响查询效率
            sqlString.append(" AND ")
                    .append(dataScope.columnName())
                    .append(" IN (")
                    .append(targetDataAuthDimension.get(0).getAuthCodes().stream()
                            .collect(Collectors.joining(",")))
                    .append(" )");
        }


        addDataScope(joinPoint, sqlString.toString());
    }

    private static void addDataScope(final JoinPoint joinPoint, String dataScopeParam) {

        if(StringUtils.isBlank(dataScopeParam)){
            return;
        }
        Object params = joinPoint.getArgs()[0];
        if (params != null && params instanceof BaseQueryEntity) {
            BaseQueryEntity baseEntity = (BaseQueryEntity) params;
            baseEntity.getParams().put(DATA_SCOPE, dataScopeParam);
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (params != null && params instanceof BaseQueryEntity) {
            BaseQueryEntity baseEntity = (BaseQueryEntity) params;
            baseEntity.getParams().put(DATA_SCOPE, StringUtils.EMPTY);
        }
    }

}
