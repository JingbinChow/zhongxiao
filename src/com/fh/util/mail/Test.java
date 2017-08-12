package com.fh.util.mail;

import com.fh.util.Tools;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class Test {
    public static void main(String[] args) {
       List<String> list= Tools.subTeamSign("000100020002000900110006");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
