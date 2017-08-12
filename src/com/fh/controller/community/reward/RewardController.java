package com.fh.controller.community.reward;

import com.fh.controller.base.BaseController;
import com.fh.entity.bo.*;
import com.fh.entity.vo.TokenVo;
import com.fh.entity.vo.UserInfoVO;
import com.fh.service.community.integral.IntegralService;
import com.fh.service.community.reward.RewardService;
import com.fh.service.finance.FinanceService;
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
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="/reward")

public class RewardController extends BaseController {

	@Resource(name="rewardService")
	private RewardService rewardService;

	@Resource(name = "financeService")
	private FinanceService financeService;
	/**
	 * 奖金转钱包
	 */




	@RequestMapping(value="findextractbonus")
	@ResponseBody

	public Map<String, Object> findextractbonus(@RequestBody TokenVo vo)throws Exception {
		Map<String, Object> modelMap =Tools.errMessageSystem();

		try{
			if((StringUtil.isNotEmpty(vo.getToken()))){
				UserInfoBO  tokneBO=rewardService.findToken(vo);
				if(tokneBO==null){
					modelMap.put("code",2);
					modelMap.put("data",null);
					modelMap.put("message","token已失效");
					return  modelMap ;
				}else{
					if((StringUtil.isNotEmpty(vo.getUserid()))){
					     RansferRecordBO bo =  new RansferRecordBO();
						Double rewardNow=Double.valueOf(vo.getReward()); //金额
						if("2".equals(vo.getType())){
							bo.setTransfer_points(rewardNow);
							bo.setPoundage(Constants.jiangjinToxiaofeijifen);
							bo.setMember_id(Integer.valueOf(vo.getUserid()));
							bo.setActual_number(rewardNow-(rewardNow*Constants.jiangjinToxiaofeijifen));

						}else if("3".equals(vo.getType())){
							bo.setTransfer_points(rewardNow);
							bo.setPoundage(Constants.jiangjinToqianbao);
							bo.setMember_id(Integer.valueOf(vo.getUserid()));
							bo.setActual_number(rewardNow-(rewardNow*Constants.jiangjinToqianbao));
						}
						modelMap.put("code",0);
						modelMap.put("data",bo);
						modelMap.put("message","成功");

					}

				}

			}
		}catch (Exception e){
			e.printStackTrace();
			return modelMap;
		}

		return modelMap;


	}


	@RequestMapping(value="extractbonus")
	@ResponseBody

	public Map<String, Object> extractbonus(@RequestBody TokenVo vo)throws Exception {

			Map<String, Object> modelMap = Tools.errMessageSystem();
			Date date = new Date();
			if ("星期二".equals(Tools.getWeek(date)) || "星期三".equals(Tools.getWeek(date))) {
				try {
					if ((StringUtil.isNotEmpty(vo.getToken()))) {
						UserInfoBO tokneBO = rewardService.findToken(vo);
						System.out.println(vo);
						vo.setUserid(tokneBO.getMember_id() + "");
						if (tokneBO == null) {
							modelMap.put("code", 2);
							modelMap.put("data", null);
							modelMap.put("message", "token已失效");
							return modelMap;
						} else {

							if (StringUtil.isNotEmpty(vo.getUserid())) {
								TbUserBO o = new TbUserBO();
								o.setUserid(Integer.valueOf(vo.getUserid()));
								TbuserbalanceBo fo = rewardService.findTbuserbalance(o);


								RansferRecordBO bo = new RansferRecordBO();
								Double rewardNow = Double.valueOf(fo.getRealreward()); //金额
								DecimalFormat df = new DecimalFormat("######0.00");

								if (fo.getRealreward() > 0) {
									if ("2".equals(vo.getType())) {
										bo.setTransfer_points(Double.valueOf(df.format(rewardNow)));
										bo.setPoundage(Double.valueOf(df.format(rewardNow * Constants.jiangjinToxiaofeijifen)));
										bo.setMember_id(Integer.valueOf(vo.getUserid()));
										bo.setActual_number(Double.valueOf(df.format(rewardNow - (rewardNow * Constants.jiangjinToxiaofeijifen))));
										//						   bo.setStatus("4");
										bo.setStatus("5");
										bo.setRoll_out("1");
										bo.setRoll_inot("2");
										bo.setRemark("奖金转账消费积分");
									} else if ("3".equals(vo.getType())) {
										bo.setTransfer_points(Double.valueOf(df.format(rewardNow)));
										bo.setPoundage(Double.valueOf(df.format(rewardNow * Constants.jiangjinToqianbao)));
										bo.setMember_id(Integer.valueOf(vo.getUserid()));
//												bo.setActual_number(Double.valueOf(rewardNow - (rewardNow * Constants.jiangjinToxiaofeijifen))));
										bo.setActual_number(Double.valueOf(df.format(rewardNow - (rewardNow * Constants.jiangjinToqianbao))));
										//						   bo.setStatus("4");
										bo.setStatus("6");
										bo.setRoll_out("1");
										bo.setRoll_inot("3");
										bo.setRemark("奖金转账钱包");
									}


									rewardService.setRecordBO(bo);//
									rewardService.setZore(bo);
									/***************************************财务确认*********************************************/

									//					   System.out.println(rewardService.findMemberName(vo));

									bo.setMember_name(rewardService.findMemberName(vo));

									financeService.confirmRecord(bo);

								} else {
									modelMap.put("code", 1);
									modelMap.put("data", null);
									modelMap.put("message", "账户没有可用余额,请刷新后重新使用");
									return modelMap;

								}
							} else {
								return modelMap;
							}
							modelMap.put("code", 0);
							modelMap.put("data", null);
							modelMap.put("message", "转账成功");
							return modelMap;

						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					return modelMap;
				}
			} else {
				modelMap.put("code", 1);
				modelMap.put("data", null);
				modelMap.put("message", "本功能仅星期二、星期三对外开放");
				return modelMap;
			}



		return modelMap;
}

	@RequestMapping(value="queryRewardListInfo")
	@ResponseBody

	public Map<String, Object> queryRewardListInfo(@RequestBody TokenVo vo)throws Exception {

		Map<String, Object> modelMap =Tools.errMessageSystem();
		TokenVo tokenVo=rewardService.findIdByToken(vo);

		if(tokenVo==null){
			modelMap.put("code", 2);
			modelMap.put("data", null);
			modelMap.put("message", "token已过期");
			return modelMap;
		}else{
			vo.setUserid(tokenVo.getUserid());
		}
		List<RansferRewardListBo> list=rewardService.queryRansferRewardList(vo);
		if(list!=null&&list.size()>0){
			modelMap.put("code", 0);
			modelMap.put("data", list);
			modelMap.put("message", "获取数据成功");
		}else{
			modelMap.put("code", 0);
			modelMap.put("data", list);
			modelMap.put("message", "暂无数据");
		}
		return modelMap;
	}

}
