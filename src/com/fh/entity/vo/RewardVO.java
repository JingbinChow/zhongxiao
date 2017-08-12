package com.fh.entity.vo;

public class RewardVO {
	private  int realNum;
	private  int memberId;
	private  String recoreTime;
	private  String type;



	private  String team_sign;
	private  Integer leavl;

	public Integer getLeavl() {
		return leavl;
	}

	public void setLeavl(Integer leavl) {
		this.leavl = leavl;
	}

	public String getTeam_sign() {
		return team_sign;
	}

	public void setTeam_sign(String team_sign) {
		this.team_sign = team_sign;
	}

	public int getRealNum() {
		return realNum;
	}

	public void setRealNum(int realNum) {
		this.realNum = realNum;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getRecoreTime() {
		return recoreTime;
	}

	public void setRecoreTime(String recoreTime) {
		this.recoreTime = recoreTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
