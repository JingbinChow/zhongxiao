package com.fh.controller.timetask;

import com.fh.controller.base.BaseController;
import com.fh.service.community.securities.SecuritiesService;
import com.fh.service.timetask.TimeTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component("TimeTaskController")
public class TimeTaskController extends BaseController {

	@Resource(name = "timeTaskService")
	private TimeTaskService timeTaskService;

	@Resource(name = "secutitiesService")
	private SecuritiesService securitiesService;

    //财务定时修改密码
	@Scheduled
	public void modify()throws Exception {
//	public Map<String, Object> login(@RequestBody AdminUserBO adminuser) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			timeTaskService.modify();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//
	@Scheduled
//	业绩定时结算
	public void settleAccounts()throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			timeTaskService.settleAccounts();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}