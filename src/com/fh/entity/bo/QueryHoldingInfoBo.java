package com.fh.entity.bo;

import java.math.BigDecimal;

/**
 * Created by cc on 2016/12/20.
 */
public class QueryHoldingInfoBo {
    private Integer id;
    private Integer memberid;
    private java.math.BigDecimal securitiesnum;
    private String securitiesid;

    public QueryHoldingInfoBo() {
    }
    public QueryHoldingInfoBo(Integer id, Integer memberid, BigDecimal securitiesnum, String securitiesid) {
        this.id = id;
        this.memberid = memberid;
        this.securitiesnum = securitiesnum;
        this.securitiesid = securitiesid;
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

    public BigDecimal getSecuritiesnum() {
        return securitiesnum;
    }

    public void setSecuritiesnum(BigDecimal securitiesnum) {
        this.securitiesnum = securitiesnum;
    }

    public String getSecuritiesid() {
        return securitiesid;
    }

    public void setSecuritiesid(String securitiesid) {
        this.securitiesid = securitiesid;
    }
}
