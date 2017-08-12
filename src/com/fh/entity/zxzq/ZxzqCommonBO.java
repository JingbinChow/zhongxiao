package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqCommonBO {
    private Integer id;
    private Integer memberid;
    private java.math.BigDecimal money;
    private java.math.BigDecimal pmoney;
    private String securitiesid;
    private String member_sn;
    private BigDecimal realprice;
    private String remark;
    private BigDecimal securities;
    private BigDecimal levered;
    private Integer automatic;

    public Integer getAutomatic() {
        return automatic;
    }

    public void setAutomatic(Integer automatic) {
        this.automatic = automatic;
    }

    public BigDecimal getLevered() {
        return levered;
    }

    public void setLevered(BigDecimal levered) {
        this.levered = levered;
    }

    public BigDecimal getSecurities() {
        return securities;
    }

    public void setSecurities(BigDecimal securities) {
        this.securities = securities;
    }

    public BigDecimal getRealprice() {
        return realprice;
    }

    public void setRealprice(BigDecimal realprice) {
        this.realprice = realprice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSecuritiesid() {
        return securitiesid;
    }

    public void setSecuritiesid(String securitiesid) {
        this.securitiesid = securitiesid;
    }



    public String getMember_sn() {
        return member_sn;
    }

    public void setMember_sn(String member_sn) {
        this.member_sn = member_sn;
    }

    public BigDecimal getPmoney() {
        return pmoney;
    }

    public void setPmoney(BigDecimal pmoney) {
        this.pmoney = pmoney;
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
}
