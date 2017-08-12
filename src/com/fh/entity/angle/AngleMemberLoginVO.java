package com.fh.entity.angle;

/**
 * Created by admin on 2017/2/13.
 *
 * 天使用户基本登录信息
 */
public class AngleMemberLoginVO {

    private int memberId;
    private String nickname;
    private String mobile;
    private String password;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
