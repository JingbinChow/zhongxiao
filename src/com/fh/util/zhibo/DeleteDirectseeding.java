package com.fh.util.zhibo;


import com.fh.entity.angle.AngleCommonVO;
import com.fh.entity.zxys.ZxysDirectSeedingBo;
import net.sf.json.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public class DeleteDirectseeding {


//    public void deleteDirectseeding(AngleCommonVO angleCommonVO)throws Exception {
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String url = "https://vcloud.163.com/app/channel/delete";
//        HttpPost httpPost = new HttpPost(url);
//
//        String appKey = "0b7315c7a8b1ae598c3163115a09d4c3";
//        String appSecret = "efc175067f9d";
//        String nonce =  String.valueOf(Math.round(Math.random()*10000000));
//        String curTime = String.valueOf((new Date()).getTime() / 1000L);
//        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
//
//        // 设置请求的header
//        httpPost.addHeader("AppKey", appKey);
//        httpPost.addHeader("Nonce", nonce);
//        httpPost.addHeader("CurTime", curTime);
//        httpPost.addHeader("CheckSum", checkSum);
//        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
//
//
//        // 设置请求的参数
////        StringEntity params = new StringEntity("{\"cid\":\""+angleCommonVO.getCid()+"}",Consts.UTF_8);
////        StringEntity params = new StringEntity("{\"cid\":\""+angleCommonVO.getCid()+"\"}",Consts.UTF_8);
////        httpPost.setEntity(params);
//
//        // 执行请求
//        HttpResponse response = httpClient.execute(httpPost);
//
//        // 打印执行结果
//        System.out.print(EntityUtils.toString(response.getEntity(), "utf-8"));
//    }
}