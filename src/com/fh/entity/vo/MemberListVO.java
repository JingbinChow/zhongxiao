package com.fh.entity.vo;

/**
 * Created by Administrator on 2017/2/9 0009.
 */
public class MemberListVO {
    private Integer member_id;
    private String member_name;
    private String member_truename;
    private String member_mobile;
    private String addTime;
    private String money;

    public MemberListVO() {
    }

    public MemberListVO(Integer member_id, String member_name, String member_truename, String member_mobile, String addTime, String money) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_truename = member_truename;
        this.member_mobile = member_mobile;
        this.addTime = addTime;
        this.money = money;
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

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

