package com.fh.entity.zxys;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/29.
 */
public class ZxysOfficeVO implements Serializable {
    private String token;
    private int officeId;
    private String officeName;
    private int officeParentid;
    private String area;
    private String consult;
    private String orderType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public int getOfficeParentid() {
        return officeParentid;
    }

    public void setOfficeParentid(int officeParentid) {
        this.officeParentid = officeParentid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
