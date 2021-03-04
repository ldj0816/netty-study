package com.study.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-01 18:53
 **/
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 定义一个channel 组，管理多有的channel
     * GlobalEventExecutor 全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 表示channel 处于活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    /**
     * 处于非活跃状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+ " 离线了");
    }

    /**
     * 表示断开连接了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端: "+ channel.remoteAddress()+ " 离开了");
        System.out.println("当前 channelGroup size： "+ channelGroup.size());
    }

    /**
     * 表示 一旦连接，第一个被执行
     * 将当前channel加入到 channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /**
         * 将该客户加入聊天的信息 推送给其他在线的客户端
         * 该方法会将 channelGroup 中所有的channel 遍历并发送消息
         */
        channelGroup.writeAndFlush("客户端: "+ channel.remoteAddress()+ "加入聊天");
        channelGroup.add(channel);
    }

    /**
     * 读取数据并转发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //根据不同的情况，回送不同的消息
        channelGroup.forEach(ch->{
            if (channel!=ch) {
                ch.writeAndFlush("[客户] " + channel.remoteAddress() + " 发送消息: " + msg + " \n");
            }else {
                ch.writeAndFlush("[自己] 发送了消息: " + msg + " \n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
