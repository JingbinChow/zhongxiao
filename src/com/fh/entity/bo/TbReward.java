package com.fh.entity.bo;

public class TbReward {
	private int id;
	private String reward_id;
	private String userid;
	private String inviterid;
	private String inviterid_leval;
	private String reward_amount;
	private String reward_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReward_id() {
		return reward_id;
	}

	public void setReward_id(String reward_id) {
		this.reward_id = reward_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getInviterid() {
		return inviterid;
	}

	public void setInviterid(String inviterid) {
		this.inviterid = inviterid;
	}

	public String getInviterid_leval() {
		return inviterid_leval;
	}

	public void setInviterid_leval(String inviterid_leval) {
		this.inviterid_leval = inviterid_leval;
	}

	public String getReward_amount() {
		return reward_amount;
	}

	public void setReward_amount(String reward_amount) {
		this.reward_amount = reward_amount;
	}

	public String getReward_time() {
		return reward_time;
	}

	public void setReward_time(String reward_time) {
		this.reward_time = reward_time;
	}
}
