package com.fh.entity.zxys;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * 存储医生的认证信息
 *
 * Created by admin on 2016/11/29.
 */
public class ZxysDoctorBO implements Serializable {

    private int id;
    private int doctor_id;
    private String doctor_name;
    private String hospital_name;
    private String hospital_level;
    private String office;
    private String title;
    private String credentials;
    private int status;
    private String id_card;
    private String card_type;
    private String bank_card;
    private String bank_name;
    private int is_online;
    private BigDecimal picture_text_consult;
    private BigDecimal mobile_consult;
    private BigDecimal video_consult;
    private BigDecimal private_doctor_consult;
    private BigDecimal appointment_consult;
    private BigDecimal hospital_post_consult;
    private String picture_text_status;  // 咨询的开关状态（0：关，1：开）
    private String mobile_status;
    private String video_status;
    private String private_doctor_status;
    private String appointment_status;
    private String hospital_post_status;
    private String adept;
    private String background;
    private String education_background;
    private String work_introduced;
    private String area;
    private String password;
    private BigDecimal discount;
    private int browsing_num;
    private int reception_num;   // 接诊次数
    private BigDecimal single_quota;
    private BigDecimal today_quota;
    private BigDecimal min_price;
    private String achievement;

    // 医生的基本信息
    private String member_avatar;
    private int member_sex;
    private String member_mobile;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_level() {
        return hospital_level;
    }

    public void setHospital_level(String hospital_level) {
        this.hospital_level = hospital_level;
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

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public int getIs_online() {
        return is_online;
    }

    public void setIs_online(int is_online) {
        this.is_online = is_online;
    }

    public BigDecimal getPicture_text_consult() {
        return picture_text_consult;
    }

    public void setPicture_text_consult(BigDecimal picture_text_consult) {
        this.picture_text_consult = picture_text_consult;
    }

    public BigDecimal getMobile_consult() {
        return mobile_consult;
    }

    public void setMobile_consult(BigDecimal mobile_consult) {
        this.mobile_consult = mobile_consult;
    }

    public BigDecimal getVideo_consult() {
        return video_consult;
    }

    public void setVideo_consult(BigDecimal video_consult) {
        this.video_consult = video_consult;
    }

    public BigDecimal getPrivate_doctor_consult() {
        return private_doctor_consult;
    }

    public void setPrivate_doctor_consult(BigDecimal private_doctor_consult) {
        this.private_doctor_consult = private_doctor_consult;
    }

    public BigDecimal getAppointment_consult() {
        return appointment_consult;
    }

    public void setAppointment_consult(BigDecimal appointment_consult) {
        this.appointment_consult = appointment_consult;
    }

    public BigDecimal getHospital_post_consult() {
        return hospital_post_consult;
    }

    public void setHospital_post_consult(BigDecimal hospital_post_consult) {
        this.hospital_post_consult = hospital_post_consult;
    }

    public String getPicture_text_status() {
        return picture_text_status;
    }

    public void setPicture_text_status(String picture_text_status) {
        this.picture_text_status = picture_text_status;
    }

    public String getMobile_status() {
        return mobile_status;
    }

    public void setMobile_status(String mobile_status) {
        this.mobile_status = mobile_status;
    }

    public String getVideo_status() {
        return video_status;
    }

    public void setVideo_status(String video_status) {
        this.video_status = video_status;
    }

    public String getPrivate_doctor_status() {
        return private_doctor_status;
    }

    public void setPrivate_doctor_status(String private_doctor_status) {
        this.private_doctor_status = private_doctor_status;
    }

    public String getAppointment_status() {
        return appointment_status;
    }

    public void setAppointment_status(String appointment_status) {
        this.appointment_status = appointment_status;
    }

    public String getHospital_post_status() {
        return hospital_post_status;
    }

    public void setHospital_post_status(String hospital_post_status) {
        this.hospital_post_status = hospital_post_status;
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

    public String getEducation_background() {
        return education_background;
    }

    public void setEducation_background(String education_background) {
        this.education_background = education_background;
    }

    public String getWork_introduced() {
        return work_introduced;
    }

    public void setWork_introduced(String work_introduced) {
        this.work_introduced = work_introduced;
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

    public int getBrowsing_num() {
        return browsing_num;
    }

    public void setBrowsing_num(int browsing_num) {
        this.browsing_num = browsing_num;
    }

    public int getReception_num() {
        return reception_num;
    }

    public void setReception_num(int reception_num) {
        this.reception_num = reception_num;
    }

    public BigDecimal getSingle_quota() {
        return single_quota;
    }

    public void setSingle_quota(BigDecimal single_quota) {
        this.single_quota = single_quota;
    }

    public BigDecimal getToday_quota() {
        return today_quota;
    }

    public void setToday_quota(BigDecimal today_quota) {
        this.today_quota = today_quota;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public BigDecimal getMin_price() {
        return min_price;
    }

    public void setMin_price(BigDecimal min_price) {
        this.min_price = min_price;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public int getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(int member_sex) {
        this.member_sex = member_sex;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }
}
