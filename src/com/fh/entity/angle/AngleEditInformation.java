package com.fh.entity.angle;

/**
 * Created by admin on 2017/2/14.
 */
public class AngleEditInformation {
      private String token;
      private String member_portrait;//头像
      private String member_nickname;//昵称
      private Integer member_sex;//性别
      private String member_sign;//个性签名
      private Integer member_age;// 年龄
      private String member_position;// 职业
      private Integer member_id;
      private Integer member_angleid;//天使号
      private String  member_truename;//真是姓名
      private String  member_idcard; //身份证号;
      private String  member_photo;//手持身份证上传

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

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public Integer getMember_angleid() {
        return member_angleid;
    }

    public void setMember_angleid(Integer member_angleid) {
        this.member_angleid = member_angleid;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMember_portrait() {
        return member_portrait;
    }

    public void setMember_portrait(String member_portrait) {
        this.member_portrait = member_portrait;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public Integer getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(Integer member_sex) {
        this.member_sex = member_sex;
    }

    public String getMember_sign() {
        return member_sign;
    }

    public void setMember_sign(String member_sign) {
        this.member_sign = member_sign;
    }

    public Integer getMember_age() {
        return member_age;
    }

    public void setMember_age(Integer member_age) {
        this.member_age = member_age;
    }

    public String getMember_position() {
        return member_position;
    }

    public void setMember_position(String member_position) {
        this.member_position = member_position;
    }
}
