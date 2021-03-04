package com.study.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-02-26 15:36
 **/
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个netty提供的httpserverCodec codec =>[code - decode]
        pipeline.addLast("my httpServerCodec", new HttpServerCodec());
        pipeline.addLast("my TestHttpServerHandler", new TestHttpServerHandler());
    }


}
