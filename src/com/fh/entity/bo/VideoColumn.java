package com.fh.entity.bo;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class VideoColumn {
    private int video_column_id;
    private String video_column_name;
    private String video_column_image_url;
    private String ishide;

    public VideoColumn() {
    }

    public VideoColumn(int video_column_id, String video_column_name, String video_column_image_url, String ishide) {
        this.video_column_id = video_column_id;
        this.video_column_name = video_column_name;
        this.video_column_image_url = video_column_image_url;
        this.ishide = ishide;
    }

    public int getVideo_column_id() {
        return video_column_id;
    }

    public void setVideo_column_id(int video_column_id) {
        this.video_column_id = video_column_id;
    }

    public String getVideo_column_name() {
        return video_column_name;
    }

    public void setVideo_column_name(String video_column_name) {
        this.video_column_name = video_column_name;
    }

    public String getVideo_column_image_url() {
        return video_column_image_url;
    }

    public void setVideo_column_image_url(String video_column_image_url) {
        this.video_column_image_url = video_column_image_url;
    }

    public String getIshide() {
        return ishide;
    }

    public void setIshide(String ishide) {
        this.ishide = ishide;
    }
}
