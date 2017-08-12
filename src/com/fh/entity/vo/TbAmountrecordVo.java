package com.fh.entity.vo;

/**
 * 新提交积分充值订单记录
 * Created by Administrator on 2016/11/2.
 */
public class TbAmountrecordVo {
    private String token;
    private int eid;
    private int member_id;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    private int uid;
    private int type;
    private String date;
    private float amount;
    private int status;
    private String bankcard;
    private String operatorid;
    private String recordTime;
    private String banktransaction;
    private String bankName;
    private float margin;
    private float equity;
    private String declaration_date;
    private String declarationPrice;//报单金额
    private int declarationNum;//积分数
    private int insurance_order_id;
    private int real_num;
    private String remark;
    private String source;
    private int unit;
    private int isdelete;
    private String poundage;
    private String management;
    private int integral;
    private int box;
    private int pid;
    private int boxtotal;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getBoxtotal() {
        return boxtotal;
    }

    public void setBoxtotal(int boxtotal) {
        this.boxtotal = boxtotal;
    }




    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getBanktransaction() {
        return banktransaction;
    }

    public void setBanktransaction(String banktransaction) {
        this.banktransaction = banktransaction;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public float getEquity() {
        return equity;
    }

    public void setEquity(float equity) {
        this.equity = equity;
    }

    public String getDeclaration_date() {
        return declaration_date;
    }

    public void setDeclaration_date(String declaration_date) {
        this.declaration_date = declaration_date;
    }

    public String getDeclarationPrice() {
        return declarationPrice;
    }

    public void setDeclarationPrice(String declarationPrice) {
        this.declarationPrice = declarationPrice;
    }

    public int getDeclarationNum() {
        return declarationNum;
    }

    public void setDeclarationNum(int declarationNum) {
        this.declarationNum = declarationNum;
    }

    public int getInsurance_order_id() {
        return insurance_order_id;
    }

    public void setInsurance_order_id(int insurance_order_id) {
        this.insurance_order_id = insurance_order_id;
    }

    public int getReal_num() {
        return real_num;
    }

    public void setReal_num(int real_num) {
        this.real_num = real_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }
}
