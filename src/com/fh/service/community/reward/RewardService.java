package com.fh.service.community.reward;

import com.fh.dao.DaoSupport;
import com.fh.entity.bo.*;
import com.fh.entity.vo.TokenVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("rewardService")
public class RewardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;




	public UserInfoBO findToken(TokenVo tokenVo) throws Exception {
		return (UserInfoBO) dao.findForObject("Reward.findToken",tokenVo);
	}
//	public UserInfoBO findMemberID(TokenVo tokenVo) throws Exception {
//		return (UserInfoBO) dao.findForObject("Reward.findToken",tokenVo);
//	}

	//生成奖金提取记录
	public void  setReward(RansferRecordBO ransferRecordBO)throws Exception{
		dao.save("Reward.setReward", ransferRecordBO);
	}


//	public void  setRecord(TokenVo vo)throws Exception{
//		dao.save("Reward.setRecord", vo);
//	}

	//转入流水BO存入
	public void  setRecordBO(RansferRecordBO ransferRecordBO)throws Exception{
		dao.save("Reward.setRecordBO", ransferRecordBO);
	}

	//奖金清零
	public void  setZore(RansferRecordBO ransferRecordBO)throws Exception{
		dao.save("Reward.setZore", ransferRecordBO);
	}


	public UserInfoBO  findMenber1 (RansferRecordBO bo)throws Exception{
		return  (UserInfoBO)dao.findForObject("Reward.findMenber", bo);
	}

	public String findMemberName(TokenVo tokenVo) throws Exception {
		return  (String)dao.findForObject("Reward.findMenberName",tokenVo);
	}

	public TbuserbalanceBo findTbuserbalance(TbUserBO o) throws Exception {
		return  (TbuserbalanceBo)dao.findForObject("Reward.findTbuserbalance",o);
	}




/**
 * 查询奖金明细
 */
public List<RansferRewardListBo> queryRansferRewardList(TokenVo token) throws Exception {
	if(token.getPageIndex()!=0&&token.getPageSize()!=0){
		token.setPageIndex((token.getPageIndex()-1)*token.getPageSize());
	}
	List<RansferRewardListBo> list= (List<RansferRewardListBo>) dao.findForList("Reward.queryRansferListById",token);
	for (RansferRewardListBo ransferRewardListBo : list) {
		if(ransferRewardListBo.getStatus()==0){
			ransferRewardListBo.setActual_number("-"+ransferRewardListBo.getActual_number());
			ransferRewardListBo.setType("0");
		}else{
			ransferRewardListBo.setType("1");
		}
	}
	return list;
}
	/**
	 * 通过token查userid
	 */
	public TokenVo findIdByToken(TokenVo tokenVo) throws Exception {
		return (TokenVo)dao.findForObject("Reward.queryUseridByToken",tokenVo);
	}












}

