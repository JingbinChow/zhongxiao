package com.fh.entity.bo;

/**
 * Created by admin on 2016/10/31.
 */
public class TbVariableBO {
    private int id;
    private String name;
    private String value;
    private String remark;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public String getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

