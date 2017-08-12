package com.fh.entity.bo;

/**
 * app端主页bean
 * @auth 李荣洲
 * Created by Administrator on 2016/11/22 0022.
 */
public class IndexInfoBo {
    private int indexid;
    private String title;
    private String url;
    private String type;
    private String remark;
    private String turnid;
    private String status;
    private String term;
    private String classify;

    public IndexInfoBo() {
    }

    public IndexInfoBo(int indexid, String title, String url, String type, String remark, String turnid, String status, String term, String classify) {
        this.indexid = indexid;
        this.title = title;
        this.url = url;
        this.type = type;
        this.remark = remark;
        this.turnid = turnid;
        this.status = status;
        this.term = term;
        this.classify = classify;
    }

    public int getIndexid() {
        return indexid;
    }

    public void setIndexid(int indexid) {
        this.indexid = indexid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTurnid() {
        return turnid;
    }

    public void setTurnid(String turnid) {
        this.turnid = turnid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
}
