package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqRecordInFormation {
//    private Integer id;
    private Integer memberid;
    private String securitiesid;
    private java.math.BigDecimal price;
    private java.math.BigDecimal realprice;
    private Integer levered;
    private String member_name;
    private String member_sn;
    private String network_team;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getSecuritiesid() {
        return securitiesid;
    }

    public void setSecuritiesid(String securitiesid) {
        this.securitiesid = securitiesid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRealprice() {
        return realprice;
    }

    public void setRealprice(BigDecimal realprice) {
        this.realprice = realprice;
    }

    public Integer getLevered() {
        return levered;
    }

    public void setLevered(Integer levered) {
        this.levered = levered;
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

    public String getNetwork_team() {
        return network_team;
    }

    public void setNetwork_team(String network_team) {
        this.network_team = network_team;
    }
}
