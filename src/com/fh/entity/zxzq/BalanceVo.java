package com.fh.entity.zxzq;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class BalanceVo {
private int userid;
    private float reward;
    private int intbalance;
    private int accbalance;
    private int remainbalance;
    private String name;

    public BalanceVo() {
    }
    public BalanceVo(int userid, float reward, int intbalance, int accbalance, int remainbalance, String name) {
        this.userid = userid;
        this.reward = reward;
        this.intbalance = intbalance;
        this.accbalance = accbalance;
        this.remainbalance = remainbalance;
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public float getReward() {
        return reward;
    }

    public void setReward(float reward) {
        this.reward = reward;
    }

    public int getIntbalance() {
        return intbalance;
    }

    public void setIntbalance(int intbalance) {
        this.intbalance = intbalance;
    }

    public int getAccbalance() {
        return accbalance;
    }

    public void setAccbalance(int accbalance) {
        this.accbalance = accbalance;
    }

    public int getRemainbalance() {
        return remainbalance;
    }

    public void setRemainbalance(int remainbalance) {
        this.remainbalance = remainbalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
