package com.fh.service.community.cash;

import com.fh.dao.DaoSupport;
import com.fh.entity.bo.*;
import com.fh.entity.vo.CashLogVo;
import com.fh.entity.vo.QueryAmounVo;
import com.fh.entity.vo.TokenVo;
import com.fh.entity.vo.WithdrawCashVo;
import com.fh.util.Constants;
import com.fh.util.RandomCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("cashService")
public class CashService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 提现
	 */
public Map<String,Object> withdrawCash(WithdrawCashVo withdrawCashVo) throws Exception {
	Map<String,Object> data=new HashMap<String, Object>();
	//通过token查memberid
	System.out.println(withdrawCashVo.getToken());
	if(withdrawCashVo.getCash()>0){
		Object obj=dao.findForObject("Cash.queryUseridByToken",withdrawCashVo);

		if(obj==null){
			data.put("message","token已失效");
			data.put("code",2);
			data.put("data",null);
			return  data;
		}
		int userid=(Integer)obj;
		withdrawCashVo.setMember_id(userid);
		//通过memberid取钱包余额
		QueryMemberCashInfoBo info=(QueryMemberCashInfoBo)dao.findForObject("Cash.queryAvailable_predeposit",withdrawCashVo);
		//判断钱包余额是否够扣去提现金额
		if (info.getAvailable_predeposit()<withdrawCashVo.getCash()){
			data.put("message","提现失败，钱包余额不够扣除");
			data.put("code",1);
			data.put("data",null);
			return  data;
		}
		try {
			//冻结钱包余额
			dao.update("Cash.freeze_predeposit",withdrawCashVo);
			QueryCashListBo qclb=new QueryCashListBo();
			long time=System.currentTimeMillis()/1000;
			String sn= RandomCode.getChar()+time;
			qclb.setPdc_add_time(time);
			qclb.setPdc_payment_state("0");
			qclb.setPdc_amount(withdrawCashVo.getCash());
			qclb.setPdc_sn(sn);
			qclb.setPdc_bank_name(info.getMember_bankname());
			qclb.setPdc_bank_no(info.getMember_bankcard());
			qclb.setPdc_member_name(info.getMember_name());
			qclb.setPdc_bank_user(info.getMember_truename());
			qclb.setPdc_member_id(info.getMember_id());
			dao.save("Cash.addCashRecord", qclb);
			CashLogVo clv=new CashLogVo();
			clv.setLg_add_time(time);
			clv.setLg_type("cash_apply");
			clv.setLg_av_amount(withdrawCashVo.getCash() * -1);
			clv.setLg_freeze_amount(withdrawCashVo.getCash());
			clv.setLg_member_name(info.getMember_name());
			clv.setLg_member_id(info.getMember_id());
			clv.setLg_desc("申请提现，冻结预存款，提现单号:"+sn);
			dao.save("Cash.reduceFreezeCashLog",clv);
			data.put("message","申请提现成功");
			data.put("code",0);
			data.put("data",null);
		} catch (Exception e) {
			data.put("message","系统内部错误");
			data.put("code",1);
			data.put("data",null);
			e.printStackTrace();
		}
	}else{
		data.put("message","请输入正确的金额");
		data.put("code",1);
		data.put("data",null);
		return  data;
	}

	return data;
}

	public UserInfoBO findToken(TokenVo tokenVo) throws Exception {
		return (UserInfoBO) dao.findForObject("Cash.findToken",tokenVo);
	}

	public List<QueryCashListBo> findQueryCashListBo(TokenVo  tokenVo) throws Exception {
		if(tokenVo.getPageIndex()!=0&&tokenVo.getPageSize()!=0){
			tokenVo.setPageIndex((tokenVo.getPageIndex()-1)*tokenVo.getPageSize());
		}
		return (List<QueryCashListBo>) dao.findForList("Cash.findQueryCashListBo",tokenVo);
	}



}

