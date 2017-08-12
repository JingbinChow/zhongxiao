package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqMemberLevel {
    private Integer id;
    private Integer member_id;
    private String member_sn;
    private Integer member_level;
    private BigDecimal member_realprice;
    private BigDecimal levered;

    public BigDecimal getLevered() {
        return levered;
    }

    public void setLevered(BigDecimal levered) {
        this.levered = levered;
    }

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

    public String getMember_sn() {
        return member_sn;
    }

    public void setMember_sn(String member_sn) {
        this.member_sn = member_sn;
    }

    public Integer getMember_level() {
        return member_level;
    }

    public void setMember_level(Integer member_level) {
        this.member_level = member_level;
    }

    public BigDecimal getMember_realprice() {
        return member_realprice;
    }

    public void setMember_realprice(BigDecimal member_realprice) {
        this.member_realprice = member_realprice;
    }
}
