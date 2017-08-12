package com.fh.entity.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class WithdrawVo {
    private String token;
    private BigDecimal amount;
    private int member_id;

    public WithdrawVo() {
    }

    public WithdrawVo(String token, BigDecimal amount, int member_id) {
        this.token = token;
        this.amount = amount;
        this.member_id = member_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
}
