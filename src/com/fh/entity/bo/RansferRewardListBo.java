package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/16 0016.
 */
public class RansferRewardListBo {
    private int ransferid;
    private String dateandtime;
    private int status;
    private  String actual_number;
    private String remark;
    private String type;

    public int getRansferid() {
        return ransferid;
    }

    public void setRansferid(int ransferid) {
        this.ransferid = ransferid;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActual_number() {
        return actual_number;
    }

    public void setActual_number(String actual_number) {
        this.actual_number = actual_number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
