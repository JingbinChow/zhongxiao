package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqWalletBo {
    private  Integer id;
    private  Integer memberid;
    private  java.math.BigDecimal money;
    private  BigDecimal frozen;
    private  String  membername;
    private BigDecimal payrmoney;
    private BigDecimal paypmoney;
    private BigDecimal paymoney;
    private String paytime;
    private String payid;
    private String paysn;
    private Integer paytype;
    private String paystatus;

    public BigDecimal getPayrmoney() {
        return payrmoney;
    }

    public void setPayrmoney(BigDecimal payrmoney) {
        this.payrmoney = payrmoney;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public BigDecimal getPaypmoney() {
        return paypmoney;
    }

    public void setPaypmoney(BigDecimal paypmoney) {
        this.paypmoney = paypmoney;
    }

    public BigDecimal getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(BigDecimal paymoney) {
        this.paymoney = paymoney;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPaysn() {
        return paysn;
    }

    public void setPaysn(String paysn) {
        this.paysn = paysn;
    }

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }
}
