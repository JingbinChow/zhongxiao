package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class WithdrawCashVo {
    private String token;
    private double cash;
    private int member_id;
    private String zqlg_admin_name;
    private String zqlg_type;
    private String cash_sn;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getCash_sn() {
        return cash_sn;
    }

    public void setCash_sn(String cash_sn) {
        this.cash_sn = cash_sn;
    }
}
