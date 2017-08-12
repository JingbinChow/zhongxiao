//package com.fh.controller.community.integral;
//
//import com.fh.controller.base.BaseController;
//import com.fh.entity.bo.*;
//import com.fh.entity.vo.*;
//
//import com.fh.service.community.common.CommonService;
//import com.fh.service.community.integral.IntegralService;
//import com.fh.service.system.appuser.AppuserService;
//import com.fh.util.Tools;
//import com.google.gson.Gson;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
//@Controller
//@RequestMapping(value = "/integral")
//public class IntegralController extends BaseController {
//
//    @Resource(name = "integralService")
//    private IntegralService integralService;
//
//    @Resource(name = "appuserService")
//    private AppuserService appuserService;
//
//
//    /**
//     * 获取当日交易净值
//     */
//    @RequestMapping(value = "integral_queryEquity")
//    @ResponseBody
//    public Map<String, Object> integralueryEquity(@RequestBody UserInfoVO user) throws Exception {
//        Map<String, Object> modelMap = Tools.errMessage();
//        //检查token是否过期
//        if ("2".equals(appuserService.userCheckTokenIsLost(user))) {
//            return modelMap;
//        } else {
//            TbEquityBO bo = integralService.integralueryEquity();
//            if (bo == null) {
//                modelMap.put("message", "无法获取到当日净 ");
//                modelMap.put("code", "1");
//                modelMap.put("data", null);
//            } else {
//                Gson gson = new Gson();
//                modelMap.put("message", "获取到当日净值 ");
//                modelMap.put("code", "0");
//                modelMap.put("data", gson.toJson(bo.getEquity()));
//            }
//        }
//        return modelMap;
//    }
//
//    /**
//     * 获取7日交易净值
//     */
//    @RequestMapping(value = "integral_queryEquitys")
//    @ResponseBody
//    public Map<String, Object> integralQueryEquitys() throws Exception {
//        Map<String, Object> modelMap = Tools.errMessage();
//        //检查token是否过期
////		if("2".equals(appuserService.userCheckTokenIsLost(user))){
////			return modelMap;
////		}else{
//        try {
//            List<TbEquityBO> list = integralService.integralQueryEquitys();
//            if (list != null && list.size() > 0) {
//                Gson gson = new Gson();
//                modelMap.put("message", "获取到七日净 ");
//                modelMap.put("code", "0");
//                modelMap.put("data", gson.toJson(list));
//            } else {
//                modelMap.put("message", "无法获取到七日净值");
//                modelMap.put("code", "1");
//                modelMap.put("data", null);
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误 ");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//        }
//
//        return modelMap;
//    }
//
//
//
//    /**
//     * 获取账户当日积分市值及账户积分情况
//     *
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "_queryBalanceInfo")
//    @ResponseBody
//    public Map<String, Object> queryBalanceInfo(@RequestBody TokenVo tokenVo) throws Exception {
//        Map<String, Object> modelMap = Tools.errMessage();
//        UserInfoBO userInfoBO = integralService.queryMemberByToken(tokenVo);
//
//        return modelMap;
//    }
//
//
//    /**
//     * 查询个人奖金数
//     */
//    @RequestMapping(value = "queryReward")
//    @ResponseBody
//    public Map<String, Object> queryReward(@RequestBody TokenVo tokenVo) throws Exception {
//        Map<String, Object> modelMap = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = integralService.findToken(tokenVo);
//            if (userInfoBO == null) {
//                modelMap.put("code", 2);
//                modelMap.put("data", null);
//                modelMap.put("message", "token已失效");
//            } else {
//                TbuserbalanceBo tb = integralService.integral_queryReward(userInfoBO);
//
//                modelMap.put("code", 0);
//                modelMap.put("data", tb);
//                modelMap.put("message", "获取奖金成功");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("code", 1);
//            modelMap.put("data", null);
//            modelMap.put("message", "系统内部错误");
//        }
//        return modelMap;
//    }
//
//    /**
//     * 通过token获取退单详情
//     */
//    @RequestMapping(value = "queryChargeBackDesc")
//    @ResponseBody
//    public Map<String, Object> queryChargeBackDesc(@RequestBody TokenVo tokenVo) throws Exception {
//        Map<String, Object> result = new HashMap<String, Object>();
//
////		if(1==1){
////			result.put("code",1);
////			result.put("data",null);
////			result.put("message","此功能暂未开放");
////			return  result;
////		}
//
//        result = integralService.queryChargeBackDesc(tokenVo);
//        return result;
//    }
//
//    /**
//     * 提交退单申请
//     */
//    @RequestMapping(value = "accountChargeBack")
//    @ResponseBody
//    public Map<String, Object> accountChargeBack(@RequestBody ChargeBackBo chargeBackBo) throws Exception {
//        Map<String, Object> result = new HashMap<String, Object>();
//
////		if(1==1){
////			result.put("code",1);
////			result.put("data",null);
////			result.put("message","此功能暂未开放");
////			return  result;
////		}
//
////        result = (chargeBackBo);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        String currentTime = sdf.format(date);
//        chargeBackBo.setCurrentTime(currentTime);
//        chargeBackBo.setSource("wap");
//        System.out.println(chargeBackBo.toString());
//        result = integralService.accountChargeBack(chargeBackBo);
//        return result;
//    }
//
//    /**
//     * 撤销退单
//     */
//    @RequestMapping(value = "removeChargeBack")
//    @ResponseBody
//    public Map<String, Object> removeChargeBack(@RequestBody QueryAmounVo queryAmounVo) throws Exception {
//        Map<String, Object> result = new HashMap<String, Object>();
//        TokenVo tv = new TokenVo();
//
//        tv = integralService.findIdByToken(tv);
//        if (tv == null) {
//            result.put("code", 2);
//            result.put("data", null);
//            result.put("message", "token已失效");
//        }
//        try {
//            integralService.removeChargeBack(queryAmounVo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.put("code", 1);
//            result.put("data", null);
//            result.put("message", "系统内部错误");
//            return result;
//        }
//        result.put("code", 0);
//        result.put("data", null);
//        result.put("message", "撤销成功");
//        return result;
//    }
//
//    /**
//     * 查看退单记录
//     */
//    @RequestMapping(value = "queryChargeBack")
//    @ResponseBody
//    public Map<String, Object> queryChargeBack(@RequestBody QueryAmounVo queryAmounVo) throws Exception {
//        Map<String, Object> result = new HashMap<String, Object>();
//        ChargeBackBo chargeBackBo = null;
//
//
//        try {
//            TokenVo tv = new TokenVo();
//            tv.setToken(queryAmounVo.getToken());
//            if (integralService.findIdByToken(tv) == null) {
//                result.put("code", 2);
//                result.put("data", null);
//                result.put("message", "token已失效");
//            }
//            chargeBackBo = integralService.getChargeBack(queryAmounVo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.put("code", 1);
//            result.put("data", null);
//            result.put("message", "系统内部错误");
//            return result;
//        }
//        if (chargeBackBo == null) {
//            result.put("code", 1);
//            result.put("data", null);
//            result.put("message", "系统数据错误");
//            return result;
//        }
//        result.put("code", 0);
//        result.put("data", chargeBackBo);
//        result.put("message", "获取数据成功");
//        return result;
//    }
//
//
//    /**
//     * 新的获取下级成员列表
//     *
//     * @return
//     */
//    @RequestMapping(value = "integral_getChildMember")
//    @ResponseBody
//    public Map<String, Object> integral_getChildMember(@RequestBody QueryMemberVo queryMemberVo) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = integralService.checkTokenIsGone(queryMemberVo);
//            if (userInfoBO != null) {
//                int member_id = userInfoBO.getMember_id();
//                queryMemberVo.setMember_id(member_id);
//                String member_name = userInfoBO.getMember_name();
//                queryMemberVo.setUserName(member_name);
//                int index = queryMemberVo.getPageIndex();
//                System.out.println(index);
//                queryMemberVo.setPageIndex((index - 1) * queryMemberVo.getPageSize());
//                /*获取总业绩*/
//                String Allachievement = integralService.getAchievement(queryMemberVo).getAchievement();
//                Double achievement = Double.valueOf(Allachievement);
//				/*获取下级成员数据*/
//                List<ChildMemberBo> list = integralService.getChildMember(queryMemberVo);
//                map.put("code", 0);
//                map.put("achievement", achievement);
//                map.put("data", list);
//                map.put("message", "获取数据成功");
//            } else {
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map = Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 获取当日个人账户市值
//     * flag为0:推荐人不存在
//     * flag为1:推荐人存在
//     * @return
//     */
//    @RequestMapping(value = "integral_queryBalanceInfo")
//    @ResponseBody
//    public Map<String, Object> integral_queryBalanceInfo(@RequestBody QueryMemberVo queryMemberVo) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        Map<String, Object> data = new HashMap<String, Object>();
//        try {
//            UserInfoBO userInfoBO = integralService.checkTokenIsGone(queryMemberVo);
//            if (userInfoBO != null) {
//                int member_id = userInfoBO.getMember_id();
//                queryMemberVo.setMember_id(member_id);
//
//                int flag = 0;
//                String inviter = userInfoBO.getInviter_id();
//                if(inviter!=null && !"".equals(inviter)){
//                    flag = 1;
//                }
//                //获取vip列表
//                List<VIPSBo> list = integralService.getVIPS();
//                //获取账户vip等级
//                String vip=integralService.queryVIP(queryMemberVo);
//
//                int v=Integer.parseInt(vip);
//                //遍历获取VIP级别信息
//                for (VIPSBo vipsBo : list) {
//                    if (v==vipsBo.getCode_item_id()){
//                        vip=vipsBo.getCode_item_name();
//                        break;
//                    }
//                }
//
//
//
//                int index = queryMemberVo.getPageIndex();
//
//                System.out.println(index);
//                queryMemberVo.setPageIndex((index - 1) * queryMemberVo.getPageSize());
//					/*获取净值*/
//                TbEquityBO bo = integralService.integralueryEquity();
//                float equity = Float.parseFloat(bo.getEquity());
//					/*通过memberid查询个人账户信息*/
//                TbuserbalanceBo tbuserbalance = integralService.getBalanceByUid(queryMemberVo);
//                float balance =  tbuserbalance.getIntbalance();
//                float realreward = tbuserbalance.getRealreward();
//                //float balance = Float.parseFloat(integralService.getBalanceByUid(queryMemberVo));
//					/*市值*/
//                float amount = balance * equity;
//					/*当前剩余可购买积分包数*/
//                VariableVo var = integralService.queryVar(queryMemberVo);
//                int overplus = Integer.parseInt(var.getValue());
//
//                data.put("equity", equity);
//                data.put("balance", balance);
//                data.put("amount", amount);
//                data.put("overplus", overplus);// 当前剩余可购买积分包数
//                data.put("viplevel",vip);//vip
//                data.put("isInviter",flag);//是否有推荐人
//                data.put("realreward",realreward);//奖金
//                data.put("inviter",inviter);
//
//                map.put("code", "0");
//                map.put("data", data);
//                map.put("message", "获取数据成功");
//            } else {
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map = Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 新提交积分充值订单记录
//     *
//     * @return
//     */
//    @RequestMapping(value = "addOrder")
//    @ResponseBody
//    public Map<String, Object> integral_addOrder(@RequestBody TbAmountrecordVo amountrecordVo) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = integralService.checkToken(amountrecordVo);
//            if (userInfoBO != null) {
//
//                if ("无".equals(userInfoBO.getInviter_id()) || userInfoBO.getInviter_id() == null || "".equals(userInfoBO.getInviter_id())) {
//                    map.put("code", "1");
//                    map.put("data", null);
//                    map.put("message", "您的个人信息不完善请完善个人信息");
//                } else {
//                    TbuserbalanceBo userbalance = integralService.queryUserBalance(amountrecordVo);
//
//                    int declarationNum = amountrecordVo.getDeclarationNum();
//
//                    String declarationPrice = amountrecordVo.getDeclarationPrice();
//                    if (declarationNum == 30000 && declarationPrice.equals("3000")) {
//                        declarationNum = 33000;
//                        amountrecordVo.setDeclarationNum(declarationNum);
//                        //第一笔订单保存记录
//
//                        int uid = userInfoBO.getMember_id();
//                        amountrecordVo.setUid(uid);
//                        TbAmountrecordVo order = integralService.queryOrderById(uid);
//                        if (order != null) {
//                            map.put("code", "1");
//                            map.put("data", null);
//                            map.put("message", "退单状态下不能购单");
//                            return map;
//                        } else {
//
//                            amountrecordVo.setBankcard(userInfoBO.getMember_bankcard());
//                            amountrecordVo.setBankName(userInfoBO.getMember_bankname());
//                            amountrecordVo.setType(amountrecordVo.getType());
//
//                            Date date = new Date();
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            String orderDate = sdf.format(date);
//                            amountrecordVo.setDeclaration_date(orderDate);
//
//                            amountrecordVo.setStatus(1);
//                            amountrecordVo.setIsdelete(0);
//
//                            amountrecordVo.setEquity(amountrecordVo.getEquity());
//                            amountrecordVo.setUnit(amountrecordVo.getBoxtotal());
//                            int box = 30000 / amountrecordVo.getBoxtotal();
//                            amountrecordVo.setBox(box);
//                            integralService.addOrder(amountrecordVo);
//                            integralService.modifyAllScore(amountrecordVo);
//                            integralService.modifyRemainbanlance(amountrecordVo);
//                            map.put("code", "0");
//                            map.put("data", null);
//                            map.put("message", "订单提交成功");
////							修改会员的状态
////							integralService.updatePrimitive(amountrecordVo);
//                            return map;
//                        }
//                    } else {
//                        int uid = userInfoBO.getMember_id();
//                        amountrecordVo.setUid(uid);
//                        amountrecordVo.setBankcard(userInfoBO.getMember_bankcard());
//                        amountrecordVo.setBankName(userInfoBO.getMember_bankname());
//                        amountrecordVo.setType(amountrecordVo.getType());
//
//                        Date date = new Date();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String orderDate = sdf.format(date);
//                        amountrecordVo.setDeclaration_date(orderDate);
//
//                        amountrecordVo.setStatus(1);
//                        amountrecordVo.setIsdelete(0);
//
//                        amountrecordVo.setEquity(amountrecordVo.getEquity());
//                        amountrecordVo.setUnit(amountrecordVo.getBoxtotal());
//                        int box = amountrecordVo.getDeclarationNum() / amountrecordVo.getBoxtotal();
//                        amountrecordVo.setBox(box);
//                        integralService.addOrder(amountrecordVo);
//                        integralService.modifyAllScore(amountrecordVo);
//                        integralService.modifyRemainbanlance(amountrecordVo);
//                        map.put("code", "0");
//                        map.put("data", null);
//                        map.put("message", "订单提交成功");
////							修改会员的状态
////							integralService.updatePrimitive(amountrecordVo);
//                        return map;
//                    }
//                }
//            } else {
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map = Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//
//    /**
//     * 订单详情页面
//     *
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "integral_getOrderByEid")
//    @ResponseBody
//    public Map<String, Object> getOrderDetail(@RequestBody TbAmountrecordVo tbAmountrecordVO) throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        Map<String, Object> modelMap = Tools.errMessage();
//
//        Map<String, Object> data = new HashMap<String, Object>();
//
//        try {
//
//            if (tbAmountrecordVO == null) {
//                map.put("code", 1);
//                map.put("message", "请输入您要查询的条件");
//                map.put("data", null);
//                return map;
//            }
//
//            UserInfoVO userInfoVO = new UserInfoVO();
//            userInfoVO.setToken(tbAmountrecordVO.getToken());
//            //检查token是否过期
//            if ("2".equals(appuserService.userCheckTokenIsLost(userInfoVO))) {
//                return modelMap;
//            }
//
//            TbAmountrecordBO tbAmountrecordBO = (TbAmountrecordBO) integralService.findOrderDetailByEid(tbAmountrecordVO.getEid());
//
//            // 对获取到的订单信息做处理
//            if (tbAmountrecordBO != null) {
//                int status = tbAmountrecordBO.getStatus();
//                String date = tbAmountrecordBO.getDeclarationDate();
//                int amountid = tbAmountrecordBO.getEid();
//                double price = 0.00;
//                if (status == 1) {
//                    if (tbAmountrecordBO.getDeclarationPrice() != null) {
//                        price = tbAmountrecordBO.getDeclarationPrice();
//                    }
//                } else {
//                    if (tbAmountrecordBO.getAmount() != null) {
//                        price = tbAmountrecordBO.getAmount();
//                    }
//                }
//
//                int integral = 0;
//                if (status == 1) {
//                    if (tbAmountrecordBO.getDeclarationNum() != null) {
//                        integral = tbAmountrecordBO.getDeclarationNum();
//                    }
//                } else {
//                    if (tbAmountrecordBO.getRealNum() != null) {
//                        integral = tbAmountrecordBO.getRealNum();
//                    }
//                }
//
//                float equity = 0.1f;
//                if (tbAmountrecordBO.getEquity() != null) {
//                    equity = tbAmountrecordBO.getEquity();
//                }
//
//                data.put("eid", amountid);
//                data.put("date", date);
//                data.put("integral", integral);
//                data.put("price", price);
//                data.put("equity", equity);
//                data.put("status", status);
//
//                map.put("code", "0");
//                map.put("data", data);
//                map.put("message", "获取数据成功");
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("code", 1);
//            map.put("message", "获取订单信息失败");
//            map.put("data", null);
//            return map;
//        }
//
//        return map;
//    }
//
//
//    /**
//     * 查询步值(spiner数据)
//     *
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "integral_getStepInfo")
//    @ResponseBody
//    public Map<String, Object> integral_getStepInfo(@RequestBody TokenVo tokenVo) throws Exception {
//        // 创建返回值对象
//        Map<String, Object> map = Tools.errMessage();
//        //校验token
//        tokenVo = integralService.findIdByToken(tokenVo);
//        if (tokenVo == null) {
//            return map;
//        }
//
//        AmountRecordBo amountRecordBo = integralService.queryAmountRecord(tokenVo);
//
//
//        List<UpgradePackage> list = null;
//        try {
//            list = integralService.integral_getStepInfo();
//
//
//            if (amountRecordBo != null) {
//                int balance = integralService.getBalanceById(tokenVo);
//                if (balance == 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        if (list.get(i).getStep_value() == 1000) {
//                            list.remove(i);
//                        }
//
//                    }
//                } else {
//                    int unit = integralService.getUnit();
//                    list = Tools.filling(balance, unit);
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("code", 1);
//            map.put("message", "查询步值失败");
//            map.put("data", null);
//            return map;
//        }
//
//        map.put("code", 0);
//        map.put("message", "查询步值成功");
//        map.put("data", list);
//
//        return map;
//    }
//
//    /**
//     * 删除积分记录（逻辑删除）
//     *
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "integral_deleteOrderByEid")
//    @ResponseBody
//    public Map<String, Object> deleteOrderByEid(@RequestBody DeleteOrderVO deleteOrderVO) throws Exception {
//
//        Map<String, Object> modelMap = Tools.errMessage();
//
//        UserInfoVO userInfoVO = new UserInfoVO();
//        userInfoVO.setToken(deleteOrderVO.getToken());
//        //检查token是否过期
//        if ("2".equals(appuserService.userCheckTokenIsLost(userInfoVO))) {
//            return modelMap;
//        }
//
//        // 通过eid查询订单信息
//        TbAmountrecordBO tbAmountrecordBO = integralService.findOrderDetailByEid(deleteOrderVO.getEid());
//
//        // 判断订单记录是否存在
//        if (tbAmountrecordBO == null) {
//            Map map = new HashMap<String, Object>();
//            map.put("code", 1);
//            map.put("data", null);
//            map.put("message", "该条记录不存在");
//            return map;
//        }
//
//        // 通过token查询用户
//
//        TokenVo tokenVo = new TokenVo();
//        tokenVo.setToken(deleteOrderVO.getToken());
//        TokenVo userId = integralService.findIdByToken(tokenVo);
//        // 设置用户id
//        tbAmountrecordBO.setUid(Integer.parseInt(userId.getUserid()));
//
//        Map<String, Object> resultMap = integralService.updateAmountStatus(tbAmountrecordBO);
//
//        return resultMap;
//    }
//
//
//    @RequestMapping(value = "backAmountrecord")
//    @ResponseBody
//    //退单清除记录
//    public Map<String, Object> backAmountrecord(@RequestBody UserBalanceInfo vo) {
//        Map<String, Object> modelMap = Tools.errMessageSystem();
//        try {
//            integralService.backAmountrecord(vo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return modelMap;
//    }
//    /**
//     * 获取待付款记录（分页查询）
//     * @param queryPageVo
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("integral_getIntegralOrders")
//    @ResponseBody
//    public Map<String, Object> getWaitPayRecordInfo(@RequestBody QueryPageVo queryPageVo) throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            Map<String, Object> modelMap = Tools.errMessage();
//            UserInfoVO userInfoVO = new UserInfoVO();
//            userInfoVO.setToken(queryPageVo.getToken());
//            //检查token是否过期
//            if ("2".equals(appuserService.userCheckTokenIsLost(userInfoVO))) {
//                return modelMap;
//            }
//
//            if(queryPageVo.getPageIndex() == 0) {
//                queryPageVo.setPageIndex(1);
//            }
//            if(queryPageVo.getPageSize() == 0) {
//                queryPageVo.setPageSize(20);
//            }
//
//            List<OrderListBO> list = integralService.getWaitPayRecordInfoList(queryPageVo);
//
//            if (list != null && list.size() > 0) {
//                map.put("code", 0);
//                map.put("message", "查询待付款订单成功");
//                map.put("data", list);
//            } else {
//                map.put("code", 1);
//                map.put("message", "暂无数据");
//                map.put("data", null);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("code", 1);
//            map.put("data", null);
//            map.put("message", "系统数据错误");
//            return map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 获取积分界面信息
//     *
//     * @param tokenVo
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("integral_getPaymentParams")
//    @ResponseBody
//    public Map<String, Object> integral_getPaymentParams(@RequestBody TokenVo tokenVo) throws Exception {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        // 检验token
//        // 校验结果
//        String verifyResult = null;
//        // 验证消息
//        String message = "";
//
//        TokenVo tokenVo1 = null;
//        if (tokenVo != null) {
//            // 通过token查询当前用户
//            tokenVo1 = integralService.findIdByToken(tokenVo);
//            if (tokenVo1 != null) {
//                verifyResult = "0";
//                message = "查询页面信息成功";
//                Map<String, Object> dataMap = integralService.getPaymentParams(tokenVo1);
//
//                map.put("code", verifyResult);
//                map.put("message", message);
//                map.put("data", dataMap);
//            } else {
//                verifyResult = "1";
//                message = "查询页面信息失败";
//
//                map.put("code", verifyResult);
//                map.put("message", message);
//                map.put("data", null);
//
//                return map;
//            }
//        } else {
//            verifyResult = "2";
//            message = "token为空或者过期";
//
//            map.put("code", verifyResult);
//            map.put("message", message);
//            map.put("data", null);
//
//            return map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 获取用户信息
//     *
//     * @return
//     */
//    @RequestMapping(value = "queryUserInfo")
//    @ResponseBody
//    public Map<String, Object> queryUserInfo(@RequestBody QueryMemberVo queryMemberVo) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfo = integralService.findUserInfo(queryMemberVo);
//            if (userInfo != null) {
//                UserInfoVO user = new UserInfoVO();
//                user.setUserName(userInfo.getMember_name());
//                user.setAddress(userInfo.getMember_address());
//                user.setArea(userInfo.getMember_area());
//                user.setBankcard(userInfo.getMember_bankcard());
//                user.setBankName(userInfo.getMember_bankname());
//                user.setEmail(userInfo.getMember_email());
//                user.setIdcard(userInfo.getMember_idcard());
//                user.setMobile(userInfo.getMember_mobile());
//                if (userInfo.getMember_sex() == null) {
//                    user.setSex("0");
//                } else {
//                    user.setSex(String.valueOf(userInfo.getMember_sex()));
//                }
//                user.setName(userInfo.getMember_truename());
//                user.setInviter(userInfo.getInviter_id());
//                user.setCardType(userInfo.getMember_cardtype());
//                queryMemberVo.setUserName(userInfo.getMember_name());
//                List<ChildMemberBo> list = integralService.getChildMember(queryMemberVo);
//                if (list != null && list.size() > 0) {
//                    map.put("code", "3");
//                    map.put("data", user);
//                    map.put("message", "账号已有下级成员，不允许修改个人信息");
//                    return map;
//                }
//                int userid = userInfo.getMember_id();
//                List<AmountRecordBo> amountRecordBo = integralService.queryAmountByMemberid(userid);
//                if (amountRecordBo != null && amountRecordBo.size() > 0) {
//                    map.put("code", "3");
//                    map.put("data", user);
//                    map.put("message", "账号已有订单记录，不允许修改个人信息");
//                    return map;
//                }
//                map.put("code", "0");
//                map.put("data", user);
//                map.put("message", "个人信息获取成功");
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("message", "系统内部错误 ");
//            map.put("code", "1");
//            map.put("data", null);
//        }
//        return map;
//    }
//}