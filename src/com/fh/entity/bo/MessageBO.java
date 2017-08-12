package com.fh.entity.bo;

public class MessageBO {
	private String account; //用户名
	private String pswd;//密码
	private String mobiles;//手机号码
	private String content;//内容
	private boolean needstatus;//是否需要状态报告
	private  String product; //产品ID(不用填写)
	private  String extno;//扩展码(不用填写)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}


	public boolean isNeedstatus() {
		return needstatus;
	}

	public void setNeedstatus(boolean needstatus) {
		this.needstatus = needstatus;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getExtno() {
		return extno;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}
}
