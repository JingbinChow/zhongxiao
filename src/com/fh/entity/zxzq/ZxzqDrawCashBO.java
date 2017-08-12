package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqDrawCashBO {
    private Integer id;
    private Integer member_id;
    private java.math.BigDecimal securitiesnum;
    private Integer memberid;
    private java.math.BigDecimal money;
    private Integer status;
    private String securitiesid;
    private BigDecimal welfare;
    private BigDecimal integration;
    private BigDecimal releasenum;
    private BigDecimal securitiesnum_after;
    private String remark;
    private String time;



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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSecuritiesid() {
        return securitiesid;
    }

    public void setSecuritiesid(String securitiesid) {
        this.securitiesid = securitiesid;
    }

    public BigDecimal getWelfare() {
        return welfare;
    }

    public void setWelfare(BigDecimal welfare) {
        this.welfare = welfare;
    }

    public BigDecimal getIntegration() {
        return integration;
    }

    public void setIntegration(BigDecimal integration) {
        this.integration = integration;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public BigDecimal getSecuritiesnum() {
        return securitiesnum;
    }

    public void setSecuritiesnum(BigDecimal securitiesnum) {
        this.securitiesnum = securitiesnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }
}
