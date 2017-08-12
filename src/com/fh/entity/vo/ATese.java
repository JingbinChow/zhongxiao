package com.fh.entity.vo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */
public class ATese {
    public static void main(String[] args) {
        String str="123465，";
        str=str.substring(0,str.length()-1);
        System.out.println(str);
        List<String> result = Arrays.asList(str.split(","));
        for (String s : result) {
            System.out.println(s);
        }
    }
}
