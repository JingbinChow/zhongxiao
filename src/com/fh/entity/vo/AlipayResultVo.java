package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class AlipayResultVo {
    private String token;
    private String out_trade_no;
    private String trade_no;
    private String app_id;
    private String total_amount;
    private String seller_id;
    private String msg;
    private String code;
    private String is_success;
    private String complete;


    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIs_success() {
        return is_success;
    }

    public void setIs_success(String is_success) {
        this.is_success = is_success;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "AlipayResultVo{" +
                "token='" + token + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", app_id='" + app_id + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", is_success='" + is_success + '\'' +
                '}';
    }
}
