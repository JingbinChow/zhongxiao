package com.fh.entity.zxzq;

public class ZxzqMemberBo {
    private Integer member_id ;
    private String  member_name;
    private String  member_truename;
    private String securities_id;
    private String  securities_team;
    private String network_id;
    private String  network_team;
    private String inviter_id;
    private String member_sn;
    private String notice_status;
    private String startDate;
    private String endDate;
    public String getNotice_status() {
        return notice_status;
    }

    public void setNotice_status(String notice_status) {
        this.notice_status = notice_status;
    }

    public String getNetwork_team() {
        return network_team;
    }

    public void setNetwork_team(String network_team) {
        this.network_team = network_team;
    }

    public String getMember_sn() {
        return member_sn;
    }

    public void setMember_sn(String member_sn) {
        this.member_sn = member_sn;
    }

    public String getInviter_id() {
        return inviter_id;
    }

    public void setInviter_id(String inviter_id) {
        this.inviter_id = inviter_id;
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

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }

    public String getSecurities_id() {
        return securities_id;
    }

    public void setSecurities_id(String securities_id) {
        this.securities_id = securities_id;
    }

    public String getSecurities_team() {
        return securities_team;
    }

    public void setSecurities_team(String securities_team) {
        this.securities_team = securities_team;
    }

    public String getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(String network_id) {
        this.network_id = network_id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
