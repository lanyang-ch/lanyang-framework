package com.lanyang.framework.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/11
 * @des
 */
public class SocketEncoder extends StringEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        super.encode(ctx, msg, out);
    }
}
