package com.lanyang.framework.redis.config;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lanyang
 * @date 2025/5/6
 * @des
 */
@Component
public class RedisIdGeneratorWithLock {

    @Autowired
    private RedissonClient redisson;

    private static final Random RANDOM = new Random(12345);

    /**
     * 获取自增长的id
     *
     * @param redisKey 调用方自己定义的redis_key
     * @param lockKey  调用方自己定义的lock_key
     * @return
     */
    public long getNextId(String redisKey, String lockKey) {
        RLock lock = redisson.getLock(lockKey);
        try {
            // 尝试加锁，最多等待100毫秒锁
            boolean locked = lock.tryLock(100, 100, TimeUnit.MILLISECONDS);
            if (!locked) {
                // 获取锁失败，随机休眠一段时间再重试
                Thread.sleep(RANDOM.nextInt(100));
                return getNextId(redisKey, lockKey);
            }
            long id = redisson.getAtomicLong(redisKey).incrementAndGet();
            return id;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked()) { // 是否还是锁定状态
                if (lock.isHeldByCurrentThread()) { // 是否是当前执行线程的锁
                    lock.unlock(); // 释放锁
                } else {
                    throw new RuntimeException("非当前线程枷锁，锁释放失败!");
                }
            }
        }
    }
}
