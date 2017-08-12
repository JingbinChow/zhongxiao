package com.fh.entity.system;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MemberLogin {
    private String username;
    private String password;

    public MemberLogin() {
    }

    public MemberLogin(String username, String password) {
        this.username = username;
        this.password = password;
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
}
