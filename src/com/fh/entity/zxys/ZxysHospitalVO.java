package com.fh.entity.zxys;

/**
 * 医院信息
 * Created by admin on 2016/12/5.
 */
public class ZxysHospitalVO {

    private int hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalLevel;
    private String hospitalArea;
    private String hospital_adapt;


    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getHospitalArea() {
        return hospitalArea;
    }

    public void setHospitalArea(String hospitalArea) {
        this.hospitalArea = hospitalArea;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospital_adapt() {
        return hospital_adapt;
    }

    public void setHospital_adapt(String hospital_adapt) {
        this.hospital_adapt = hospital_adapt;
    }
}
