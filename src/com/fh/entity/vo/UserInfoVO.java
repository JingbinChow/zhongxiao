package com.fh.entity.vo;

public class UserInfoVO {

	private String userName;
	private String passWord;
	private String token;
	private String email;
	private String idcard;
	private String name;
	private String inviter;
	private String address;
	private String mobile;
	private String bankName;
	private String bankcard;
	private String sex;
	private String area;
	private String team_sign;
	private String member_time;
	private String member_login_time;
	private String member_old_login_time;
	private String newpassWord;
	private String cardType;
	private String mobile_type;
	private String mobile_ip;
	private String gps;
	private String member_paypwd;
	private String isSuccess;
	private String code;
	// 证券推荐人id
	private String securities_id;

	// 分页数据
	private int pageIndex;  // 页码
	private int pageSize;   // 页容量
	private int startIndex;


	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMember_paypwd() {
		return member_paypwd;
	}

	public void setMember_paypwd(String member_paypwd) {
		this.member_paypwd = member_paypwd;
	}

	public String getTeam_sign() {
		return team_sign;
	}

	public void setTeam_sign(String team_sign) {
		this.team_sign = team_sign;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMember_time() {
		return member_time;
	}

	public void setMember_time(String member_time) {
		this.member_time = member_time;
	}

	public String getMember_login_time() {
		return member_login_time;
	}

	public void setMember_login_time(String member_login_time) {
		this.member_login_time = member_login_time;
	}

	public String getMember_old_login_time() {
		return member_old_login_time;
	}

	public void setMember_old_login_time(String member_old_login_time) {
		this.member_old_login_time = member_old_login_time;
	}

	public String getNewpassWord() {
		return newpassWord;
	}

	public void setNewpassWord(String newpassWord) {
		this.newpassWord = newpassWord;
	}

	public String getMobile_type() {
		return mobile_type;
	}

	public void setMobile_type(String mobile_type) {
		this.mobile_type = mobile_type;
	}

	public String getMobile_ip() {
		return mobile_ip;
	}

	public void setMobile_ip(String mobile_ip) {
		this.mobile_ip = mobile_ip;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getSecurities_id() {
		return securities_id;
	}

	public void setSecurities_id(String securities_id) {
		this.securities_id = securities_id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
