package com.lanyang.framework.netty.runner;

import com.lanyang.framework.netty.initializer.NettyServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author lanyang
 * @date 2025/12/11
 * @des
 */
@Component
@RequiredArgsConstructor
public class NettyServerRunner implements ApplicationRunner {

    private final NettyServer nettyServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyServer.start();
    }
}
