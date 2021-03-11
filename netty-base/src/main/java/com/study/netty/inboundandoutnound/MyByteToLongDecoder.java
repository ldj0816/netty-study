package com.study.netty.inboundandoutnound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 11:40
 **/
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode 会根据接收的数据，呗调用多次，直到没有新的元素被添加到list,或者是bytebuf 没有更多的可读字节为止
     * 若list out 不为空 就会将list的内容传给下一个 ChannelInboundHandler 处理，该处理器也会被调用多次
     * @param ctx 上下文对象
     * @param in 入栈的bytebuf
     * @param out list集合，将解码后的数据穿个下一个handler处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decode 被调用" );
        //long 8个字节 需要判断有8个字节才能读取一个long
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
