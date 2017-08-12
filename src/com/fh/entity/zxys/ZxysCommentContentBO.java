package com.fh.entity.zxys;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 2016-12-01
 * Time: 13:18
 */
public class ZxysCommentContentBO {
    private Integer patient_id;
    private String patient_name;
    private String comment_content;
    private String label;
    private Integer start_level;
    private String create_time;
    private Integer sum;
    private Integer session_id;
    private String consultation_type;
    private String out_trade_no;
    private String member_avatar;
    private List<ZxysVariableBO> variableBOList;

    // 标签属性

    // 分页
    private Integer pageIndex;
    private Integer pageSize;

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

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getStart_level() {
        return start_level;
    }

    public void setStart_level(Integer start_level) {
        this.start_level = start_level;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getConsultation_type() {
        return consultation_type;
    }

    public void setConsultation_type(String consultation_type) {
        this.consultation_type = consultation_type;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getSession_id() {
        return session_id;
    }

    public void setSession_id(Integer session_id) {
        this.session_id = session_id;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }
}
