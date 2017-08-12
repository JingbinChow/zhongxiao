package com.fh.service.jpush;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("jpushService")
public class JpushService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取列表
	 */
	public List<PageData> listPageAMenu() throws Exception {
		return (List<PageData>) dao.findForList("JpushMapper.list", null);
	}
	
}
