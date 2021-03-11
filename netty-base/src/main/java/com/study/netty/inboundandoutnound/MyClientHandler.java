package com.study.netty.inboundandoutnound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 15:16
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(" MyClientHandler channelRead0 ");
        System.out.println("收到服务器消息= "+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        /**
         * abcdabcdabcdabcd 16字节
         * 2、该处理器的前一个handler 是 MyLongToByteEncoder
         * 3、MyLongToByteEncoder的父类是MessageToByteEncoder
         * 4、父类有acceptOutboundMessage 判断此数据是否该由他处理
         * 因此编写encode 需注意类传入类型和处理类型要一致
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd",CharsetUtil.UTF_8));
    }
}
