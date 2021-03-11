package com.study.netty.inboundandoutnound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 15:12
 **/
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * 编码方法
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用");
        System.out.println("msg="+msg);
        out.writeLong( msg);
    }
}
