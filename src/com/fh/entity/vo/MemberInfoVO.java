package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/12/16 0016.
 */
public class MemberInfoVO {
    private String name;
    private int sex;
    private String idcard;
    private String bankname;
    private String bankcard;
    private String mobile;
    private String area;
    private String address;
    private String email;
    private String token;
    private String username;
    private String inviter;
    private String cardType;
    private String payPwd;

    public MemberInfoVO() {
    }

    public MemberInfoVO(String name, int sex, String idcard, String bankname, String bankcard, String mobile, String area, String address, String email, String token, String username, String inviter, String cardType, String payPwd) {
        this.name = name;
        this.sex = sex;
        this.idcard = idcard;
        this.bankname = bankname;
        this.bankcard = bankcard;
        this.mobile = mobile;
        this.area = area;
        this.address = address;
        this.email = email;
        this.token = token;
        this.username = username;
        this.inviter = inviter;
        this.cardType = cardType;
        this.payPwd = payPwd;
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

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }
}
