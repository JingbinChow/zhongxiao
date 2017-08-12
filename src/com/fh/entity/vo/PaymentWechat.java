package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/12/12 0012.
 */
public class PaymentWechat {
    private String out_trade_no;
    private String transaction_id;
    private int total_fee;
    private String time_end;
    private String trade_state;
    private String trade_mode;
    private String return_code;
    private String appid;
    private String device_info;

    public PaymentWechat() {
    }

    public PaymentWechat(String out_trade_no, String transaction_id, int total_fee, String time_end, String trade_state, String trade_mode, String return_code) {
        this.out_trade_no = out_trade_no;
        this.transaction_id = transaction_id;
        this.total_fee = total_fee;
        this.time_end = time_end;
        this.trade_state = trade_state;
        this.trade_mode = trade_mode;
        this.return_code = return_code;
    }

    public PaymentWechat(String out_trade_no, String transaction_id, int total_fee, String time_end, String trade_state, String trade_mode, String return_code, String appid, String device_info) {
        this.out_trade_no = out_trade_no;
        this.transaction_id = transaction_id;
        this.total_fee = total_fee;
        this.time_end = time_end;
        this.trade_state = trade_state;
        this.trade_mode = trade_mode;
        this.return_code = return_code;
        this.appid = appid;
        this.device_info = device_info;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getTrade_mode() {
        return trade_mode;
    }

    public void setTrade_mode(String trade_mode) {
        this.trade_mode = trade_mode;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }
}
