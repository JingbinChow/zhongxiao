package com.fh.entity.zxys;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Date: 2016-12-01
 * Time: 11:57
 */
public class ZxysRecordVO {

    private String token;
    private Integer doctorId;
    private String doctorName;
    private Integer patientId;
    private String patientName;
    private Integer patientSex;
    private Integer patientAge;
    private String patientArea;
    private String office;
    private String patientOrderTime;
    private String patientDescribe;
    private String patientPictures;
    private BigDecimal fee;
    private String status;
    private String payWay; // 支付方式（1：支付宝（默认），2：积分支付，3：微信支付）
    private String pay_time;
    private String consultationType;
    private String patientHotDisease;
    private String createTime;
    private String searchWay;
    private String tradeNo;
    private String outTradeNo;
    private String commentContent;
    private String label;
    private Integer startLevel;
    private Integer praise;
    private Integer sessionId;
    private String condition;
    private String orderTime;
    private String hospitalName;
    private String title;

    // 分页属性
    private int pageIndex;  // 页码
    private int pageSize;   // 页容量
    private int totalCount;     // 数据总数
    private int startIndex;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(Integer patientSex) {
        this.patientSex = patientSex;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientArea() {
        return patientArea;
    }

    public void setPatientArea(String patientArea) {
        this.patientArea = patientArea;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getPatientOrderTime() {
        return patientOrderTime;
    }

    public void setPatientOrderTime(String patientOrderTime) {
        this.patientOrderTime = patientOrderTime;
    }

    public String getPatientDescribe() {
        return patientDescribe;
    }

    public void setPatientDescribe(String patientDescribe) {
        this.patientDescribe = patientDescribe;
    }

    public String getPatientPictures() {
        return patientPictures;
    }

    public void setPatientPictures(String patientPictures) {
        this.patientPictures = patientPictures;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }

    public String getPatientHotDisease() {
        return patientHotDisease;
    }

    public void setPatientHotDisease(String patientHotDisease) {
        this.patientHotDisease = patientHotDisease;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSearchWay() {
        return searchWay;
    }

    public void setSearchWay(String searchWay) {
        this.searchWay = searchWay;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(Integer startLevel) {
        this.startLevel = startLevel;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStartIndex() {
//        if(startIndex > totalCount) {
//            // 显示最后5条数据
//            if(totalCount >= pageSize) {
//                startIndex = totalCount - pageSize;
//            }else {
//                startIndex = 0;
//            }
//        }

        return (pageIndex -1)*pageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
