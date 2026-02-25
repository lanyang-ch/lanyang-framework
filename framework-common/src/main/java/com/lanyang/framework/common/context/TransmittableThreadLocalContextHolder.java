package com.lanyang.framework.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lanyang
 * @date 2024/9/5
 * @des 当线程在执行过程中切换到另一个线程时（例如，使用线程池），
 * 通过 TransmittableThreadLocal 可以保留原始线程的变量值，并在切换后恢复这些值，使得新线程能够继续使用原始线程的变量
 */
public class TransmittableThreadLocalContextHolder {

    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    private static Map<String, Object> get(){
        Map<String, Object> map = THREAD_LOCAL.get();
        if(map == null){
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void set(String key, Object value){
        Map<String, Object> map = get();
        map.put(key, Objects.isNull(value) ? StringUtils.EMPTY : value);
    }

    public static String get(String key){
        Map<String, Object> map = get();
        Object value = map.get(key);
        return Objects.isNull(value) ? StringUtils.EMPTY : value.toString();
    }

    public static <T> T getObject(String key){
        Map<String, Object> map = get();
        Object value = map.get(key);
        return (T) value;
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }


}
