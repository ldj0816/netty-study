package com.study.nio;

import java.util.concurrent.CountDownLatch;

/**
 * @author: 吕东杰
 * @description: //TODO
 * @create: 2021-02-26 15:47
 **/
public class Test {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i =0;i<3;i++){
            final int b = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(b==1){
                            int a = 1/0;
                        }
                    }catch (Exception e){
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println("====");
    }

}
