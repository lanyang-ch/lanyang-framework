package com.lanyang.framework.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/11
 * @des
 */
public class SocketDecoder extends StringDecoder {

    /**
     * 编码插件，接受到的数据在此处进行过滤，做拆包粘包处理等操作
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        super.decode(ctx, in, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("decode exception");
    }
}
