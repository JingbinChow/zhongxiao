package com.fh.entity.zxzq;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ZxzqRecordBO {
    private Integer id;
    private Integer memberid;
    private String securitiesid;
    private Integer status;
    private java.math.BigDecimal  securities ;
    private java.math.BigDecimal  securitiesnum ;
    private java.math.BigDecimal  price;
    private java.math.BigDecimal realprice;
    private String remark;
    private java.math.BigDecimal  levered ;
    private java.sql.Timestamp otime  ;
    private java.sql.Timestamp  ctime ;
    private Integer frozen;

    public Integer getFrozen() {
        return frozen;
    }

    public void setFrozen(Integer frozen) {
        this.frozen = frozen;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getRealprice() {
        return realprice;
    }

    public void setRealprice(BigDecimal realprice) {
        this.realprice = realprice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getSecuritiesid() {
        return securitiesid;
    }

    public void setSecuritiesid(String securitiesid) {
        this.securitiesid = securitiesid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getSecurities() {
        return securities;
    }

    public void setSecurities(BigDecimal securities) {
        this.securities = securities;
    }

    public BigDecimal getSecuritiesnum() {
        return securitiesnum;
    }

    public void setSecuritiesnum(BigDecimal securitiesnum) {
        this.securitiesnum = securitiesnum;
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

    public Timestamp getOtime() {
        return otime;
    }

    public void setOtime(Timestamp otime) {
        this.otime = otime;
    }

    public Timestamp getCtime() {
        return ctime;
    }

    public void setCtime(Timestamp ctime) {
        this.ctime = ctime;
    }
}
