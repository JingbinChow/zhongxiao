package com.fh.entity.zxys;

import java.math.BigDecimal;

/**
 * 医生开通服务实体类
 * Created by admin on 2016/12/13.
 */
public class ZxysServiceVO {
    private String token;
    private String status;  // 服务开关（0：关，1：开）
    private String serviceType;
    private BigDecimal price;
    private int doctorId;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
