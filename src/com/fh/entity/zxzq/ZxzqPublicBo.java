package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqPublicBo {
    private Integer memberid;
    private BigDecimal securities;//可提现手续费
    private BigDecimal poundage;//手续费
    private BigDecimal backsecurities;//回填金额
    private BigDecimal extract;//提取金额
    private BigDecimal money;//换算成金额

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public BigDecimal getSecurities() {
        return securities;
    }

    public void setSecurities(BigDecimal securities) {
        this.securities = securities;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getBacksecurities() {
        return backsecurities;
    }

    public void setBacksecurities(BigDecimal backsecurities) {
        this.backsecurities = backsecurities;
    }

    public BigDecimal getExtract() {
        return extract;
    }

    public void setExtract(BigDecimal extract) {
        this.extract = extract;
    }
}
