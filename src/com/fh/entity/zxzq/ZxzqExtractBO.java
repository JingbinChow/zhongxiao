package com.fh.entity.zxzq;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class ZxzqExtractBO {
    private BigDecimal money;
    private BigDecimal securitiesnum;

    public ZxzqExtractBO() {
    }

    public ZxzqExtractBO(BigDecimal money, BigDecimal securitiesnum) {
        this.money = money;
        this.securitiesnum = securitiesnum;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getSecuritiesnum() {
        return securitiesnum;
    }

    public void setSecuritiesnum(BigDecimal securitiesnum) {
        this.securitiesnum = securitiesnum;
    }
}
