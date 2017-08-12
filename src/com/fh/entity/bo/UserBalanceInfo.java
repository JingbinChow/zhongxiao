package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class UserBalanceInfo {
    private String member_name;
    private double available_predeposit;
    private int intbalance;
    private double available_rc_balance;
    private String vip;
    private  String charitableFund;
    private String bankCard;
    private String bankName;
    private String member_id ;
    private String member_ids;
    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_ids() {
        return member_ids;
    }

    public void setMember_ids(String member_ids) {
        this.member_ids = member_ids;
    }



    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }


    public double getAvailable_predeposit() {
        return available_predeposit;
    }

    public void setAvailable_predeposit(double available_predeposit) {
        this.available_predeposit = available_predeposit;
    }

    public int getIntbalance() {
        return intbalance;
    }

    public void setIntbalance(int intbalance) {
        this.intbalance = intbalance;
    }

    public double getAvailable_rc_balance() {
        return available_rc_balance;
    }

    public void setAvailable_rc_balance(double available_rc_balance) {
        this.available_rc_balance = available_rc_balance;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getCharitableFund() {
        return charitableFund;
    }

    public void setCharitableFund(String charitableFund) {
        this.charitableFund = charitableFund;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "UserBalanceInfo{" +
                "member_name='" + member_name + '\'' +
                ", available_predeposit=" + available_predeposit +
                ", intbalance=" + intbalance +
                ", available_rc_balance=" + available_rc_balance +
                ", vip='" + vip + '\'' +
                ", charitableFund='" + charitableFund + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
