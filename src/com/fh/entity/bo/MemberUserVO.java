package com.fh.entity.bo;

import java.math.BigDecimal;

/**
 * Created by cc on 2016/12/19.
 */
public class MemberUserVO {
    private Integer memberid;
    private java.math.BigDecimal securitiesnum;

    public MemberUserVO(Integer memberid, BigDecimal securitiesnum) {
        this.memberid = memberid;
        this.securitiesnum = securitiesnum;
    }

    public MemberUserVO() {
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
}
