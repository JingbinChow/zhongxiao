package com.fh.entity.zxys;

/**
 * Created by Administrator on 2016/12/22 0022.
 */
public class BindBankCardBO {
    private Integer id;
    private Integer member_id;
    private String bankcard;
    private String bankname;
    private String banktype;
    private String bankuser;
    private String bankmobile;
    private String bindtime;

    public BindBankCardBO() {
    }

    public BindBankCardBO(Integer id, Integer member_id, String bankcard, String bankname, String banktype, String bankuser, String bankmobile, String bindtime) {
        this.id = id;
        this.member_id = member_id;
        this.bankcard = bankcard;
        this.bankname = bankname;
        this.banktype = banktype;
        this.bankuser = bankuser;
        this.bankmobile = bankmobile;
        this.bindtime = bindtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getBankuser() {
        return bankuser;
    }

    public void setBankuser(String bankuser) {
        this.bankuser = bankuser;
    }

    public String getBankmobile() {
        return bankmobile;
    }

    public void setBankmobile(String bankmobile) {
        this.bankmobile = bankmobile;
    }

    public String getBindtime() {
        return bindtime;
    }

    public void setBindtime(String bindtime) {
        this.bindtime = bindtime;
    }
}
