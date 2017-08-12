package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class QueryMemberCashInfoBo {
    private String member_name;
    private String member_truename;
    private String member_bankname;
    private String member_bankcard;
    private int member_id;
    private double available_predeposit;
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

    public String getMember_bankname() {
        return member_bankname;
    }

    public void setMember_bankname(String member_bankname) {
        this.member_bankname = member_bankname;
    }

    public String getMember_bankcard() {
        return member_bankcard;
    }

    public void setMember_bankcard(String member_bankcard) {
        this.member_bankcard = member_bankcard;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public double getAvailable_predeposit() {
        return available_predeposit;
    }

    public void setAvailable_predeposit(double available_predeposit) {
        this.available_predeposit = available_predeposit;
    }
}
