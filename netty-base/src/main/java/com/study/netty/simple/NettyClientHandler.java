package com.study.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * @author: 吕东杰
 * @description: 我们自定义一个handler 需要继续netty 规定好的某个handlerAdapter
 * @create: 2021-02-25 14:45
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪时，触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server ctx = "+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server ", CharsetUtil.UTF_8));
    }

    /**
     * 通道读取时，会触发
     * @param ctx 上下文对象，含有管道pipeline, 通道
     * @param msg 客户端发送的数据 默认是object的形式
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = "+ctx);System.out.println("server ctx = "+ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息是："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址："+ctx.channel().remoteAddress());
    }


    /**
     * 需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
