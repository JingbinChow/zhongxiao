package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/11/2.
 */
public class AddOrderVo {
    private String token;
    private int type;
    private int pid;
    private double declarationPrice;//报单金额
    private int declarationNum;//报单积分数
    private int boxtotal;
    private float equity;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public int getBoxtotal() {
        return boxtotal;
    }

    public void setBoxtotal(int boxtotal) {
        this.boxtotal = boxtotal;
    }

    public float getEquity() {
        return equity;
    }

    public void setEquity(float equity) {
        this.equity = equity;
    }
}
