package com.fh.entity.zxzq;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 流通币提现实体类
 * Created by Administrator on 2017/3/20 0020.
 */
public class ZxzqExtract {
    private int id;
    private int memberid;
    private String oddnumbers;
    private BigDecimal cash;
    private BigDecimal frozen;
    private BigDecimal rate;
    private int status;
    private Timestamp ctime;
    private Timestamp mtime;
    private String remark;
    private String createAddress;
    private String modifyAddress;

    public ZxzqExtract() {
    }

    public ZxzqExtract(int id, int memberid, String oddnumbers, BigDecimal cash, BigDecimal frozen, BigDecimal rate, int status, Timestamp ctime, Timestamp mtime, String remark, String createAddress, String modifyAddress) {
        this.id = id;
        this.memberid = memberid;
        this.oddnumbers = oddnumbers;
        this.cash = cash;
        this.frozen = frozen;
        this.rate = rate;
        this.status = status;
        this.ctime = ctime;
        this.mtime = mtime;
        this.remark = remark;
        this.createAddress = createAddress;
        this.modifyAddress = modifyAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getOddnumbers() {
        return oddnumbers;
    }

    public void setOddnumbers(String oddnumbers) {
        this.oddnumbers = oddnumbers;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCtime() {
        return ctime;
    }

    public void setCtime(Timestamp ctime) {
        this.ctime = ctime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateAddress() {
        return createAddress;
    }

    public void setCreateAddress(String createAddress) {
        this.createAddress = createAddress;
    }

    public String getModifyAddress() {
        return modifyAddress;
    }

    public void setModifyAddress(String modifyAddress) {
        this.modifyAddress = modifyAddress;
    }

    public Timestamp getMtime() {
        return mtime;
    }

    public void setMtime(Timestamp mtime) {
        this.mtime = mtime;
    }
}
