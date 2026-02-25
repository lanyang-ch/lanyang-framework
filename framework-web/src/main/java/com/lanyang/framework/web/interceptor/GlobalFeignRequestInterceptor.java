package com.lanyang.framework.web.interceptor;

import com.lanyang.framework.common.constant.FeignHeaderConstants;
import com.lanyang.framework.common.constant.SecurityConstants;
import com.lanyang.framework.web.util.SecurityUtils;
import com.lanyang.framework.web.util.ServletUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author lanyang
 * @date 2025/4/17
 * @des
 */
public class GlobalFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if(httpServletRequest == null){
            return;
        }

        Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
        //所有feign请求默认添加请求头 用户id
        if(!headers.containsKey(SecurityConstants.USER_ID)){
            headers.put(SecurityConstants.USER_ID, SecurityUtils.getUserId() == null? StringUtils.EMPTY : SecurityUtils.getUserId().toString());
        }

        //所有feign请求默认添加请求头 租户信息
        if(!headers.containsKey(SecurityConstants.TENANT_ID)){
            headers.put(SecurityConstants.TENANT_ID, SecurityUtils.getTenantId() == null? StringUtils.EMPTY : SecurityUtils.getTenantId().toString());
        }

        //所有feign请求默认添加请求头 拦截校验服务间通讯权限
        if(!headers.containsKey(FeignHeaderConstants.FROM_SOURCE)){
            headers.put(FeignHeaderConstants.FROM_SOURCE, FeignHeaderConstants.INNER);
        }

        //遍历请求头
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            requestTemplate.header(entry.getKey(), entry.getValue());
        }


    }
}
