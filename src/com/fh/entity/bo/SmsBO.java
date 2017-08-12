package com.fh.entity.bo;

public class SmsBO {
	private Integer log_id;
	private String  log_phone;
	private String  log_captcha;
	private String  log_ip;
	private String  log_msg;
	private Integer log_type;
	private Integer add_time;
    private Integer member_id;
	private String member_name;
	private String registrationtype;

	public String getRegistrationtype() {
		return registrationtype;
	}

	public void setRegistrationtype(String registrationtype) {
		this.registrationtype = registrationtype;
	}

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}

	public String getLog_phone() {
		return log_phone;
	}

	public void setLog_phone(String log_phone) {
		this.log_phone = log_phone;
	}

	public String getLog_captcha() {
		return log_captcha;
	}

	public void setLog_captcha(String log_captcha) {
		this.log_captcha = log_captcha;
	}

	public String getLog_ip() {
		return log_ip;
	}

	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}

	public String getLog_msg() {
		return log_msg;
	}

	public void setLog_msg(String log_msg) {
		this.log_msg = log_msg;
	}

	public Integer getLog_type() {
		return log_type;
	}

	public void setLog_type(Integer log_type) {
		this.log_type = log_type;
	}

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}

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
}
