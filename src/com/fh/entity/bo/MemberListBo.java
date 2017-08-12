package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class MemberListBo {
    private int userId;
    private String userName;
    private String name;
    private String bankCard;

    public MemberListBo() {
    }

    public MemberListBo(int userId, String userName, String name, String bankCard) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.bankCard = bankCard;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }
}
