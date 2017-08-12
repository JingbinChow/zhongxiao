package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class ChildMemberBo {
    private String username;
    private String name;
    private int balance;
    private int level;
    private String vip;

    public ChildMemberBo() {
    }

    public ChildMemberBo(String username, String name, int balance, int level, String vip) {
        this.username = username;
        this.name = name;
        this.balance=balance;
        this.level=level;
        this.vip=vip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
