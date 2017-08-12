package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class FeedBackVo {
    private String content;
    private String type;
    private long ftime;
    private int member_id;
    private String member_name;
    private String source;
    private String token;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getFtime() {
        return ftime;
    }

    public void setFtime(long ftime) {
        this.ftime = ftime;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
