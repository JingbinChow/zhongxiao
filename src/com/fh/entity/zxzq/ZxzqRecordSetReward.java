package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqRecordSetReward {
    private  Integer memberid;
    private  String  securitiesid;
    private  Integer invitedid;
    private  Integer invitelevel;
    private  java.math.BigDecimal rewardpoint;
    private  java.math.BigDecimal rewardnum;

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

    public Integer getInvitedid() {
        return invitedid;
    }

    public void setInvitedid(Integer invitedid) {
        this.invitedid = invitedid;
    }

    public Integer getInvitelevel() {
        return invitelevel;
    }

    public void setInvitelevel(Integer invitelevel) {
        this.invitelevel = invitelevel;
    }

    public BigDecimal getRewardpoint() {
        return rewardpoint;
    }

    public void setRewardpoint(BigDecimal rewardpoint) {
        this.rewardpoint = rewardpoint;
    }

    public BigDecimal getRewardnum() {
        return rewardnum;
    }

    public void setRewardnum(BigDecimal rewardnum) {
        this.rewardnum = rewardnum;
    }
}
