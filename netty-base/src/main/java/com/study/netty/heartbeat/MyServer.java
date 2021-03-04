package com.study.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-02 14:53
 **/
public class MyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //在bossGroup 增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * 加入一个netty提供的 IdlStateHandler
                             * IdlStateHandler 提供的处理空闲状态的处理器
                             * long readerIdleTime：表示多长时间没有读了,就会发送心跳检测包，检测是否连接的状态
                             * long writerIdleTime: 表示多长时间没有写操作了，就会发送心跳检测包，检测是否连接的状态
                             * long allIdleTime： 表示多长时间没有读写操作了，就会发送心跳检测包，检测是否连接的状态
                             * 说明：Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed read,
                             *      write, or both operation for a while.
                             * 当IdlStateHandler 触发后， 就会传递给管道的下一个 handler去处理
                             * 通过调用（触发） 下一个handler的 userEventTiggered,在该方法中去处理 IdlStateHandler（读空闲， 写空闲， 读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(13,5,7, TimeUnit.SECONDS));
                            //加入一个队空闲检测 进一步处理的 handler(自定义)
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(6688).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
