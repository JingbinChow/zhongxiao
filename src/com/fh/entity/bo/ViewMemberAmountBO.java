package com.fh.entity.bo;

/**
 * Created by admin on 2016-11-11.
 */
public class ViewMemberAmountBO {
    private Integer uid;
    private float amount;
    private String recordTime;
    private Integer real_num;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getReal_num() {
        return real_num;
    }

    public void setReal_num(Integer real_num) {
        this.real_num = real_num;
    }
}
