//package com.fh.controller.finance;
//
//import com.fh.controller.base.BaseController;
//import com.fh.entity.bo.*;
//import com.fh.entity.vo.AmountListVO;
//import com.fh.entity.vo.ConfirAmountVO;
//import com.fh.entity.vo.QueryCashListVo;
//import com.fh.entity.vo.QueryChargeBackListVo;
//import com.fh.service.community.integral.IntegralService;
//import com.fh.service.finance.FinanceService;
//import com.fh.util.MD5;
//import com.fh.util.Tools;
//import com.google.gson.Gson;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping(value = "/finance")
//public class FinanceController extends BaseController {
//
//    @Resource(name = "financeService")
//    private FinanceService financeService;
//
//    @Resource(name = "integralService")
//    private IntegralService integralService;
//
//    /**
//     * 财务登录
//     */
//    @RequestMapping(value = "financeLogin")
//    @ResponseBody
////	public Map<String, Object> userLogin()throws Exception {
//    public Map<String, Object> financeLogin(@RequestBody AdminUserBO adminuser) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            adminuser.setPassWord(MD5.md5(adminuser.getPassWord()));
//            AdminUserBO bo = financeService.login(adminuser);
//            if (bo == null) {
//                modelMap.put("message", "用户名或密码错误");
//                modelMap.put("code", "1");
//                modelMap.put("data", null);
//            } else {
//                if (bo.getRid() == 1) {
//                    Gson gons = new Gson();
//                    modelMap.put("message", "登录成功");
//                    modelMap.put("code", "0");
//                    modelMap.put("data", gons.toJson(bo));
//                } else {
//                    modelMap.put("message", "您无登录此系统权限");
//                    modelMap.put("code", "1");
//                    modelMap.put("data", null);
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//        }
//        return modelMap;
//    }
//
//    /**
//     * 获取列表
//     */
//    @RequestMapping(value = "finance_queryAllAmount")
//    @ResponseBody
//    public Map<String, Object> getFinanceRecord(@RequestBody AmountListVO amountListVO) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        List<AmountListBO> list = new ArrayList<AmountListBO>();
//        try {
//            amountListVO.setStart((amountListVO.getPageIndex() - 1) * amountListVO.getPageSize());
//            list = financeService.getFinanceRecord(amountListVO);
//            CountBO countBO = new CountBO();
//            countBO=financeService.countComfirm();
//
//            if (list != null && list.size() > 0) {
//                List<AmountListBO> listvo = new ArrayList<AmountListBO>();
//                for (int i = 0; i < list.size(); i++) {
//                    AmountListBO j = list.get(i);
//                    j.setIntegral(j.getDeclaration_num());
//                    if (j.getStatus() == 1) {
//                        j.setAmount(Double.valueOf(j.getDeclaration_price()));
//                        j.setDate(j.getDeclaration_date());
//                    } else if(j.getStatus() == 2) {
//                        j.setDate(j.getRecordtime());
//                    }
//                    listvo.add(j);
//                }
//
//                modelMap.put("message", "查询成功");
//                modelMap.put("code", "0");
//                modelMap.put("data", listvo);
//                modelMap.put("uncomfirm",countBO.getUncomfirm());
//                modelMap.put("comfirm",countBO.getComfirm());
//            } else {
//                Gson gons = new Gson();
//                modelMap.put("message", "该条件下暂无订单数据");
//                modelMap.put("code", "1");
//                modelMap.put("data", null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "0");
//            modelMap.put("data", null);
//        }
//        return modelMap;
//    }
//
//    /**
//     * 确认到账
//     */
//    @RequestMapping(value = "finance_orderToAccount")
//    @ResponseBody
//    public Map<String, Object> setFinanceRecord(@RequestBody ConfirAmountVO confirAmountVO) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            UserInfoBO userInfoBO = financeService.findUserInfoByUserName(confirAmountVO);
//            UserInfoBO infoBO = new UserInfoBO();
//
//            //根据 ConfirAmountVO userName查询信息
//
//            //判断该用户上级是否激活
//            if(userInfoBO.getTeam_sign().trim().length()>8){
//                //判断是否存在上级
//                infoBO=financeService.queryUserByUserName(userInfoBO);
//                if(infoBO!=null){
//                    //根据uid 和status判断上级是否激活
//                    List<TbAmountrecordBO> list = financeService.checkActivation(infoBO);
//                    if(list==null||list.size()<=0){
//                        modelMap.put("message", "该用户上级未激活，此订单不能确认");
//                        modelMap.put("code", "1");
//                        modelMap.put("data", null);
//                        return modelMap;
//                    }
//                }
//            }
//            TbAmountrecordBO tbAmountrecordBO = financeService.queryTbAmountrecordByEid(confirAmountVO);
//            if(tbAmountrecordBO!=null){
//                float equity = tbAmountrecordBO.getEquity();
//                float amount = Float.valueOf(confirAmountVO.getAmount().trim());
//                if(userInfoBO!=null){
//                    tbAmountrecordBO.setUid(userInfoBO.getMember_id());
//                    tbAmountrecordBO.setStatus(2);
//                    tbAmountrecordBO.setAmount(amount);
//                    tbAmountrecordBO.setRealNum((int) (amount / equity
//                    ));
//                    tbAmountrecordBO.setDeclarationPrice((double)amount);
//
//                    tbAmountrecordBO.setDeclarationNum((int) (amount / equity
//                    ));
//                    //生成奖金
//                    tbAmountrecordBO.setOperatorid(confirAmountVO.getOperatorid());
//                    tbAmountrecordBO.setBankcard(confirAmountVO.getBankCard());
//                    tbAmountrecordBO.setBanktransaction(confirAmountVO.getBankTransaction());
//                    tbAmountrecordBO.setDate(confirAmountVO.getForwardTime());
//                    tbAmountrecordBO.setBankName(confirAmountVO.getBankName());
//
//                    String recordTime = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
//                    tbAmountrecordBO.setRecordTime(recordTime);
//                    tbAmountrecordBO.setEquity(equity);
//                    float margin = (amount / equity) - ((int) (amount / equity));
////                    tbAmountrecordBO.setMargin(margin);
//                    tbAmountrecordBO.setSource("wap");
//
//                    //添加数据
//                    Map<String,Object> serviceMap = new HashMap<String, Object>();
//                    serviceMap=financeService.addAmount(tbAmountrecordBO);
//
//
//                    Map<String,Object> setRewardMap = new HashMap<String, Object>();
//                    CountRewardBO countRewardBO = new CountRewardBO();
//                    countRewardBO.setRealnum((int) (amount/equity));
//                    countRewardBO.setEid(String.valueOf(confirAmountVO.getEid()));
//                    countRewardBO.setCreateDate(recordTime);
//                    countRewardBO.setUserid(String.valueOf(userInfoBO.getMember_id()));
//                    setRewardMap = financeService.shopReward(countRewardBO);
//
//
//                    modelMap.put("serviceMap",serviceMap);
//                    modelMap.put("setRewardMap",setRewardMap);
//                    modelMap.put("message", "订单提交成功");
//                    modelMap.put("code", "0");
//                    modelMap.put("data", null);
//                }else{
//                    modelMap.put("message", "用户不存在");
//                    modelMap.put("code", "1");
//                    modelMap.put("data", null);
//                    return modelMap;
//                }
//            }else{
//                modelMap.put("message", "账单不存在(eid)不存在");
//                modelMap.put("code", "1");
//                modelMap.put("data", null);
//                return modelMap;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "0");
//            modelMap.put("data", null);
//        }
//        return modelMap;
//    }
//
//
//    /**
//     *查詢縂獎金
//     */
//    @RequestMapping(value = "findReward")
//    @ResponseBody
////	public Map<String, Object> userLogin()throws Exception {
//    public Map<String, Object> findReward() throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            TbuserbalanceBo tbuserbalanceBo =financeService.findReward();
//            if (tbuserbalanceBo == null) {
//                modelMap.put("message", "查询失败");
//                modelMap.put("code", "1");
//                modelMap.put("data", null);
//            } else {
//
//
//                    modelMap.put("message", "查询成功");
//                    modelMap.put("code", "0");
//                    modelMap.put("data", tbuserbalanceBo);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//        }
//        return modelMap;
//    }
//
//
//    /**
//     *确认结算
//     */
//    @RequestMapping(value = "setReward")
//    @ResponseBody
////	public Map<String, Object> userLogin()throws Exception {
//    public Map<String, Object> setReward() throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            financeService.setReward();
//                modelMap.put("message", "结算成功");
//                modelMap.put("code", "0");
//                modelMap.put("data", null);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//        }
//        return modelMap;
//    }
//
//
//    /**
//     * 财务查询转账记录
//     */
//    @RequestMapping(value = "findRecord")
//    @ResponseBody
//
//    public Map<String, Object> findRecord() throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//
//               List<RansferRecordBO> list = financeService.findRecord();
//                modelMap.put("message", "查询成功");
//                modelMap.put("code", "0");
//                modelMap.put("data", list);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//        }
//        return modelMap;
//    }
//
//    /**
//     * 财务确认转账记录
//     */
//    @RequestMapping(value = "confirmRecord")
//    @ResponseBody
//    public Map<String, Object> confirmRecord(@RequestBody RansferRecordBO ransferRecordBO) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            financeService.confirmRecord(ransferRecordBO);
//            modelMap.put("message", "确认成功");
//            modelMap.put("code", "0");
//            modelMap.put("data", null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//        }
//        return modelMap;
//    }
//
///**
// * 带条件的拉取退单列表
// */
//@RequestMapping(value = "queryChargeBackList")
//@ResponseBody
//public Map<String, Object> queryChargeBackList(@RequestBody QueryChargeBackListVo queryChargeBackListVo) throws Exception {
//    Map<String, Object> modelMap = new HashMap<String, Object>();
//    try {
//       List<QueryChargeBackListBo> list=financeService.queryChargeBackList(queryChargeBackListVo);
//        if(list!=null&&list.size()>0){
//            modelMap.put("message", "获取列表成功");
//            modelMap.put("code", "0");
//            modelMap.put("data", list);
//        }else{
//            modelMap.put("message", "暂无数据");
//            modelMap.put("code", "0");
//            modelMap.put("data", null);
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        modelMap.put("message", "系统内部错误");
//        modelMap.put("code", "1");
//        modelMap.put("data", null);
//        return modelMap;
//    }
//    return modelMap;
//}
//
//    /**
//     * 财务确认退单
//     */
//    @RequestMapping(value = "accountChargeBack")
//    @ResponseBody
//    public Map<String, Object> accountChargeBack(@RequestBody QueryChargeBackListBo queryChargeBackListBo) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            UserBalanceInfo vo =new UserBalanceInfo();
//            vo.setMember_id(String.valueOf(queryChargeBackListBo.getMember_id()));
//            integralService.backAmountrecord(vo);
//            financeService.accountChargeBack(queryChargeBackListBo);
//
//                modelMap.put("message", "退单已确认");
//                modelMap.put("code", "0");
//                modelMap.put("data", null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        return modelMap;
//    }
//    /**
//     * 带条件的拉取提现列表
//     */
//    @RequestMapping(value = "getCashList")
//    @ResponseBody
//    public Map<String, Object> getCashList(@RequestBody  QueryCashListVo queryCashListVo) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            List<QueryCashListBo> list=financeService.queryCashList(queryCashListVo);
//            if(list!=null&&list.size()>0){
//                modelMap.put("message", "获取列表成功");
//                modelMap.put("code", "0");
//                modelMap.put("data", list);
//                System.out.println(modelMap.get("message"));
//            }else{
//                modelMap.put("message", "暂无数据");
//                modelMap.put("code", "0");
//                modelMap.put("data", null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        return modelMap;
//    }
//    /**
//     * 财务确认提现
//     */
//    @RequestMapping(value = "accountCash")
//    @ResponseBody
//    public Map<String, Object> accountCash(@RequestBody QueryCashListBo queryCashListBo) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            long time=System.currentTimeMillis()/1000;
//            queryCashListBo.setPdc_payment_time(time);
//            financeService.accountCashByPid(queryCashListBo);
//            modelMap.put("message", "提现已确认");
//            modelMap.put("code", "0");
//            modelMap.put("data", null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        return modelMap;
//    }
//    /**
//     * 删除未确认订单
//     */
//    @RequestMapping(value = "deleteAmountrecord")
//    @ResponseBody
//    public Map<String, Object> deleteAmountrecord() throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//
//            financeService.deleteAmountrecord();
//            modelMap.put("message", "已经删除"+36+"小时未确认订单");
//            modelMap.put("code", "0");
//            modelMap.put("data", null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelMap.put("message", "系统内部错误");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        return modelMap;
//    }
//}
