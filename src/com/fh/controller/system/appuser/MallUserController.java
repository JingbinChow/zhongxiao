package com.fh.controller.system.appuser;

import com.fh.controller.base.BaseController;
import com.fh.entity.bo.TokenBO;
import com.fh.entity.bo.UserInfoBO;
import com.fh.entity.vo.MessageVO;
import com.fh.entity.vo.UserInfoVO;
import com.fh.service.system.appuser.AppuserService;
import com.fh.util.MD5;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/** 
 * 类名称：AppuserController
 * 创建人：FH 
 * 创建时间：2014年6月28日
 * @version
 */
@Controller
@RequestMapping(value="/zhongxiaotouzi/malluser")
public class MallUserController extends BaseController {
	
	@Resource(name="appuserService")
	private AppuserService appuserService;


	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/user_login")
	@ResponseBody
//	public Map<String, Object> userLogin()throws Exception {
		public Map<String, Object> appLogin(@RequestBody UserInfoVO user)throws Exception {
//		UserInfoVO user =new UserInfoVO();
//		user.setUserName("zxuser");
//		user.setPassWord("asdfghjkl");
		String token =Tools.getToken();
		Map<String, Object> modelMap = new   HashMap<String, Object>();
		user.setPassWord( MD5.md5(user.getPassWord()));
		UserInfoBO bo= appuserService.userLogin(user);
		if(bo == null){
			modelMap.put("message", "用户名或密码错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
		}else{
			//生成token
			TokenBO tokenBO = new TokenBO();
			tokenBO.setToken(token);
			tokenBO.setClient_type("wap");
			tokenBO.setMember_id(bo.getMember_id());
			tokenBO.setMember_name(bo.getMember_name());
			appuserService.setToken(tokenBO);
			MessageVO vo =new MessageVO();
			vo.setToken(token);
			vo.setFlag(bo.getTeam_sign());
			vo.setUid(String.valueOf(bo.getMember_id()));
			Gson gons =new Gson();
			modelMap.put("message", "登录成功");
			modelMap.put("code","0");
			modelMap.put("data",gons.toJson(vo));
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
	@RequestMapping(value="/user_register")
	@ResponseBody
	public Map<String, Object> userRegister(@RequestBody UserInfoVO user)throws Exception {
		Map<String, Object> modelMap = new   HashMap<String, Object>();
		try{
			if(StringUtil.isNotEmpty(user.getBankcard())){
				UserInfoBO userInfoBO= appuserService.checkBankCard(user);
				if(userInfoBO==null){
					//校验用户名是否重复
					if(StringUtil.isNotEmpty(user.getUserName())){
						UserInfoBO bo= appuserService.checkUserName(user);
						if(bo ==null){
							UserInfoBO infoBO =new UserInfoBO();
							infoBO.setMember_address(Tools.filterNull(user.getAddress()));
							infoBO.setMember_area(Tools.filterNull(user.getArea()));
							infoBO.setMember_bankname(Tools.filterNull(user.getBankName()));
							infoBO.setMember_bankcard(Tools.filterNull(user.getBankcard()));
							infoBO.setMember_email(Tools.filterNull(user.getEmail()));
							infoBO.setMember_idcard(Tools.filterNull(user.getIdcard()));
							infoBO.setMember_mobile(Tools.filterNull(user.getMobile()));
							infoBO.setMember_passwd(MD5.md5(Tools.filterNull(user.getPassWord())));
							infoBO.setMember_sex(Integer.valueOf(Tools.filterNull(user.getSex())));
							infoBO.setMember_name(Tools.filterNull(user.getUserName()));
							infoBO.setMember_truename(Tools.filterNull(user.getName()));


							if(StringUtil.isNotEmpty(user.getInviter())){
								//将推荐人id 赋予为username  便于查找该推荐人是否存在
								user.setUserName(user.getInviter());
								//查询推荐人是否存在
								UserInfoBO inBO= appuserService.checkUserName(user);
								if(inBO == null){
									modelMap.put("message", "推荐人不存在");
									modelMap.put("code","0");
									modelMap.put("data",null);
									return  modelMap;
								}else{
									infoBO.setInviter_id(inBO.getMember_id()+"");
								}
							}else{
								infoBO.setInviter_id(null);
							}
							appuserService.userRegister(infoBO);

							modelMap.put("message", "注册成功");
							modelMap.put("code","0");
							modelMap.put("data",null);

						}else{
							modelMap.put("code","1");
							modelMap.put("data",null);
							modelMap.put("message", "该用户名已存在");
							return  modelMap;
						}
					}
				}else{
					modelMap.put("code","1");
					modelMap.put("data",null);
					modelMap.put("message", "该银行卡已经注册");
					return  modelMap;
				}
			}
		}catch (Exception e){
			modelMap.put("message", "系统内部错误");
			modelMap.put("code","1");
			modelMap.put("data",null);
			e.printStackTrace();
		}
		return modelMap;
	}

}
