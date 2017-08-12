package com.fh.controller.community.common;

import com.fh.controller.base.BaseController;
import com.fh.entity.bo.MessageInfoBo;
import com.fh.entity.bo.*;
import com.fh.entity.system.User;
import com.fh.entity.vo.*;
import com.fh.entity.zxys.AdminDoctorListBo;
import com.fh.huanxin.service.impl.TalkDataServiceImpl;
import com.fh.service.community.common.CommonService;
import com.fh.util.Constants;
import com.fh.util.MD5;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.rmi.CORBA.Stub;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="/common")
public class CommonController extends BaseController {

	@Resource(name = "commonService")
	private CommonService commonService;

	@RequestMapping(value = "version")
	@ResponseBody
	public Map<String, Object> version(@RequestBody TokenVo vo) throws Exception {
		Map<String, Object> modelMap = Tools.errMessage();
		TokenVo url = new TokenVo();

		if (vo.getType() == null || "".equals(vo.getType())) {
			vo.setType("zxsq_version");
			vo.setUrl("zxsq_url");
		} else if (vo.getType().equals("2")) {
			vo.setType("zxys_version");
			vo.setUrl("zxys_url");
		}

		TokenVo tokenVo = commonService.queryVersion(vo);

		if (tokenVo.getVersion().equals(vo.getVersion())) {
			modelMap.put("message", "已是最新版本");
			modelMap.put("code", "0");
			modelMap.put("data", url);
		} else {
			url.setUrl(tokenVo.getUrl());
			modelMap.put("message", "版本过于老旧请到官网更新客户端");
			modelMap.put("code", "1");
			modelMap.put("data", url);
		}

		return modelMap;
	}

	@RequestMapping(value = "validateForegister")
	@ResponseBody
	//获取短信验证
	public Map<String, Object> validateForegister(@RequestBody TokenVo vo) throws Exception {
		Map<String, Object> modelMap = Tools.errMessage();

		if (StringUtil.isNotEmpty(vo.getPhone())) {//注册
			TokenVo reVo = commonService.validateForegister(vo);
			if ("000000".equals(reVo.getCode())) {
				modelMap.put("message", "获取验证码失败,请60秒后重新获取");
				modelMap.put("code", "1");
				modelMap.put("data", null);
			} else {
				modelMap.put("message", "获取验证码成功");
				modelMap.put("code", "0");
				modelMap.put("data", reVo);
			}
		} else {
			modelMap.put("message", "请填写手机号码");
			modelMap.put("code", "1");
			modelMap.put("data", null);
		}

		return modelMap;
	}

