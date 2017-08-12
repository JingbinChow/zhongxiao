package com.fh.entity.vo;

/**
 * Created by admin on 2016/11/3.
 */
public class DeleteOrderVO {
    private String token;
    private int eid;
    private int uid;
    private int box;

    public String getToken() {
        return token;
    }

    public int getEid() {
        return eid;
    }

    public int getBox() {
        return box;
    }

    public int getUid() {
        return uid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
