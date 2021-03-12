package com.study.netty.protoclotcp;

/**
 * @author: 吕东杰
 * @description: 协议
 * @create: 2021-03-12 09:48
 **/
public class MessgaeProtocol {

    private int len;

    private byte[] ccontent;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getCcontent() {
        return ccontent;
    }

    public void setCcontent(byte[] ccontent) {
        this.ccontent = ccontent;
    }
}
