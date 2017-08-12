package com.fh.entity.zxzq;

import java.math.BigDecimal;

public class ZxzqYjBo {
   private Integer member_id;
   private String  member_name;
   private String  member_sn;
   private BigDecimal bigyj;
   private BigDecimal smallyj;

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_sn() {
        return member_sn;
    }

    public void setMember_sn(String member_sn) {
        this.member_sn = member_sn;
    }

    public BigDecimal getBigyj() {
        return bigyj;
    }

    public void setBigyj(BigDecimal bigyj) {
        this.bigyj = bigyj;
    }

    public BigDecimal getSmallyj() {
        return smallyj;
    }

    public void setSmallyj(BigDecimal smallyj) {
        this.smallyj = smallyj;
    }
}
