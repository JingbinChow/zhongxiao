package com.fh.entity.zxzq;

import java.math.BigDecimal;

/**
 * Created by admin on 2016-11-17.
 */
public class ZxzqInformationBO {
    private Integer id;
    private Integer memberid;
    private BigDecimal welfare;
    private BigDecimal integration;
    private BigDecimal securities;
    private String securitiesid;
    private Integer type;
    private BigDecimal achievement;
    private BigDecimal assigned;
    private  int status;
    private BigDecimal charitable_reward;
    private String time;

    public BigDecimal getCharitable_reward() {
        return charitable_reward;
    }

    public void setCharitable_reward(BigDecimal charitable_reward) {
        this.charitable_reward = charitable_reward;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getAchievement() {
        return achievement;
    }

    public void setAchievement(BigDecimal achievement) {
        this.achievement = achievement;
    }

    public BigDecimal getAssigned() {
        return assigned;
    }

    public void setAssigned(BigDecimal assigned) {
        this.assigned = assigned;
    }

    public String getSecuritiesid() {
        return securitiesid;
    }

    public void setSecuritiesid(String securitiesid) {
        this.securitiesid = securitiesid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
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

    public BigDecimal getSecurities() {
        return securities;
    }

    public void setSecurities(BigDecimal securities) {
        this.securities = securities;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
