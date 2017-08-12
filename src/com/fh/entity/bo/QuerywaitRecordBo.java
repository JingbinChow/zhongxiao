package com.fh.entity.bo;

/**
 * Created by admin on 2016/11/3.
 */
public class QuerywaitRecordBo {

    // tb_amountrecord部分
    private Integer eid;
    private Integer status;
    private double declaration_price;
    private int declaration_num;
    private float amount;
    private int real_num;
    private int type;
    private String declarartion_date;
    private String recordTime;

    public Integer getStatus() {
        return status;
    }

    public double getDeclaration_price() {
        return declaration_price;
    }

    public float getAmount() {
        return amount;
    }

    public int getDeclaration_num() {
        return declaration_num;
    }

    public int getReal_num() {
        return real_num;
    }

    public int getType() {
        return type;
    }

    public Integer getEid() {
        return eid;
    }

    public String getDeclarartion_date() {
        return declarartion_date;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setDeclarartion_date(String declarartion_date) {
        this.declarartion_date = declarartion_date;
    }

    public void setDeclaration_num(int declaration_num) {
        this.declaration_num = declaration_num;
    }

    public void setDeclaration_price(double declaration_price) {
        this.declaration_price = declaration_price;
    }

    public void setReal_num(int real_num) {
        this.real_num = real_num;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public void setType(int type) {
        this.type = type;
    }
}
