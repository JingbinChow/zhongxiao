package com.fh.entity.bo;

public class TbScorerecordBO {
	private Integer sid;
	private Integer type;
	private Float equity;
	private Float cancellednum;
	private Integer successnum;
	private Integer status;
	private String createdate;
	private Integer userid;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Float getEquity() {
		return equity;
	}

	public void setEquity(Float equity) {
		this.equity = equity;
	}

	public Float getCancellednum() {
		return cancellednum;
	}

	public void setCancellednum(Float cancellednum) {
		this.cancellednum = cancellednum;
	}

	public Integer getSuccessnum() {
		return successnum;
	}

	public void setSuccessnum(Integer successnum) {
		this.successnum = successnum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
}
