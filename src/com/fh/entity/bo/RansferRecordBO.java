package com.fh.entity.bo;

import java.util.Date;

public class RansferRecordBO {
	private Integer  ransferid;
	private Integer  member_id;

	private String  roll_out;
	private String  roll_inot;

	private String dateandtime;
	private String remark;

	private String status;
	private double transfer_points;
	private double poundage;
	private double actual_number;
	private String  member_name;
	private String  member_truename;


	public String getMember_truename() {
		return member_truename;
	}

	public void setMember_truename(String member_truename) {
		this.member_truename = member_truename;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public double getTransfer_points() {
		return transfer_points;
	}

	public void setTransfer_points(double transfer_points) {
		this.transfer_points = transfer_points;
	}

	public double getPoundage() {
		return poundage;
	}

	public void setPoundage(double poundage) {
		this.poundage = poundage;
	}

	public double getActual_number() {
		return actual_number;
	}

	public void setActual_number(double actual_number) {
		this.actual_number = actual_number;
	}

	public Integer getRansferid() {
		return ransferid;
	}

	public void setRansferid(Integer ransferid) {
		this.ransferid = ransferid;
	}

	public Integer getMember_id() {
		return member_id;
	}

	public void setMember_id(Integer member_id) {
		this.member_id = member_id;
	}



	public String getRoll_out() {
		return roll_out;
	}

	public void setRoll_out(String roll_out) {
		this.roll_out = roll_out;
	}

	public String getRoll_inot() {
		return roll_inot;
	}

	public void setRoll_inot(String roll_inot) {
		this.roll_inot = roll_inot;
	}


	public String getDateandtime() {
		return dateandtime;
	}

	public void setDateandtime(String dateandtime) {
		this.dateandtime = dateandtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
