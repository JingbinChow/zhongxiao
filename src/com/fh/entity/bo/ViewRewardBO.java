package com.fh.entity.bo;

/**
 * Created by admin on 2016-11-10.
 */
public class ViewRewardBO {
    private Integer member_id ;
    private String  member_name ;
    private String  team_sign ;
    private float reward;
    private float achievement;
    private int level ;
    private int leveladd ;
    private int reallevel ;
    private float amount  ;

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getTeam_sign() {
        return team_sign;
    }

    public void setTeam_sign(String team_sign) {
        this.team_sign = team_sign;
    }

    public float getReward() {
        return reward;
    }

    public void setReward(float reward) {
        this.reward = reward;
    }

    public float getAchievement() {
        return achievement;
    }

    public void setAchievement(float achievement) {
        this.achievement = achievement;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLeveladd() {
        return leveladd;
    }

    public void setLeveladd(int leveladd) {
        this.leveladd = leveladd;
    }

    public int getReallevel() {
        return reallevel;
    }

    public void setReallevel(int reallevel) {
        this.reallevel = reallevel;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
