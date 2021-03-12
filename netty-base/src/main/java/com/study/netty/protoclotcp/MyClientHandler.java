package com.study.netty.protoclotcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-11 18:07
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        int len = msg.getLen();
        byte[] content = msg.getCcontent();
        System.out.println("客户端接收到数据如下：");
        System.out.println("长度："+ len);
        System.out.println("内容："+ new String(content,CharsetUtil.UTF_8));
        System.out.println("客户接收到数量="+ (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送十条数据 hello server 
        for (int i = 0; i < 5; i++) {
            String message = "今天天气冷，吃火锅";
            byte[] content = message.getBytes(CharsetUtil.UTF_8);
            int length = message.getBytes(CharsetUtil.UTF_8).length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setCcontent(content);
            messageProtocol.setLen(length);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
