package com.study.netty.inboundandoutnound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 15:10
 **/
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入出站的handler 对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());
        //入栈解码器
        pipeline.addLast(new MyByteToLongDecoder());
        //加入自定义handler 处理业务
        pipeline.addLast(new MyClientHandler());
    }
}
