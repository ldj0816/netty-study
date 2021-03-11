package com.study.netty.inboundandoutnound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 15:03
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从客户端 "+ctx.channel().remoteAddress()+" 读取到long："+msg);
        //给客户端发送 long
        ctx.writeAndFlush(456456L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
