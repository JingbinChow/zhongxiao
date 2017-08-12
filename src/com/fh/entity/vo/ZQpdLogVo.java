package com.fh.entity.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Auth:李荣洲
 * Created by Administrator on 2016/12/2 0002.
 */
public class ZQpdLogVo {
    private int zqlg_id;
    private int zqlg_member_id;
    private String zqlg_member_name;
    private String zqlg_admin_name;
    private String zqlg_type;
    private BigDecimal zqlg_av_amount;
    private BigDecimal zqlg_freeze_amount;
    private Timestamp zqlg_add_time;
    private String zqlg_desc;

    public ZQpdLogVo() {
    }

    public ZQpdLogVo(int zqlg_id, int zqlg_member_id, String zqlg_member_name, String zqlg_admin_name, String zqlg_type, BigDecimal zqlg_av_amount, BigDecimal zqlg_freeze_amount, Timestamp zqlg_add_time, String zqlg_desc) {
        this.zqlg_id = zqlg_id;
        this.zqlg_member_id = zqlg_member_id;
        this.zqlg_member_name = zqlg_member_name;
        this.zqlg_admin_name = zqlg_admin_name;
        this.zqlg_type = zqlg_type;
        this.zqlg_av_amount = zqlg_av_amount;
        this.zqlg_freeze_amount = zqlg_freeze_amount;
        this.zqlg_add_time = zqlg_add_time;
        this.zqlg_desc = zqlg_desc;
    }

    public int getZqlg_id() {
        return zqlg_id;
    }

    public void setZqlg_id(int zqlg_id) {
        this.zqlg_id = zqlg_id;
    }

    public int getZqlg_member_id() {
        return zqlg_member_id;
    }

    public void setZqlg_member_id(int zqlg_member_id) {
        this.zqlg_member_id = zqlg_member_id;
    }

    public String getZqlg_member_name() {
        return zqlg_member_name;
    }

    public void setZqlg_member_name(String zqlg_member_name) {
        this.zqlg_member_name = zqlg_member_name;
    }

    public String getZqlg_admin_name() {
        return zqlg_admin_name;
    }

    public void setZqlg_admin_name(String zqlg_admin_name) {
        this.zqlg_admin_name = zqlg_admin_name;
    }

    public String getZqlg_type() {
        return zqlg_type;
    }

    public void setZqlg_type(String zqlg_type) {
        this.zqlg_type = zqlg_type;
    }

    public BigDecimal getZqlg_av_amount() {
        return zqlg_av_amount;
    }

    public void setZqlg_av_amount(BigDecimal zqlg_av_amount) {
        this.zqlg_av_amount = zqlg_av_amount;
    }

    public BigDecimal getZqlg_freeze_amount() {
        return zqlg_freeze_amount;
    }

    public void setZqlg_freeze_amount(BigDecimal zqlg_freeze_amount) {
        this.zqlg_freeze_amount = zqlg_freeze_amount;
    }

    public Timestamp getZqlg_add_time() {
        return zqlg_add_time;
    }

    public void setZqlg_add_time(Timestamp zqlg_add_time) {
        this.zqlg_add_time = zqlg_add_time;
    }

    public String getZqlg_desc() {
        return zqlg_desc;
    }

    public void setZqlg_desc(String zqlg_desc) {
        this.zqlg_desc = zqlg_desc;
    }
}
