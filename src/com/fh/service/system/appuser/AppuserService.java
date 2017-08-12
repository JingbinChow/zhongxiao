package com.fh.service.system.appuser;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.bo.*;
import com.fh.entity.system.MemberBankList;
import com.fh.entity.vo.*;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("appuserService")
public class AppuserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//======================================================================================

	/*
	* 通过id获取数据
	*/
	public PageData findByUiId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUiId", pd);
	}
	/*
	* 通过loginname获取数据
	*/
	public PageData findByUId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUId", pd);
	}

	/*
	* 通过邮箱获取数据
	*/
	public PageData findByUE(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUE", pd);
	}

	/*
	* 通过编号获取数据
	*/
	public PageData findByUN(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUN", pd);
	}

	/*
	* 保存用户
	*/
	public void saveU(PageData pd)throws Exception{
		dao.save("AppuserMapper.saveU", pd);
	}
	/*
	* 修改用户
	*/
	public void editU(PageData pd)throws Exception{
		dao.update("AppuserMapper.editU", pd);
	}
	/*
	* 删除用户
	*/
	public void deleteU(PageData pd)throws Exception{
		dao.update("AppuserMapper.deleteU", pd);
	}
	/*
	* 批量删除用户
	*/
	public void deleteAllU(String[] USER_IDS)throws Exception{
		dao.update("AppuserMapper.deleteAllU", USER_IDS);
	}
	/*
	*用户列表(全部)
	*/
	public List<PageData> listAllUser(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.listAllUser", pd);
	}

	/*
	*用户列表(用户组)
	*/
	public List<PageData> listPdPageUser(Page page)throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.userlistPage", page);
	}

	/*
	* 保存用户IP
	*/
	public void saveIP(PageData pd)throws Exception{
		dao.update("AppuserMapper.saveIP", pd);
	}

	/*
	* 登录判断
	*/
	public PageData getUserByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.getUserInfo", pd);
	}
	/*
	* 跟新登录时间
	*/
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("AppuserMapper.updateLastLogin", pd);
	}
	//======================================================================================


    /*
	* app 登录判断
	*/
	public PageData appLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Member.appLogin", pd);
	}
	//	/*
	//    * app 生成token
	//    */
	//	public PageData setToken(PageData pd)throws Exception{
	//		return (PageData)dao.findForObject("33haoMember.setToken", pd);
	//	}
	/*
    * app 生成token
    */
	public void setToken(TokenBO tokenBO)throws Exception{
		dao.findForObject("Member.delToken", tokenBO);
		dao.findForObject("Member.setToken", tokenBO);

	}


	/*
    * 用户登录确认
    */
	public UserInfoBO userLogin (UserInfoVO user)throws Exception{
		return (UserInfoBO)dao.findForObject("Member.userLogin", user);
	}

	/*
    * TOKEN登录验证
    */
	public String userCheckTokenIsLost(UserInfoVO user)throws Exception{
		TokenBO tokenBO =(TokenBO)dao.findForObject("Member.userCheckTokenIsLost", user);
		String str ="2";
		if(tokenBO == null){
			 str ="2";
		}else{
			 str ="0";
		}
		return str;
	}


	/*
    * 查询银行卡是否注册
    */
	public UserInfoBO checkBankCard (UserInfoVO user)throws Exception{
		return (UserInfoBO)dao.findForObject("Member.checkBankCard", user);
	}
	/*
     * 查询用户名是否重复
    */
	public UserInfoBO checkUserName (UserInfoVO user)throws Exception{
		return (UserInfoBO)dao.findForObject("Member.checkUserName", user);
	}
	/*
     * 查询用户名是否重复
    */
	public void userModifyPassWord (UserInfoVO user)throws Exception{
		dao.findForObject("Member.userModifyPassWord", user);
	}
	/**
	 * 通过token查userid
	 */
	public TokenVo findIdByToken(TokenVo tokenVo) throws Exception {

		TokenVo token= null;

			token = (TokenVo)dao.findForObject("Member.queryUseridByToken",tokenVo);


		return token;
	}

	/**
	 * 通过token查member_truename
	 */
	public TokenVo findTrueNameByToken(TokenVo tokenVo) throws Exception {

		TokenVo token= null;
		try {
			token = (TokenVo)dao.findForObject("Member.queryTrueNameByToken",tokenVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return token;
	}
	/**
	 * 查询个人账户详情
	 */

	public UserBalanceInfo queryUserBalanceInfo(TokenVo tokenVo) throws Exception {
		UserBalanceInfo info=(UserBalanceInfo)dao.findForObject("Member.queryUserBalanceInfo",tokenVo);
		System.out.println(info);
		return info;

	}
	/**
	 * 通过userid查询VIP
	 */
	public String queryVIP(TokenVo tokenVo) throws Exception {
		String vip ="";
		TbuserbalanceBo bo=(TbuserbalanceBo)dao.findForObject("Member.queryVIPByToken",tokenVo);
		if(bo.getPrimitive() == -1 &&  bo.getLevel()==1){
		   vip ="-1";
		}else{
			vip =String.valueOf(bo.getLevel());
		}
		return vip;
	}

	/**
	 * 通过userid查询慈善基金
	 */
	public String queryCharitable(TokenVo tokenVo) throws Exception {
		String charitable=(String)dao.findForObject("Member.queryCharitableByToken",tokenVo);
		if(charitable==null){
			return "0";
		}
		System.out.println(charitable);
		return charitable;
	}

	/**
	 * 查询会员等级列表
	 */
	public List<VIPSBo> getVIPS() throws Exception {
		return (List<VIPSBo>)dao.findForList("Member.getVIPS",null);
	}
	/**
	 * 意见反馈
	 */
	public void feedBack(FeedBackVo feedBackVo) throws Exception {
		dao.save("Member.feedBack",feedBackVo);
	}
	/**
	 * 通过memberid查membername
	 */
	public UserInfoBO findMemberNameById(TokenVo tokenVo) throws Exception {
		String memberName="";
		UserInfoBO userInfoBo = (UserInfoBO) dao.findForObject("Member.queryMemberInfo",tokenVo);
//		if(userInfoBo!=null){
//			memberName=userInfoBo.getMember_name();
//		}
		return userInfoBo;
	}
	/**
	 * App注册
	 */
	public Map<String,Object> registerMember(UserInfoBO userInfoBO) throws Exception {
		Map<String,Object> map= Tools.errMessageSystem();
		if(userInfoBO.getMember_name()==null||"".equals(userInfoBO.getMember_name())) {
			map.put("message","用户名不允许为空");
			map.put("code",1);
			map.put("data",null);
			return map;
		}
		UserInfoVO user=new UserInfoVO();
		user.setUserName(userInfoBO.getMember_name());
		UserInfoBO userInfo= (UserInfoBO) dao.findForObject("Member.checkUserName", user);
		if(userInfo!=null){
			map.put("message","用户名已存在");
			map.put("code",1);
			map.put("data",null);
			return map;
		}else{
			try {
				userInfoBO.setMember_passwd(MD5.md5(userInfoBO.getMember_passwd()));
				dao.save("Member.registerMember",userInfoBO);
				UserInfoBO userInfo1= (UserInfoBO) dao.findForObject("Member.checkUserName", user);
				if(userInfo1==null){
					map.put("message","内部数据错误");
					map.put("code",1);
					map.put("data",null);
					return map;

				}else{
					UserBalanceVo userBalanceVo=new UserBalanceVo();
					userBalanceVo.setUserid(userInfo1.getMember_id());
					userBalanceVo.setAccbalance((float) 0.0);
					userBalanceVo.setIntbalance(0);
					userBalanceVo.setRemainbalance(100000);
					dao.save("Member.addUserBalance", userBalanceVo);
					this.initHealthCondition(userInfo1.getMember_id());
					map.put("message","注册成功");
					map.put("code",0);
					map.put("data",null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return map;
			}
		}
		return map;
	}
	/**
	 * 通过userid查member
	 */
	public UserInfoBO queryMemberInfo(TokenVo vo) throws Exception {
		int member_id=Integer.parseInt(vo.getUserid().trim());
		return (UserInfoBO)dao.findForObject("Member.queryMemberInfo", member_id);
	}

	/**
	 * 获取银行列表
	 */
	public List<MemberBankList> getUserBanknameList() throws Exception{
		return (List<MemberBankList>) dao.findForList("Member.getUserBanknameList", null);
	}

	/**
	 *  通过id查询定位信息
	 */
	public LocationVo queryLocation(LocationVo locationVo) throws Exception {
		return (LocationVo)dao.findForObject("Member.queryLocationById",locationVo);
	}
	/**
	 * 新增定位信息
	 */
	public synchronized void  addLocation(LocationVo locationVo) throws Exception {
		dao.save("Member.addLocation", locationVo);
	}
	/**
	 * 修改定位信息
	 */
	public void updateLocation(LocationVo locationVo) throws Exception {
		dao.update("Member.updateLocation", locationVo);
	}





	/**
	 * 用户登录时，保存IP,GPS,type,area,login_time,member_id
	 * @param loginLogBo
	 * @return
	 * @throws Exception
	 */
	public void saveLoginLog(LoginLogBo loginLogBo) throws Exception {

		dao.save("Member.saveLoginLog", loginLogBo);
	}

	/**
	 * 登录时修改登录时间 上次登录时间 登录次数
	 * @param userInfoBO
	 * @throws Exception
	 */
	public void updateLoginTime(UserInfoBO userInfoBO)throws Exception{
		dao.findForObject("Member.updateLoginTime",userInfoBO);
	}



	/**
	 * 根据id查询token
	 * @param userInfoBO
	 * @return
	 * @throws Exception
	 */
	public TokenBO findTokenById(UserInfoBO userInfoBO)throws Exception{
		return (TokenBO) dao.findForObject("Member.findTokenById",userInfoBO);
	}


	/**
	 * 根据ID修改token
	 * @param tokenBO
	 * @throws Exception
	 */
	public void updateTokenById(TokenBO tokenBO)throws Exception{
		dao.update("Member.updateTokenById", tokenBO);

	}

	/**
	 * 添加token
	 * @param tokenBO
	 * @throws Exception
	 */
	public void saveToken(TokenBO tokenBO)throws Exception{
		dao.findForObject("Member.setToken", tokenBO);
	}




	//==========================注册==========================================

	/*
    * 用户注册
    */
	public void userRegister (UserInfoBO infoBO)throws Exception{
		dao.findForObject("Member.userRegister", infoBO);
	}

	/*
    * 查询银行卡是否注册
    */
	public UserInfoBO checkBankCardForAdd (UserInfoVO userInfoVO)throws Exception{
		return (UserInfoBO)dao.findForObject("Member.checkBankCardForAdd", userInfoVO);
	}
	/*
     * 查询用户名是否重复
    */
	public UserInfoBO checkUserNameForAdd (UserInfoVO userInfoVO)throws Exception{
		return (UserInfoBO)dao.findForObject("Member.checkUserNameForAdd", userInfoVO);
	}


	/*
     * 查询用户(真实姓名)是否重复
    */
	public UserInfoBO checkTrueNameForAdd (UserInfoVO userInfoVO)throws Exception{
		return (UserInfoBO)dao.findForObject("Member.checkTrueNameForAdd", userInfoVO);
	}

	//个人账户数据初始化
	public void saveUserBalance(TbuserbalanceBo tbuserbalanceBo)throws Exception{
		dao.save("Member.saveUserBalance",tbuserbalanceBo);
	}

	/**
	 * 通过用户名查询信息
	 * @param userInfoBO
	 * @return
	 * @throws Exception
	 */
	public UserInfoBO findByName(UserInfoBO userInfoBO)throws Exception{
		return (UserInfoBO) dao.findForObject("Member.findByName",userInfoBO);
	}



	//================================================================================




	//=============================查询用户信息==================================

	//根据token查询用户id
	public UserInfoBO queryByToken(TokenBO tokenBO)throws Exception{
		return (UserInfoBO) dao.findForObject("Member.queryByToken",tokenBO);
	}


	/**
	 *查询交易记录
	 * @param userInfoBO
	 * @return
	 * @throws Exception
	 */
	public TbAmountrecordBO findAmountrecordById(UserInfoBO userInfoBO)throws Exception{
		return (TbAmountrecordBO) dao.findForObject("Member.findAmountrecordById", userInfoBO);
	}

	/**
	 * 根据inviter查询信息
	 * @param userInfoBO
	 * @return
	 * @throws Exception
	 */
	public List<MemberInfoBo> queryByInviterId(UserInfoBO userInfoBO)throws Exception{
		return (List<MemberInfoBo>) dao.findForList("Member.queryByInviterId", userInfoBO);
	}

	//======================================================================

	//=================================修改个人基本信息====================================================

	/*
    * 修改信息查询银行卡
    */
	public UserInfoBO findBankCardForUpdate(UpdateUserInfoVo updateUserInfoVo) throws Exception{
		return (UserInfoBO) dao.findForObject("Member.findBankCardForUpdate", updateUserInfoVo);
	}

	/**
	 * 修改基本信息
	 * @param userInfoBO
	 * @throws Exception
	 */
	public void updateUserInfo(UserInfoBO userInfoBO)throws Exception {
		dao.update("Member.updateUserInfo", userInfoBO);
	}

	/**
	 * 检查用户的支付信息是否完整
	 */
	public Map<String,Object> checkPayInfo(TokenVo tokenVo) throws Exception {
		Map<String,Object> map=Tools.errMessageSystem();
		try {
			//查询用户支付信息
			PayInfoBo payInfoBo= (PayInfoBo) dao.findForObject("Member.checkPayInfo",tokenVo);

			//校验身份证号
			if (payInfoBo.getMember_idcard()==null||"".equals(payInfoBo.getMember_idcard())){
				map.put("code","1");
				map.put("data",null);
				map.put("message","您的身份证信息没有填写,请完善");
				return map;
			}
			//校验真实姓名
			if (payInfoBo.getMember_truename()==null||"".equals(payInfoBo.getMember_truename())){
				map.put("code","1");
				map.put("data",null);
				map.put("message","您的真实姓名没有填写,请完善");
				return map;
			}
			//校验银行卡
			if (payInfoBo.getMember_bankcard()==null||"".equals(payInfoBo.getMember_bankcard())){
				map.put("code","1");
				map.put("data",null);
				map.put("message","您的银行卡信息不完整,请完善");
				return map;
			}
			//校验银行信息
			if (payInfoBo.getMember_bankname()==null||"".equals(payInfoBo.getMember_bankname())){
				map.put("code","1");
				map.put("data",null);
				map.put("message","您的银行名称信息不完整,请完善");
				return map;
			}
			//校验银行卡类型
			if (payInfoBo.getMember_cardtype()==null||"".equals(payInfoBo.getMember_cardtype())){
				map.put("code","1");
				map.put("data",null);
				map.put("message","您的银行卡类型没有选择,请完善");
				return map;
			}
			//校验支付密码
			if (payInfoBo.getMember_paypwd()==null||"".equals(payInfoBo.getMember_paypwd())){
				map.put("code","1");
				map.put("data",null);
				map.put("message","您还没有填写支付密码,请完善");
				return map;
			}
			map.put("code","0");
			map.put("data",null);
			map.put("message","支付信息校验成功");
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
		return map;
	}

	//======================================================================

	//=================================保存姓名，身份证号====================================================
	//保存支付密码
	public boolean setPayPassword(UserInfoBO userInfoBO)throws Exception{
		try{
			dao.findForObject("Member.setPayPassword",userInfoBO);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/*
    * 保存姓名，身份证号
    */
	public void updateMemberInfo( UserInfoBO userInfoBO) throws Exception{
		 dao.update("Member.updateMemberInfo", userInfoBO);
	}

	//======================================================================

	/**
	 * 保存用户银行卡相关信息
	 * @param userInfoBO
	 */
	public void saveBankCardInfo(UserInfoBO userInfoBO) throws Exception {

		dao.update("Member.saveBankCardInfo", userInfoBO);
	}


	// ========================================================================================================

	/**
	 * 设置团队标识
	 * @param inviterid
	 * @return
	 */
	public String setTeamSign(String inviterid) throws Exception {

		String teamSign = "";

		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setInviter(inviterid);

		if("".equals(inviterid) || inviterid == null) {
			// 推荐人为空
			List<String> listMax = (List<String>) dao.findForList("Member.getTopTeamSign", null);
			if(listMax != null && listMax.size() > 0) {
				if(listMax.get(0) == null || "" == listMax.get(0)) {
					teamSign = "0001";
				}else {
					String str = listMax.get(0);  // 团队标示
					int len = str.length();    // 字符串长度
					int index = 0;   // 预定义第一个不为0自负的位置
					char strs[] = str.toCharArray();    // 将字符串转换成字符数组
					for (int i = 0; i < len; i ++){
						if ('0' != strs[i]){
							index = i;
							break;
						}
					}
					teamSign = str.substring(index, len);    // 截取字符串
					int i = Integer.valueOf(teamSign) + 1;
					if (i < 10) {
						teamSign = "000" + i;
					} else if (i < 100) {
						teamSign = "00" + i;
					} else if(i<1000){
						teamSign = "0" + i;
					}else{
						teamSign=""+i;
					}

				}
			}
		}else {
			// 推荐人不为空
			List<UserInfoBO> list = (List<UserInfoBO>) dao.findForList("Member.getTeamSignByInviterid", userInfoVO);

			// 该人员有下级团队人员
			if(list != null && list.size() > 0) {
				UserInfoBO userInfoBO = list.get(0);

				if(userInfoBO == null) {
					List<UserInfoBO> listBase = (List<UserInfoBO>) dao.findForList("Member.getTeamSignByMemberName", userInfoVO);

					UserInfoBO userInfoBO1 = listBase.get(0);
					if(userInfoBO1 != null) {
						teamSign = listBase.get(0).getTeam_sign() + "0001";
					}else {
						return null;
					}

				}else {
					String str = list.get(0).getTeam_sign();    // 团队标识符
					String str1 = str.substring(str.length()-4, str.length());   // 该级人员团队标识符
					String strFather = str.substring(0, str.length() - 4);   // 该级人员父节点团队标识符

					int len = str1.length();    // 取得字符串长度
					int index = 0;   // 预定义一个非0字符的位置
					char strs[] = str1.toCharArray();   // 将字符串转换成字符数组
					for (int i = 0; i < len; i++){
						if('0' != strs[i]) {
							index = i;
							break;
						}
					}

					teamSign = str1.substring(index, len);   // 截取字符串
					int i = Integer.valueOf(teamSign) + 1;
					if (i < 10) {
						teamSign = "000" + i;
					} else if ( i < 100) {
						teamSign = "00" + i;
					} else if(i<1000){
						teamSign = "0" + i;
					}else{
						teamSign=""+i;
					}
					teamSign = strFather + teamSign;
				}
			}
		}
		return teamSign;
	}

	/**
	 * 更新用户信息（团队标识符，推荐人）
	 * （需要：userName,team_sign,inviter）
	 * @param userInfoVO
	 */
	public void updateTeamSign(UserInfoVO userInfoVO) {

		try {
			dao.update("Member.updateTeamSign", userInfoVO);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * 检查是否可以修改个人信息
	 * @auth 李荣洲
	 * @param tokenVo
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> checkCanModify(TokenVo tokenVo) throws Exception {
		Map<String,Object> map=Tools.errMessageSystem();
		List<UserInfoBO> list=null;
		try {
			//查询是否有子成员
			// 2016-01-16注
//			list = (List<UserInfoBO>) dao.findForList("Member.queryChildMembers", tokenVo);
//			if (list.size() > 0) {
//				map.put("message", "账号已有下级成员，不允许修改个人信息");
//				map.put("code", "1");
//				map.put("data", null);
//				return map;
//			}
//			//查询是否有有效订单
			// 2016-01-16注
//			list = (List<UserInfoBO>) dao.findForList("Member.queryAmountsByUid", tokenVo);
//			if (list.size() > 0) {
//				map.put("message", "账号已有订单记录，不允许修改个人信息");
//				map.put("code", "1");
//				map.put("data", null);
//				return map;
//			}
			map.put("message", "校验通过");
			map.put("code", "0");
			map.put("data", null);
		}catch (Exception e){
			e.printStackTrace();
			return map;
		}
		return  map;
	}

	public void modifyMobile(UserInfoBO userInfoBO) throws Exception {
		dao.update("Member.modifyMobile",userInfoBO);
	}
	/*public Map<String,Object> addHealthCondition(HealthConditionVo healthConditionVo) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		HealthConditionVo healthCondition=this.queryHealthCondition(healthConditionVo);
		if(healthCondition!=null){

			map.put("message","");
			map.put("code","");
			map.put("data","");
			return map;
		}
		dao.save("",);
		return map;
	}*/
//	public Map<String,Object> updateBodyCondition (HealthConditionVo healthConditionVo) throws Exception {
//		Map<String,Object> map=Tools.errMessageSystem();
//		try {
//			dao.update("Member.modifyBodyCondition",healthConditionVo);
//		} catch (Exception e) {
//			e.printStackTrace();
//			map=Tools.errMessageSystem();
//			return map;
//		}
//		map.put("message", "身体状况修改成功");
//		map.put("code", "0");
//		map.put("data", null);
//		return map;
//	}

	/**
	 * 修改健康状况
	 * @param healthConditionVo
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> updateHealthCondition (HealthConditionVo healthConditionVo) throws Exception {
		Map<String,Object> map=Tools.errMessageSystem();
		try {
			dao.update("Member.modifyHealthCondition",healthConditionVo);
		} catch (Exception e) {
			e.printStackTrace();
			map=Tools.errMessageSystem();
			return map;
		}
		map.put("message", "健康状况修改成功");
		map.put("code", "0");
		map.put("data", null);
		return map;
	}

	/**
	 * 获取健康状况
	 * @param healthConditionVo
	 * @return
	 * @throws Exception
	 */
	public HealthConditionVo queryHealthCondition(HealthConditionVo healthConditionVo) throws Exception {
		HealthConditionVo healthCondition= null;
		try {
			healthCondition=(HealthConditionVo) dao.findForObject("Member.queryHealthCondition",healthConditionVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return healthCondition;
	}
/**
 * 初始化健康状况
 */
	public void initHealthCondition(int member_id) throws Exception {
		dao.save("Member.initHealthCondition",member_id);
	}
	/**
	 * 通过memberid查询是否有下单记录
	 * @param userInfoBO
	 * @return
	 * @throws Exception
	 */
	public List<AmountListBO> checkAmountByMemberId(UserInfoBO userInfoBO) throws Exception {
		List<AmountListBO> list= (List<AmountListBO>) dao.findForList("Member.queryAmountsByMemberId",userInfoBO);
		return list;
	}

	/**
	 * 通过member_name查询是否有下级成员
	 * @param userInfoBO
	 * @return
	 * @throws Exception
	 */
	public UserInfoBO checkMemeberByInviter(UserInfoBO userInfoBO) throws Exception {
		UserInfoBO user= (UserInfoBO) dao.findForObject("Member.queryChildMembersByMemberName",userInfoBO);
		return user;
	}

}

