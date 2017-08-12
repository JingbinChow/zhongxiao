package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class DeleteMemberVo {
    private int uid;

    public DeleteMemberVo() {
    }

    public DeleteMemberVo(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
