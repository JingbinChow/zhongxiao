package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class QueryPageVo {
    private int uid;
    private int eid;
    private int pageIndex;
    private int pageSize;
    private int startIndex;
    private String token;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getStartIndex() {
        return (pageIndex - 1) * pageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }


}
