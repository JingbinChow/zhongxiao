package com.fh.entity.vo;

/**
 * Created by admin on 2016-11-08.
 */
public class ConfirAmountVO {
    private String userName;
    private String bankCard;
    private String amount;
    private String operatorid;
    private String bankTransaction;
    private String forwardTime;
    private int eid;
    private String name;
    private String bankName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getBankTransaction() {
        return bankTransaction;
    }

    public void setBankTransaction(String bankTransaction) {
        this.bankTransaction = bankTransaction;
    }

    public String getForwardTime() {
        return forwardTime;
    }

    public void setForwardTime(String forwardTime) {
        this.forwardTime = forwardTime;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
