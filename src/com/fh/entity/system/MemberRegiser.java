package com.fh.entity.system;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MemberRegiser {
    private String username;
    private String password;
    private String name;
    private int sex;//0女1男
    private String idcard;
    private String address;
    private String email;
    private String mobile;
    private String inviterid;

    public MemberRegiser() {
    }

    public MemberRegiser(String username, String password, String name, int sex, String idcard, String address, String email, String mobile, String inviterid) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.idcard = idcard;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.inviterid = inviterid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviterid() {
        return inviterid;
    }

    public void setInviterid(String inviterid) {
        this.inviterid = inviterid;
    }
}
