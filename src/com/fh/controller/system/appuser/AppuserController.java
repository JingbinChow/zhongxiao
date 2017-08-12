package com.fh.controller.system.appuser;

import com.fh.controller.base.BaseController;
import com.fh.entity.bo.*;
import com.fh.entity.system.MemberBankList;
import com.fh.entity.vo.*;
import com.fh.huanxin.model.TalkNode;
import com.fh.huanxin.service.impl.TalkDataServiceImpl;
import com.fh.service.system.appuser.AppuserService;
import com.fh.util.MD5;
import com.fh.util.MessageTemplate;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：AppuserController
 * 创建人：FH
 * 创建时间：2014年6月28日
 * @version
 */
@Controller
//@RequestMapping(value="/zhongxiaotouzi/user")
@RequestMapping(value="/")
public class AppuserController extends BaseController {

	@Resource(name="appuserService")
	private AppuserService appuserService;

	@Resource(name = "talkDataService")
	private TalkDataServiceImpl huanxinService;


	/**
	 * 请求登录，验证用户,同时保存IP,GPS,type,area,login_time,member_id
	 */
	@RequestMapping(value="user_login")
	@ResponseBody
	public Map<String, Object> appLogin(@RequestBody UserInfoVO userInfoVO)throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String newToken = Tools.getToken();
		try {
//			userInfoVO.setPassWord(MD5.md5(userInfoVO.getPassWord()));
			UserInfoBO bo = appuserService.userLogin(userInfoVO);
			//获取当前时间戳
			String time=String.valueOf(System.currentTimeMillis()/1000);
			//获取当前时间
			String loginTime= Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
			if (bo == null) {
				modelMap.put("message", "用户名或密码错误");
				modelMap.put("code", "1");
				modelMap.put("data", null);
			} else {

				//修改登录时间 上次登录时间 登陆次数
				bo.setMember_old_login_time(bo.getMember_login_time());
				bo.setMember_login_time(time);
				bo.setMember_login_num(bo.getMember_login_num() + 1);
				appuserService.updateLoginTime(bo);

				//保存数据(IP,GPS,type,area,login_time,member_id)
				LoginLogBo loginLogBo = new LoginLogBo();
				loginLogBo.setMember_id(bo.getMember_id());
				loginLogBo.setLogin_time(loginTime);
				loginLogBo.setType(userInfoVO.getMobile_type());
				loginLogBo.setArea(userInfoVO.getArea());
				loginLogBo.setGps(userInfoVO.getGps());
				loginLogBo.setIp(userInfoVO.getMobile_ip());
				appuserService.saveLoginLog(loginLogBo);

				//判断该用户是否有token值
				TokenBO tokenBO=appuserService.findTokenById(bo);

				if(tokenBO!=null){
					//如果存在修改用户token值
					tokenBO.setMember_id(bo.getMember_id());
					tokenBO.setToken(newToken);
					tokenBO.setLogin_time(time);
					appuserService.updateTokenById(tokenBO);
				}else{
					//如果不存在添加新的token
					tokenBO = new TokenBO();
					tokenBO.setMember_id(bo.getMember_id());
					tokenBO.setClient_type("wap");
					tokenBO.setToken(newToken);
					tokenBO.setMember_name(bo.getMember_name());
					tokenBO.setLogin_time(time);
					appuserService.saveToken(tokenBO);
				}
				MessageVO vo = new MessageVO();
				vo.setToken(newToken);
				vo.setFlag(bo.getTeam_sign());
				vo.setUid(String.valueOf(bo.getMember_id()));
				vo.setUsername(bo.getMember_name());
				Gson gons = new Gson();
				int state=0;
				//1代表主要信息完整2代表信息不完整
				if(bo.getInviter_id()==null||
						bo.getMember_truename()==null||
						"".equals(bo.getMember_truename().trim())||
						bo.getMember_bankcard()==null||
						bo.getMember_idcard()==null||
						bo.getMember_bankname()==null||
						bo.getTeam_sign()==null||
						"".equals(bo.getTeam_sign().trim())
						){
					state=2;
				}else{
					state=1;
				}
				modelMap.put("message", "登录成功");
				modelMap.put("code", "0");
				modelMap.put("state",state);
				modelMap.put("data", vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("message", "系统内部错误");
			modelMap.put("code", "0");
			modelMap.put("data", null);
		}
		return modelMap;
	}
	//	//1.1.1.校验token过期状态
//	@RequestMapping(value="/user_checkTokenIsLost")
//	@ResponseBody
////	public Map<String, Object> userLogin()throws Exception {
//	public Map<String, Object> userCheckTokenIsLost(@RequestBody UserInfoVO user)throws Exception {
//		TokenBO tokenBO= appuserService.userCheckTokenIsLost(user);
//		Map<String, Object> modelMap = new   HashMap<String, Object>();
//		if(tokenBO == null){
//			modelMap.put("message", "");
//			modelMap.put("code","0");
//			modelMap.put("data",null);
//		}else{
//			modelMap.put("message", "");
//			modelMap.put("code","2");
//			modelMap.put("data",null);
//		}
//		return modelMap;
//	}
	//注册
	@RequestMapping(value="user_register")
	@ResponseBody
	public Map<String, Object> userRegister(@RequestBody UserInfoVO userInfoVO)throws Exception {
		Map<String, Object> modelMap = new   HashMap<String, Object>();
		try{
			//校验用户名是否重复
			UserInfoBO infoBO =new UserInfoBO();
			if(StringUtil.isNotEmpty(userInfoVO.getUserName())){
				if(Tools.isEmpty(userInfoVO.getPassWord())){
					modelMap.put("message","密码不允许为空");
					modelMap.put("code","1");
					modelMap.put("data",null);
					return modelMap;
				}
				if(Tools.isEmpty(userInfoVO.getMobile())){
					modelMap.put("message","手机号不允许为空");
					modelMap.put("code","1");
					modelMap.put("data",null);
					return modelMap;
				}
				UserInfoBO bo= appuserService.checkUserNameForAdd(userInfoVO);
				if(bo ==null){
					//判断银行卡是否存在
					if(StringUtil.isNotEmpty(userInfoVO.getBankcard())) {
						UserInfoBO userInfoBO = appuserService.checkBankCardForAdd(userInfoVO);
						if (userInfoBO == null) {
							if(Tools.checkBankCard(userInfoVO.getBankcard())){
								infoBO.setMember_bankcard(Tools.filterNull(userInfoVO.getBankcard()));
							}else {
								modelMap.put("code","1");
								modelMap.put("data",null);
								modelMap.put("message", "请输入正确的银行卡号");
								return  modelMap;
							}
						}else{
							modelMap.put("code","1");
							modelMap.put("data",null);
							modelMap.put("message", "该银行卡已经注册");
							return  modelMap;
						}
					}
					if(StringUtil.isNotEmpty(userInfoVO.getMobile())){
						if(Tools.checkMobileNumber(userInfoVO.getMobile())){
							infoBO.setMember_mobile(Tools.filterNull(userInfoVO.getMobile()));
						}else{
							modelMap.put("code","1");
							modelMap.put("data",null);
							modelMap.put("message", "请输入正确的手机号");
							return  modelMap;
						}

					}
					if(StringUtil.isNotEmpty(userInfoVO.getEmail())){
						if(Tools.checkEmail(userInfoVO.getEmail())){
							infoBO.setMember_email(Tools.filterNull(userInfoVO.getEmail()));
						}else{
							modelMap.put("code","1");
							modelMap.put("data",null);
							modelMap.put("message", "请输入正确的邮箱地址");
							return  modelMap;
						}

					}
					if(StringUtil.isNotEmpty(userInfoVO.getIdcard())){
						if(Tools.checkidCard(userInfoVO.getIdcard())){
							infoBO.setMember_idcard(Tools.filterNull(userInfoVO.getIdcard()));
						}else {
							modelMap.put("code","1");
							modelMap.put("data",null);
							modelMap.put("message", "请输入正确的身份证号");
							return  modelMap;
						}

					}

					if(StringUtil.isNotEmpty(userInfoVO.getSex())){
						try {
							int sexId=Integer.parseInt(userInfoVO.getSex());
							if(sexId==0||sexId==1||sexId==2||sexId==3){
								infoBO.setMember_sex(sexId);
							}else{
								modelMap.put("message", "性别格式错误 0:没填1:男 2:女 3:保密");
								modelMap.put("code","1");
								modelMap.put("data",null);
								return  modelMap;
							}

						}catch (Exception e){
							e.printStackTrace();
							modelMap.put("message", "性别格式错误 0:没填1:男 2:女 3:保密");
							modelMap.put("code","1");
							modelMap.put("data",null);
							return  modelMap;
						}
					}else{
						infoBO.setMember_sex(0);
					}

					if(StringUtil.isNotEmpty(userInfoVO.getName())){
						infoBO.setMember_truename(Tools.filterNull(userInfoVO.getName()));
					}
					if(StringUtil.isNotEmpty(userInfoVO.getAddress())){
						infoBO.setMember_address(Tools.filterNull(userInfoVO.getAddress()));
					}
					if(StringUtil.isNotEmpty(userInfoVO.getBankName())){
						infoBO.setMember_bankname(Tools.filterNull(userInfoVO.getBankName()));
					}
					if(StringUtil.isNotEmpty(userInfoVO.getArea())) {
						infoBO.setMember_area(Tools.filterNull(userInfoVO.getArea()));
					}
					if(StringUtil.isNotEmpty(userInfoVO.getCardType())){
						infoBO.setMember_cardtype(Tools.filterNull(userInfoVO.getCardType()));
					}
					infoBO.setMember_name(Tools.filterNull(userInfoVO.getUserName()));
					infoBO.setMember_passwd(MD5.md5(Tools.filterNull(userInfoVO.getPassWord())));

					//获取当前时间戳
					String time=String.valueOf(System.currentTimeMillis()/1000);
					infoBO.setMember_time(time);


					//判断邀请人是否存在
					String team_sign = "";
					if(StringUtil.isNotEmpty(userInfoVO.getInviter())){
						//将推荐人id 赋予为username  便于查找该推荐人是否存在
						userInfoVO.setUserName(userInfoVO.getInviter());
						//查询推荐人是否存在
						UserInfoBO inBO= appuserService.checkUserNameForAdd(userInfoVO);
						if(inBO == null){
							modelMap.put("message", "推荐人不存在");
							modelMap.put("code","1");
							modelMap.put("data",null);
							return  modelMap;
						}else{
							infoBO.setInviter_id(inBO.getMember_name());
							team_sign=appuserService.setTeamSign(inBO.getMember_name());

						}
					}else{
						infoBO.setInviter_id(null);
						team_sign=appuserService.setTeamSign(userInfoVO.getInviter());
					}
					infoBO.setTeam_sign(team_sign);
					appuserService.userRegister(infoBO);
					//个人账户数据初始化
					try {

						TbuserbalanceBo tbuserbalanceBo = new TbuserbalanceBo();
						infoBO=appuserService.findByName(infoBO);
						tbuserbalanceBo.setUserid(infoBO.getMember_id());
						tbuserbalanceBo.setReward(0.0f);
						tbuserbalanceBo.setIntbalance(0);
						tbuserbalanceBo.setAccbalance(0.0f);
						tbuserbalanceBo.setRemainbalance(100000);
						tbuserbalanceBo.setName(infoBO.getMember_truename());
						appuserService.saveUserBalance(tbuserbalanceBo);
						appuserService.initHealthCondition(infoBO.getMember_id());
						modelMap.put("message", "注册成功");
						modelMap.put("code","0");
						modelMap.put("data",null);
						return modelMap;
					}catch (Exception e){
						e.printStackTrace();
						modelMap.put("message", "个人账户数据初始化失败");
						modelMap.put("code","1");
						modelMap.put("data",null);
						return modelMap;
					}
				}else{
					modelMap.put("code","1");
					modelMap.put("data",null);
					modelMap.put("message", "该用户名已存在");
					return  modelMap;
				}
			}else{
				modelMap.put("code","1");
				modelMap.put("data",null);
				modelMap.put("message", "用户名不能为空");
				return  modelMap;
			}

		}catch (Exception e){
			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
			e.printStackTrace();
			return modelMap;
		}
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value="user_modifyPassWord")
	@ResponseBody
//	public Map<String, Object> userLogin()throws Exception {
	public Map<String, Object> userModifyPassWord(@RequestBody UserInfoVO user)throws Exception {
		Map<String, Object> modelMap = new  HashMap<String, Object>();
		TokenBO tokenBO = new TokenBO();
		try{
			tokenBO.setToken(user.getToken());
			//根据token查询id
			UserInfoBO userInfoBO = appuserService.queryByToken(tokenBO);
			//验证token是否失效
			if(userInfoBO!=null){
				userInfoBO.setMember_passwd(MD5.md5(user.getPassWord()));
//				appuserService.modifyPassword(userInfoBO);
				modelMap.put("message", "修改密码成功");
				modelMap.put("code","0");
				modelMap.put("data",null);
			}else {
				modelMap.put("message", "token已过期");
				modelMap.put("code","2");
				modelMap.put("data",null);
				return modelMap;
			}
		}catch (Exception e){
			e.printStackTrace();

			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 查询个人账户详情
	 */
	@RequestMapping(value="queryUserBalanceInfo")
	@ResponseBody
	public Map<String, Object> queryUserBalanceInfo(@RequestBody TokenVo tokenVo)throws Exception {
		Map<String, Object> modelMap = new  HashMap<String, Object>();
		UserBalanceInfo userBalanceInfo=null;
		try {
			tokenVo=appuserService.findIdByToken(tokenVo);

			if(tokenVo==null){
				modelMap.put("message", "token已过期");
				modelMap.put("code","2");
				modelMap.put("data",null);
				return modelMap;
			}
			userBalanceInfo=appuserService.queryUserBalanceInfo(tokenVo);

			//获取vip列表
			List<VIPSBo> list=appuserService.getVIPS();
			//获取账户vip等级
			String vip=appuserService.queryVIP(tokenVo);
			int v=Integer.parseInt(vip);
			//遍历获取VIP级别信息
			for (VIPSBo vipsBo : list) {
				if (v==vipsBo.getCode_item_id()){
					vip=vipsBo.getCode_item_name();
					break;
				}
			}
			String charitableFund=appuserService.queryCharitable(tokenVo);
			System.out.println(vip+"-----"+charitableFund);


			userBalanceInfo.setVip(vip);
			userBalanceInfo.setCharitableFund(charitableFund);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
			return modelMap;
		}
		modelMap.put("message", "获取数据成功");
		modelMap.put("code","0");
		modelMap.put("data",userBalanceInfo);
		return modelMap;
	}
	/**
	 * 用户意见反馈
	 */
	@RequestMapping(value = "memberFeedBack")
	@ResponseBody
	public Map<String,Object> memberFeedBack(@RequestBody FeedBackVo feedBackVo) throws Exception {
		Map<String,Object> map=Tools.errMessage();
		TokenVo tv=new TokenVo();
		tv.setToken(feedBackVo.getToken());
		int userid=0;
		try {
			tv=appuserService.findIdByToken(tv);
			if(tv==null){
				return  map;
			}
			UserInfoBO user =appuserService.findMemberNameById(tv);

			if(feedBackVo.getSource()==null){
				feedBackVo.setSource("1");
			}
			feedBackVo.setMember_id(user.getMember_id());
			feedBackVo.setFtime(System.currentTimeMillis()/1000);
			feedBackVo.setMember_name(user.getMember_name());
			appuserService.feedBack(feedBackVo);
			map.put("message","您的意见已反馈到我们的平台，谢谢");
			map.put("code",0);
			map.put("data",null);
		} catch (Exception e) {
			e.printStackTrace();
			map=Tools.errMessageSystem();
			return map;
		}
		return map;
	}
	/**
	 * app端注册
	 */
	@RequestMapping(value = "registerMember")
	@ResponseBody
	public Map<String,Object> registerMember(@RequestBody UserInfoBO userInfoBO ) throws Exception {
		Map<String,Object>	map=appuserService.registerMember(userInfoBO);
		// 通过member_name查询用户信息
		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setUserName(userInfoBO.getMember_name());
		UserInfoBO userInfo = appuserService.checkUserName(userInfoVO);

		// 注册环信
		TalkNode node = huanxinService.userSave("zxkj_"+userInfo.getMember_id(), MD5.md5("123456"), null);

		if(node != null) {
			// 环信注册成功
			if(node.getEntities() == null) {
				if(node.getStatusCode() == 400) {
					map.put("code", "0");
					map.put("data", null);
					map.put("message", "请不要重复注册");

					return map;
				}
			}else {
				if(node.getEntities().get(0).getActivated() == true) {
					map.put("code", "0");
					map.put("data", null);
					map.put("message", "注册成功");

					return map;
				}

			}

		}
		return map;
	}

	/**
	 * 检查个人信息是否可以修改
	 * @auth 李荣洲
	 * @param tokenVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkCanModify")
	@ResponseBody
	public Map<String,Object> checkCanModify(@RequestBody  TokenVo tokenVo ) throws Exception {

		Map<String,Object>	map=Tools.errMessage();
		tokenVo=appuserService.findIdByToken(tokenVo);
		if(tokenVo==null){
			return map;
		}
		map=appuserService.checkCanModify(tokenVo);
	return map;
	}
	/**
	 * 校验信息是否完整
	 * *****
	 *
	 */
	@RequestMapping(value = "checkMemberInfo")
	@ResponseBody
	public Map<String,Object> checkMemberInfo(@RequestBody  TokenVo tokenVo ) throws Exception {

		Map<String,Object>	map=Tools.errMessage();
		TokenVo vo=appuserService.findIdByToken(tokenVo);
		if(vo==null){
			return map;
		}else{
			UserInfoBO userInfoBO=appuserService.queryMemberInfo(vo);

			if(1==userInfoBO.getMember_id()){
				map.put("message","校验信息通过");
				map.put("code","0");
				map.put("data",null);
				return map;
			}
			if(userInfoBO.getMember_idcard()==null||"".equals(userInfoBO.getMember_idcard().trim())){
				map.put("message","请完善您的身份证信息");
				map.put("code","1");
				map.put("data",null);
				return map;
			}
			if(userInfoBO.getMember_truename()==null||"".equals(userInfoBO.getMember_truename().trim())){
				map.put("message","请完善您的真实姓名");
				map.put("code","1");
				map.put("data",null);
				return map;
			}
			if(userInfoBO.getMember_bankcard()==null||userInfoBO.getMember_bankname()==null||"".equals(userInfoBO.getMember_bankcard().trim()) || "".equals(userInfoBO.getMember_bankname().trim())){
				map.put("message","请绑定您的银行卡信息");
				map.put("code","1");
				map.put("data",null);
				return map;

			}
			if(userInfoBO.getInviter_id()==null||"".equals(userInfoBO.getInviter_id().trim())||userInfoBO.getTeam_sign()==null||"".equals(userInfoBO.getTeam_sign().trim())){
				map.put("message","您还没有网络位置，请补全有效的推荐人");
				map.put("code","1");
				map.put("data",null);
				return map;

			}

				map.put("message","校验信息通过");
				map.put("code","0");
				map.put("data",null);


		}
		return map;
	}


	/**
	 * 2016-10-31
	 * 获取银行列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "user_getBanknameList")
	@ResponseBody
	public Map<String,Object> getBanknameList() throws Exception{
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<MemberBankList> list = appuserService.getUserBanknameList();
			if(list!=null&&list.size()>0){
				modelMap.put("message", "获取银行列表成功");
				modelMap.put("code","0");
				Gson json = new Gson();
				modelMap.put("data",json.toJson(list));
			}else{
				modelMap.put("message", "无法获取基本信息");
				modelMap.put("code","1");
				modelMap.put("data",null);
			}
		}catch (Exception e){
			e.printStackTrace();
			modelMap.put("message", "系统内部错误 ");
			modelMap.put("code","1");
			modelMap.put("data",null);
		}
		return modelMap;
	}
	/**
	 * 获取IP和定位
	 */
	@RequestMapping(value = "putLocation")
	@ResponseBody
	public Map<String,Object> putLocation(@RequestBody LocationVo locationVo ,HttpServletRequest request) throws Exception{

		Map<String, Object> modelMap = Tools.errMessage();
		try {
			TokenVo tokenVo=new TokenVo();
			tokenVo.setToken(locationVo.getToken());
			tokenVo=appuserService.findIdByToken(tokenVo);
			if(tokenVo==null){
				return modelMap;
			}else{
				String ip=Tools.getRemortIP(request);
				locationVo.setIp(ip);
				locationVo.setMember_id(Integer.parseInt(tokenVo.getUserid().trim()));
				Date date =new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=sdf.format(date);
				locationVo.setLogin_time(time);
				LocationVo location=appuserService.queryLocation(locationVo);
				if(location==null){
					appuserService.addLocation(locationVo);
				}else{
					appuserService.updateLocation(locationVo);
				}
				modelMap.put("message", "成功");
				modelMap.put("code","0");
				modelMap.put("data",null);
			}
		}catch (Exception e){
			e.printStackTrace();
			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
		}
		return modelMap;
	}

	/**
	 * 通过token查询用户信息以及是否可修改信息
	 * @param tokenVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryUserInfoByToken")
	@ResponseBody
	public Map<String,Object> queryUserInfoByToken(@RequestBody TokenVo tokenVo) throws Exception {
		Map<String,Object> map=Tools.errMessage();
		TokenBO tokenBO =new TokenBO();
		tokenBO.setToken(tokenVo.getToken());
		UserInfoBO userInfoBO = new UserInfoBO();
		userInfoBO = appuserService.queryByToken(tokenBO);
		if(userInfoBO!=null){
			MemberInfoVO member=new MemberInfoVO();
			member.setUsername(userInfoBO.getMember_name());
			member.setAddress(userInfoBO.getMember_address());
			member.setArea(userInfoBO.getMember_area());
			member.setBankcard(userInfoBO.getMember_bankcard());
			member.setBankname(userInfoBO.getMember_bankname());
			member.setEmail(userInfoBO.getMember_email());
			member.setIdcard(userInfoBO.getMember_idcard());
			member.setMobile(userInfoBO.getMember_mobile());
			member.setPayPwd(userInfoBO.getMember_paypwd());
			if(userInfoBO.getMember_sex()==null){
				member.setSex(0);
			}else{
				member.setSex(userInfoBO.getMember_sex());
			}
			member.setName(userInfoBO.getMember_truename());
			member.setInviter(userInfoBO.getInviter_id());
			member.setCardType(userInfoBO.getMember_cardtype());
			List<AmountListBO> list=appuserService.checkAmountByMemberId(userInfoBO);
 			// 2016-01-16注
//			if(list!=null&&list.size()>0){
//				map.put("code", "3");
//				map.put("data",member );
//				map.put("message", "账号已有订单记录，不允许修改个人信息");
//				return map;
//			}
			// 2016-01-16注
//			if(appuserService.checkMemeberByInviter(userInfoBO)!=null){
//				map.put("code", "3");
//				map.put("data",member );
//				map.put("message", "账号已有下级成员，不允许修改个人信息");
//				return map;
//			}
			map.put("code", "0");
			map.put("data",member );
			map.put("message", "个人信息获取成功");
//			return map;
	}else{
		return map;
	}
		return map;
	}

	/**
	 * 2016-10-31
	 * 根据token查询用户基本信息
	 * @param tokenVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "user_queryUserInfo")
	@ResponseBody
	public Map<String,Object> user_queryUserInfo(@RequestBody TokenVo tokenVo) throws Exception{
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try{
			TokenBO tokenBO =new TokenBO();
			tokenBO.setToken(tokenVo.getToken());
			UserInfoBO userInfoBO = new UserInfoBO();
			userInfoBO = appuserService.queryByToken(tokenBO);
			if(userInfoBO!=null){
				//查询交易记录
				TbAmountrecordBO tbAmountrecordBO = appuserService.findAmountrecordById(userInfoBO);
				// 2016-01-16注
//				if(tbAmountrecordBO!=null){
//					modelMap.put("code", "1");
//					modelMap.put("message", "账号已有订单记录，不允许修改个人信息");
//					modelMap.put("data", null);
//					return modelMap;
//				}
				List<MemberInfoBo> list = appuserService.queryByInviterId(userInfoBO);
				// 2016-01-16注
//				if(list.size()>0){
//					modelMap.put("code", "1");
//					modelMap.put("message", "账号已有下级成员，不允许修改个人信息");
//					modelMap.put("data", null);
//					return modelMap;
//				}
				if(userInfoBO.getMember_sex()==null){
					userInfoBO.setMember_sex(0);
				}
				modelMap.put("code", "0");
				modelMap.put("message", "个人信息获取成功");
				modelMap.put("data", userInfoBO);
				return modelMap;
			}else{
				modelMap.put("code", "2");
				modelMap.put("message", "token已过期");
				modelMap.put("data", null);
				return modelMap;
			}
		}catch (Exception e){
			e.printStackTrace();
			modelMap.put("code", "1");
			modelMap.put("message", "系统内部错误");
			modelMap.put("data", null);
			return modelMap;
		}
	}


	/**
	 * 修改个人基本信息  (未完成)
	 */
	@RequestMapping("user_modifyUserInfo")
	@ResponseBody
	public Map<String,Object> updateMemberBasic(@RequestBody UpdateUserInfoVo updateUserInfoVo) throws Exception{
		Map<String, Object> modelMap = new HashMap<String, Object>();
		TokenBO tokenBO = new TokenBO();
		try {
			tokenBO.setToken(updateUserInfoVo.getToken());

			//根据token查询id
			UserInfoBO userInfoBO = appuserService.queryByToken(tokenBO);
			//验证token是否失效
			if(userInfoBO!=null){
				if(!Tools.checkBankCard(updateUserInfoVo.getBankcard())){
					modelMap.put("message", "请输入正确的银行卡号");
					modelMap.put("code","1");
					modelMap.put("data",null);
					return modelMap;
				}
				if(!Tools.checkMobileNumber(updateUserInfoVo.getMobile())){
					modelMap.put("message", "请输入正确的手机号");
					modelMap.put("code","1");
					modelMap.put("data",null);
					return modelMap;
				}
				if(!Tools.checkEmail(updateUserInfoVo.getEmail())){
					modelMap.put("message", "请输入正确的邮箱地址");
					modelMap.put("code","1");
					modelMap.put("data",null);
					return modelMap;
				}
				if(!Tools.checkidCard(updateUserInfoVo.getIdcard())){
					modelMap.put("message", "请输入正确的身份证号");
					modelMap.put("code","1");
					modelMap.put("data",null);
					return modelMap;
				}
				//验证要修改的银行卡账号是否和原有的银行卡账号一样
				if(!updateUserInfoVo.getBankcard().equals(userInfoBO.getMember_bankcard())){
					//验证修改后的银行卡是否存在
					UserInfoBO infoBO = appuserService.findBankCardForUpdate(updateUserInfoVo);
					if(infoBO!=null){
						modelMap.put("message", "输入的银行卡号已存在 ");
						modelMap.put("code","1");
						modelMap.put("data",null);
						return modelMap;
					}
				}

				UserInfoBO infoBO = new UserInfoBO();
				infoBO.setMember_name(updateUserInfoVo.getInviter());
				String teamSign = "";
				//修改时是否填写邀请人信息
				if(StringUtil.isNotEmpty(updateUserInfoVo.getInviter())){
					infoBO=appuserService.findByName(infoBO);
					//填写的邀请人是否存在
					if(infoBO==null){
						modelMap.put("message", "输入的邀请人不存在请确认后再输");
						modelMap.put("code","1");
						modelMap.put("data",null);
						return modelMap;
					}
					// 存在的邀请人信息是否完善主要验证team_sign是否为空
					if(infoBO.getTeam_sign()==null||"".equals(infoBO.getTeam_sign())){
						modelMap.put("message", "您的邀请人信息未完善");
						modelMap.put("code","1");
						modelMap.put("data",null);
						return modelMap;
					}
					userInfoBO.setInviter_id(updateUserInfoVo.getInviter());
					teamSign = appuserService.setTeamSign(updateUserInfoVo.getInviter());

				}else{
					userInfoBO.setInviter_id(updateUserInfoVo.getInviter());
					teamSign = appuserService.setTeamSign(updateUserInfoVo.getInviter());
				}
				userInfoBO.setTeam_sign(teamSign);
				userInfoBO.setMember_truename(updateUserInfoVo.getName());
				if(StringUtil.isNotEmpty(updateUserInfoVo.getSex())){
					try {
						int sexId=Integer.parseInt(updateUserInfoVo.getSex());
						if(sexId==0||sexId==1||sexId==2||sexId==3){
							userInfoBO.setMember_sex(sexId);
						}else{
							modelMap.put("message", "性别格式错误 0:没填 1:男 2:女 3:保密");
							modelMap.put("code","1");
							modelMap.put("data",null);
							return  modelMap;
						}

					}catch (Exception e){
						e.printStackTrace();
						modelMap.put("message", "性别格式错误 0:没填1:男 2:女 3:保密");
						modelMap.put("code","1");
						modelMap.put("data",null);
						return  modelMap;
					}
				}else{
					userInfoBO.setMember_sex(0);
				}
				userInfoBO.setMember_idcard(updateUserInfoVo.getIdcard());
				userInfoBO.setMember_bankname(updateUserInfoVo.getBankname());
				userInfoBO.setMember_mobile(updateUserInfoVo.getMobile());
				userInfoBO.setMember_area(updateUserInfoVo.getArea());
				userInfoBO.setMember_address(updateUserInfoVo.getAddress());
				userInfoBO.setMember_email(updateUserInfoVo.getEmail());
				userInfoBO.setMember_cardtype(updateUserInfoVo.getCardType());
				userInfoBO.setMember_bankcard(updateUserInfoVo.getBankcard());
				//修改信息
				appuserService.updateUserInfo(userInfoBO);
				modelMap.put("message", "修改信息成功");
				modelMap.put("code","0");
				modelMap.put("data",null);
				return modelMap;
			}else{
				modelMap.put("code", "2");
				modelMap.put("message", "token已过期");
				modelMap.put("data", null);
				return modelMap;
			}
		}catch (Exception e){
			e.printStackTrace();
			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
			return modelMap;
		}
	}

	/**
	 * 校验交易信息是否完整
	 */
	@RequestMapping("checkPayInfo")
	@ResponseBody
	public Map<String,Object> checkPayInfo(@RequestBody TokenVo tokenVo) throws Exception{
		Map<String,Object> result=Tools.errMessage();
		tokenVo=appuserService.findIdByToken(tokenVo);
		if(tokenVo==null){
			return result;
		}
		result=appuserService.checkPayInfo(tokenVo);
		return result;
	}

	/**
	 * 保存姓名，身份证号
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateMemberInfo")
	@ResponseBody
	public Map<String, Object> updateMemberInfo(@RequestBody UserInfoVO userInfo) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		TokenBO tokenBO = new TokenBO();
		try {
			tokenBO.setToken(userInfo.getToken());

			//根据token查询id
			UserInfoBO userInfoBO = appuserService.queryByToken(tokenBO);
			//验证token是否失效
			if(userInfoBO!=null){
				//验证身份证号
				if(Tools.checkidCard(userInfo.getIdcard())) {

						userInfoBO.setMember_truename(userInfo.getName());


						userInfoBO.setMember_idcard(userInfo.getIdcard());

				}else {
					modelMap.put("code", "1");
					modelMap.put("message", "请输入正确的身份证号");
					modelMap.put("data", null);
					return modelMap;
				}
				appuserService.updateMemberInfo(userInfoBO);
				modelMap.put("message", "保存成功 ");
				modelMap.put("code","0");
				modelMap.put("data",null);
			} else {
				modelMap.put("code", "2");
				modelMap.put("message", "token已过期");
				modelMap.put("data", null);
				return modelMap;
			}
		}catch (Exception e){
			e.printStackTrace();
			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
		}
		return modelMap;
	}




	//保存支付密码
	@RequestMapping("user_setPayPassword")
	@ResponseBody
	public Map<String ,Object> setPayPassword(@RequestBody UserInfoVO userInfoVO)throws Exception{
		Map<String, Object> modelMap = new HashMap<String, Object>();
		TokenBO tokenBO = new TokenBO();
		try{
			tokenBO.setToken(userInfoVO.getToken());
			//根据token查询id
			UserInfoBO userInfoBO = appuserService.queryByToken(tokenBO);
			//验证token是否失效
			if(userInfoBO!=null){
				boolean result = true;

					userInfoBO.setMember_paypwd(MD5.md5(Tools.filterNull(userInfoVO.getMember_paypwd())));

				result = appuserService.setPayPassword(userInfoBO);
				if(result == true) {
					modelMap.put("code", "0");
					modelMap.put("message", "支付密码提交成功");
					modelMap.put("data", null);
				}else{
					modelMap.put("code", "1");
					modelMap.put("message", "支付密码提交失败");
					modelMap.put("data", null);
					return modelMap;
				}
			}else{
				modelMap.put("code", "2");
				modelMap.put("message", "token已过期");
				modelMap.put("data", null);
				return modelMap;
			}
		}catch (Exception e){
			e.printStackTrace();
			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
			return modelMap;
		}
		return modelMap;
	}


	/**
	 * 保存银行卡信息（卡号，银行，银行卡类型）
	 * (若数据存在，则进行修改，若不存在，则添加)
	 * @param userInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("user_saveBankCardInfo")
	@ResponseBody
	public Map<String, Object> saveBankCardInfo(@RequestBody UserInfoVO userInfoVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		TokenBO tokenBO = new TokenBO();
		try {
			if(userInfoVO.getBankcard()==null ||"".equals(userInfoVO.getBankcard())){
				map.put("code","1");
				map.put("message", "请输入银行卡号");
				map.put("data", null);
				return map;
			}
			if(userInfoVO.getBankName()==null ||"".equals(userInfoVO.getBankName())||"开户银行".equals(userInfoVO.getBankName())){
				map.put("code","1");
				map.put("message", "请选择卡属银行");
				map.put("data", null);
				return map;
			}
			if(userInfoVO.getCardType()==null ||"".equals(userInfoVO.getCardType())){
				map.put("code","1");
				map.put("message", "请选择银行卡类型");
				map.put("data", null);
				return map;
			}
			tokenBO.setToken(userInfoVO.getToken());
			//根据token查询id
			UserInfoBO userInfoBO = appuserService.queryByToken(tokenBO);
			//验证token是否失效
			if(userInfoBO != null) {

				// 判断绑定银行卡时添加的持卡人是否是注册用户的真是姓名
//				if(!userInfoBO.getMember_truename().equals(userInfoVO.getName())){
//					map.put("code","1");
//					map.put("message", "绑定银行卡持卡人姓名与注册用户真实姓名不符，请重新填写");
//					map.put("data", null);
//
//					return map;
//				}

				// 如果输入的银行卡好满足条件，则进行保存的相关操作

				if(Tools.checkBankCard(userInfoVO.getBankcard())) {
//					if(userInfoBO.getMember_bankcard() == null ||   "".equals(userInfoBO.getMember_bankcard()) ) {
						userInfoBO.setMember_bankcard(userInfoVO.getBankcard());
//					}
//					if(userInfoBO.getMember_bankname() == null || "".equals(userInfoBO.getMember_bankname()) ) {
						userInfoBO.setMember_bankname(userInfoVO.getBankName());
//					}
//					if(userInfoBO.getMember_cardtype() == null || "".equals(userInfoBO.getMember_cardtype()) ) {
						userInfoBO.setMember_cardtype(userInfoVO.getCardType());
//					}
				}else {
					map.put("code","1");
					map.put("message", "请输入正确的银行卡号");
					map.put("data", null);

					return map;
				}

				// 根据userid对用户信息进行完善
				appuserService.saveBankCardInfo(userInfoBO);
				map.put("code","0");
				map.put("message", "银行卡信息保存成功");
				map.put("data", null);
			}else {
				map.put("code", "2");
				map.put("message", "token已过期");
				map.put("data", null);
				return map;
			}
		}catch (Exception e){
			e.printStackTrace();
			map.put("code","1");
			map.put("message", "系统内部错误");
			map.put("data", null);
			return map;
		}
		return map;
	}

	/**
	 * 通过token获取用户姓名
	 * @param tokenVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("user_getTrueNameByToken")
	@ResponseBody
	public Map<String, Object> user_getTrueNameByToken(@RequestBody TokenVo tokenVo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TokenVo tokenVo1 = appuserService.findTrueNameByToken(tokenVo);
			if(tokenVo1 != null) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("trueName", tokenVo1.getTrueName());
				resultMap.put("cardType",tokenVo1.getCardType());
				resultMap.put("bankName",tokenVo1.getBankName());
				resultMap.put("bankCard",tokenVo1.getBankCard());
				map.put("code", "0");
				map.put("message", "获取用户名成功！");
				map.put("data", resultMap);
			}else {
				map.put("code", "2");
				map.put("message", "token以过期");
				map.put("data", null);
				return map;
			}
		}catch (Exception e){
			e.printStackTrace();
			map.put("code", "1");
			map.put("message", "系统内部错误");
			map.put("data", null);
			return map;
		}
		return map;
	}

//	/**
//	 * 获取用户的团队标识
//	 * 需要参数:inviter
//	 * @param userInfoVO
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("user_getTeamSign")
//	@ResponseBody
//	public Map<String, Object> getTeamSogn(@RequestBody UserInfoVO userInfoVO) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		try {
//			String sign = appuserService.setTeamSign(userInfoVO.getInviter());
//			if(sign != null && !"".equals(sign)) {
//				map.put("code", "0");
//				map.put("message", "生成团队标识成功");
//				map.put("data", sign);
//			}else {
//				map.put("code", "1");
//				map.put("message", "该推荐人不存在");
//				map.put("data", null);
//			}
//		}catch (Exception e){
//			e.printStackTrace();
//			map.put("code", "1");
//			map.put("message","系统内部错误");
//			map.put("data", null);
//
//			return map;
//		}
//
//		return map;
//	}

	/**
	 * 更新用户推荐人相关信息（推荐人，团队标识）
	 * 需要参数：inviter,userName
	 * @param userInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("user_updateTeamSign")
	@ResponseBody
	public Map<String, Object> updateTeamSign(@RequestBody UserInfoVO userInfoVO) throws Exception {


		Map<String, Object> map = new HashMap<String, Object>();
		try {

			TokenVo tokenVo = new TokenVo();
			tokenVo.setToken(userInfoVO.getToken());
			TokenVo tokenVo1 = appuserService.findIdByToken(tokenVo);

			String sign = appuserService.setTeamSign(userInfoVO.getInviter());

			if( sign == null) {
				map.put("code", "1");
				map.put("message","推荐人不存在");
				map.put("data", null);

				return map;
			}

			if(tokenVo1 == null) {
				map.put("code", "2");
				map.put("message","token过期");
				map.put("data", null);
			}else {
				userInfoVO.setTeam_sign(sign);
				userInfoVO.setUserName(tokenVo1.getName());
				appuserService.updateTeamSign(userInfoVO);

				map.put("code", "0");
				map.put("message","信息更新成功");
				map.put("data", null);
			}

		}catch (Exception e){
			e.printStackTrace();
			map.put("code", "1");
			map.put("message","系统内部错误");
			map.put("data", null);

			return map;
		}

		return map;
	}
	@RequestMapping("getMemberCardAndName")
	@ResponseBody
	public Map<String, Object> getMemberCardAndName(@RequestBody TokenBO tokenBO) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		UserInfoBO userInfoBO= null;
		try {
			userInfoBO = appuserService.queryByToken(tokenBO);
		} catch (Exception e) {
			e.printStackTrace();
			map=Tools.errMessageSystem();
			return map;
		}
		if(userInfoBO==null){
			return map;
		}
		CardAndNameBo cardAndNameBo=new CardAndNameBo(userInfoBO.getMember_idcard(),userInfoBO.getMember_truename(),userInfoBO.getMember_mobile());
		map.put("message","获取成功");
		map.put("code","0");
		map.put("data",cardAndNameBo);
		return map;
	}

	/**
	 * 修改绑定手机号
	 * @param userInfoBO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMobile")
	@ResponseBody
	public Map<String, Object> updateMobile(@RequestBody UserInfoBO userInfoBO) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		TokenBO tokenBO=new TokenBO();
		tokenBO.setToken(userInfoBO.getToken());
		UserInfoBO user=appuserService.queryByToken(tokenBO);
		if(user==null){
			return map;
		}
		userInfoBO.setMember_id(user.getMember_id());
		try {
			appuserService.modifyMobile(userInfoBO);
		} catch (Exception e) {
			e.printStackTrace();
			map=Tools.errMessageSystem();
			return map;
		}
		map.put("message","修改成功");
		map.put("code","0");
		map.put("data",null);
		return map;
	}

//	/**
//	 * 修改身体状况
//	 * 上行参数：weight height ismarried
//	 * @param healthConditionVo
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("updateBodyCondition")
//	@ResponseBody
//	public Map<String, Object> updateBodyCondition(@RequestBody HealthConditionVo healthConditionVo ) throws Exception {
//		Map<String, Object> map = Tools.errMessage();
//		TokenBO tokenBO=new TokenBO();
//		tokenBO.setToken(healthConditionVo.getToken());
//		UserInfoBO user= null;
//		try {
//			user = appuserService.queryByToken(tokenBO);
//		} catch (Exception e) {
//			e.printStackTrace();
//			map=Tools.errMessageSystem();
//			return map;
//		}
//		if(user==null){
//                return map;
//            }
//			healthConditionVo.setMember_id(user.getMember_id());
//			map=appuserService.updateBodyCondition(healthConditionVo);
//
//		return map;
//	}

	/**
	 *修改健康状况信息
	 * @param healthConditionVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateHealthCondition")
	@ResponseBody
	public Map<String, Object> updateHealthCondition(@RequestBody HealthConditionVo healthConditionVo ) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		TokenBO tokenBO=new TokenBO();
		tokenBO.setToken(healthConditionVo.getToken());

		UserInfoBO user= null;
		try {
			user = appuserService.queryByToken(tokenBO);
		} catch (Exception e) {
			e.printStackTrace();
			map=Tools.errMessageSystem();
			return map;
		}
		if(user==null){
				return map;
			}
			healthConditionVo.setMember_id(user.getMember_id());
			map=appuserService.updateHealthCondition(healthConditionVo);

		return map;
	}
	/**
	 * 查询用户健康状况
	 * @param healthConditionVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryHealthCondition")
	@ResponseBody
	public Map<String, Object> queryHealthCondition(@RequestBody HealthConditionVo healthConditionVo ) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		TokenBO tokenBO=new TokenBO();
		tokenBO.setToken(healthConditionVo.getToken());
		HealthConditionVo healthCodition=null;
		try {
			//校验token
			UserInfoBO user=appuserService.queryByToken(tokenBO);
			if(user==null){
				return map;
            }
			healthConditionVo.setMember_id(user.getMember_id());
			//通过member_id查询健康状况信息
			healthCodition=appuserService.queryHealthCondition(healthConditionVo);
		} catch (Exception e) {
			e.printStackTrace();
			map=Tools.errMessageSystem();
			return map;
		}
		if("0".equals(healthCodition.getModify_state())){
			map.put("message","初始化数据");
			map.put("code","5");
			map.put("data",healthCodition);

		}else{
			map.put("message","获取数据成功");
			map.put("code","0");
			map.put("data",healthCodition);
		}

		return map;
	}

}
