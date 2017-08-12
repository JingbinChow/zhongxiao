package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqRecordVo {
    private Integer id;
    private Integer memberid;
    private String securitiesid;
    private Integer status;
    private Double  securities ;
    private Double  securitiesnum ;
    private Double  price ;
    private BigDecimal realprice;
    private Double  levered ;
    private String otime  ;
    private String  ctime ;
    private Integer frozen;

    public Integer getFrozen() {
        return frozen;
    }

    public void setFrozen(Integer frozen) {
        this.frozen = frozen;
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

    public Double getSecurities() {
        return securities;
    }

    public void setSecurities(Double securities) {
        this.securities = securities;
    }

    public Double getSecuritiesnum() {
        return securitiesnum;
    }

    public void setSecuritiesnum(Double securitiesnum) {
        this.securitiesnum = securitiesnum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLevered() {
        return levered;
    }

    public void setLevered(Double levered) {
        this.levered = levered;
    }

    public String getOtime() {
        return otime;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
