package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/12/22 0022.
 */
public class ConnectionBO {
    private Integer constant_id;
    private String constant_key;
    private String constant_value;

    public ConnectionBO() {
    }

    public ConnectionBO(Integer constant_id, String constant_key, String constant_value) {
        this.constant_id = constant_id;
        this.constant_key = constant_key;
        this.constant_value = constant_value;
    }

    public Integer getConstant_id() {
        return constant_id;
    }

    public void setConstant_id(Integer constant_id) {
        this.constant_id = constant_id;
    }

    public String getConstant_key() {
        return constant_key;
    }

    public void setConstant_key(String constant_key) {
        this.constant_key = constant_key;
    }

    public String getConstant_value() {
        return constant_value;
    }

    public void setConstant_value(String constant_value) {
        this.constant_value = constant_value;
    }
}
