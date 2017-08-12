package com.fh.util;

import java.util.Random;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class RandomCode {

    public static String getChar(){
        Random random = new Random();

        int x = random.nextInt(89999999);

        x=x+10000000;

        return String.valueOf(x);

    }
}
