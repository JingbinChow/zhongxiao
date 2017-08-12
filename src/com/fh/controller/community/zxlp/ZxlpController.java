package com.fh.controller.community.zxlp;

import com.fh.entity.bo.TokenBO;
import com.fh.entity.bo.UserInfoBO;
import com.fh.entity.vo.TokenVo;
import com.fh.entity.vo.UpdateUserInfoVo;
import com.fh.entity.vo.UserInfoVO;
import com.fh.entity.vo.WithdrawCashVo;
import com.fh.entity.zxlp.ZxlpCommonVo;
import com.fh.entity.zxzq.*;
import com.fh.service.community.securities.SecuritiesService;
import com.fh.service.community.zxlp.ZxlpService;
import com.fh.util.MD5;
import com.fh.util.RandomCode;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.fh.util.sign.Base64;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/zxlp")

public class ZxlpController {
    @Resource(name = "zxlpService")
    private ZxlpService zxlpService;


    //查询积分
    @RequestMapping(value = "queryIntegration")
    @ResponseBody
    public Map<String, Object> queryIntegration(@RequestBody ZxlpCommonVo zxlpCommonVo) {
        Map<String, Object> modelMap = Tools.errMessageSystemZxlp();
        try {
            //验签
            String str1  =zxlpCommonVo.getSig();//前台的
            String str2=zxlpCommonVo.getMember_uuid()+"&"+"zhongxiao";//后台
            str2=Tools.getBASE64(str2).replaceAll("[\\s*\t\n\r]", "");
            str2=MD5.md5(str2);
            if(str1.equals(str2)){
                ZxlpCommonVo  zxlpCommonVoNew =zxlpService.queryIntegration(zxlpCommonVo);
                if(zxlpCommonVoNew == null){
                    modelMap.put("message", "用户不存在");
                    modelMap.put("code", "11");
                    modelMap.put("data", null);
                    return modelMap;
                }else{
                    modelMap.put("message", "查询成功");
                    modelMap.put("code", "00");
                    modelMap.put("data", zxlpCommonVoNew);
                    return modelMap;
                }
            }else {
                modelMap.put("message", "签名错误");
                modelMap.put("code", "99");
                modelMap.put("data", null);
                return modelMap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelMap;
    }

    //下单
    @RequestMapping(value = "zxlpOrder")
    @ResponseBody
    public Map<String, Object> zxlpOrder(@RequestBody ZxlpCommonVo zxlpCommonVo) {
        Map<String, Object> modelMap = Tools.errMessageSystemZxlp();
        try {
            //验签
            String str1  =zxlpCommonVo.getSig();//前台的
            String str2=zxlpCommonVo.getMember_uuid()+"&"+zxlpCommonVo.getOrder_id()+"&"+zxlpCommonVo.getOrder_time()+"&"+zxlpCommonVo.getIntegration()+"&"+zxlpCommonVo.getOrder_dec()+"&"+"zhongxiao";
            str2=Tools.getBASE64(str2).replaceAll("[\\s*\t\n\r]", "");
            str2=MD5.md5(str2);
            //判断签名是否错误
            if(str1.equals(str2)){
                ZxlpCommonVo  zxlpCommonVoNew =zxlpService.queryIntegration(zxlpCommonVo);
                zxlpCommonVo.setMember_id(zxlpCommonVoNew.getMember_id());
                //判断用户是否存在
                if(zxlpCommonVoNew == null){
                    modelMap.put("message", "用户不存在");
                    modelMap.put("code", "11");
                    modelMap.put("data", null);
                    return modelMap;
                }else{
                    //判断积分时候足够
                    int i =zxlpCommonVoNew.getIntegration().compareTo(zxlpCommonVo.getIntegration());
                    // -1 为持有积分小于下单所需积分
                    if(i == -1){
                        modelMap.put("message", "余额不足");
                        modelMap.put("code", "12");
                        modelMap.put("data", null);
                        return modelMap;
                    }else{
                        //计算剩余积分
                        zxlpCommonVo.setRemain_integration(zxlpCommonVoNew.getIntegration().subtract(zxlpCommonVo.getIntegration()));
                        //下单
                        zxlpService.zxlpOrder(zxlpCommonVo);
                        //积分扣除
                        zxlpService.zxlpIntegrationBack(zxlpCommonVo);

                        //查询订单返回
                        ZxlpCommonVo zxlpQury =zxlpService.queryOrder(zxlpCommonVo);

                        modelMap.put("message", "下单成功");
                        modelMap.put("code", "00");
                        modelMap.put("data", zxlpQury);
                        return modelMap;
                    }
                }
            }else {
                modelMap.put("message", "签名错误");
                modelMap.put("code", "99");
                modelMap.put("data", null);
                return modelMap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelMap;
    }

    //撤回订单
    @RequestMapping(value = "orderBack")
    @ResponseBody
    public Map<String, Object> orderBack(@RequestBody ZxlpCommonVo zxlpCommonVo) {
        Map<String, Object> modelMap = Tools.errMessageSystemZxlp();
        try {
            //验签
            String str1  =zxlpCommonVo.getSig();//前台的
//            String str2 =null;//后台
            String str2=zxlpCommonVo.getMember_uuid()+"&"+zxlpCommonVo.getOrder_id()+"&"+"zhongxiao";
            str2=Tools.getBASE64(str2).replaceAll("[\\s*\t\n\r]", "");
            str2=MD5.md5(str2);
            if(str1.equals(str2)){
                //判断用户是否存在
                ZxlpCommonVo  zxlpCommonVoNew =zxlpService.queryIntegration(zxlpCommonVo);
                zxlpCommonVo.setMember_id(zxlpCommonVoNew.getMember_id());
                if(zxlpCommonVoNew == null){
                    modelMap.put("message", "用户不存在");
                    modelMap.put("code", "11");
                    modelMap.put("data", null);
                    return modelMap;
                }else{
                    //判断订单是否存在
                    ZxlpCommonVo zxlpQury =zxlpService.queryOrder(zxlpCommonVo);
                    if(zxlpQury ==null){
                        modelMap.put("message", "订单不存在");
                        modelMap.put("code", "14");
                        modelMap.put("data", null);
                        return modelMap;
                    }else{
                        //更改状态为2 退单状态
                        zxlpService.orderBack(zxlpCommonVo);
                        //查询剩余积分加上扣除积分还原
                        zxlpCommonVo.setIntegration(zxlpCommonVoNew.getIntegration().add(zxlpQury.getIntegration()));
                        zxlpService.zxlpIntegrationAdd(zxlpCommonVo);
                        modelMap.put("message", "返还成功");
                        modelMap.put("code", "00");
                        modelMap.put("data", null);
                        return modelMap;
                    }

                }
            }else {
                modelMap.put("message", "签名错误");
                modelMap.put("code", "99");
                modelMap.put("data", null);
                return modelMap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelMap;
    }





}
