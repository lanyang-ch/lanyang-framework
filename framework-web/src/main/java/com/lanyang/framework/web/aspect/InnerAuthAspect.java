package com.lanyang.framework.web.aspect;

import com.lanyang.framework.web.annotation.InnerAuth;
import com.lanyang.framework.web.exception.InnerAuthException;
import com.lanyang.framework.common.constant.FeignHeaderConstants;
import com.lanyang.framework.common.constant.SecurityConstants;
import com.lanyang.framework.web.util.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author lanyang
 * @date 2025/6/24
 * @des
 */
@Aspect
@Component
public class InnerAuthAspect implements Ordered {

    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable {
        String source = ServletUtils.getRequest().getHeader(FeignHeaderConstants.FROM_SOURCE);
        // 内部请求验证
        if (!StringUtils.equals(FeignHeaderConstants.INNER, source)) {
            throw new InnerAuthException(401, "没有内部访问权限，不允许访问");
        }

        String userId = ServletUtils.getRequest().getHeader(SecurityConstants.USER_ID);

        // 用户信息验证
        if (innerAuth.isUser() && StringUtils.isEmpty(userId)) {
            throw new InnerAuthException(401, "没有设置用户信息，不允许访问 ");
        }
        return point.proceed();
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
