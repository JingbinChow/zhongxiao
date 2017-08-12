package com.fh.entity.zxys;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 2016-12-01
 * Time: 13:18
 */
public class ZxysCommentContentVO {
    private Integer doctorId;
    private Integer patientId;
    private String patientName;
    private String commentContent;
    private String label;
    private Integer startLevel;
    private Date createTime;
    private Integer sum;
    private Integer sessionId;
    private Integer consultationType;
    private String out_trade_no;
    private List<ZxysVariableBO> variableBOList;

    // 分页
    private Integer pageIndex;
    private Integer pageSize;

    // token验证
    private String token;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<ZxysVariableBO> getVariableBOList() {
        return variableBOList;
    }

    public void setVariableBOList(List<ZxysVariableBO> variableBOList) {
        this.variableBOList = variableBOList;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(Integer consultationType) {
        this.consultationType = consultationType;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
