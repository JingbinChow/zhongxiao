package com.fh.entity.zxys;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
public class ZxysFinishRecordList {
    private Integer id;
    private String patient_sex;
    private String patient_area;
    private Integer status;
    private String patient_name;
    private Integer patient_age;
    private String  session_id;
    private String order_time;
    private String out_trade_no;
    private String patient_id;

    public ZxysFinishRecordList() {
    }

    public ZxysFinishRecordList(Integer id, String patient_sex, String patient_area, Integer status, String patient_name, Integer patient_age, String session_id, String order_time, String out_trade_no, String patient_id) {
        this.id = id;
        this.patient_sex = patient_sex;
        this.patient_area = patient_area;
        this.status = status;
        this.patient_name = patient_name;
        this.patient_age = patient_age;
        this.session_id = session_id;
        this.order_time = order_time;
        this.out_trade_no = out_trade_no;
        this.patient_id = patient_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatient_sex() {
        return patient_sex;
    }

    public void setPatient_sex(String patient_sex) {
        this.patient_sex = patient_sex;
    }

    public String getPatient_area() {
        return patient_area;
    }

    public void setPatient_area(String patient_area) {
        this.patient_area = patient_area;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public Integer getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(Integer patient_age) {
        this.patient_age = patient_age;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }
}
