package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/16 0016.
 */
public class QueryChargeBackListBo {
    private int eid;
    private int type;
    private int status;
    private String declaration_date;
    private String declaration_price;
    private String amount;
    private String declaration_num;
    private String poundage;
    private String member_name;
    private String member_truename;
    private String management;
    private int member_id;
    private String recordTime;
    private double upReward;//给上级生成的奖金
    private double downReward;//自己获得的奖金

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeclaration_date() {
        return declaration_date;
    }

    public void setDeclaration_date(String declaration_date) {
        this.declaration_date = declaration_date;
    }

    public String getDeclaration_price() {
        return declaration_price;
    }

    public void setDeclaration_price(String declaration_price) {
        this.declaration_price = declaration_price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeclaration_num() {
        return declaration_num;
    }

    public void setDeclaration_num(String declaration_num) {
        this.declaration_num = declaration_num;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public double getUpReward() {
        return upReward;
    }

    public void setUpReward(double upReward) {
        this.upReward = upReward;
    }

    public double getDownReward() {
        return downReward;
    }

    public void setDownReward(double downReward) {
        this.downReward = downReward;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