	@RequestMapping(value = "confirmValidate")
	@ResponseBody
	//确认短信验证
	public Map<String, Object> confirmValidate(@RequestBody TokenVo vo) throws Exception {
		Map<String, Object> modelMap = Tools.errMessage();

		if (StringUtil.isEmpty(vo.getCode())) {
			modelMap.put("message", "请输入验证码");
			modelMap.put("code", "1");
			modelMap.put("data", null);
		} else if (StringUtil.isEmpty(vo.getPhone())) {
			modelMap.put("message", "请输入手机号码");
			modelMap.put("code", "1");
			modelMap.put("data", null);
		} else {
			SmsBO bo = commonService.confirmValidate(vo);
			if (bo == null) {
				modelMap.put("message", "验证码错误");
				modelMap.put("code", "1");
				modelMap.put("data", null);
			} else {
				int i = bo.getAdd_time(); //生成验证码时间撮
				int seconds = Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000));//当前时间搓
				int interval = seconds - i;
				if (interval > 1800) {
					modelMap.put("message", "验证码已过期,请重新获取验证码");
					modelMap.put("code", "1");
					modelMap.put("data", null);
				} else {
					bo.setRegistrationtype(vo.getRegistrationypeType());
					commonService.setType(bo);

					modelMap.put("message", "验证成功");
					modelMap.put("code", "0");
					modelMap.put("data", null);
				}


			}

		}
		return modelMap;
	}


	//	密码找回获取验证码
	@RequestMapping(value = "findBackPassWord")
	@ResponseBody
	public Map<String, Object> findBackPassWord(@RequestBody TokenVo vo) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		UserInfoBO userInfoBO = commonService.checkMember(vo);
		if (userInfoBO != null) {
			vo.setPhone(userInfoBO.getMember_mobile());
			vo.setUserid(userInfoBO.getMember_id() + "");
			vo.setName(userInfoBO.getMember_name());
			TokenVo reVo = commonService.validateFindBackPassword(vo);
			if ("000000".equals(reVo.getCode())) {
				map.put("message", "获取验证码失败,请60秒后重新获取");
				map.put("code", "1");
				map.put("data", null);
			} else {
				map.put("message", "获取验证码成功");
				map.put("code", "0");
				map.put("data", reVo.getCode());
			}

			//	密码找回


		} else {
			map.put("message", "用户名不存在");
			map.put("code", "1");
			map.put("data", null);
			return map;
		}
		return map;
	}

	@RequestMapping(value = "test")
	@ResponseBody
	public Map<String, Object> test() throws Exception {
		Map<String, Object> modelMap = Tools.errMessage();
		try {
			commonService.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelMap;
	}

	@RequestMapping(value = "confirmPassWord")
	@ResponseBody
	//确认短信验证
	public Map<String, Object> confirmPassWord(@RequestBody TokenVo vo) throws Exception {
		Map<String, Object> modelMap = Tools.errMessage();


		if (StringUtil.isEmpty(vo.getCode())) {
			modelMap.put("message", "请输入验证码");
			modelMap.put("code", "1");
			modelMap.put("data", null);
			return modelMap;
		} else {
			try {
				UserInfoBO userInfoBO = commonService.checkMember(vo);
				if (userInfoBO != null) {
					vo.setPhone(userInfoBO.getMember_mobile());
					SmsBO bo = commonService.confirmPassWord(vo);
					if (bo == null) {
						modelMap.put("message", "验证码错误");
						modelMap.put("code", "1");
						modelMap.put("data", null);
					} else {
						int i = bo.getAdd_time(); //生成验证码时间撮
						int seconds = Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000));//当前时间搓
						int interval = seconds - i;
						if (interval > 1800) {
							modelMap.put("message", "验证码已过期,请重新获取验证码");
							modelMap.put("code", "1");
							modelMap.put("data", null);
						} else {
							modelMap.put("message", "验证成功");
							modelMap.put("code", "0");
							modelMap.put("data", null);
						}
					}
				} else {
					modelMap.put("message", "用户名不存在");
					modelMap.put("code", "1");
					modelMap.put("data", null);
					return modelMap;

				}
			} catch (Exception e) {
				e.printStackTrace();
				modelMap = Tools.errMessageSystem();
				return modelMap;
			}


		}

		return modelMap;
	}

	/**
	 * 忘记密码
	 */
	@RequestMapping(value = "modifyPassWord")
	@ResponseBody
	public Map<String, Object> modifyPassWord(@RequestBody TokenVo tokenVo) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		try {
			UserInfoBO userInfoBO = null;
//			userInfoBO.setMember_passwd(MD5.md5(userInfoVO.getPassWord()));
//			userInfoBO.setMember_name(userInfoVO.getUserName());
			userInfoBO = commonService.checkMember(tokenVo);
			if (userInfoBO != null) {
				userInfoBO.setMember_passwd(MD5.md5(tokenVo.getPassWord()));
				commonService.modifyPassWord(userInfoBO);
				map.put("code", 0);
				map.put("data", null);
				map.put("message", "修改密码成功");
			} else {
				map.put("code", 1);
				map.put("data", null);
				map.put("message", "您输入的用户名不正确");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = Tools.errMessageSystem();
			return map;
		}
		return map;
	}

	/**
	 * 分页查询资讯信息
	 */
	@RequestMapping(value = "queryMessageInfo")
	@ResponseBody
	public Map<String, Object> queryMessageInfo(@RequestBody QueryPageVo vo) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		try {
			UserInfoBO userInfoBO = commonService.checkTokenIsGone(vo);
			if (userInfoBO != null) {
				List<MessageInfoBo> list = commonService.queryMessageInfo(vo);
				if (list != null && list.size() > 0) {
					map.put("code", 0);
					map.put("data", list);
					map.put("message", "获取数据成功");
				} else {
					map.put("code", 0);
					map.put("data", null);
					map.put("message", "暂无数据");
				}

			} else {
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = Tools.errMessageSystem();
			return map;
		}
		return map;
	}

	/**
	 * 分页查询中孝良品数据
	 */
	@RequestMapping(value = "queryMessageMall")
	@ResponseBody
	public Map<String, Object> queryMessageMall(@RequestBody QueryPageVo vo) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		try {
			UserInfoBO userInfoBO = commonService.checkTokenIsGone(vo);
			if (userInfoBO == null) {
				return map;
			} else {
				List<QueryMessageMallBo> list = commonService.queryMessageMall(vo);
				if (list != null && list.size() > 0) {
					map.put("data", list);
					map.put("message", "获取数据成功");
					map.put("code", 0);
				} else {
					map.put("data", null);
					map.put("message", "暂无数据");
					map.put("code", 0);
				}
				map.put("data", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = Tools.errMessageSystem();
			return map;
		}
		return map;
	}

	/**
	 * 通过member表中的一些特定字段查询咨询信息
	 *
	 * @param commonMemberVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryMemberInfo")
	@ResponseBody
	public Map<String, Object> queryMemberInfo(@RequestBody CommonMemberVo commonMemberVo) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		try {
			UserInfoBO memberInfo = commonService.queryMemberInfo(commonMemberVo);
			if (memberInfo != null) {
				map.put("data", memberInfo);
				map.put("message", "获取数据成功");
				map.put("code", 0);
			} else {
				map.put("data", null);
				map.put("message", "暂无数据");
				map.put("code", 0);
			}
			map.put("data", memberInfo);
		} catch (Exception e) {
			e.printStackTrace();
			map = Tools.errMessageSystem();
			return map;
		}
		return map;
	}

	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public Map<String, Object> appLogin(@RequestBody UserInfoVO userInfoVO) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String newToken = Tools.getToken();
		try {
			userInfoVO.setPassWord(MD5.md5(userInfoVO.getPassWord()));
			MemberBO bo = commonService.userLogin(userInfoVO);
			if (bo == null) {
				modelMap.put("message", "用户名或密码错误");
				modelMap.put("code", "1");
				modelMap.put("data", null);
			} else {
				//保存当前登录时间
//				//保存member_id   login_time type area gps ip
//				appuserService.saveLoginLog(userInfoVO);
				//判断该用户是否有token值
				UserInfoBO user = new UserInfoBO();
				user.setMember_id(bo.getMember_id());
				TokenBO tokenBO = commonService.findTokenById(user);

				if (tokenBO != null) {
					//如果存在修改用户token值
					tokenBO.setMember_id(bo.getMember_id());
					tokenBO.setToken(newToken);
					commonService.updateTokenById(tokenBO);
				} else {
					//如果不存在添加新的token
					tokenBO = new TokenBO();
					tokenBO.setMember_id(bo.getMember_id());
					tokenBO.setToken(newToken);
					tokenBO.setClient_type("wap");
					tokenBO.setMember_name(bo.getMember_name());
					commonService.saveToken(tokenBO);
				}
				MessageVoNew vo = new MessageVoNew();
				vo.setToken(newToken);
				vo.setFlag(bo.getTeam_sign());
				vo.setUid(String.valueOf(bo.getMember_id()));
				vo.setUsername(bo.getMember_name());

				modelMap.put("code", "0");
				modelMap.put("data", vo);
				modelMap.put("message", "登录成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("code", "1");
			modelMap.put("data", null);
			modelMap.put("message", "系统内部错误");
		}
		return modelMap;
	}


	/**
	 * 修改密码
	 */
	@RequestMapping(value = "updatePassword")
	@ResponseBody
	public Map<String, Object> updatePassword(@RequestBody UserInfoVO userInfoVO) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		TokenVo tokenVo = new TokenVo();
		tokenVo.setToken(userInfoVO.getToken());
		try {
			tokenVo = commonService.checkUserinfo(tokenVo);
			if (tokenVo != null) {
				UserInfoBO userInfoBO = new UserInfoBO();
				userInfoBO.setMember_passwd(MD5.md5(userInfoVO.getPassWord()));
				userInfoBO.setMember_name(tokenVo.getName());
				userInfoBO = commonService.checkUserForModifyPassWord(userInfoBO);

				if (userInfoBO != null) {
					userInfoBO.setMember_passwd(MD5.md5(userInfoVO.getNewpassWord()));
					commonService.modifyPassWord(userInfoBO);
					map.put("code", "0");
					map.put("data", null);
					map.put("message", "修改密码成功");
				} else {
					map.put("code", "1");
					map.put("data", null);
					map.put("message", "用户名或密码不正确");
					return map;
				}
			} else {
				map.put("code", "2");
				map.put("data", null);
				map.put("message", "token已过期");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = Tools.errMessageSystem();
			return map;
		}
		return map;
	}

	/**
	 * 获取主页初始数据信息
	 *
	 * @return
	 * @throws Exception
	 * @auth 李荣洲
	 */

	@RequestMapping(value = "queryIndexInfo")
	@ResponseBody()
	public Map<String, Object> queryIndexInfo() throws Exception {
		Map<String, Object> map = Tools.errMessageSystem();
		try {
			Map<String, Object> listMap = new HashMap<String, Object>();
			List<IndexInfoBo> goodList = commonService.queryIndexInfoGood();
			List<IndexInfoBo> doctorList = commonService.queryIndexInfoDoctor();
			List<IndexInfoBo> videoList = commonService.queryIndexInfoVideo();
			listMap.put("good", goodList);
			listMap.put("doctor", doctorList);
			listMap.put("video", videoList);
			map.put("code", "0");
			map.put("message", "获取数据成功");
			map.put("data", listMap);
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
		return map;
	}

	@RequestMapping(value = "checkPaypwd")
	@ResponseBody()
	public Map<String, Object> checkPaypwd(@RequestBody UserInfoBO userInfoBO) throws Exception {
		Map<String, Object> map = Tools.errMessage();
		UserInfoBO user = new UserInfoBO();
		try {
			if (userInfoBO.getToken() != null) {
				TokenVo tokenVo = new TokenVo();
				tokenVo = commonService.checkUserinfo(tokenVo);
				if (tokenVo == null) {
					return map;
				}
				userInfoBO.setMember_id(Integer.parseInt(tokenVo.getUserid().trim()));

				user = commonService.queryPaypwd(userInfoBO);
			} else {
				user = commonService.queryPaypwd(userInfoBO);
			}
			if (user.getMember_paypwd() == null) {
				map.put("code", "1");
				map.put("message", "未设置支付密码，校验未通过");
				map.put("data", null);
				return map;
			}
			if (MD5.md5(userInfoBO.getMember_paypwd()).equals(user.getMember_paypwd())) {
				map.put("code", "0");
				map.put("message", "密码正确");
				map.put("data", null);
			} else {
				map.put("code", "1");
				map.put("message", "密码错误");
				map.put("data", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Tools.errMessageSystem();
		}
		return map;
	}

	@RequestMapping(value = "/admin_queryMembers")
	@ResponseBody
	public List<MemberListVO> admin_queryMembers(@RequestBody AdminQueryMemberVo queryMemberVo) throws Exception {
//        AdminDoctorListBo adminDoctorListBo=new AdminDoctorListBo();
//		List<AdminDoctorListBo> list=doctorService.queryDoctors(adminDoctorListBo);
		if(queryMemberVo.getAddTimeStart()!=null&&!"".equals(queryMemberVo.getAddTimeStart())){
			queryMemberVo.setAddTimeStart(queryMemberVo.getAddTimeStart() + "%");
		}
		if(queryMemberVo.getAddTimeEnd()!=null&&!"".equals(queryMemberVo.getAddTimeEnd())){
			queryMemberVo.setAddTimeEnd(queryMemberVo.getAddTimeEnd() + "%");
		}
		if(queryMemberVo.getMember_name()!=null&&!"".equals(queryMemberVo.getMember_name())){
			queryMemberVo.setMember_name(queryMemberVo.getMember_name() + "%");
		}

		List<MemberListVO> list = commonService.queryMemberListByTime(queryMemberVo);
		return list;
	}

	@RequestMapping(value = "sendMessage")
	@ResponseBody
	//获取短信验证
	public String sendMessage(@RequestBody SendMessageVO vo) throws Exception {
		String result = "";
		if (StringUtil.isNotEmpty(vo.getSend())) {//注册
			result = commonService.MessageToSend(vo);
		}
		if (result.equals("1")) {
			return "fail";
		}else{
			return "0";
		}
	}
}
