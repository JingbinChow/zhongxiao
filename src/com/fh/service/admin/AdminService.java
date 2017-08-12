package com.fh.service.admin;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.bo.*;
import com.fh.entity.vo.*;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("adminService")
public class AdminService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//======================================================================================
    /*
    * App端用户登录确认
    */
	public AdminUserBO login(AdminUserBO adminuser) throws Exception {
		return (AdminUserBO) dao.findForObject("AdminMapper.login", adminuser);
	}


	/**
	 * 分页查询注册会员列表
	 */
	public List<MemberListBo> queryMembers(QueryMemberVo queryMemberVo) throws Exception {
		return (List<MemberListBo>) dao.findForList("AdminMapper.queryMembers", queryMemberVo);
	}

	/**
	 * 查询注册会员是否有已确认到账的积分订单
	 */
	public List<AmountListBO> queryAmounts(MemberListBo memberListBo) throws Exception {
		List<AmountListBO> list = (List<AmountListBO>) dao.findForList("AdminMapper.queryAmounts", memberListBo);
		return list;
	}

	/**
	 * 查询注册会员
	 */
	public List<UserInfoBO> findMemberByMemberId(MemberListBo memberListBo) throws Exception {
		List<UserInfoBO> list = (List<UserInfoBO>) dao.findForList("AdminMapper.findMemberByMemberId", memberListBo);
		return list;
	}
	/**
	 * 删除个人账户信息
	 *
	 * @return
	 */
	public void deleteMember(MemberListBo memberListBo) throws Exception {
		dao.delete("AdminMapper.deleteBalance", memberListBo);
		dao.delete("AdminMapper.deleteAmount", memberListBo);
		dao.delete("AdminMapper.deleteMember", memberListBo);
	}

}

