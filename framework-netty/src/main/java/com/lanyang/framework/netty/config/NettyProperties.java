package com.lanyang.framework.netty.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lanyang
 * @date 2025/12/11
 * @des
 */
@ConfigurationProperties(prefix = "netty")
@Component
@Setter
@Getter
public class NettyProperties {

    /** 端口号 */
    public Integer port = 18050;

    /** boss线程 */
    public Integer bossCount = 1;

    /** worker线程 */
    public Integer workerCount = 50;

    /** 是否active */
    public boolean keepalive = true;

    /**  */
    public Integer backlog = 100;

    /** 心跳周期，实际为发帧间隔，单位为 秒 */
    public Integer heartbeat = 2;

    /** 定时器延时启动时间，单位为 秒 */
    public Integer delay = 35;

    /** 采集周期 单位为 分钟，与数据库中周期单位对应 */
    public Integer readPeriod = 15;

    /** JSON 格式数据的解码器 默认为true,默认报文为json格式*/
    public Boolean jsonDecoder = true;

    /**
     * json 数据包最大长度 默认5KB
     */
    public Integer maxObjectLength = 5120;

}
