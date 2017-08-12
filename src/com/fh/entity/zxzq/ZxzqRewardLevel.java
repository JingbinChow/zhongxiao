package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqRewardLevel {
    private  Integer member_id;
    private  String  member_sn;
    private  String  securities_id;
    private  BigDecimal  bred;
    private  String member_name;

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getMember_sn() {
        return member_sn;
    }

    public void setMember_sn(String member_sn) {
        this.member_sn = member_sn;
    }

    public String getSecurities_id() {
        return securities_id;
    }

    public void setSecurities_id(String securities_id) {
        this.securities_id = securities_id;
    }

    public BigDecimal getBred() {
        return bred;
    }

    public void setBred(BigDecimal bred) {
        this.bred = bred;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
}
