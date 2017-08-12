package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqXbWalletBo {
    private  Integer id;
    private  Integer member_id;
    private  BigDecimal xb;
    private String etime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public BigDecimal getXb() {
        return xb;
    }

    public void setXb(BigDecimal xb) {
        this.xb = xb;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
