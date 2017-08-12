package com.fh.entity.zxys;

import java.io.Serializable;

/**
 * Created by admin on 2016/12/7.
 */
public class ZxysUserVO implements Serializable {

    private String token;
    private int userId;
    private String trueName;
    private String password;
    private String headPortrait;
    private String sex;
    private int age;
    private String mobile;
    private int type;
    private int friend_type;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFriend_type() {
        return friend_type;
    }

    public void setFriend_type(int friend_type) {
        this.friend_type = friend_type;
    }
}
