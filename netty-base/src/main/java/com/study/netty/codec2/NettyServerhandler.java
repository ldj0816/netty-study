package com.study.netty.codec2;

import com.study.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * @author: 吕东杰
 * @description: 我们自定义一个handler 需要继续netty 规定好的某个handlerAdapter
 * @create: 2021-02-25 14:45
 **/
public class NettyServerhandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx 上下文对象，含有管道pipeline, 通道
     * @param msg 客户端发送的数据 默认是object的形式
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = "+ctx);
        //读取从客户端发送的 StudentPOJO.Student
        MyDataInfo.MyMessage myMessage = (MyDataInfo.MyMessage) msg;
        MyDataInfo.MyMessage.DataType dataType = myMessage.getDataType();
        if (dataType== MyDataInfo.MyMessage.DataType.StudentType) {
            System.out.println("学生名字："+myMessage.getStudent().getName());
        }else if(dataType== MyDataInfo.MyMessage.DataType.WorkerType) {
            System.out.println("工人名字："+myMessage.getWorker().getName());
        }else {
            System.out.println("传输类型错误");
        }
    }

    /**
     * 数据读取完毕后
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存，并刷新 一般讲，对发送的 数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端", CharsetUtil.UTF_8));
    }

    /**
     * 需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
