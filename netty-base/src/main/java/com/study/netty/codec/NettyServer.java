package com.study.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author: 吕东杰
 * @description: //netty 实现聊天系统
 * @create: 2021-02-25 14:22
 **/
public class NettyServer {
    public static void main(String[] args) throws Exception {
        //都是无限循环
        //只是处理连接请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //真正的与客户端业务处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器端启动的对象 来配置启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置两个线程组
            bootstrap.group(bossGroup,workerGroup )
                    .channel(NioServerSocketChannel.class) //使用其作为通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程对列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //指定对哪种对象解码
                            pipeline.addLast("decoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            pipeline.addLast(new NettyServerhandler());
                        }
                    }); //给workerGroup 的 EventLoop对应的管道设置处理器
            System.out.println("server is ready");
            //启动服务器 并绑定一个端口并且同步处理
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
        }


    }
}
