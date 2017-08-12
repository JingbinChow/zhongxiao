package com.fh.entity.system;

/**
 * 获取银行列表
 * Created by admin on 2016/10/31.
 */
public class MemberBankList {
    private Integer bankId;
    private String bankKey;
    private String bankName;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankKey() {
        return bankKey;
    }

    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
