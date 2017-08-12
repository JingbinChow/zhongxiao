package com.fh.util.zhibo;


import com.fh.entity.angle.AngleCommonVO;
import com.fh.entity.zxys.ZxysDirectSeedingBo;
import net.sf.json.JSONObject;


import java.util.Iterator;
import java.util.Map;


public class CreateDirectseeding {


    public ZxysDirectSeedingBo  createDirectseeding(AngleCommonVO angleCommonVO){

         DirectSeeding directseeding =new DirectSeeding();
        ZxysDirectSeedingBo zxysDirectSeedingBo =new ZxysDirectSeedingBo();
        try {
            String a =directseeding.directSeeding("tszb"+"--"+String.valueOf(angleCommonVO.getMemberid()) ,"0");
            JSONObject  jasonObject = JSONObject.fromObject(a);
            Map map = (Map)jasonObject;
            JSONObject  jasonObject1  =(JSONObject)map.get("ret");
            Iterator<String> sIterator = jasonObject1.keys();
            while(sIterator.hasNext()){
                // 获得key
                String key = sIterator.next();
                // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                String value = jasonObject1.getString(key);

                if("httpPullUrl".equals(key)){
                    zxysDirectSeedingBo.setHttpPullUrl(value);
                }else if("hlsPullUrl".equals(key)){
                    zxysDirectSeedingBo.setHlsPullUrl(value);
                }else if("rtmpPullUrl".equals(key)){
                    zxysDirectSeedingBo.setRtmpPullUrl(value);
                }else if("name".equals(key)){
                    zxysDirectSeedingBo.setName(value);
                }else if("pushUrl".equals(key)){
                    zxysDirectSeedingBo.setPushUrl(value);
                }else if("ctime".equals(key)){
                    zxysDirectSeedingBo.setCtime(value);
                }else if("cid".equals(key)){
                    zxysDirectSeedingBo.setCid(value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return zxysDirectSeedingBo;

    }

}