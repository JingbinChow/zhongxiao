package com.fh.entity.angle;

/**
 * Created by Administrator on 2017/2/23 0023.
 */
public class AuthenticationBO {
    private Integer member_id;
    private String member_truename;
    private String member_idcard;

    public AuthenticationBO() {
    }

    public AuthenticationBO(Integer member_id, String member_truename, String member_idcard) {
        this.member_id = member_id;
        this.member_truename = member_truename;
        this.member_idcard = member_idcard;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }

    public String getMember_idcard() {
        return member_idcard;
    }

    public void setMember_idcard(String member_idcard) {
        this.member_idcard = member_idcard;
    }
}
