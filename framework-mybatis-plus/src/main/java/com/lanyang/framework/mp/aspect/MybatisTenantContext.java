package com.lanyang.framework.mp.aspect;

/**
 * @author lanyang
 * @date 2025/5/6
 * @des
 */
public class MybatisTenantContext {

    private static final ThreadLocal<Boolean> TENANT_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static Boolean get() {
        return TENANT_CONTEXT_THREAD_LOCAL.get();
    }

    public static void set(boolean ignore) {
        TENANT_CONTEXT_THREAD_LOCAL.set(ignore);
    }

    public static void clear() {
        TENANT_CONTEXT_THREAD_LOCAL.remove();
    }
}
