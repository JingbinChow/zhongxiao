package com.fh.entity.zxzq;

import java.math.BigDecimal;

/**
 * Created by admin on 2016-11-17.
 */
public class ZxzqVariableBO {
    private Integer id;
    private Integer type;
    private BigDecimal amount;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
