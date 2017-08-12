package com.fh.entity.bo;

/**
 * Created by admin on 2016/11/4.
 */
public class OrderListBO {

    private double declarationPrice;//积分金额
    private int declarationNum;//购买积分数
    private int status;//交易状态
    private int type;
    private int eid;
    private String date;

    public OrderListBO(double declarationPrice, int declarationNum, int status, int type, int eid, String date) {
        this.declarationPrice = declarationPrice;
        this.declarationNum = declarationNum;
        this.status = status;
        this.type = type;
        this.eid = eid;
        this.date = date;
    }

    public double getDeclarationPrice() {
        return declarationPrice;
    }

    public void setDeclarationPrice(double declarationPrice) {
        this.declarationPrice = declarationPrice;
    }

    public int getDeclarationNum() {
        return declarationNum;
    }

    public void setDeclarationNum(int declarationNum) {
        this.declarationNum = declarationNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
