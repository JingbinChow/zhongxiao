package com.fh.entity.bo;

/**
 * Created by admin on 2016-11-10.
 */
public class CountRewardBO {
    private int realnum;
    private String userid;
    private String createDate;
    private String  eid;
    private String inviter;
    private String inviterid_leval;
    private String money;
    private String reward_time;
    private String reward_remark;
    public int getRealnum() {
        return realnum;
    }

    public void setRealnum(int realnum) {
        this.realnum = realnum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getInviterid_leval() {
        return inviterid_leval;
    }

    public void setInviterid_leval(String inviterid_leval) {
        this.inviterid_leval = inviterid_leval;
    }


    public String getReward_time() {
        return reward_time;
    }

    public void setReward_time(String reward_time) {
        this.reward_time = reward_time;
    }

    public String getReward_remark() {
        return reward_remark;
    }

    public void setReward_remark(String reward_remark) {
        this.reward_remark = reward_remark;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
