package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqQueryLevel {
    private Integer memberid;
    private BigDecimal securities;
    private BigDecimal level;

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

    public BigDecimal getLevel() {
        return level;
    }

    public void setLevel(BigDecimal level) {
        this.level = level;
    }
}
