package com.fh.entity.bo;

public class VideoBO {
	private  String  video_id; //'视频ID'
	private  String  video_name;//'视频名称'
	private  String  video_url;//''视频地址''
	private  String  video_image_url;//'视频缩略图地址'
	private  String  video_type;//'视频栏目'
	private  String  isdelete;//'是否删除\r\n0-未删除\r\n1-已删除\r\n'
	private  String  ishide;//'是否隐藏\r\n0-显示\r\n1-隐藏'
	private  String  token; //'视频ID'
	private int pageIndex;
	private int pageSize;
	private String video_imagesmall_url;

	public String getVideo_imagesmall_url() {
		return video_imagesmall_url;
	}

	public void setVideo_imagesmall_url(String video_imagesmall_url) {
		this.video_imagesmall_url = video_imagesmall_url;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVideo_id() {
		return video_id;
	}

	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}

	public String getVideo_name() {
		return video_name;
	}

	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getVideo_image_url() {
		return video_image_url;
	}

	public void setVideo_image_url(String video_image_url) {
		this.video_image_url = video_image_url;
	}

	public String getVideo_type() {
		return video_type;
	}

	public void setVideo_type(String video_type) {
		this.video_type = video_type;
	}

	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}

	public String getIshide() {
		return ishide;
	}

	public void setIshide(String ishide) {
		this.ishide = ishide;
	}
}
