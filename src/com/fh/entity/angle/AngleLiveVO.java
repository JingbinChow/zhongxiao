package com.fh.entity.angle;

import java.util.Date;

/**
 * Created by admin on 2017/2/14.
 */
public class AngleLiveVO {


    private String token;
    private int liveId;
    private int memberId;
    private String liveCity;
    private String liveDescribe;
    private String liveTopic;
    private String livePicture;
    private Date liveTime;
    private int status;
    private int liveCheck;
    private String liveChannel;
    private int liveroomNum;
    private String remark;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getLiveCity() {
        return liveCity;
    }

    public void setLiveCity(String liveCity) {
        this.liveCity = liveCity;
    }

    public String getLiveDescribe() {
        return liveDescribe;
    }

    public void setLiveDescribe(String liveDescribe) {
        this.liveDescribe = liveDescribe;
    }

    public String getLiveTopic() {
        return liveTopic;
    }

    public void setLiveTopic(String liveTopic) {
        this.liveTopic = liveTopic;
    }

    public String getLivePicture() {
        return livePicture;
    }

    public void setLivePicture(String livePicture) {
        this.livePicture = livePicture;
    }

    public Date getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Date liveTime) {
        this.liveTime = liveTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLiveCheck() {
        return liveCheck;
    }

    public void setLiveCheck(int liveCheck) {
        this.liveCheck = liveCheck;
    }

    public String getLiveChannel() {
        return liveChannel;
    }

    public void setLiveChannel(String liveChannel) {
        this.liveChannel = liveChannel;
    }

    public int getLiveroomNum() {
        return liveroomNum;
    }

    public void setLiveroomNum(int liveroomNum) {
        this.liveroomNum = liveroomNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
