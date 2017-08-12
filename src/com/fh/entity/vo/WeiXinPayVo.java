package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class WeiXinPayVo {
    private String out_trade_no;
    private String total_fee;
    private String detail;

    public WeiXinPayVo() {
    }

    public WeiXinPayVo(String out_trade_no, String total_fee, String detail) {
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
}
