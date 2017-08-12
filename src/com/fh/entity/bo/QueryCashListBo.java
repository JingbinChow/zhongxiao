package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class QueryCashListBo {
    private int pdc_id;
    private String pdc_sn;
    private int pdc_member_id;
    private String pdc_member_name;
    private double pdc_amount;
    private String pdc_bank_name;
    private String pdc_bank_user;
    private String  pdc_payment_state;
    private long pdc_add_time;
    private String pdc_bank_no;
    private String pdc_payment_admin;
    private long pdc_payment_time;
    private String pdc_remark;



    public int getPdc_id() {
        return pdc_id;
    }

    public void setPdc_id(int pdc_id) {
        this.pdc_id = pdc_id;
    }

    public int getPdc_member_id() {
        return pdc_member_id;
    }

    public void setPdc_member_id(int pdc_member_id) {
        this.pdc_member_id = pdc_member_id;
    }

    public String getPdc_member_name() {
        return pdc_member_name;
    }

    public void setPdc_member_name(String pdc_member_name) {
        this.pdc_member_name = pdc_member_name;
    }

    public double getPdc_amount() {
        return pdc_amount;
    }

    public void setPdc_amount(double pdc_amount) {
        this.pdc_amount = pdc_amount;
    }

    public String getPdc_bank_name() {
        return pdc_bank_name;
    }

    public void setPdc_bank_name(String pdc_bank_name) {
        this.pdc_bank_name = pdc_bank_name;
    }

    public String getPdc_bank_user() {
        return pdc_bank_user;
    }

    public void setPdc_bank_user(String pdc_bank_user) {
        this.pdc_bank_user = pdc_bank_user;
    }

    public String getPdc_payment_state() {
        return pdc_payment_state;
    }

    public void setPdc_payment_state(String pdc_payment_state) {
        this.pdc_payment_state = pdc_payment_state;
    }

    public long getPdc_add_time() {
        return pdc_add_time;
    }

    public void setPdc_add_time(long pdc_add_time) {
        this.pdc_add_time = pdc_add_time;
    }

    public String getPdc_payment_admin() {
        return pdc_payment_admin;
    }

    public void setPdc_payment_admin(String pdc_payment_admin) {
        this.pdc_payment_admin = pdc_payment_admin;
    }

    public long getPdc_payment_time() {
        return pdc_payment_time;
    }

    public void setPdc_payment_time(long pdc_payment_time) {
        this.pdc_payment_time = pdc_payment_time;
    }

    public String getPdc_bank_no() {
        return pdc_bank_no;
    }

    public void setPdc_bank_no(String pdc_bank_no) {
        this.pdc_bank_no = pdc_bank_no;
    }

    public String getPdc_sn() {
        return pdc_sn;
    }

    public void setPdc_sn(String pdc_sn) {
        this.pdc_sn = pdc_sn;
    }

    public String getPdc_remark() {
        return pdc_remark;
    }

    public void setPdc_remark(String pdc_remark) {
        this.pdc_remark = pdc_remark;
    }
}
