package com.fh.service.timetask;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.bo.*;

import com.fh.util.HttpSender;
import com.fh.util.MD5;
import com.fh.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("timeTaskService")
public class TimeTaskService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//======================================================================================
    /*
    * 财务定时修改密码
    */
	public void modify ()throws Exception{
		List<AdminUserBO> list =(List<AdminUserBO>)dao.findForList("TimeTaskMapper.login","");
		if(list !=null && list.size()>0){
			for(int i=0 ;i<list.size();i++){
				AdminUserBO adminBo = list.get(i);
				int j = (int)(Math.random()*(9999-1000 + 1))+1000;//产生1000-9999的随机数
				String str ="zxkj"+j;
				String message="您今日的登陆密码为:"+"zxkj"+j;
				adminBo.setPassWord(MD5.md5(str));
				dao.update("TimeTaskMapper.modify",adminBo);
				MessageBO bo =new MessageBO();
				bo.setMobiles(adminBo.getPhone());
				bo.setContent(message);
				HttpSender.message(bo);

			}



		}



	}
	/*
     * 业绩定时结算
     */
	public void settleAccounts ()throws Exception{
		/*********************************************计算当月分红金额**************************************************************/
		String str= Tools.minusDay().substring(0,7);
		CommonBO commonBO = (CommonBO)dao.findForObject("TimeTaskMapper.findStatus",str);
        //分红金额
		int i =(int)commonBO.getTotal()/100;
		/*********************************************第一次分红 慈善会员和慈孝股东**************************************************/
		//大于等于9万  查询符合条件的人员
		List<TbAmountrecordBO> list9 = (List<TbAmountrecordBO>)dao.findForList("TimeTaskMapper.findPeople",90000);
		if(list9 !=null && list9.size()>0){
			String uuid = String.valueOf(UUID.randomUUID());
			String time = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
			for(int j=0;j<list9.size();j++){
				TbAmountrecordBO  bo= list9.get(j);
				//插入tb_reward
				RewardBO rewardBO =new RewardBO();
				rewardBO.setReward_id(uuid);//编号
				rewardBO.setInviterid(String.valueOf(bo.getUid()));
				rewardBO.setReward_amount(String.valueOf(i)); //分红金额
				rewardBO.setReward_time(time);
				rewardBO.setReward_remark("慈善会员和慈孝股东分红");
				dao.save("TimeTaskMapper.saveReward", rewardBO);
				//更新tb_userbalance
				TbuserbalanceBo tbuserbalanceBo =new TbuserbalanceBo();
				tbuserbalanceBo.setUserid(bo.getUid());
				tbuserbalanceBo.setReward(i);
				dao.update("TimeTaskMapper.updateTbuserbalance", tbuserbalanceBo);

			}
		}
//        大于等于30万  查询符合条件的人员
		List<TbAmountrecordBO> list30 = (List<TbAmountrecordBO>)dao.findForList("TimeTaskMapper.findPeople",300000);

		if(list30 !=null && list30.size()>0){
			String uuid = String.valueOf(UUID.randomUUID());
			String time = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
			for(int j=0;j<list30.size();j++){
				TbAmountrecordBO  bo= list9.get(j);
				//插入tb_reward
				RewardBO rewardBO =new RewardBO();
				rewardBO.setReward_id(uuid);//编号
				rewardBO.setInviterid(String.valueOf(bo.getUid()));
				rewardBO.setReward_amount(String.valueOf(i)); //分红金额
				rewardBO.setReward_time(time);
				rewardBO.setReward_remark("慈孝股东分红");
				dao.save("TimeTaskMapper.saveReward", rewardBO);
				//更新tb_userbalance
				TbuserbalanceBo tbuserbalanceBo =new TbuserbalanceBo();
				tbuserbalanceBo.setUserid(bo.getUid());
				tbuserbalanceBo.setReward(i);
				dao.update("TimeTaskMapper.updateTbuserbalance", tbuserbalanceBo);

			}
		}


}

}

