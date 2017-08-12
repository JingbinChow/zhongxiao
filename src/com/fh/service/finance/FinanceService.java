package com.fh.service.finance;

import com.fh.dao.DaoSupport;
import com.fh.entity.bo.*;
import com.fh.entity.vo.*;
import com.fh.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("financeService")
public class FinanceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//======================================================================================
    /*
    * App端用户登录确认
    */
	public AdminUserBO login(AdminUserBO adminuser) throws Exception {
		return (AdminUserBO) dao.findForObject("FinanceMapper.login", adminuser);
	}
	/*
	*APP财务获取订单列表
	*/
	public List<AmountListBO> getFinanceRecord(AmountListVO amountListVO) throws Exception {
		return (List<AmountListBO>) dao.findForList("FinanceMapper.getFinanceRecord", amountListVO);
	}

	public CountBO countComfirm()throws Exception{
		return (CountBO) dao.findForObject("FinanceMapper.countComfirm",null);
	}
	/*
	*APP财务订单确认
	*/
	public TbAmountrecordBO setFinanceRecord(ConfirAmountListVO confirAmountListVO) throws Exception {
		return (TbAmountrecordBO) dao.findForObject("FinanceMapper.setFinanceRecord", confirAmountListVO);
	}
	/*
	*APP财务确认订单
	*/
	public UserInfoBO findUserInfoBO(ConfirAmountListVO confirAmountListVO) throws Exception {
		return (UserInfoBO) dao.findForObject("FinanceMapper.findUserInfoBO", confirAmountListVO);
	}


	public UserInfoBO findUserInfoString(String str) throws Exception {
		return (UserInfoBO) dao.findForObject("FinanceMapper.findUserInfoString", str);
	}



	/*
	*APP财务确认订单
	*/
	public void confirm(TbAmountrecordBO bo) throws Exception {
		dao.findForObject("FinanceMapper.confirm", bo);
	}
	/**
	 * APP财务通过eid获取净值
	 */
	public int findEquityByEid(ConfirAmountListVO confirAmountListVO) throws Exception {
		return  (Integer)dao.findForObject("FinanceMapper.findQuityByEid",confirAmountListVO);
	}
	/**
	 * APP财务确认积分订单到帐
	 */
	public void AmountSave(AmountSaveVo amountSaveVo) throws Exception {
		dao.update("FinanceMapper.AmountSave",amountSaveVo);
		dao.update("FinanceMapper.AmountSave",amountSaveVo);

	}



	public TbuserbalanceBo findReward() throws Exception {
		return  (TbuserbalanceBo)dao.findForObject("FinanceMapper.findReward",null);
	}

	public void setReward() throws Exception {
	    //结算userblances
		dao.update("FinanceMapper.setbalance",null);
		//进入记录
		dao.update("FinanceMapper.setRwardRocrd",null);
		//消费积分记录
		dao.update("FinanceMapper.rcbLog",null);
		//中小积分
//		dao.update("FinanceMapper.jiangjinchiTojifen",null);
		List<RewardResultBo> list= (List<RewardResultBo>) dao.findForList("FinanceMapper.findRewards",null);
		for (RewardResultBo rewardResultBo : list) {
			//逐条同步消费积分
			dao.update("FinanceMapper.updateForReward",rewardResultBo);
		}
		//奖金池清零
		dao.update("FinanceMapper.setRewardZero",null);
	}
	  //财务查询转账记录

	 public List findRecord() throws Exception {
		   return (List<RansferRecordBO>)dao.findForList("FinanceMapper.findRecord", null);
	 }


	public void confirmRecord(RansferRecordBO ransferRecordBO) throws Exception {
		if("2".equals(ransferRecordBO.getRoll_inot())){

			//新增积分流水
			dao.save("FinanceMapper.setjifen", ransferRecordBO);
			//member表
			dao.update("FinanceMapper.setjifenMember", ransferRecordBO);
		}else if("3".equals(ransferRecordBO.getRoll_inot())){
			//新增钱包流水
			try {
				dao.save("FinanceMapper.setqianbao", ransferRecordBO );
				//member表
				dao.update("FinanceMapper.setqianbaoMember", ransferRecordBO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

//		dao.update("FinanceMapper.setstatus", ransferRecordBO);


	}

/**
* 获取退单列表
*/
public List<QueryChargeBackListBo> queryChargeBackList(QueryChargeBackListVo queryChargeBackListVo) throws Exception {
	if(queryChargeBackListVo.getPageIndex()>0&&queryChargeBackListVo.getPageSize()!=0){
		queryChargeBackListVo.setPageIndex((queryChargeBackListVo.getPageIndex()-1)*queryChargeBackListVo.getPageSize());
		System.out.println("start:"+queryChargeBackListVo.getPageIndex()+"pageSize:"+queryChargeBackListVo.getPageSize());
	}
	List<QueryChargeBackListBo> list= (List<QueryChargeBackListBo>) dao.findForList("FinanceMapper.queryChargeBackList",queryChargeBackListVo);
	return list;
}
/**
* 确认退单
*/
	public void accountChargeBack(QueryChargeBackListBo queryChargeBackListBo) throws Exception {
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		queryChargeBackListBo.setRecordTime(simpleDateFormat.format(date));
		dao.update("FinanceMapper.accountChargeBackByEid",queryChargeBackListBo);
		dao.update("FinanceMapper.updateBalanceInfo",queryChargeBackListBo);
		dao.update("FinanceMapper.modifyAmountStatus",queryChargeBackListBo);

	}
	/**
	 * 业绩扣除自身和上级业绩同时备份数据
	 */
	public void reSetAchievement(QueryChargeBackListBo queryChargeBackListBo){

	}
	/**
	 * 拉去待确认提现列表
	 */
	public List<QueryCashListBo> queryCashList(QueryCashListVo queryCashListVo) throws Exception {
		if(queryCashListVo.getPageIndex()>0&&queryCashListVo.getPageSize()!=0){
			queryCashListVo.setPageIndex((queryCashListVo.getPageIndex()-1)*queryCashListVo.getPageSize());
			System.out.println("start:"+queryCashListVo.getPageIndex()+"pageSize:"+queryCashListVo.getPageSize());
		}

		List<QueryCashListBo> list=null;
		System.out.println(queryCashListVo.getStatus());
		list= (List<QueryCashListBo>) dao.findForList("FinanceMapper.queryCashList",queryCashListVo);
		return list;
	}
	/**
	 * 确认提现信息
	 */
	public void accountCashByPid(QueryCashListBo queryCashListBo) throws Exception {
		dao.update("FinanceMapper.accountCashByPid",queryCashListBo);
		try {
			dao.update("FinanceMapper.updateFreeze_predeposit",queryCashListBo);
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		CashLogVo clv=new CashLogVo();
		clv.setLg_desc("提现成功，提现单号:" + queryCashListBo.getPdc_sn());
		clv.setLg_member_id(queryCashListBo.getPdc_member_id());
		clv.setLg_member_name(queryCashListBo.getPdc_member_name());
		clv.setLg_av_amount(0.00);
//		long time=System.currentTimeMillis()/1000;
		clv.setLg_add_time(queryCashListBo.getPdc_payment_time());
		clv.setLg_freeze_amount(-1 * queryCashListBo.getPdc_amount());
		clv.setLg_admin_name(queryCashListBo.getPdc_payment_admin());
		clv.setLg_type("cash_pay");
		dao.save("FinanceMapper.reduceFreezeCashLog",clv);
	}

	//删除36小时未确认订单
	public void deleteAmountrecord() throws Exception{
		List<ViewDeleteAmountrecordBO>  list = (List<ViewDeleteAmountrecordBO>)dao.findForList("FinanceMapper.findAmountrecordTime",null);
		if(list !=null&& list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				ViewDeleteAmountrecordBO bo =list.get(i);
				//包数
				int allscores = Integer.valueOf(bo.getDeclaration_num()) / 30;
				bo.setPack(allscores);
				//tb_userbanlance
				dao.update("FinanceMapper.backTbUserbalance", bo);
				//tb_tb_variable
				dao.update("FinanceMapper.backTbVariable", bo);
				//tb_amountrecord
				dao.update("FinanceMapper.backtbAmountrecord", bo);


			}


		}
	}



	//===============================确认到账======================================

	//根据 ConfirAmountVO userName查询信息
	public UserInfoBO findUserInfoByUserName(ConfirAmountVO confirAmountVO)throws Exception{
		return (UserInfoBO) dao.findForObject("FinanceMapper.findUserInfoByUserName",confirAmountVO);
	}
	//判断是否存在上级
	public UserInfoBO queryUserByUserName(UserInfoBO userInfoBO)throws Exception{
		return (UserInfoBO) dao.findForObject("FinanceMapper.queryUserByUserName",userInfoBO);
	}

	//根据uid 和status判断上级是否激活
	public List<TbAmountrecordBO> checkActivation(UserInfoBO userInfoBO)throws Exception{
		return (List<TbAmountrecordBO>) dao.findForList("FinanceMapper.checkActivation",userInfoBO);
	}

	//根据eid查询tb_amountrecord
	public TbAmountrecordBO queryTbAmountrecordByEid(ConfirAmountVO confirAmountVO)throws Exception{
		return (TbAmountrecordBO) dao.findForObject("FinanceMapper.queryTbAmountrecordByEid",confirAmountVO);
	}

	//更新账户信息
	public boolean addAmountResult(TbAmountrecordBO tbAmountrecordBO)throws Exception{
		try {
			dao.findForObject("FinanceMapper.addAmountResult",tbAmountrecordBO);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//更新账户信息判断
	public Map<String, Object> addAmount(TbAmountrecordBO tbAmountrecordBO)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		result = addAmountResult(tbAmountrecordBO);
		int id= tbAmountrecordBO.getUid();
		try {
			if(result ==true){
				TbScorerecordBO tbScorerecordBO = new TbScorerecordBO();
				tbScorerecordBO.setEquity(tbAmountrecordBO.getEquity());
				tbScorerecordBO.setStatus(1);
				tbScorerecordBO.setUserid(id);
				// 暂定购买数量成功
				tbScorerecordBO.setSuccessnum(tbAmountrecordBO.getDeclarationNum());

				String createdate = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
				tbScorerecordBO.setCreatedate(createdate);
				if("未下订单线下直接打款".equals(tbAmountrecordBO.getRemark())){
					DeleteOrderVO deleteOrderVO = new DeleteOrderVO();
					deleteOrderVO.setBox(tbAmountrecordBO.getRealNum()/tbAmountrecordBO.getUnit());
					deleteOrderVO.setUid(tbAmountrecordBO.getUid());

					//更新balance数据
					modifyRemainbanlance(deleteOrderVO);

					DeleteOrderVO delete = new DeleteOrderVO();
					delete.setBox(tbAmountrecordBO.getRealNum() / tbAmountrecordBO.getUnit());

					//更新tb_variable中的value数据
					modifyAllScore(delete);

				}
				boolean scordRecord = true;

				//添加tb_scorerecord表中数据
				scordRecord = addScordRecord(tbScorerecordBO);
				if(scordRecord==true){
					// 数据同步到userBalance表里
					AmountRecordBo amountRecordBo = new AmountRecordBo();
					amountRecordBo.setUid(id);
					amountRecordBo.setAmount(tbAmountrecordBO.getAmount());
					amountRecordBo.setIntegral(tbAmountrecordBO.getRealNum());
					boolean updateIntBalance = true;

					// 确认进账后同步修改Balance表的积分数
					updateIntBalance=modifyIntbalace(amountRecordBo);
					if(updateIntBalance==true){
						map.put("code", "0");
						map.put("message", "已分配成员数据同步操作成功!");
						map.put("data", null);
						return map;
					}else{
						map.put("code", "1");
						map.put("message", "userBalance表数据同步失败");
						map.put("data", null);
						return map;
					}

				}else {
					map.put("code", "1");
					map.put("message", "积分记录表提交失败");
					map.put("data", null);
					return map;
				}

			}else{
				map.put("code", "1");
				map.put("message", "操作失败");
				map.put("data", null);
				return map;
			}
		}catch (Exception e){
			e.printStackTrace();
			map.put("code", "1");
			map.put("message", "系统内部错误");
			map.put("data", null);
		}

		return map;
	}

	//更新balance数据
	public void modifyRemainbanlance(DeleteOrderVO deleteOrderVO)throws Exception{
		dao.findForObject("FinanceMapper.modifyRemainbanlance",deleteOrderVO);
	}

	//更新tb_variable中的value数据
	public void modifyAllScore(DeleteOrderVO deleteOrderVO)throws Exception{
		dao.findForObject("FinanceMapper.modifyAllScore",deleteOrderVO);
	}

	// 确认进账后同步修改Balance表的积分数
	public boolean modifyIntbalace(AmountRecordBo amountRecordBo)throws Exception{
		try {
			dao.findForObject("FinanceMapper.modifyIntbalace",amountRecordBo);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//添加tb_scorerecord表中数据
	public boolean addScordRecord(TbScorerecordBO tbScorerecordBO)throws Exception{
		try {
			dao.findForObject("FinanceMapper.addScordRecord",tbScorerecordBO);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//确认等级查询
	public ViewMemberAmountBO checkLeavl(ViewMemberAmountBO viewMemberAmountBO)throws Exception{
		return (ViewMemberAmountBO) dao.findForObject("FinanceMapper.checkLeavl",viewMemberAmountBO);
	}


	//================================================================================================================

	//推荐人 新注册人员等级
	public Map<String,Object> shopReward(CountRewardBO countRewardBO)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			//查询全部上级成员名单
			String str = findPersion(countRewardBO);
			if(str.length()>0){
				List<ViewRewardBO> list = findAllReward(str);
				if(list!=null && list.size()>0){
					countRewardBO.setRealnum(countRewardBO.getRealnum()/10);

					//计算奖金
					setReward(list, countRewardBO);
					//计算业绩奖金
					setAchReward(list, countRewardBO);
					//新增业务计算
					setAchievement(str, countRewardBO);
					//从新计算确认订单会员等级
					setleval(countRewardBO);
				}
			}
			map.put("code", "0");
			map.put("message", "等级已确认");
			map.put("data", null);

		}catch (Exception e){
			e.printStackTrace();
			map.put("code", "0");
			map.put("message", "等级确认失败");
			map.put("data", null);
		}
		return map;
	}




	//查询全部上级人员名单
	public  String  findPersion(CountRewardBO countRewardBO) throws Exception {
		//全部上级人员id;
		String persion= "";
		GetLeavlBO getLeavlBO = new GetLeavlBO();
		getLeavlBO = (GetLeavlBO)dao.findForObject("FinanceMapper.queryInviterLeavl",countRewardBO);
		int  level=(Integer.parseInt(getLeavlBO.getLeavl()))/4;
		String teamsign=getLeavlBO.getTeamsign();

		if(level>0){
			for(int i=1;i<=level ;i++ ) {
				persion = persion + "'" + teamsign.substring(0, i * 4) + "'" + ",";
			}
			persion =persion.substring(0,persion.length()-1);
		}

		GetLeavlNameBO getLeavlNameBO = new GetLeavlNameBO();
		getLeavlNameBO.setPersion(persion);
		getLeavlNameBO= (GetLeavlNameBO) dao.findForObject("FinanceMapper.queryAllInviterName", getLeavlNameBO);


		String id = getLeavlNameBO.getName();
		return id;
	}


	//查询全部上级人员信息 ok
	public  List<ViewRewardBO>   findAllReward(String str) throws Exception {
//		String hql ="select * from view_reward  where member_id  in ("+str+")  order by  team_sign desc";
//		List  list=dao.queryRecordJb(hql);
		List<ViewRewardBO> list=(List<ViewRewardBO>)dao.findForList("FinanceMapper.queryByStr",str);
		if(list !=null && list.size()>0){
			return list;
		}
		return  list;
	}




	// 计算奖金 ok
	public void  setReward(List<ViewRewardBO> list,CountRewardBO countRewardBo )throws  Exception{
		try {
			String uuid = String.valueOf(UUID.randomUUID());
			for(int i=0 ;i<list.size();i++){
				ViewRewardBO bo =list.get(i);
				//第一级特殊处理 2345级别正常处理
				if(i==0){
					if(bo.getLevel()!=0){
						//如果推荐人等级为0 直接跳出 不在寻找上一级
						Double j =0.00; //已经执行完级别奖励数
						int  leval=0; //已经执行完级别
						//进入循环 如有会员级别为0 直接跳出循环   已经奖励完级别不在奖励 ,
						for(int k=0;k<list.size();k++){
							ViewRewardBO sp =list.get(k);
							String inviter =String.valueOf(sp.getMember_id());
							if(sp.getReallevel() ==1 && sp.getReallevel() != leval&& sp.getReallevel() >= leval   ){

								Double money =countRewardBo.getRealnum() * (0.05-j);
								DecimalFormat df   = new DecimalFormat("######0.0");
								j =0.05;
								leval=1;
								if(j>0){
									countRewardBo.setEid(countRewardBo.getEid());
									countRewardBo.setUserid(countRewardBo.getUserid());
									countRewardBo.setInviter(inviter);
									countRewardBo.setInviterid_leval(String.valueOf(i+1));
									countRewardBo.setMoney(df.format(money));
									countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
									countRewardBo.setReward_remark("招商奖励");
									dao.findForObject("FinanceMapper.countReward",countRewardBo);

									TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
									tbuserBalanceBo.setMoney(df.format(money));
									tbuserBalanceBo.setUserid(Integer.parseInt(inviter));

									dao.findForObject("FinanceMapper.countRewardUpdateBalance",tbuserBalanceBo);

								}

							}else if(sp.getReallevel() ==2 && sp.getReallevel() != leval&& sp.getReallevel()>=leval ){
								Double money =countRewardBo.getRealnum() * (0.07-j);
								DecimalFormat   df   = new DecimalFormat("######0.0");
								j =0.07;
								leval=2;
								if(j>0){
									countRewardBo.setInviter(inviter);
									countRewardBo.setInviterid_leval(String.valueOf(i+1));
									countRewardBo.setMoney(df.format(money));
									countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
									countRewardBo.setReward_remark("招商奖励");
									dao.findForObject("FinanceMapper.countReward",countRewardBo);

									TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
									tbuserBalanceBo.setMoney(df.format(money));
									tbuserBalanceBo.setUserid(Integer.parseInt(inviter));

									dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);
								}

							}else if(sp.getReallevel() ==3 && sp.getReallevel() != leval&& sp.getReallevel()>=leval ){
								Double money =countRewardBo.getRealnum() * (0.08-j);
								DecimalFormat   df   = new DecimalFormat("######0.0");
								j =0.08;
								leval=3;
								if(j>0){
									countRewardBo.setInviter(inviter);
									countRewardBo.setInviterid_leval(String.valueOf(i+1));
									countRewardBo.setMoney(df.format(money));
									countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
									countRewardBo.setReward_remark("招商奖励");
									dao.findForObject("FinanceMapper.countReward",countRewardBo);

									TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
									tbuserBalanceBo.setMoney(df.format(money));
									tbuserBalanceBo.setUserid(Integer.parseInt(inviter));

									dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);
								}

							}else if(sp.getReallevel()>=4 && sp.getReallevel() != leval&& sp.getReallevel()>=leval){
								Double money =countRewardBo.getRealnum() * (0.10-j);
								DecimalFormat   df   = new DecimalFormat("######0.0");
								j =0.10;
								leval=4;
								if(j>0){
									countRewardBo.setInviter(inviter);
									countRewardBo.setInviterid_leval(String.valueOf(i+1));
									countRewardBo.setMoney(df.format(money));
									countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
									countRewardBo.setReward_remark("招商奖励");
									dao.findForObject("FinanceMapper.countReward",countRewardBo);

									TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
									tbuserBalanceBo.setMoney(df.format(money));
									tbuserBalanceBo.setUserid(Integer.parseInt(inviter));

									dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);
								}

							}
						}
					}
				}else{
					//如果会员等级为0 则不发奖励
					if(bo.getLevel()!=0){
						String jiLi =this.levalForsalary(countRewardBo.getRealnum(),i ,bo.getReallevel());
						String inviter =String.valueOf(bo.getMember_id());
						countRewardBo.setInviter(inviter);
						countRewardBo.setInviterid_leval(String.valueOf(i+1));
						countRewardBo.setMoney(jiLi);
						countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
						countRewardBo.setReward_remark("服务佣金");
						dao.findForObject("FinanceMapper.countReward",countRewardBo);

						TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
						tbuserBalanceBo.setMoney(jiLi);
						tbuserBalanceBo.setUserid(Integer.parseInt(inviter));

						dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);

					}
				}
				//超过5级跳出循环
				if(i==4){
					break;
				}
			}
		}catch (Exception e ){
			e.printStackTrace();
		}
	}




	//新注册会员金额  上层推荐人级别  奖励级别
	public String levalForsalary(int realnum ,int i,int reallevel)throws Exception{
		Double[] aixinMember ={0.05, 0.04, 0.03, 0.02, 0.01};  //爱心会员
		Double[] gongyiMember={0.07, 0.05, 0.04, 0.03, 0.02};  //公益会员
		Double[] cishanMember={0.08, 0.06, 0.05, 0.04, 0.03};  //慈善会员
		Double[] cixiaoMember={0.10, 0.07, 0.06, 0.05, 0.04};  //慈孝股东
		Double total=0.0;
		//特殊0级别进不来
		if(reallevel==1){
			total =aixinMember[i] * realnum;
		}else if(reallevel==2){
			total =gongyiMember[i] * realnum;
		}else if(reallevel==3){
			total =cishanMember[i] * realnum;
		}else if(reallevel>=3){
			total =cixiaoMember[i] * realnum;
		}
		String str =String.valueOf(Math.floor(total));
		return str;
	}


	// 根据业绩确认分发奖金
	public void  setAchReward(List<ViewRewardBO> list, CountRewardBO countRewardBo)throws  Exception{
		String uuid = String.valueOf(UUID.randomUUID());
		int  leval=0; //已经执行完级别
		for(int i=0;i<list.size();i++){
			Double j =0.00; //已经执行完级别奖励数


			ViewRewardBO bo =list.get(i);
			//根据总业绩计算奖金   业绩小于100万不触发奖励
			if(1000000<=bo.getAchievement()&&bo.getAchievement()<6000000 && leval<1){
				Double money = countRewardBo.getRealnum() * (0.02-j);
				leval=1;
				j=0.02;
				countRewardBo.setInviter(String.valueOf(bo.getMember_id()));
				countRewardBo.setInviterid_leval(String.valueOf(i + 1));
				countRewardBo.setMoney(String.valueOf(money));
				countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
				countRewardBo.setReward_remark("管理津贴");
				dao.findForObject("FinanceMapper.countReward",countRewardBo);

				TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
				tbuserBalanceBo.setMoney(String.valueOf(money));
				tbuserBalanceBo.setUserid(bo.getMember_id());

				dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);




				/****************平级奖励**************************/
				int l =i+1;
				for(int k=l;k<list.size();k++){
					ViewRewardBO pingji =list.get(k); //本级人员下一级
					if(pingji.getAchievement()<6000000){
						ViewRewardBO pingjilast =list.get(i); //本级人员；
						//如果下一级人员 -本级人员 =大于30000 0.1%平级奖励
						if((pingji.getAchievement()-pingjilast.getAchievement())>=300000){
							countRewardBo.setInviter(String.valueOf(pingji.getMember_id()));
							countRewardBo.setInviterid_leval(String.valueOf(k + 1));
							countRewardBo.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
							countRewardBo.setReward_remark("平级奖励");
							dao.findForObject("FinanceMapper.countReward",countRewardBo);

							TbuserbalanceBo tbuserBalanceBo1 = new TbuserbalanceBo();
							tbuserBalanceBo1.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							tbuserBalanceBo1.setUserid(pingji.getMember_id());

							dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo1);

						}
					}
				}

			}else if(6000000<=bo.getAchievement()&&bo.getAchievement()<150000000 &&leval<2){
				Double money = countRewardBo.getRealnum() * (0.04-j);
				leval=2;
				j=0.04;

				countRewardBo.setInviter(String.valueOf(bo.getMember_id()));
				countRewardBo.setInviterid_leval(String.valueOf(i+1));
				countRewardBo.setMoney(String.valueOf(money));
				countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
				countRewardBo.setReward_remark("管理津贴");
				dao.findForObject("FinanceMapper.countReward",countRewardBo);

				TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
				tbuserBalanceBo.setMoney(String.valueOf(money));
				tbuserBalanceBo.setUserid(bo.getMember_id());

				dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);





				/****************平级奖励**************************/
				int l =i+1;
				for(int k=l;k<list.size();k++){
					ViewRewardBO pingji =list.get(k); //本级人员下一级
					if(pingji.getAchievement()<150000000){
						ViewRewardBO pingjilast =list.get(i); //本级人员；
						//如果下一级人员 -本级人员 =大于30000 0.1%平级奖励
						if((pingji.getAchievement()-pingjilast.getAchievement())>=300000){

							countRewardBo.setInviter(String.valueOf(pingji.getMember_id()));
							countRewardBo.setInviterid_leval(String.valueOf(k + 1));
							countRewardBo.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
							countRewardBo.setReward_remark("平级奖励");
							dao.findForObject("FinanceMapper.countReward",countRewardBo);

							TbuserbalanceBo tbuserBalanceBo1 = new TbuserbalanceBo();
							tbuserBalanceBo1.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							tbuserBalanceBo1.setUserid(pingji.getMember_id());

							dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo1);

						}
					}
				}
			}else if(15000000<=bo.getAchievement()&&bo.getAchievement()<35000000 &&leval<3){
				Double money =countRewardBo.getRealnum() * (0.06-j);
				leval=3;
				j=0.06;

				countRewardBo.setInviter(String.valueOf(bo.getMember_id()));
				countRewardBo.setInviterid_leval(String.valueOf(i + 1));
				countRewardBo.setMoney(String.valueOf(money));
				countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
				countRewardBo.setReward_remark("管理津贴");
				dao.findForObject("FinanceMapper.countReward",countRewardBo);

				TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
				tbuserBalanceBo.setMoney(String.valueOf(money));
				tbuserBalanceBo.setUserid(bo.getMember_id());

				dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);


				/****************平级奖励**************************/
				int l =i+1;
				for(int k=l;k<list.size();k++){
					ViewRewardBO pingji =list.get(k); //本级人员下一级
					if(pingji.getAchievement()<35000000){
						ViewRewardBO pingjilast =list.get(i); //本级人员；
						//如果下一级人员 -本级人员 =大于30000 0.1%平级奖励
						if((pingji.getAchievement()-pingjilast.getAchievement())>=300000){

							countRewardBo.setInviter(String.valueOf(pingji.getMember_id()));
							countRewardBo.setInviterid_leval(String.valueOf(k + 1));
							countRewardBo.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
							countRewardBo.setReward_remark("平级奖励");
							dao.findForObject("FinanceMapper.countReward",countRewardBo);

							TbuserbalanceBo tbuserBalanceBo1 = new TbuserbalanceBo();
							tbuserBalanceBo1.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							tbuserBalanceBo1.setUserid(pingji.getMember_id());

							dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo1);


						}
					}
				}
			}else if(bo.getAchievement()>=35000000 &&leval<4){
				Double money =countRewardBo.getRealnum() * (0.10-j);
				leval=4;
				j=0.10;

				countRewardBo.setInviter(String.valueOf(bo.getMember_id()));
				countRewardBo.setInviterid_leval(String.valueOf(i+1));
				countRewardBo.setMoney(String.valueOf(money));
				countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
				countRewardBo.setReward_remark("管理津贴");
				dao.findForObject("FinanceMapper.countReward",countRewardBo);

				TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
				tbuserBalanceBo.setMoney(String.valueOf(money));
				tbuserBalanceBo.setUserid(bo.getMember_id());

				dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo);



				/****************平级奖励**************************/
				int l =i+1;
				for(int k=l;k<list.size();k++){
					ViewRewardBO pingji =list.get(k); //本级人员下一级
					if(pingji.getAchievement()>=35000000){
						ViewRewardBO pingjilast =list.get(i); //本级人员；
						//如果下一级人员 -本级人员 =大于30000 0.1%平级奖励
						if((pingji.getAchievement()-pingjilast.getAchievement())>=300000){

							countRewardBo.setInviter(String.valueOf(pingji.getMember_id()));
							countRewardBo.setInviterid_leval(String.valueOf(k+1));
							countRewardBo.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							countRewardBo.setReward_time(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
							countRewardBo.setReward_remark("平级奖励");
							dao.findForObject("FinanceMapper.countReward",countRewardBo);

							TbuserbalanceBo tbuserBalanceBo1 = new TbuserbalanceBo();
							tbuserBalanceBo1.setMoney(String.valueOf((countRewardBo.getRealnum()*0.01)));
							tbuserBalanceBo1.setUserid(pingji.getMember_id());

							dao.findForObject("FinanceMapper.countRewardUpdateBalance", tbuserBalanceBo1);


						}
					}
				}
			}
		}
	}



	// 新增业绩计算
	public void  setAchievement(String str, CountRewardBO countRewardBO)throws  Exception{
		try{
			List sqlbatch = new ArrayList();   //批处理语句
			//全部上级人员 增加业绩

			TbuserbalanceBo tbuserBalanceBo = new TbuserbalanceBo();
			tbuserBalanceBo.setMoney(countRewardBO.getRealnum() + "");
			tbuserBalanceBo.setUserIdzu(str);
			dao.findForObject("FinanceMapper.countNewReward",tbuserBalanceBo);
//			String sql="update  tb_userbalance  set  achievement= achievement +"+realnum+"  where   userid  in ("+str+")";
//			sqlbatch.add(sql);
//			dao.saveOrUpdate((String[])sqlbatch.toArray(new String[sqlbatch.size()]));
		}catch (Exception e){
//			System.out.print(userid);
			e.printStackTrace();
//			System.out.print(userid);
		}

	}


	//确认等级
	public void  setleval(CountRewardBO countRewardBO)throws Exception{

		ViewMemberAmountBO viewMemberAmountBO = new ViewMemberAmountBO();
		viewMemberAmountBO.setUid(Integer.parseInt(countRewardBO.getUserid()));
		//查询实际成交奖金
		viewMemberAmountBO = checkLeavl(viewMemberAmountBO);
		int total = (int)viewMemberAmountBO.getAmount();
		int level=0;//等级
		if(total<3000){
			level=0;
		}
		if(total<30000&&total>=3000){
			level=1;
		}else if(total<90000&&total>=30000){
			level=2;
		}else if(total<300000&&total>=90000){
			level=3;
		}else if(total>=300000) {
			level=4;
		}

		TbuserbalanceBo tbuserbalanceBo = new TbuserbalanceBo();
		tbuserbalanceBo.setLevel(level);
		tbuserbalanceBo.setUserid(Integer.parseInt(countRewardBO.getUserid()));

		dao.findForObject("FinanceMapper.confirmLevel",tbuserbalanceBo);
	}

}

