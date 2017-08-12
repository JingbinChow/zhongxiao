package com.fh.entity.vo;

/**
 * Created by Administrator on 2017/2/9 0009.
 */
public class SendMessageVO {
    //发送消息内容
    private String message;
    //发送群体（手机号串）
    private String send;

    public SendMessageVO() {
    }

    public SendMessageVO(String message, String send) {
        this.message = message;
        this.send = send;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }
}
