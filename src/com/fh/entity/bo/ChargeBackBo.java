package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class ChargeBackBo {
    private int eid;//订单编号
    private int userId;
    private String bankName;
    private String name;
    private String bankCard;
    private int balance;//退单积分数
    private float equity;//净值
    private double finalAmount;//实际退单金额
    private double returnAmount;//退单金额
    private String lastTime ;
    //是否缴纳手续费
    private String isTax;
    private double poundage;//手续费
    private double management;//管理费
    private String currentTime;//退单日期
    private String source;
    private int Status;//状态
    private String mpercent;
    private String ppercent;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
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

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getIsTax() {
        return isTax;
    }

    public void setIsTax(String isTax) {
        this.isTax = isTax;
    }

    public double getPoundage() {
        return poundage;
    }

    public void setPoundage(double poundage) {
        this.poundage = poundage;
    }

    public double getManagement() {
        return management;
    }

    public void setManagement(double management) {
        this.management = management;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getEquity() {
        return equity;
    }

    public void setEquity(float equity) {
        this.equity = equity;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMpercent() {
        return mpercent;
    }

    public void setMpercent(String mpercent) {
        this.mpercent = mpercent;
    }

    public String getPpercent() {
        return ppercent;
    }

    public void setPpercent(String ppercent) {
        this.ppercent = ppercent;
    }

    @Override
    public String toString() {
        return "ChargeBackBo{" +
                "eid=" + eid +
                ", userId=" + userId +
                ", bankName='" + bankName + '\'' +
                ", name='" + name + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", balance=" + balance +
                ", equity=" + equity +
                ", finalAmount=" + finalAmount +
                ", returnAmount=" + returnAmount +
                ", lastTime='" + lastTime + '\'' +
                ", isTax='" + isTax + '\'' +
                ", poundage=" + poundage +
                ", management=" + management +
                ", currentTime='" + currentTime + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
