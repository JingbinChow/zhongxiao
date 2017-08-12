package com.fh.entity.bo;

public class UserInfoBO {
	private String member_name ;
	private Integer member_id;
	private String team_sign;
	private String inviter_id;
	private String token;
	private String member_bankcard;
	private String member_passwd;
	private String member_mobile;
	private String member_email;
	private String member_truename;
	private String member_address;
	private Integer member_sex;
	private String member_idcard;
	private String member_bankname;
	private String  member_time;
	private String  member_old_login_time;
	private String  member_login_time;
	private String member_area;
	private String member_cardtype;
	private Integer member_login_num;
	private String achievement;
	private String member_paypwd;
	private String securities_team;
	private String securities_id;
	private double money;
	private int member_age;
	private int member_points;

	//网络标识
	private  String network_team;
	private  String network_id;

	//用户标识member_sn
	private String member_sn;

	// 分页数据
	private int pageIndex;  // 页码
	private int pageSize;   // 页容量
	private int startIndex;

	public String getMember_sn() {
		return member_sn;
	}

	public void setMember_sn(String member_sn) {
		this.member_sn = member_sn;
	}

	public String getNetwork_id() {
		return network_id;
	}

	public void setNetwork_id(String network_id) {
		this.network_id = network_id;
	}

	public String getNetwork_team() {
		return network_team;
	}

	public void setNetwork_team(String network_team) {
		this.network_team = network_team;
	}

	public String getSecurities_id() {
		return securities_id;
	}

	public void setSecurities_id(String securities_id) {
		this.securities_id = securities_id;
	}

	public String getMember_paypwd() {
		return member_paypwd;
	}

	public void setMember_paypwd(String member_paypwd) {
		this.member_paypwd = member_paypwd;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public Integer getMember_id() {
		return member_id;
	}

	public void setMember_id(Integer member_id) {
		this.member_id = member_id;
	}

	public String getTeam_sign() {
		return team_sign;
	}

	public void setTeam_sign(String team_sign) {
		this.team_sign = team_sign;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMember_bankcard() {
		return member_bankcard;
	}

	public void setMember_bankcard(String member_bankcard) {
		this.member_bankcard = member_bankcard;
	}

	public String getMember_passwd() {
		return member_passwd;
	}

	public void setMember_passwd(String member_passwd) {
		this.member_passwd = member_passwd;
	}

	public String getMember_mobile() {
		return member_mobile;
	}

	public void setMember_mobile(String member_mobile) {
		this.member_mobile = member_mobile;
	}

	public String getMember_email() {
		return member_email;
	}

	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}

	public String getMember_truename() {
		return member_truename;
	}

	public void setMember_truename(String member_truename) {
		this.member_truename = member_truename;
	}

	public String getMember_address() {
		return member_address;
	}

	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}

	public Integer getMember_sex() {
		return member_sex;
	}

	public void setMember_sex(Integer member_sex) {
		this.member_sex = member_sex;
	}

	public String getMember_idcard() {
		return member_idcard;
	}

	public void setMember_idcard(String member_idcard) {
		this.member_idcard = member_idcard;
	}

	public String getMember_bankname() {
		return member_bankname;
	}

	public void setMember_bankname(String member_bankname) {
		this.member_bankname = member_bankname;
	}

	public String getMember_time() {
		return member_time;
	}

	public void setMember_time(String member_time) {
		this.member_time = member_time;
	}

	public String getMember_old_login_time() {
		return member_old_login_time;
	}

	public void setMember_old_login_time(String member_old_login_time) {
		this.member_old_login_time = member_old_login_time;
	}

	public String getMember_login_time() {
		return member_login_time;
	}

	public void setMember_login_time(String member_login_time) {
		this.member_login_time = member_login_time;
	}
	public String getMember_area() {
		return member_area;
	}

	public void setMember_area(String member_area) {
		this.member_area = member_area;
	}

	public String getInviter_id() {
		return inviter_id;
	}

	public void setInviter_id(String inviter_id) {
		this.inviter_id = inviter_id;
	}

	public String getMember_cardtype() {
		return member_cardtype;
	}

	public void setMember_cardtype(String member_cardtype) {
		this.member_cardtype = member_cardtype;
	}

	public Integer getMember_login_num() {
		return member_login_num;
	}

	public void setMember_login_num(Integer member_login_num) {
		this.member_login_num = member_login_num;
	}

	public String getSecurities_team() {
		return securities_team;
	}

	public void setSecurities_team(String securities_team) {
		this.securities_team = securities_team;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getMember_age() {
		return member_age;
	}

	public void setMember_age(int member_age) {
		this.member_age = member_age;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartIndex() {
		return (pageIndex - 1) * pageSize;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getMember_points() {
		return member_points;
	}

	public void setMember_points(int member_points) {
		this.member_points = member_points;
	}
}
