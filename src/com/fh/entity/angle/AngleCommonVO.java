package com.fh.entity.angle;

/**
 * Created by admin on 2017/2/14.
 */
public class AngleCommonVO {

    private Integer memberid;
    private String  live_topic;
    private String picture;
    private String member_nickname;
    private String member_mobile;
    private String member_password;

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getMember_password() {
        return member_password;
    }

    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public String getLive_topic() {
        return live_topic;
    }

    public void setLive_topic(String live_topic) {
        this.live_topic = live_topic;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }



    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }
}
