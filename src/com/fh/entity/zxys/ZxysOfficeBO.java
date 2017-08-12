package com.fh.entity.zxys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/11/29.
 */
public class ZxysOfficeBO implements Serializable {

    private int id;
    private String office_name;
    private int office_parentid;
    private List<ZxysOfficeBO> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public int getOffice_parentid() {
        return office_parentid;
    }

    public void setOffice_parentid(int office_parentid) {
        this.office_parentid = office_parentid;
    }

    public List<ZxysOfficeBO> getList() {
        return list;
    }

    public void setList(List<ZxysOfficeBO> list) {
        this.list = list;
    }
}
