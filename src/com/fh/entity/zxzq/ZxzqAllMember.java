package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqAllMember {
    private  Integer member_id;
    private  String member_name;
    private  String member_sn;
    private  String network_id;
    private  String network_team;

    public String getNetwork_team() {
        return network_team;
    }

    public void setNetwork_team(String network_team) {
        this.network_team = network_team;
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

    public String getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(String network_id) {
        this.network_id = network_id;
    }
}
