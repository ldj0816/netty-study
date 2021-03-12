package com.study.netty.protoclotcp;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 18:11
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count =0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getCcontent();
        System.out.println("服务器端接收到数据如下：");
        System.out.println("长度："+ len);
        System.out.println("内容："+ new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器端接收到数量="+ (++this.count));
        //回复消息
        String resContent = UUID.randomUUID().toString();
        int resLen = resContent.getBytes(CharsetUtil.UTF_8).length;
        //构建一个下一包
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(resLen);
        messageProtocol.setCcontent(resContent.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
