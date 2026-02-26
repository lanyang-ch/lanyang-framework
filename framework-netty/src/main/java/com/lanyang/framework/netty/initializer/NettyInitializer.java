package com.lanyang.framework.netty.initializer;

import com.lanyang.framework.netty.config.NettyProperties;
import com.lanyang.framework.netty.handler.NettyChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/11
 * @des
 */
@Component
@RequiredArgsConstructor
public class NettyInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 线程池用于数据存取到数据库耗时业务处理
     */
    EventExecutorGroup execGroup = new DefaultEventExecutorGroup(100);

    private final NettyProperties nettyProperties;
    private final List<NettyChannelHandler> handlerList;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //pipeline.addLast(new LineBasedFrameDecoder(10240));
        // 使用JsonObjectDecoder来解码JSON对象
        if(nettyProperties.getJsonDecoder()){
            pipeline.addLast(new JsonObjectDecoder(nettyProperties.getMaxObjectLength(), true));
        }
        // 添加其他的ChannelHandler来处理解码后的数据
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        //pipeline.addLast(new ReadTimeoutHandler(ServerPeriod.TIMEOUT.getValue(), TimeUnit.SECONDS));

        if(CollectionUtils.isNotEmpty(handlerList)){
            handlerList.forEach(e->pipeline.addLast(execGroup, e));
        }
    }
}
