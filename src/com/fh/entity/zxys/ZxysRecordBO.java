package com.fh.entity.zxys;

import org.apache.james.mime4j.field.datetime.DateTime;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 2016-12-01
 * Time: 10:56
 */
public class ZxysRecordBO {
    private Integer id;
    private Integer doctor_id;
    private String doctor_name;
    private Integer patient_id;
    private String patient_name;
    private Integer patient_sex;
    private Integer patient_age;
    private String patient_area;
    private String office;
    private String patient_ordertime;
    private String patient_describe;
    private String patient_pictures;
    private BigDecimal fee;
    private String status;
    private String payway;
    private String pay_time;
    private String consultation_type;
    private String patient_hotdisease;
    private String create_time;
    private String search_way;
    private String trade_no;
    private String out_trade_no;
    private String comment_content;
    private String label;
    private Integer start_level;
    private Integer praise;
    private Integer session_id;
    private String order_time;
    private String hospital_name;
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
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

    public Integer getPatient_sex() {
        return patient_sex;
    }

    public void setPatient_sex(Integer patient_sex) {
        this.patient_sex = patient_sex;
    }

    public Integer getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(Integer patient_age) {
        this.patient_age = patient_age;
    }

    public String getPatient_area() {
        return patient_area;
    }

    public void setPatient_area(String patient_area) {
        this.patient_area = patient_area;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getPatient_ordertime() {
        return patient_ordertime;
    }

    public void setPatient_ordertime(String patient_ordertime) {
        this.patient_ordertime = patient_ordertime;
    }

    public String getPatient_describe() {
        return patient_describe;
    }

    public void setPatient_describe(String patient_describe) {
        this.patient_describe = patient_describe;
    }

    public String getPatient_pictures() {
        return patient_pictures;
    }

    public void setPatient_pictures(String patient_pictures) {
        this.patient_pictures = patient_pictures;
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

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getConsultation_type() {
        return consultation_type;
    }

    public void setConsultation_type(String consultation_type) {
        this.consultation_type = consultation_type;
    }

    public String getPatient_hotdisease() {
        return patient_hotdisease;
    }

    public void setPatient_hotdisease(String patient_hotdisease) {
        this.patient_hotdisease = patient_hotdisease;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSearch_way() {
        return search_way;
    }

    public void setSearch_way(String search_way) {
        this.search_way = search_way;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
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

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getSession_id() {
        return session_id;
    }

    public void setSession_id(Integer session_id) {
        this.session_id = session_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
