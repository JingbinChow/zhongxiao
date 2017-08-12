package com.fh.entity.zxys;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 医生
 *
 * Created by admin on 2016/12/2.
 */
public class ZxysDoctorVO implements Serializable {

    private String token;
    private int id;
    private int doctorId;
    private String doctorName;
    private String hospitalName;
    private String hospitalLevel;
    private String office;
    private String title;
    private String credentials;
    private int status;
    private String idCard;
    private String cardType;
    private String bankCard;
    private String bankName;
    private int is_online;
    private BigDecimal picture_textConsult;
    private BigDecimal mobileConsult;
    private BigDecimal videoConsult;
    private BigDecimal privateDoctorConsult;
    private BigDecimal appointmentConsult;
    private BigDecimal hospitalPostConsult;
    private String adept;
    private String background;
    private String educationBackground;
    private String workIntroduced;
    private String area;
    private String password;
    private BigDecimal discount;
    private int browsingNum;
    private int receptionNum;
    private BigDecimal singleQuota;
    private BigDecimal todayQuota;
    private BigDecimal minPrice;

    // 医生的基本信息
    // 医生头像
    private String memberAvatar;
    private String memberMobile;
    private String verifyCode;
    private String sex;
    private String currentPic;// 近期照片


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getIs_online() {
        return is_online;
    }

    public void setIs_online(int is_online) {
        this.is_online = is_online;
    }

    public BigDecimal getPicture_textConsult() {
        return picture_textConsult;
    }

    public void setPicture_textConsult(BigDecimal picture_textConsult) {
        this.picture_textConsult = picture_textConsult;
    }

    public BigDecimal getMobileConsult() {
        return mobileConsult;
    }

    public void setMobileConsult(BigDecimal mobileConsult) {
        this.mobileConsult = mobileConsult;
    }

    public BigDecimal getVideoConsult() {
        return videoConsult;
    }

    public void setVideoConsult(BigDecimal videoConsult) {
        this.videoConsult = videoConsult;
    }

    public BigDecimal getPrivateDoctorConsult() {
        return privateDoctorConsult;
    }

    public void setPrivateDoctorConsult(BigDecimal privateDoctorConsult) {
        this.privateDoctorConsult = privateDoctorConsult;
    }

    public BigDecimal getAppointmentConsult() {
        return appointmentConsult;
    }

    public void setAppointmentConsult(BigDecimal appointmentConsult) {
        this.appointmentConsult = appointmentConsult;
    }

    public BigDecimal getHospitalPostConsult() {
        return hospitalPostConsult;
    }

    public void setHospitalPostConsult(BigDecimal hospitalPostConsult) {
        this.hospitalPostConsult = hospitalPostConsult;
    }

    public String getAdept() {
        return adept;
    }

    public void setAdept(String adept) {
        this.adept = adept;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getWorkIntroduced() {
        return workIntroduced;
    }

    public void setWorkIntroduced(String workIntroduced) {
        this.workIntroduced = workIntroduced;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getBrowsingNum() {
        return browsingNum;
    }

    public void setBrowsingNum(int browsingNum) {
        this.browsingNum = browsingNum;
    }

    public int getReceptionNum() {
        return receptionNum;
    }

    public void setReceptionNum(int receptionNum) {
        this.receptionNum = receptionNum;
    }

    public BigDecimal getSingleQuota() {
        return singleQuota;
    }

    public void setSingleQuota(BigDecimal singleQuota) {
        this.singleQuota = singleQuota;
    }

    public BigDecimal getTodayQuota() {
        return todayQuota;
    }

    public void setTodayQuota(BigDecimal todayQuota) {
        this.todayQuota = todayQuota;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCurrentPic() {
        return currentPic;
    }

    public void setCurrentPic(String currentPic) {
        this.currentPic = currentPic;
    }
}
