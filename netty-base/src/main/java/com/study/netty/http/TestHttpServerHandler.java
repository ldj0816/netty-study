package com.study.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author: 吕东杰
 * @description:
 * 1、是ChannelInboundHandlerAdapter的子类
 * 2、HttpObject 客户端和服务器端相互通讯的数据被封装而成
 * @create: 2021-02-26 15:35
 **/
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断msg 而不是 httprequest
        if(msg instanceof HttpRequest){
            System.out.println("msg 类型 = "+msg.getClass());
            System.out.println("客户端 地址 = "+ctx.channel().remoteAddress());

            //回复消息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello i am  server", CharsetUtil.UTF_8);

            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //response 返回
            ctx.writeAndFlush(response);

        }
    }
}
