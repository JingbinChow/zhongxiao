package com.fh.entity.bo;

public class TbUserBO {
	private Integer userid;
	private String username;
	private String loginpwd;
	private String inviterid;
	private String mobile;
	private String teamsign;
	private String createTime;
	private String token;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginpwd() {
		return loginpwd;
	}

	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}

	public String getInviterid() {
		return inviterid;
	}

	public void setInviterid(String inviterid) {
		this.inviterid = inviterid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTeamsign() {
		return teamsign;
	}

	public void setTeamsign(String teamsign) {
		this.teamsign = teamsign;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
