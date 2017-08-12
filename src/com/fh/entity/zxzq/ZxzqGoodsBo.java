package com.fh.entity.zxzq;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ZxzqGoodsBo {
    private  Integer id;
    private  java.math.BigDecimal price;
    private  java.math.BigDecimal levered;
    private  java.math.BigDecimal t_1;
    private  java.math.BigDecimal t_2;
    private  java.math.BigDecimal t_3;
    private  java.math.BigDecimal t_bred;
    private  java.math.BigDecimal t_bred_f;
    private  java.math.BigDecimal securities;

    public BigDecimal getT_bred() {
        return t_bred;
    }

    public void setT_bred(BigDecimal t_bred) {
        this.t_bred = t_bred;
    }

    public BigDecimal getT_bred_f() {
        return t_bred_f;
    }

    public void setT_bred_f(BigDecimal t_bred_f) {
        this.t_bred_f = t_bred_f;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getLevered() {
        return levered;
    }

    public void setLevered(BigDecimal levered) {
        this.levered = levered;
    }

    public BigDecimal getT_1() {
        return t_1;
    }

    public void setT_1(BigDecimal t_1) {
        this.t_1 = t_1;
    }

    public BigDecimal getT_2() {
        return t_2;
    }

    public void setT_2(BigDecimal t_2) {
        this.t_2 = t_2;
    }

    public BigDecimal getT_3() {
        return t_3;
    }

    public void setT_3(BigDecimal t_3) {
        this.t_3 = t_3;
    }

    public BigDecimal getSecurities() {
        return securities;
    }

    public void setSecurities(BigDecimal securities) {
        this.securities = securities;
    }
}
