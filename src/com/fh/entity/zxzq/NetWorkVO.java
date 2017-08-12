package com.fh.entity.zxzq;

/**
 * Created by admin on 2016-11-21.
 */
public class NetWorkVO {
    private Integer member_id;
    private String network_id;
    private String network_team;
    private String member_name;
    private Integer newUserId;
    private Integer upMemberId;
    private String newNetworkTeam;

    public String getNewNetworkTeam() {
        return newNetworkTeam;
    }

    public void setNewNetworkTeam(String newNetworkTeam) {
        this.newNetworkTeam = newNetworkTeam;
    }

    public Integer getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(Integer newUserId) {
        this.newUserId = newUserId;
    }

    public Integer getUpMemberId() {
        return upMemberId;
    }

    public void setUpMemberId(Integer upMemberId) {
        this.upMemberId = upMemberId;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(String network_id) {
        this.network_id = network_id;
    }

    public String getNetwork_team() {
        return network_team;
    }

    public void setNetwork_team(String network_team) {
        this.network_team = network_team;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
}
