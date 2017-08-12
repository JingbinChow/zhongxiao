package com.fh.entity.angle;

import java.util.Date;

/**
 * Created by admin on 2017/2/14.
 */
public class AngleMessageBO {

    private int live_id;
    private int member_id;
    private String live_city;
    private String live_describe;
    private String live_topic;
    private String live_picture;
    private Date live_time;
    private int status;
//    private int live_check;
    private String live_channel;
    private int liveroom_num;
//    private String remark;

    private  String cid;
    private  String pushurl;
    private  String rtmppullurl;
    private  String picture;
    private  Integer live_status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private  String code;

    public Integer getLive_status() {
        return live_status;
    }

    public void setLive_status(Integer live_status) {
        this.live_status = live_status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRtmppullurl() {
        return rtmppullurl;
    }

    public void setRtmppullurl(String rtmppullurl) {
        this.rtmppullurl = rtmppullurl;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPushurl() {
        return pushurl;
    }

    public void setPushurl(String pushurl) {
        this.pushurl = pushurl;
    }

    public int getLive_id() {
        return live_id;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getLive_city() {
        return live_city;
    }

    public void setLive_city(String live_city) {
        this.live_city = live_city;
    }

    public String getLive_describe() {
        return live_describe;
    }

    public void setLive_describe(String live_describe) {
        this.live_describe = live_describe;
    }

    public String getLive_topic() {
        return live_topic;
    }

    public void setLive_topic(String live_topic) {
        this.live_topic = live_topic;
    }

    public String getLive_picture() {
        return live_picture;
    }

    public void setLive_picture(String live_picture) {
        this.live_picture = live_picture;
    }

    public Date getLive_time() {
        return live_time;
    }

    public void setLive_time(Date live_time) {
        this.live_time = live_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    public int getLive_check() {
//        return live_check;
//    }
//
//    public void setLive_check(int live_check) {
//        this.live_check = live_check;
//    }


    public String getLive_channel() {
        return live_channel;
    }

    public void setLive_channel(String live_channel) {
        this.live_channel = live_channel;
    }

    public int getLiveroom_num() {
        return liveroom_num;
    }

    public void setLiveroom_num(int liveroom_num) {
        this.liveroom_num = liveroom_num;
    }

//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
}
