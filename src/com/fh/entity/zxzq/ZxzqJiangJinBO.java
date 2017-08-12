package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class  ZxzqJiangJinBO {
    private Integer memberid;
    private BigDecimal securitiesnum;
    private BigDecimal assigned;
    private BigDecimal releasenum;
    private BigDecimal securitiesnum_after;
    private BigDecimal securities;


    public BigDecimal getSecurities() {
        return securities;
    }

    public void setSecurities(BigDecimal securities) {
        this.securities = securities;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public BigDecimal getSecuritiesnum() {
        return securitiesnum;
    }

    public void setSecuritiesnum(BigDecimal securitiesnum) {
        this.securitiesnum = securitiesnum;
    }

    public BigDecimal getAssigned() {
        return assigned;
    }

    public void setAssigned(BigDecimal assigned) {
        this.assigned = assigned;
    }

    public BigDecimal getReleasenum() {
        return releasenum;
    }

    public void setReleasenum(BigDecimal releasenum) {
        this.releasenum = releasenum;
    }

    public BigDecimal getSecuritiesnum_after() {
        return securitiesnum_after;
    }

    public void setSecuritiesnum_after(BigDecimal securitiesnum_after) {
        this.securitiesnum_after = securitiesnum_after;
    }
}
