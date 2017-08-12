package com.fh.entity.zxys;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class AdminDoctorListBo implements Serializable {
    private String member_id;
    private String member_name;
    private String member_truename;
    private String member_mobile;
    private String status;
    private String hospital_name;
    private String area;

    public AdminDoctorListBo() {
    }

    public AdminDoctorListBo(String member_id, String member_name, String member_truename, String member_mobile, String status, String hospital_name, String area) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_truename = member_truename;
        this.member_mobile = member_mobile;
        this.status = status;
        this.hospital_name = hospital_name;
        this.area = area;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
