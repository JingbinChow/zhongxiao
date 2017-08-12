package com.fh.entity.bo;

public class ViewDeleteAmountrecordBO {

	private  Integer  uid ;
	private  String declaration_num;
	private  String time;
	private  Integer pack;
	private  Integer eid;

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getDeclaration_num() {
		return declaration_num;
	}

	public void setDeclaration_num(String declaration_num) {
		this.declaration_num = declaration_num;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}