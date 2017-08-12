package com.fh.entity.zxlp;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class ZxlpCommonVo {
    private int member_id; //用户id
    private String member_name; //用户名
    private BigDecimal integration; //积分
    private String  order_time ; //下单时间
    private String order_dec; //订单时间
    private String sig; //订单时间
    private String outer_trn_id;//系统生成订单
    private String order_id; //订单号
    private BigDecimal remain_integration;//剩余积分
    private String member_uuid;

    public String getMember_uuid() {
        return member_uuid;
    }

    public void setMember_uuid(String member_uuid) {
        this.member_uuid = member_uuid;
    }

    public BigDecimal getRemain_integration() {
        return remain_integration;
    }

    public void setRemain_integration(BigDecimal remain_integration) {
        this.remain_integration = remain_integration;
    }


    public String getOuter_trn_id() {
        return outer_trn_id;
    }

    public void setOuter_trn_id(String outer_trn_id) {
        this.outer_trn_id = outer_trn_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }





    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public BigDecimal getIntegration() {
        return integration;
    }

    public void setIntegration(BigDecimal integration) {
        this.integration = integration;
    }

    public String getOrder_dec() {
        return order_dec;
    }

    public void setOrder_dec(String order_dec) {
        this.order_dec = order_dec;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
