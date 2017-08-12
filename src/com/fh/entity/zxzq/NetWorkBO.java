package com.fh.entity.zxzq;

import java.math.BigDecimal;

/**
 * Created by admin on 2016-11-21.
 */
public class NetWorkBO {
    private Integer member_id;
    private String network_id;
    private String network_team;
    private String member_name;
    private String securities_id;
    private BigDecimal sum;

    public String getSecurities_id() {
        return securities_id;
    }

    public void setSecurities_id(String securities_id) {
        this.securities_id = securities_id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
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
