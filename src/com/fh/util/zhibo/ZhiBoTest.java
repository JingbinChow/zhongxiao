package com.fh.util.zhibo;

import com.fh.entity.zxys.ZxysDirectSeedingBo;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZhiBoTest {


    public static void main(String[] args) throws UnsupportedEncodingException {
//        CreateDirectseeding createDirectseeding =new CreateDirectseeding();
        QueryDirectseeding queryDirectseeding =new QueryDirectseeding();
        try {
//            ZxysDirectSeedingBo zxysDirectSeedingBo= createDirectseeding.createDirectseeding(122, "测试1", "0");
            queryDirectseeding.queryDirectseeding();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}