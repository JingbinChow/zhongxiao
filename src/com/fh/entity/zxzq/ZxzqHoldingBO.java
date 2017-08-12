package com.fh.entity.zxzq;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/11/22.
 */
public class ZxzqHoldingBO {

    private int id;
    private int memberid;
    private BigDecimal securitiesnum;
    private BigDecimal securitiesid;
    private BigDecimal rate;    // 当日兑换比例


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public BigDecimal getSecuritiesnum() {
        return securitiesnum;
    }

    public void setSecuritiesnum(BigDecimal securitiesnum) {
        this.securitiesnum = securitiesnum;
    }

    public BigDecimal getSecuritiesid() {
        return securitiesid;
    }

    public void setSecuritiesid(BigDecimal securitiesid) {
        this.securitiesid = securitiesid;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
