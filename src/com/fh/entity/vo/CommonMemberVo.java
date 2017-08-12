package com.fh.entity.vo;


/**
 * Created by Administrator on 2016/11/1.
 */
public class CommonMemberVo {
    private String member_id;
    private String member_name;
    private String member_truename;
    private String team_sign;

    public String getTeam_sign() {
        return team_sign;
    }

    public void setTeam_sign(String team_sign) {
        this.team_sign = team_sign;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
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

}
