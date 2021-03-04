package com.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-03-01 17:22
 **/
public class NettyByteBuf01 {

    public static void main(String[] args) {
        /**
         * 1.创建一个对象，该对象包含一个长度为10的byte数组
         * 2.无需使用flip() 底层维护了 readIndex和 writeIndex
         * 3。通过readIndex和 writeIndex 和 capacity 将buffer 分成三个区域
         *    0--readIndex 已读的区域
         *    readIndex--writeIndex 可读的区域
         *    writeIndex--capacity 可写的区域
         */
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
    }
}
