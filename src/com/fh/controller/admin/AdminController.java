package com.fh.controller.admin;

import com.fh.controller.base.BaseController;
import com.fh.entity.bo.*;
import com.fh.entity.vo.AmountListVO;
import com.fh.entity.vo.ConfirAmountListVO;
import com.fh.entity.vo.QueryMemberVo;
import com.fh.entity.vo.RewardVO;
import com.fh.service.admin.AdminService;
import com.fh.util.MD5;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    @Resource(name = "adminService")
    private AdminService adminService;

    /**************************************超级管理员 唯一账号***********************************************************************************/
    /**
     * 超级管理员登陆
     */
    @RequestMapping(value = "adminLogin")
    @ResponseBody
//	public Map<String, Object> userLogin()throws Exception {
    public Map<String, Object> adminLogin(@RequestBody AdminUserBO adminuser) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            adminuser.setPassWord(MD5.md5(adminuser.getPassWord()));
            AdminUserBO bo = adminService.login(adminuser);
            if (bo == null) {
                modelMap.put("message", "用户名或密码错误");
                modelMap.put("code", "1");
                modelMap.put("data", null);
            } else {
                if (bo.getRid() == 0) {
                    Gson gons = new Gson();
                    modelMap.put("message", "登录成功");
                    modelMap.put("code", "0");
                    modelMap.put("data", gons.toJson(bo));
                } else {
                    modelMap.put("message", "您无登录此系统权限");
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
        }
        return modelMap;
    }

    /**
     * 获取按条件分页查询用户列表
     */
    @RequestMapping(value = "queryMembers")
    @ResponseBody()
    public Map<String, Object> getMember(@RequestBody QueryMemberVo queryMemberVo) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        int index = queryMemberVo.getPageIndex();

        System.out.println(index);
        queryMemberVo.setPageIndex((index - 1) * queryMemberVo.getPageSize());
        try {
            List<MemberListBo> list = adminService.queryMembers(queryMemberVo);
            if (list != null && list.size() > 0) {
                result.put("message", "获取数据成功");
                result.put("code", "0");
                result.put("data", list);
            } else {
                result.put("message", "没有匹配到数据");
                result.put("code", "0");
                result.put("data", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "系统内部错误");
            result.put("code", "1");
            result.put("data", null);
        }

        return result;
    }

    /**
     * 按memberid删除注册账户信息
     */
    @RequestMapping(value = "deleteMemberById")
    @ResponseBody()
    public Map<String, Object> deleteMemberById(@RequestBody MemberListBo memberListBo) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        List<AmountListBO> list = adminService.queryAmounts(memberListBo);

        List<UserInfoBO> lsitUserInfoBO = adminService.findMemberByMemberId(memberListBo);

        if (list != null && list.size() > 0) {
            result.put("code", "1");
            result.put("data", null);
            result.put("message", "用户积分订单已确认，禁止删除");
        } else if (lsitUserInfoBO != null && lsitUserInfoBO.size() > 0) {
            result.put("code", "1");
            result.put("data", null);
            result.put("message", "该用户已组建团队，禁止删除");
        } else {
            try {
                adminService.deleteMember(memberListBo);
                result.put("code", "0");
                result.put("data", null);
                result.put("message", "删除成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.put("code", "1");
                result.put("data", null);
                result.put("message", "系统内部错误");
            }
        }
        return result;
    }

    /**
     * 获取按条件分页查询用户列表
     */
    @RequestMapping(value = "queryMembersList")
    @ResponseBody()
    public Map<String, Object> queryMemberList(@RequestBody QueryMemberVo queryMemberVo) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        int index = queryMemberVo.getPageIndex();

        System.out.println(index);
        queryMemberVo.setPageIndex((index - 1) * queryMemberVo.getPageSize());
        try {
            List<MemberListBo> list = adminService.queryMembers(queryMemberVo);
            if (list != null && list.size() > 0) {
                result.put("message", "获取数据成功");
                result.put("code", "0");
                result.put("data", list);
            } else {
                result.put("message", "没有匹配到数据");
                result.put("code", "0");
                result.put("data", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "系统内部错误");
            result.put("code", "1");
            result.put("data", null);
        }

        return result;
    }

}

