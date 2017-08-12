package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class UserBalanceVo {
    private int userid;
    private float accbalance;
    private int intbalance;
    private int remainbalance;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public float getAccbalance() {
        return accbalance;
    }

    public void setAccbalance(float accbalance) {
        this.accbalance = accbalance;
    }

    public int getIntbalance() {
        return intbalance;
    }

    public void setIntbalance(int intbalance) {
        this.intbalance = intbalance;
    }

    public int getRemainbalance() {
        return remainbalance;
    }

    public void setRemainbalance(int remainbalance) {
        this.remainbalance = remainbalance;
    }
}
