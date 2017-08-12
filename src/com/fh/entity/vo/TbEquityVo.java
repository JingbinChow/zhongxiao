package com.fh.entity.vo;

/**
 * Created by admin on 2016/11/1.
 */
public class TbEquityVo {

    private Integer eid ;
    private String equity;
    private String createdate;
    private String operatorid;

    public Integer getEid() {
        return eid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public String getEquity() {
        return equity;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }
}
