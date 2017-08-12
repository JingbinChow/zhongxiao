package com.fh.controller.system.head;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Role;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.Tools;

/** 
 * 类名称：HeadController
 * 创建人：FH 
 * 创建时间：2014年8月16日
 * @version
 */
@Controller
@RequestMapping(value="/head")
public class HeadController extends BaseController {
	
	@Resource(name="userService")
	private UserService userService;	

	
	
	/**
	 * 获取头部信息
	 */
	@RequestMapping(value="/getUname")
	@ResponseBody
	public Object getList(HttpSession session) {
		
		Map map = new HashMap();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			
			PageData pds = new PageData();
			pds = (PageData)session.getAttribute("userpds");
			
			if(null == pds){
				String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();											//获取当前登录者loginname
				pd.put("USERNAME", USERNAME);
				pds = userService.findByUId(pd);
				session.setAttribute("userpds", pds);
			}
			
			pdList.add(pds);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 保存皮肤
	 */
	@RequestMapping(value="/setSKIN")
	public void setSKIN(PrintWriter out,HttpSession session){
		mv.clear();
		try{
			pd = this.getPageData();
			
			String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();											//获取当前登录者loginname
			pd.put("USERNAME", USERNAME);
			userService.setSKIN(pd);
			session.removeAttribute(Const.SESSION_userpds);
			session.removeAttribute(Const.SESSION_USERROL);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 去系统设置页面
	 */
	@RequestMapping(value="/goSystem")
	public ModelAndView goEditEmail() throws Exception{
		mv.clear();
		pd = this.getPageData();
		pd.put("YSYNAME", Tools.readTxtFile(Const.SYSNAME));	//读取系统名称
		pd.put("COUNTPAGE", Tools.readTxtFile(Const.PAGE));		//读取每页条数
		String strEMAIL = Tools.readTxtFile(Const.EMAIL);		//读取邮件配置
		if(null != strEMAIL && !"".equals(strEMAIL)){
			String strEM[] = strEMAIL.split(",fh,");
			if(strEM.length == 4){
				pd.put("SMTP", strEM[0]);
				pd.put("PORT", strEM[1]);
				pd.put("EMAIL", strEM[2]);
				pd.put("PAW", strEM[3]);
			}
		}
		mv.setViewName("system/head/sys_edit");
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 保存系统设置
	 */
	@RequestMapping(value="/saveSys")
	public ModelAndView saveU(PrintWriter out) throws Exception{
		mv.clear();
		pd = this.getPageData();
		Tools.writeFile(Const.SYSNAME,pd.getString("YSYNAME"));	//写入系统名称
		Tools.writeFile(Const.PAGE,pd.getString("COUNTPAGE"));	//写入每页条数
		Tools.writeFile(Const.EMAIL,pd.getString("SMTP")+",fh,"+pd.getString("PORT")+",fh,"+pd.getString("EMAIL")+",fh,"+pd.getString("PAW"));	//写入邮件服务器配置
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
}
