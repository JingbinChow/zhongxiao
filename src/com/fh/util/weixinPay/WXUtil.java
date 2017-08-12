package com.fh.util.weixinPay;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class WXUtil {
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
