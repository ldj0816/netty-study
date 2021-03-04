package com.study.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-02-22 16:13
 **/
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress  = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 8;

        while (true){
            int byteRead = 0;

            while (byteRead<messageLength){
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                Arrays.asList(byteBuffers).stream().forEach(byteBuffer -> {
                    StringBuilder stringBuilder = new StringBuilder("position:");
                    stringBuilder.append(byteBuffer.position());
                    stringBuilder.append(", limit:");
                    stringBuilder.append(byteBuffer.limit());
                    System.out.println(stringBuilder.toString());
                });
            }
            Arrays.asList(byteBuffers).stream().forEach(byteBuffer ->{
                byteBuffer.flip();
            });

            //将数据读出，显示到客户端
            long byteWrite = 0;
            while (byteWrite<messageLength){
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }
            Arrays.asList(byteBuffers).stream().forEach(byteBuffer ->{
                byteBuffer.clear();
            });

        }
    }
}
