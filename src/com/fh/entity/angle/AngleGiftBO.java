package com.fh.entity.angle;

import java.math.BigDecimal;

/**
 * Created by admin on 2017/2/14.
 * 直播礼物
 */
public class AngleGiftBO {
    private String gift_name;
    private BigDecimal gift_price;
    private String gift_pic;

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    public BigDecimal getGift_price() {
        return gift_price;
    }

    public void setGift_price(BigDecimal gift_price) {
        this.gift_price = gift_price;
    }

    public String getGift_pic() {
        return gift_pic;
    }

    public void setGift_pic(String gift_pic) {
        this.gift_pic = gift_pic;
    }
}
