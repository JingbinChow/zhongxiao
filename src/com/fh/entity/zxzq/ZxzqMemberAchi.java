package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqMemberAchi {
    private BigDecimal realprice;
    private Integer   member_id;
    private String member_name;
    private String member_sn;
    private String netword_id;
    private BigDecimal achievement;
    private String network_team;

    public BigDecimal getRealprice() {
        return realprice;
    }

    public void setRealprice(BigDecimal realprice) {
        this.realprice = realprice;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_sn() {
        return member_sn;
    }

    public void setMember_sn(String member_sn) {
        this.member_sn = member_sn;
    }

    public String getNetword_id() {
        return netword_id;
    }

    public void setNetword_id(String netword_id) {
        this.netword_id = netword_id;
    }

    public BigDecimal getAchievement() {
        return achievement;
    }

    public void setAchievement(BigDecimal achievement) {
        this.achievement = achievement;
    }

    public String getNetwork_team() {
        return network_team;
    }

    public void setNetwork_team(String network_team) {
        this.network_team = network_team;
    }
}
