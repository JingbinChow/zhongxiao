package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/10/25 0025.
 */
public class DoctorBo {
    private int id;
    private String doctor_name;
    private String department;
    private String  level;
    private String  affiliated_hospital;
    private String price;
    private String clinic;
    private String medical_background;
    private String medical_education_background;
    private String work_is_introduced;
    private String thumb;
    private String  area_name;
    private int  area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public String getAffiliated_hospital() {
        return affiliated_hospital;
    }

    public void setAffiliated_hospital(String affiliated_hospital) {
        this.affiliated_hospital = affiliated_hospital;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getMedical_background() {
        return medical_background;
    }

    public void setMedical_background(String medical_background) {
        this.medical_background = medical_background;
    }

    public String getMedical_education_background() {
        return medical_education_background;
    }

    public void setMedical_education_background(String medical_education_background) {
        this.medical_education_background = medical_education_background;
    }

    public String getWork_is_introduced() {
        return work_is_introduced;
    }

    public void setWork_is_introduced(String work_is_introduced) {
        this.work_is_introduced = work_is_introduced;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
