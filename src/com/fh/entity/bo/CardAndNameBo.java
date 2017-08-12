package com.fh.entity.bo;

/**
 * Created by Administrator on 2016/12/8 0008.
 */
public class CardAndNameBo {
    private String idCard;
    private String trueName;
    private String mobile;

    public CardAndNameBo() {
    }

    public CardAndNameBo(String idCard, String trueName, String mobile) {
        this.idCard = idCard;
        this.trueName = trueName;
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
