package com.study.netty.protoclotcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-12 09:58
 **/
public class MyMessageDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        //需要将的道德的二进制字节码转成messageProtoccol
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        //封装成 messageProtocol 放入out 传给下一个handler
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setCcontent(content);
        messageProtocol.setLen(len);
        out.add(messageProtocol);
    }
}
