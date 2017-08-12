package com.fh.entity.angle;

/**
 * Created by admin on 2017/2/14.
 */
public class AngleInfoBO {
      private  Integer member_id;
      private  String member_portrait; //头像
      private  String member_nickname; //昵称
      private  Integer live_status;//直播状态
      private  Integer liveroom_num;//直播间人数

    public Integer getLiveroom_num() {
        return liveroom_num;
    }

    public void setLiveroom_num(Integer liveroom_num) {
        this.liveroom_num = liveroom_num;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getMember_portrait() {
        return member_portrait;
    }

    public void setMember_portrait(String member_portrait) {
        this.member_portrait = member_portrait;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public Integer getLive_status() {
        return live_status;
    }

    public void setLive_status(Integer live_status) {
        this.live_status = live_status;
    }
}
