package com.fh.controller.community.cash;

import com.fh.controller.base.BaseController;
import com.fh.entity.bo.*;
import com.fh.entity.vo.QueryAmounVo;
import com.fh.entity.vo.TokenVo;
import com.fh.entity.vo.UserInfoVO;
import com.fh.entity.vo.WithdrawCashVo;
import com.fh.service.community.cash.CashService;
import com.fh.service.community.integral.IntegralService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.util.Constants;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="/cash")
public class CashController extends BaseController {

	@Resource(name="cashService")
	private CashService cashService;

	@RequestMapping(value = "withdrawCash")
	@ResponseBody()
	public Map<String,Object> withdrawCash(@RequestBody WithdrawCashVo withdrawCashVo) throws Exception {
		Map<String,Object> data=new HashMap<String, Object>();
		Date date=new Date();
//		System.out.println(Tools.getWeek(date));
		if("星期二".equals(Tools.getWeek(date))||"星期三".equals(Tools.getWeek(date))) {
			data = cashService.withdrawCash(withdrawCashVo);
		}else{
			data.put("message","提现功能仅周二周三开放");
			data.put("code",1);
			data.put("data",null);

		}


		return data;
	}

	@RequestMapping(value = "cashlist")
	@ResponseBody()
	public Map<String,Object> cashlist(@RequestBody TokenVo vo){
		Map<String, Object> modelMap =Tools.errMessageSystem();

		try{
			if((StringUtil.isNotEmpty(vo.getToken()))){
				UserInfoBO  tokneBO=cashService.findToken(vo);
				if(tokneBO==null){
					modelMap.put("code",2);
					modelMap.put("data",null);
					modelMap.put("message","token已失效");
					return  modelMap ;
				}else{
					vo.setUserid(tokneBO.getMember_id()+"");
					List<QueryCashListBo> bo=cashService.findQueryCashListBo(vo);
						modelMap.put("code",0);
						modelMap.put("data",bo);
						modelMap.put("message","成功");
				}

			}
		}catch (Exception e){
			e.printStackTrace();
			return modelMap;
		}

		return modelMap;




	}



}
