package com.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-01 17:36
 **/
public class NettyByteBuf02 {

    public static void main(String[] args) {
        /**
         * 创建bytebuf
         *
         */
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello, world", CharsetUtil.UTF_8);
        //使用相关的方法
        if (byteBuf.hasArray()) {
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes, CharsetUtil.UTF_8));
        }

    }
}
