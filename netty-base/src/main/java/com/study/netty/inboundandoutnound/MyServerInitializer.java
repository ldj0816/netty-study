package com.study.netty.inboundandoutnound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 11:37
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入入栈的handler 进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyLongToByteEncoder());
        //加入自定义的handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
