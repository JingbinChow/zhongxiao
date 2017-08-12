package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
public class WangPosVo {
    private int posrec_id;
    private String attach;
    private String  bp_id;
    private String buyer;
    private String cashier_trade_no;
    private String check_fee;
    private String device_en;
    private String input_charset;
    private String mcode;
    private String operator_name;
    private String out_trade_no;
    private String pay_fee;
    private String pay_info;
    private String pay_type;
    private String refund_amount;
    private String time_create;
    private String time_end;
    private String total_fee;
    private String trade_status;
    private String sign_type;
    private String sign;

    public WangPosVo() {
    }

    public WangPosVo(int posrec_id, String attach, String bp_id, String buyer, String cashier_trade_no, String check_fee, String device_en, String input_charset, String mcode, String operator_name, String out_trade_no, String pay_fee, String pay_info, String pay_type, String refund_amount, String time_create, String time_end, String total_fee, String trade_status, String sign_type, String sign) {
        this.posrec_id = posrec_id;
        this.attach = attach;
        this.bp_id = bp_id;
        this.buyer = buyer;
        this.cashier_trade_no = cashier_trade_no;
        this.check_fee = check_fee;
        this.device_en = device_en;
        this.input_charset = input_charset;
        this.mcode = mcode;
        this.operator_name = operator_name;
        this.out_trade_no = out_trade_no;
        this.pay_fee = pay_fee;
        this.pay_info = pay_info;
        this.pay_type = pay_type;
        this.refund_amount = refund_amount;
        this.time_create = time_create;
        this.time_end = time_end;
        this.total_fee = total_fee;
        this.trade_status = trade_status;
        this.sign_type = sign_type;
        this.sign = sign;
    }

    public int getPosrec_id() {
        return posrec_id;
    }

    public void setPosrec_id(int posrec_id) {
        this.posrec_id = posrec_id;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBp_id() {
        return bp_id;
    }

    public void setBp_id(String bp_id) {
        this.bp_id = bp_id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getCashier_trade_no() {
        return cashier_trade_no;
    }

    public void setCashier_trade_no(String cashier_trade_no) {
        this.cashier_trade_no = cashier_trade_no;
    }

    public String getCheck_fee() {
        return check_fee;
    }

    public void setCheck_fee(String check_fee) {
        this.check_fee = check_fee;
    }

    public String getDevice_en() {
        return device_en;
    }

    public void setDevice_en(String device_en) {
        this.device_en = device_en;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPay_fee() {
        return pay_fee;
    }

    public void setPay_fee(String pay_fee) {
        this.pay_fee = pay_fee;
    }

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
