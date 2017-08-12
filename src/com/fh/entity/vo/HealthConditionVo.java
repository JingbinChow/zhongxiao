package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class HealthConditionVo {
    private String token;
    private int condition_id;
    private int member_id;
    private String height;
    private String weight;
    private String is_married;
    private String smoking_status;
    private String smoke_ages;
    private String smoke_number;
    private String drink_status;
    private String diet_is_regular;
    private String is_sleep_normal;
    private String is_crap_normal;
    private String is_take_painkillers;
    private String modify_state;

    public HealthConditionVo() {
    }

    public HealthConditionVo(String token, int condition_id, int member_id, String height, String weight, String is_married, String smoking_status, String smoke_ages, String smoke_number, String drink_status, String diet_is_regular, String is_sleep_normal, String is_crap_normal, String is_take_painkillers, String modify_state) {
        this.token = token;
        this.condition_id = condition_id;
        this.member_id = member_id;
        this.height = height;
        this.weight = weight;
        this.is_married = is_married;
        this.smoking_status = smoking_status;
        this.smoke_ages = smoke_ages;
        this.smoke_number = smoke_number;
        this.drink_status = drink_status;
        this.diet_is_regular = diet_is_regular;
        this.is_sleep_normal = is_sleep_normal;
        this.is_crap_normal = is_crap_normal;
        this.is_take_painkillers = is_take_painkillers;
        this.modify_state = modify_state;
    }

    public String getModify_state() {
        return modify_state;
    }

    public void setModify_state(String modify_state) {
        this.modify_state = modify_state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCondition_id() {
        return condition_id;
    }

    public void setCondition_id(int condition_id) {
        this.condition_id = condition_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getIs_married() {
        return is_married;
    }

    public void setIs_married(String is_married) {
        this.is_married = is_married;
    }

    public String getSmoking_status() {
        return smoking_status;
    }

    public void setSmoking_status(String smoking_status) {
        this.smoking_status = smoking_status;
    }

    public String getSmoke_ages() {
        return smoke_ages;
    }

    public void setSmoke_ages(String smoke_ages) {
        this.smoke_ages = smoke_ages;
    }

    public String getSmoke_number() {
        return smoke_number;
    }

    public void setSmoke_number(String smoke_number) {
        this.smoke_number = smoke_number;
    }

    public String getDrink_status() {
        return drink_status;
    }

    public void setDrink_status(String drink_status) {
        this.drink_status = drink_status;
    }

    public String getDiet_is_regular() {
        return diet_is_regular;
    }

    public void setDiet_is_regular(String diet_is_regular) {
        this.diet_is_regular = diet_is_regular;
    }

    public String getIs_sleep_normal() {
        return is_sleep_normal;
    }

    public void setIs_sleep_normal(String is_sleep_normal) {
        this.is_sleep_normal = is_sleep_normal;
    }

    public String getIs_crap_normal() {
        return is_crap_normal;
    }

    public void setIs_crap_normal(String is_crap_normal) {
        this.is_crap_normal = is_crap_normal;
    }

    public String getIs_take_painkillers() {
        return is_take_painkillers;
    }

    public void setIs_take_painkillers(String is_take_painkillers) {
        this.is_take_painkillers = is_take_painkillers;
    }
}

