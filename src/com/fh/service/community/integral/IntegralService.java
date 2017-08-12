package com.fh.service.community.integral;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.bo.*;
import com.fh.entity.vo.*;
import com.fh.util.Constants;
import com.fh.util.PageData;
import com.fh.util.Tools;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("integralService")
public class IntegralService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;




	/*
     * 获取当日净值
    */
	public TbEquityBO integralueryEquity ( )throws Exception{
		return(TbEquityBO)dao.findForObject("Integral.integralueryEquity", null);
	}
	/*
     * 获取7日净值
    */

	public List<TbEquityBO> integralQueryEquitys() throws Exception {
		return (List<TbEquityBO>) dao.findForList("Integral.integralQueryEquitys", null);
	}

//	/*
//     * 获取7日净值
//    */
//
//	public List<TbEquityBO> integral_queryBalanceInfo() throws Exception {
//		return (List<TbEquityBO>) dao.findForList("Integral.integralQueryEquitys", null);
//	}
	/**
	 *通过token查询用户
	 */
	public UserInfoBO queryMemberByToken(TokenVo tokenVo) throws Exception {
		return (UserInfoBO)dao.findForObject("Integral.queryMemberByToken",tokenVo);
	}
    /**
     * 查询会员等级列表
     */
    public List<VIPSBo> getVIPS() throws Exception {
        return (List<VIPSBo>)dao.findForList("Integral.getVIPS",null);
    }
	/**
	 * 获取个人账户当日市值及积分情况
	 */
	public Map<String,Object> integral_balanceInfo(UserInfoBO userInfoBO){
		Map<String,Object> data=new HashMap<String, Object>();
		return data;
	}
	/**
	 * 查询个人奖金数
	 */
	public TbuserbalanceBo integral_queryReward(UserInfoBO userInfoBO) throws Exception {
		return (TbuserbalanceBo)dao.findForObject("Integral.queryReward",userInfoBO);
	}
	/**
	 * Tuser表通过token查用户
	 */
	public UserInfoBO findToken(TokenVo tokenVo) throws Exception {
		return (UserInfoBO) dao.findForObject("Integral.findToken",tokenVo);
	}
    /**
     * 通过uid查询退单详情
     */
    public Map<String, Object> queryChargeBackDesc(TokenVo tokenVo) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        tokenVo= (TokenVo) dao.findForObject("Integral.queryUseridByToken", tokenVo);
        if(tokenVo==null){
            map.put("message", "token已过期");
            map.put("code", 2);
            map.put("data", null);
            return map;
        }
        AmountRecordBo amountRecordBo= (AmountRecordBo) dao.findForObject("Integral.queryAmountByUid",tokenVo);
        if(amountRecordBo!=null){
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            String time=dateFormat.format(date);
            if(Tools.daysBetween(amountRecordBo.getDeclaration_date(),time)<356){
                map.put("message", "您是原始会员1年之内不允许退单");
                map.put("code", 1);
                map.put("data", null);
                return map;
            }
        }
        if((ChargeBackBo)dao.findForObject("Integral.queryAmountById",tokenVo)!=null){
            map.put("message", "您的退单申请已申请，请勿重复操作");
            map.put("code", 3);
            map.put("data", null);
            return map;
        }

        Object obj = null;
        ChargeBackBo chargeBackBo = null;
        try {
            chargeBackBo = (ChargeBackBo) dao.findForObject("Integral.queryChargeBack", tokenVo);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "系统内部错误");
            map.put("code", 1);
            map.put("data", null);
            return map;
        }
        if (chargeBackBo != null) {
            chargeBackBo.setUserId(Integer.parseInt(tokenVo.getUserid()));
            if (chargeBackBo.getBalance() == 0) {
                map.put("message", "用户没有可退订单");
                map.put("code", 1);
                map.put("data", null);
                return map;
            }
            obj = dao.findForObject("Integral.queryRecordTime", tokenVo);

            if (obj != null) {
                chargeBackBo.setLastTime(obj.toString());
            } else {
                map.put("message", "用户没有可退订单");
                map.put("code", 1);
                map.put("data", null);
                return map;
            }
            Date d1 = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d2 = null;
            int days = 0;
            try {
                d2 = dateFormat.parse(chargeBackBo.getLastTime());
            } catch (ParseException e) {
                e.printStackTrace();

            }
            days = (int) ((d1.getTime() - d2.getTime()) / 86400000);
            System.out.println("间隔天数:" + days);
            if (days > 10) {
                chargeBackBo.setIsTax("需要扣除手续费和管理费");
            } else {
                chargeBackBo.setIsTax("不需要扣除手续费和管理费");
            }
            double d = 0;
            try {
                TbEquityBO tbEquity = (TbEquityBO) dao.findForObject("Integral.queryCurrentEquity", null);
                float equity = Float.parseFloat(tbEquity.getEquity().trim());
                chargeBackBo.setEquity(equity);
                DecimalFormat df = new DecimalFormat("0.00");
                d = Double.parseDouble(String.valueOf(chargeBackBo.getBalance() * equity));
                d = Double.parseDouble(df.format(d));
                chargeBackBo.setReturnAmount(d);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("数据类型转换异常");
            }
//            System.out.println(d);
            List<taxNumBo> list = (List<taxNumBo>) dao.findForList("Integral.queryTaxNum", null);
            System.out.println(list.size());
            double poundage = Constants.jifentuidanshouxufei;
            double management = Constants.jifentuidanguanlifei;
//            for (taxNumBo taxNumBo : list) {
//                if ("手续费".equals(taxNumBo.getCode_item_name())) {
//                    System.out.println(taxNumBo.getCode_item_id()+taxNumBo.getCode_item_name());
//                    poundage = Double.parseDouble(taxNumBo.getCode_item_id().trim());
//                }
//                if ("管理费".equals(taxNumBo.getCode_item_name())) {
//                    System.out.println(taxNumBo.getCode_item_id() + taxNumBo.getCode_item_name());
//                    management = Double.parseDouble(taxNumBo.getCode_item_id().trim());
//                }
//            }


            System.out.println(poundage+"------"+management);
            if(days>10) {
                chargeBackBo.setPpercent((poundage*100)+"%");
                chargeBackBo.setMpercent((management*100)+"%");
                DecimalFormat df = new DecimalFormat("0.00");
                chargeBackBo.setPoundage(Double.parseDouble(df.format(chargeBackBo.getReturnAmount() * poundage)));
                chargeBackBo.setManagement(Double.parseDouble(df.format(chargeBackBo.getReturnAmount() * management)));
                chargeBackBo.setFinalAmount(Double.parseDouble(df.format(chargeBackBo.getReturnAmount() - chargeBackBo.getPoundage() - chargeBackBo.getManagement())));
            }else{
                chargeBackBo.setPpercent("0%");
                chargeBackBo.setMpercent("0%");
                DecimalFormat df = new DecimalFormat("0.00");
                chargeBackBo.setPoundage(0.00);
                chargeBackBo.setManagement(0.00);
                chargeBackBo.setFinalAmount(Double.parseDouble(df.format(chargeBackBo.getReturnAmount())));
            }
            map.put("message", "获取退单详情成功");
            map.put("code", 0);
            map.put("data", chargeBackBo);

        } else {
            map.put("message", "系统数据错误");
            map.put("code", 1);
            map.put("data", null);
            return map;
        }
        return map;
    }

    /**
     * 提交退单记录
     */
    public Map<String,Object> accountChargeBack(ChargeBackBo chargeBackBo) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            dao.save("Integral.addChargeBack", chargeBackBo);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "系统内部错误");
            map.put("code", 1);
            map.put("data", null);
            return map;
        }
        map.put("message", "提交订单成功");
        map.put("code", 0);
        map.put("data", null);
        return map;
    }
    /**
     * 撤销退单
     */
    public void removeChargeBack(QueryAmounVo queryAmounVo) throws Exception {

        dao.update("Integral.removeChargeBack",queryAmounVo);


    }
    /**
     * 通过token查userid
     */
    public TokenVo findIdByToken(TokenVo tokenVo) throws Exception {
        return (TokenVo)dao.findForObject("Integral.queryUseridByToken",tokenVo);
    }
    /**
     *
     */
    public ChargeBackBo getChargeBack(QueryAmounVo queryAmounVo) throws Exception {
        return (ChargeBackBo)dao.findForObject("Integral.queryAmount",queryAmounVo);
    }
    /**
     *新的获取下级成员列表
     * 校验token是否失效
     */
    public UserInfoBO checkTokenIsGone(QueryMemberVo queryMemberVo) throws Exception {
        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Integral.queryMember",queryMemberVo);
        return userInfoBO;
    }

    /**
     * 获取总业绩
     * @param queryMemberVo
     * @return
     * @throws Exception
     */
    public UserInfoBO getAchievement(QueryMemberVo queryMemberVo) throws Exception {
        if(queryMemberVo.getPageIndex()!=0&&queryMemberVo.getPageSize()!=0){
            queryMemberVo.setPageIndex((queryMemberVo.getPageIndex()-1)*queryMemberVo.getPageSize());
        }
        UserInfoBO getAchievement =(UserInfoBO) dao.findForObject("Integral.getAchievement", queryMemberVo);
        return getAchievement;
    }

    /**
     * 新的获取下级成员列表
     */
    public List<ChildMemberBo> getChildMember(QueryMemberVo queryMemberVo) throws Exception {
        if(queryMemberVo.getPageIndex()!=0&&queryMemberVo.getPageSize()!=0){
            queryMemberVo.setPageIndex((queryMemberVo.getPageIndex()-1)*queryMemberVo.getPageSize());
        }
        List<ChildMemberBo> list = (List<ChildMemberBo>) dao.findForList("Integral.getChildMember", queryMemberVo);
        List<VIPSBo> vipList = (List<VIPSBo>) dao.findForList("Integral.queryAllVip", queryMemberVo);
        for(int i=0;i<list.size();i++){
            for (VIPSBo vipBean : vipList) {
                if (list.get(i).getLevel()==vipBean.getCode_item_id()){
                    list.get(i).setVip(vipBean.getCode_item_name());
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 通过memberid查询个人账户信息
     */
    public TbuserbalanceBo getBalanceByUid(QueryMemberVo queryMemberVo) throws Exception {
        TbuserbalanceBo tbuserbalance =(TbuserbalanceBo) dao.findForObject("Integral.getBalanceByUid", queryMemberVo);
        return tbuserbalance;
    }

    /**
     * 通过name查询value
     */
    public VariableVo queryVar(QueryMemberVo queryMemberVo) throws Exception {
        VariableVo var =(VariableVo)dao.findForObject("Integral.queryVar", queryMemberVo);
        return var;
    }


    /**
     * 通过memberid查询member信息
     */
    public UserInfoBO queryMemberById(QueryMemberVo queryMemberVo) throws Exception {
        UserInfoBO member =(UserInfoBO) dao.findForObject("Integral.queryMemberById", queryMemberVo);
        return member;
    }

    /**
     * 新的提交订单
     * 校验token是否失效
     */
    public UserInfoBO checkToken(TbAmountrecordVo amountrecordVo) throws Exception {
        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Integral.queryAllMember",amountrecordVo);
        return userInfoBO;
    }
    /**
     * 新的提交订单
     * 根据token查询tb_userbalance中的数据
     * primitive
     */
    public TbuserbalanceBo queryUserBalance(TbAmountrecordVo amountrecordVo) throws Exception {
        TbuserbalanceBo userbalance= (TbuserbalanceBo) dao.findForObject("Integral.queryUserBalance",amountrecordVo);
        return userbalance;
    }
    /**
     * 新的提交订单
     * 修改tb_amountrecord中的数据
     * declarationNum
     */
    public void updateDeclarationNum(TbAmountrecordVo amountrecordVo) throws Exception {
        dao.update("Integral.updateDeclarationNum",amountrecordVo);
    }
    /**
     * 新的提交订单
     * 修改tb_userbalance中的数据
     * primitive
     */
    public void updatePrimitive(TbAmountrecordVo amountrecordVo) throws Exception {
         dao.update("Integral.updatePrimitive",amountrecordVo);
    }
    /**
     * 新的提交订单
     * 根据uid查询order中的数据
     */
    public  TbAmountrecordVo queryOrderById(int  uid) throws Exception {
        TbAmountrecordVo order = (TbAmountrecordVo) dao.findForObject("Integral.queryOrder", uid);
        return order;

    }

    /**
     * 新提交积分充值订单记录
     * 插入数据
     */
    public void addOrder(TbAmountrecordVo amountrecordVo) throws Exception {
        dao.save("Integral.addOrder", amountrecordVo);
    }
    /*新提交积分充值订单记录同步数据*/
    public void modifyAllScore(TbAmountrecordVo amountrecordVo)throws Exception{
        dao.update("Integral.modifyAllScore",amountrecordVo);
    }
    /*新提交积分充值订单记录同步数据*/
    public void modifyRemainbanlance(TbAmountrecordVo amountrecordVo)throws Exception{
        dao.update("Integral.modifyRemainbanlance",amountrecordVo);
    }

    //财务确认退单删除各项业绩
    public void backOrder (String userid) throws Exception {
        try {
            //将原有记录存入退单记录表
            this.saveBackecordR(userid);
            //扣除本人全部业绩
            this.clearAchievement(userid);
            //扣除上级业绩
            this.clearTopAchievement(userid);
            // 奖金获取   再不够 从待结算获取 待计算为负数
            this.backTbReward(userid);
            // 生成奖金记录 与 扣单记录相抵消
            this.clearLevel(userid);
            //等级清0
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void  saveBackecordR(String uid){

    }
    public void  clearAchievement(String uid){

    }
    public void  clearTopAchievement(String uid){

    }
    public void  backTbReward(String uid){

    }
    public void  clearLevel(String uid){

    }


    /**
     * 通过e_id查找订单详情
     * @param eId
     * @return
     * @throws Exception
     */
    public TbAmountrecordBO findOrderDetailByEid(int eId) throws Exception {
        return (TbAmountrecordBO) dao.findForObject("Integral.queryOrderDetailByEid", eId);
    }

    /**
     * 查询步值(spiner数据)
     * @return
     * @throws Exception
     */
    public List<UpgradePackage> integral_getStepInfo() throws Exception {

        List<UpgradePackage> list = (List<UpgradePackage>) dao.findForList("Integral.queryStepInfo", null);

        return list;
    }

    /**
     * 逻辑删除订单信息（删除积分记录）
     * @param tbAmountrecordBO
     * @return
     */
    public Map<String, Object> updateAmountStatus(TbAmountrecordBO tbAmountrecordBO) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 删除订单记录
            dao.update("Integral.updateAmountStatus", tbAmountrecordBO);

            Integer declarationNum = tbAmountrecordBO.getDeclarationNum();
            Integer unit = tbAmountrecordBO.getUnit();
            if(declarationNum == null || declarationNum == 0) {
                declarationNum = 0;
            }
            if(unit == null || unit ==0) {
                unit = 1;
            }
            if(declarationNum==33000){
                declarationNum=30000;
            }
            int box = declarationNum/unit;

            DeleteOrderVO deleteOrderVO = new DeleteOrderVO();
            deleteOrderVO.setBox(box);
            deleteOrderVO.setEid(tbAmountrecordBO.getEid());
            deleteOrderVO.setUid(tbAmountrecordBO.getUid());

            // 扣除用户积分
            this.updateUserBalance(deleteOrderVO);

            //还原可购买数量
            this.updateVariable(deleteOrderVO);

        }catch (Exception e){
            e.printStackTrace();
            map.put("message", "系统内部错误");
            map.put("code", 1);
            map.put("data", null);
            return map;
        }

        map.put("message", "订单记录删除成功");
        map.put("code", 0);
        map.put("data", null);


        return map;
    }

    /**
     * 还原变量值(tb_variable:value)
     * @param deleteOrderVO
     * @throws Exception
     */

    private void updateVariable(DeleteOrderVO deleteOrderVO) throws Exception {

        dao.update("Integral.updateVariable", deleteOrderVO);
    }


    /**
     * 用户积分剩余购买包数更新
     * @param deleteOrderVO
     */
    private void updateUserBalance(DeleteOrderVO  deleteOrderVO ) throws Exception {
        dao.update("Integral.updateUserBalance", deleteOrderVO);
    }

    /**
     * 查询用户是否下过单
     */
    public  AmountRecordBo queryAmountRecord(TokenVo tokenVo) throws Exception {
        return (AmountRecordBo)dao.findForObject("Integral.queryAmountRecordById",tokenVo);
    }

    /**
     * 返回换算报数单位
     */
    public int getUnit() throws Exception {
        return  (Integer)dao.findForObject("Integral.getUnit",null);
    }
    /**
     *通过userid获取balance
     */
    public int getBalanceById(TokenVo tokenVo) throws Exception {
        return (Integer)dao.findForObject("Integral.getIntbalance",tokenVo);
    }


    public void backAmountrecord(UserBalanceInfo vo)throws Exception{
        //复制记录
        dao.save("Integral.amountrecord",vo);
        //本次退单记录
        dao.save("Integral.recordeid",vo);
         //奖金相减(简单)
         List<TbReward>  list = (List)dao.findForList("Integral.findReward", vo);
         if(list !=null && list.size()>0){
             for(int i=0; i<list.size();i++){
                 TbReward bo =list.get(i);
                 dao.update("Integral.backReward", bo);
             }
         }
        //tb_rewared  记录相抵
        dao.save("Integral.copyRewared",vo);
         //上级业绩递减
          CommonMemberVo comVO =new CommonMemberVo();
          comVO.setMember_id(vo.getMember_id());
          UserInfoBO bo =(UserInfoBO)dao.findForObject("Common.queryMemberInfo" ,comVO);
          int i = bo.getTeam_sign().length()/4;
          String str="";
          if(i >1){
              for(int j =1 ;j<i ;j++){
                  str = str + "'" + bo.getTeam_sign().substring(0, j * 4) + "'" + ",";
              }
              str =str.substring(0,str.length()-1);
          }
          vo.setMember_ids(str);
          dao.update("Integral.clearTopAchievement",vo);

         //重置
         dao.update("Integral.clearUserbalance",vo);
         dao.update("Integral.clearVariable",vo);
   }

    /**
     * 分页查询待付款记录
     * @param queryPageVo
     * @return
     */
    public List<OrderListBO> getWaitPayRecordInfoList(QueryPageVo queryPageVo) throws Exception {

        List<QuerywaitRecordBo> list = (List<QuerywaitRecordBo>) dao.findForList("Integral.getWaitPayRecordInfoByPage", queryPageVo);

        List<OrderListBO> orderList = new ArrayList<OrderListBO>();
        OrderListBO order = null;

        if(list != null && list.size() > 0) {
            for (QuerywaitRecordBo bo: list){
                int eid = bo.getEid();
                int status = bo.getStatus();
                int type = bo.getType();
                if(status ==1) {
                    double declarationPrice = bo.getDeclaration_price();
                    int declarationNum = bo.getDeclaration_num();
                    String date = bo.getDeclarartion_date();
                    order = new OrderListBO(declarationPrice, declarationNum, status, type,eid,date);
                }else if(status ==2) {
                    double declarationPrice = bo.getAmount();
                    int declarationNum = bo.getReal_num();
                    String date = bo.getRecordTime();
                    order = new OrderListBO(declarationPrice, declarationNum, status, type,eid,date);
                }else if(status ==4) {
                    double declarationPrice = bo.getDeclaration_price();
                    int declarationNum = bo.getDeclaration_num();
                    String date = bo.getDeclarartion_date();
                    order = new OrderListBO(declarationPrice, declarationNum, status, type,eid,date);
                }else if(status ==5) {
                    double declarationPrice = bo.getAmount();
                    int declarationNum = bo.getReal_num();
                    String date = bo.getRecordTime();
                    order = new OrderListBO(declarationPrice, declarationNum, status, type,eid,date);
                }

                orderList.add(order);
            }
        }

        return orderList;
    }


    /**
     * 获取当日最新净值和当前用户的可购买数量
     * @param tokenVo
     * @return
     * @throws Exception
     */
    public Map<String, Object> getPaymentParams(TokenVo tokenVo) throws Exception {


        // 查询当日最新的净值
        TbEquityBO tbEquityBO = (TbEquityBO) dao.findForObject("Integral.queryNewCurrentEquity", null);
        //获取可购买的数量
        TbuserbalanceBo tbuserbalanceBo = (TbuserbalanceBo) dao.findForObject("Integral.queryCountByUserId", tokenVo.getUserid());
        // 查询用户信息
        UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Integral.findToken",tokenVo);
        // 查询Var
        TbVariableBO mixVar = null;
        {
            String name = "mixtotal";

            mixVar = (TbVariableBO) dao.findForObject("Integral.queryVariable", name);
        }

        TbVariableBO boxVar = null;
        {
            String name = "boxtotal";

            boxVar = (TbVariableBO) dao.findForObject("Integral.queryVariable", name);
        }

        TbVariableBO boxstepVar = null;
        {
            String name = "boxstep";

            boxstepVar = (TbVariableBO) dao.findForObject("Integral.queryVariable", name);
        }

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("equity", tbEquityBO.getEquity());
        dataMap.put("remainblance", tbuserbalanceBo.getRemainbalance());
        dataMap.put("mixtotal", mixVar.getValue());
        dataMap.put("boxtotal", boxVar.getValue());
        dataMap.put("boxstep", boxstepVar.getValue());


        return dataMap;
    }


    /**
     * 获取用户信息
     * @return
     */
    public  UserInfoBO findUserInfo(QueryMemberVo queryMemberVo) throws Exception{
        UserInfoBO user = (UserInfoBO)dao.findForObject("Integral.findUserInfo", queryMemberVo);
        return user;
    }
    /**
     * 验证用户是否有订单记录
     * @return
     */
    public List<AmountRecordBo> queryAmountByMemberid(int userid) throws Exception{
        List<AmountRecordBo> amountRecordBo =  (List<AmountRecordBo>) dao.findForList("Integral.queryAmountByMemberid", userid);
        return amountRecordBo;
    }

    /**
     * 通过userid查询VIP
     */
    public String queryVIP(QueryMemberVo queryMemberVo) throws Exception {
        String vip ="";
        TbuserbalanceBo bo=(TbuserbalanceBo)dao.findForObject("Integral.queryVIPByToken",queryMemberVo);
        if(bo.getPrimitive() == -1 &&  bo.getLevel()==1){
            vip ="-1";
        }else{
            vip =String.valueOf(bo.getLevel());
        }
        return vip;
    }
}

