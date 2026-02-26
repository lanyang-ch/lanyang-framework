package com.lanyang.framework.netty.initializer;

import com.lanyang.framework.netty.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author lanyang
 * @date 2025/12/11
 * @des
 */
@Component
@RequiredArgsConstructor
public class NettyServer {

//    @Autowired
//    @Qualifier("serverBootstrap")
    private final ServerBootstrap serverBootstrap;
    private final NettyProperties nettyProperties;

    private Channel serverChannel;

    public void start() throws Exception {
        serverChannel = serverBootstrap.bind(nettyProperties.getPort()).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() throws Exception {
        serverChannel.close();
        serverChannel.parent().close();
    }
}
