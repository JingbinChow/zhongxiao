package com.fh.entity.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Auth:李荣洲
 *  提现
 * Created by Administrator on 2016/12/2 0002.
 */
public class ZQCashLogVo {
    private int cash_id;
    private String cash_sn;
    private Integer cash_member_id;
    private String cash_member_name;
    private BigDecimal cash_amount;
    private String cash_bank_name;
    private String cash_bank_no;
    private String cash_bank_user;
    private Timestamp cash_add_time;
    private Timestamp cash_payment_time;
    private String cash_payment_state;
    private String cash_payment_admin;
    private String cash_remark;

    public ZQCashLogVo() {
    }

    public ZQCashLogVo(int cash_id, String cash_sn, int cash_member_id, String cash_member_name, BigDecimal cash_amount, String cash_bank_name, String cash_bank_no, String cash_bank_user, Timestamp cash_add_time, Timestamp cash_payment_time, String cash_payment_state, String cash_payment_admin, String cash_remark) {
        this.cash_id = cash_id;
        this.cash_sn = cash_sn;
        this.cash_member_id = cash_member_id;
        this.cash_member_name = cash_member_name;
        this.cash_amount = cash_amount;
        this.cash_bank_name = cash_bank_name;
        this.cash_bank_no = cash_bank_no;
        this.cash_bank_user = cash_bank_user;
        this.cash_add_time = cash_add_time;
        this.cash_payment_time = cash_payment_time;
        this.cash_payment_state = cash_payment_state;
        this.cash_payment_admin = cash_payment_admin;
        this.cash_remark = cash_remark;
    }

    public int getCash_id() {
        return cash_id;
    }

    public void setCash_id(int cash_id) {
        this.cash_id = cash_id;
    }

    public String getCash_sn() {
        return cash_sn;
    }

    public void setCash_sn(String cash_sn) {
        this.cash_sn = cash_sn;
    }

    public int getCash_member_id() {
        return cash_member_id;
    }

    public void setCash_member_id(int cash_member_id) {
        this.cash_member_id = cash_member_id;
    }

    public String getCash_member_name() {
        return cash_member_name;
    }

    public void setCash_member_name(String cash_member_name) {
        this.cash_member_name = cash_member_name;
    }

    public BigDecimal getCash_amount() {
        return cash_amount;
    }

    public void setCash_amount(BigDecimal cash_amount) {
        this.cash_amount = cash_amount;
    }

    public String getCash_bank_name() {
        return cash_bank_name;
    }

    public void setCash_bank_name(String cash_bank_name) {
        this.cash_bank_name = cash_bank_name;
    }

    public String getCash_bank_no() {
        return cash_bank_no;
    }

    public void setCash_bank_no(String cash_bank_no) {
        this.cash_bank_no = cash_bank_no;
    }

    public String getCash_bank_user() {
        return cash_bank_user;
    }

    public void setCash_bank_user(String cash_bank_user) {
        this.cash_bank_user = cash_bank_user;
    }

    public Timestamp getCash_add_time() {
        return cash_add_time;
    }

    public void setCash_add_time(Timestamp cash_add_time) {
        this.cash_add_time = cash_add_time;
    }

    public Timestamp getCash_payment_time() {
        return cash_payment_time;
    }

    public void setCash_payment_time(Timestamp cash_payment_time) {
        this.cash_payment_time = cash_payment_time;
    }

    public String getCash_payment_state() {
        return cash_payment_state;
    }

    public void setCash_payment_state(String cash_payment_state) {
        this.cash_payment_state = cash_payment_state;
    }

    public String getCash_payment_admin() {
        return cash_payment_admin;
    }

    public void setCash_payment_admin(String cash_payment_admin) {
        this.cash_payment_admin = cash_payment_admin;
    }

    public String getCash_remark() {
        return cash_remark;
    }

    public void setCash_remark(String cash_remark) {
        this.cash_remark = cash_remark;
    }
}
