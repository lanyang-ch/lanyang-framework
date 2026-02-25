package com.lanyang.framework.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lanyang
 * @date 2025/5/6
 * @des
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
@RequiredArgsConstructor
public class RedisService {
    public final RedisTemplate redisTemplate;

    public final StringRedisTemplate stringRedisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public <T> boolean setCacheObjectIfAbsent(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    public Long increment(final String key, final long delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection) {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 批量缓存地理位置信息
     *
     * @param key 缓存key
     * @param map key为member，value为地理位置
     * @return 添加数
     */
    public Long setGeoMap(String key, Map<String, Point> map) {
        return redisTemplate.boundGeoOps(key).add(map);
    }

    /**
     * 缓存单个地理位置信息
     *
     * @param key    缓存key
     * @param member 地理位置member
     * @param point  地理位置
     * @return 添加数
     */
    public Long setGeoMember(String key, String member, Point point) {
        return redisTemplate.boundGeoOps(key).add(point, member);
    }

    /**
     * 移除地理位置信息
     *
     * @param key     缓存key
     * @param members 需要移除的member
     * @return 删除数
     */
    public Long removeGeoMember(String key, String... members) {
        return redisTemplate.boundZSetOps(key).remove(members);
    }

    /**
     * 根据位置获取附近地理位置点
     * (切换redisson后此方法失效获取不到距离)
     *
     * @param key    Geo缓存key
     * @param lng    经度
     * @param lat    纬度
     * @param radius 半径/km
     * @return GeoResults<RedisGeoCommands.GeoLocation < String>>
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Long>> getGeoResultsByRadius(String key, Double lng, Double lat, Double radius) {
        Point point = new Point(lng, lat);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(point, distance);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeCoordinates()
                .includeDistance()
                .sortAscending();
        return redisTemplate.boundGeoOps(key).radius(circle, args);
    }

    /**
     * 根据位置获取附近地理位置点(基于lua脚本)
     *
     * @param key    Geo缓存key
     * @param lng    经度
     * @param lat    纬度
     * @param radius 半径/km
     * @return Object
     */
    public List<List<String>> getGeoResultsByRadiusLua(String key, Double lng, Double lat, Double radius) {
        String script = "return redis.call('GEORADIUS', KEYS[1], ARGV[1], ARGV[2], ARGV[3], 'km', 'WITHDIST');";
        List<String> keys = Collections.singletonList(key);

        List<String> args = Arrays.asList(
                String.format(Locale.ROOT, "%.6f", lng),
                String.format(Locale.ROOT, "%.6f", lat),
                String.format(Locale.ROOT, "%.1f", radius)
        );
        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(List.class);

        List result = stringRedisTemplate.execute(redisScript, keys, args.toArray());
        return result == null ? new ArrayList<>() : (List<List<String>>) result;
    }

    public <T> List<T> multiGet(String prefix){
        // 获取指定前缀的所有key
        Set<String> keys = redisTemplate.keys(prefix.concat("*"));
        // 批量获取value
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 尝试获取锁
     *
     * @param key        锁的键
     * @param value      锁的值（客户端ID或唯一标识符）
     * @param expireTime 锁的过期时间（秒）
     * @return 是否成功获取锁
     */
    public boolean tryLock(String key, String value, long expireTime) {
        return this.tryLock(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 尝试加锁
     *
     * @param key        锁key
     * @param value      锁value（唯一标识符）
     * @param expireTime 锁过期时间
     * @param timeUnit   锁过期时间单位
     * @return 是否成功获取锁
     */
    public boolean tryLock(String key, String value, long expireTime, TimeUnit timeUnit) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
        return result != null && result;
    }

    /**
     * 释放锁
     * lua校验锁value是否一致防止释放其他线程的锁
     *
     * @param key   锁key
     * @param value 锁value（唯一标识符）
     * @return 是否成功释放锁
     */
    public boolean unlock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), value);
        return result != null && result == 1;
    }

}
