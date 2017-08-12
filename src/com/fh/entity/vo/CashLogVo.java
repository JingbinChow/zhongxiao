package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class CashLogVo {
private int lg_member_id;
    private String lg_member_name;
    private String lg_admin_name;
    private String lg_type;
    private double lg_av_amount;
    private double lg_freeze_amount;
    private long lg_add_time;
    private String lg_desc;

    public int getLg_member_id() {
        return lg_member_id;
    }

    public void setLg_member_id(int lg_member_id) {
        this.lg_member_id = lg_member_id;
    }

    public String getLg_member_name() {
        return lg_member_name;
    }

    public void setLg_member_name(String lg_member_name) {
        this.lg_member_name = lg_member_name;
    }

    public String getLg_admin_name() {
        return lg_admin_name;
    }

    public void setLg_admin_name(String lg_admin_name) {
        this.lg_admin_name = lg_admin_name;
    }

    public String getLg_type() {
        return lg_type;
    }

    public void setLg_type(String lg_type) {
        this.lg_type = lg_type;
    }

    public double getLg_av_amount() {
        return lg_av_amount;
    }

    public void setLg_av_amount(double lg_av_amount) {
        this.lg_av_amount = lg_av_amount;
    }

    public double getLg_freeze_amount() {
        return lg_freeze_amount;
    }

    public void setLg_freeze_amount(double lg_freeze_amount) {
        this.lg_freeze_amount = lg_freeze_amount;
    }

    public long getLg_add_time() {
        return lg_add_time;
    }

    public void setLg_add_time(long lg_add_time) {
        this.lg_add_time = lg_add_time;
    }

    public String getLg_desc() {
        return lg_desc;
    }

    public void setLg_desc(String lg_desc) {
        this.lg_desc = lg_desc;
    }
}
