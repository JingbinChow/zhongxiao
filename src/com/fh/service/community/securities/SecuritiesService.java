package com.fh.service.community.securities;

import com.fh.dao.DaoSupport;
import com.fh.entity.bo.MemberUserVO;
import com.fh.entity.bo.QueryHoldingInfoBo;
import com.fh.entity.bo.TokenBO;
import com.fh.entity.bo.UserInfoBO;
import com.fh.entity.system.User;
import com.fh.entity.vo.*;
import com.fh.entity.zxzq.*;
import com.fh.util.MD5;
import com.fh.util.RandomCode;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;


@Service("secutitiesService")
public class SecuritiesService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    // 购买证券
    public void saveRecord(ZxzqCommonBO zxzqCommonBO) throws Exception {
        /********************获取投资额信息*****************************/
        //获取当日兑换率
        ZxzqDrawCashBO zxzqDrawCashBO = this.queryRate(zxzqCommonBO);
        // 通过id 查询购买证券的投资额（取证券数）
        ZxzqGoodsBo  zxzqGoodsBo=  this.queryZxzqGoods(zxzqCommonBO);


        //查询原先金额
        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);

        //一共证券
        zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities().add(zxzqMemberLevel.getMember_realprice()));
        //确认杠杆
        ZxzqMemberLevel zxzqMemberLevelNew =this.setLevered(zxzqCommonBO);

        //重新调整zxzq_memaber_lever
        this.setZxzqMemberLevel(zxzqMemberLevelNew);





        /*********************20170331*******************************/
//        //查询原先等级
//        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);
//
//        ZxzqRecordBO zxzqRecordBO = this.queryByRecordBONew(zxzqCommonBO);
//        if(zxzqRecordBO ==null){
//            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel =this.setLevered(zxzqCommonBO);
//            //重新调整zxzq_memaber_lever
//            this.setZxzqMemberLevel(zxzqMemberLevel);
//        }else{
//            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel =this.setLevered(zxzqCommonBO);
//
//        }


        //开始下单
        /*******************下单************************************************************/
         ZxzqRecordBO bo = new ZxzqRecordBO();
        //生成当前记录订单号
        String securitiesid = getSecuritiesid();


        //生成证券报单记录
        bo.setMemberid(zxzqCommonBO.getMemberid());//用户id
        bo.setSecuritiesid(securitiesid);//订单号
        bo.setSecurities(zxzqGoodsBo.getSecurities()); //购买证券
        bo.setLevered(zxzqMemberLevelNew.getLevered()); //系数
        // 根据当日兑换比例和购买证券数计算购买所需金额
        bo.setPrice(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities()));// 购买证券所需金额
        bo.setRealprice(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities())); //  真实花费金额

        bo.setSecuritiesnum(zxzqGoodsBo.getSecurities().multiply(zxzqMemberLevelNew.getLevered()));// 真实获得证券数
        /**********************订单表*****************************************/
        // 生成报单记录表
        dao.save("Securities.saveRecord", bo);


        ZxzqHoldingBO zxzqHoldingBO =(ZxzqHoldingBO)dao.findForObject("Securities.queryHolding", zxzqCommonBO);
        if(zxzqHoldingBO ==null){
            dao.save("Securities.saveHolding", bo);
        }else{
            dao.save("Securities.updateHolding", bo);
        }
        /************************用户钱包更新****************************************/
        ZxzqWalletBo zxzqWalletBo = new ZxzqWalletBo();
        // 查询该用户钱包现有金额
        zxzqWalletBo = this.queryZxzqWallet(zxzqCommonBO);
        BigDecimal price1 = zxzqWalletBo.getMoney(); // 该用户钱包现有金额

        // 通过计算获得该用户购买证券后剩余金额
        BigDecimal money = price1.subtract(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities())); //该用户购买证券后剩余金额
        zxzqWalletBo.setMoney(money);
        // 更新该用户钱包金额
        dao.findForObject("Securities.updateWalletMoney", zxzqWalletBo);

        /************************生成钱包消费记录*******************************************/
        ZxzqWalletBo walletBo = new ZxzqWalletBo();
        walletBo.setMemberid(zxzqWalletBo.getMemberid());  // 用户id
        walletBo.setPayrmoney(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities())); // 购买证券花费金额
        walletBo.setPayid(securitiesid);   // 订单号

        // 生成钱包消费记录
        dao.save("Securities.saveWalletReward", walletBo);

        /****************************更新可购买证券数*******************************************/
        ZxzqVariableBO zxzqVariableBO = new ZxzqVariableBO();
        zxzqVariableBO.setType(1);
        // 根据type获取当前可购买的证券数
        zxzqVariableBO = ckeckVariable(zxzqVariableBO);
        BigDecimal amount = zxzqVariableBO.getAmount();   // 当前可购买证券总数

        // 更新可购买证券数
        amount = amount.subtract(zxzqGoodsBo.getSecurities()); // 购买证券后剩余可购买证券总数
        zxzqVariableBO.setAmount(amount);
        // 更新可购买证券总数
        dao.findForObject("Securities.updateVariable", zxzqVariableBO);
    }
    // 新购买证券
    public void newSaveRecord(ZxzqCommonBO zxzqCommonBO) throws Exception {
        /********************获取投资额信息*****************************/
        //获取当日兑换率
//        ZxzqDrawCashBO zxzqDrawCashBO = this.queryRate(zxzqCommonBO);
        // 通过id 查询购买证券的投资额（取证券数）
        ZxzqGoodsBo  zxzqGoodsBo=  this.queryZxzqGoods(zxzqCommonBO);


//        //查询原先金额
//        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);
//
//        //一共证券
//        zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities().add(zxzqMemberLevel.getMember_realprice()));
//        //确认杠杆
//        ZxzqMemberLevel zxzqMemberLevelNew =this.setLevered(zxzqCommonBO);
//
//        //重新调整zxzq_memaber_lever
//        this.setZxzqMemberLevel(zxzqMemberLevelNew);

        /*********************20170331*******************************/
        //查询原先等级
        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);

        ZxzqRecordBO zxzqRecordBO = this.queryByRecordBONew(zxzqCommonBO);
        if(zxzqRecordBO ==null){
            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
            //确认杠杆
            zxzqMemberLevel =this.setLevered(zxzqCommonBO);
            //重新调整zxzq_memaber_lever
            this.setZxzqMemberLevel(zxzqMemberLevel);
        }else{
            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
            //确认杠杆
            zxzqMemberLevel =this.setLevered(zxzqCommonBO);

        }
        //开始下单
        /*******************下单************************************************************/
        ZxzqRecordBO bo = new ZxzqRecordBO();
        //生成当前记录订单号
        String securitiesid = getSecuritiesid();


        //生成证券报单记录
        bo.setMemberid(zxzqCommonBO.getMemberid());//用户id
        bo.setSecuritiesid(securitiesid);//订单号
        bo.setSecurities(zxzqGoodsBo.getSecurities()); //购买证券
        bo.setLevered(zxzqMemberLevel.getLevered()); //系数
        // 根据当日兑换比例和购买证券数计算购买所需金额
        bo.setPrice(zxzqGoodsBo.getSecurities());// 购买证券所需金额
        bo.setRealprice(zxzqGoodsBo.getSecurities()); //  真实花费金额

        bo.setSecuritiesnum(zxzqGoodsBo.getSecurities().multiply(zxzqMemberLevel.getLevered()));// 真实获得证券数
        /**********************订单表*****************************************/
        // 生成报单记录表
        dao.save("Securities.newSaveRecord", bo);


        ZxzqHoldingBO zxzqHoldingBO =(ZxzqHoldingBO)dao.findForObject("Securities.queryHolding", zxzqCommonBO);
        if(zxzqHoldingBO ==null){
            dao.save("Securities.saveHolding", bo);
        }else{
            dao.save("Securities.updateHolding", bo);
        }
        /************************用户钱包更新****************************************/
//        ZxzqWalletBo zxzqWalletBo = new ZxzqWalletBo();
//        // 查询该用户钱包现有金额
//        zxzqWalletBo = this.queryZxzqWallet(zxzqCommonBO);
//        BigDecimal price1 = zxzqWalletBo.getMoney(); // 该用户钱包现有金额
//
//        // 通过计算获得该用户购买证券后剩余金额
//        BigDecimal money = price1.subtract(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities())); //该用户购买证券后剩余金额
//        zxzqWalletBo.setMoney(money);
//        // 更新该用户钱包金额
//        dao.findForObject("Securities.updateWalletMoney", zxzqWalletBo);

        /************************生成钱包消费记录*******************************************/
//        ZxzqWalletBo walletBo = new ZxzqWalletBo();
//        walletBo.setMemberid(zxzqWalletBo.getMemberid());  // 用户id
//        walletBo.setPayrmoney(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities())); // 购买证券花费金额
//        walletBo.setPayid(securitiesid);   // 订单号
//
//        // 生成钱包消费记录
//        dao.save("Securities.saveWalletReward", walletBo);

        /****************************更新可购买证券数*******************************************/
        ZxzqVariableBO zxzqVariableBO = new ZxzqVariableBO();
        zxzqVariableBO.setType(1);
        // 根据type获取当前可购买的证券数
        zxzqVariableBO = ckeckVariable(zxzqVariableBO);
        BigDecimal amount = zxzqVariableBO.getAmount();   // 当前可购买证券总数

        // 更新可购买证券数
        amount = amount.subtract(zxzqGoodsBo.getSecurities()); // 购买证券后剩余可购买证券总数
        zxzqVariableBO.setAmount(amount);
        // 更新可购买证券总数
        dao.findForObject("Securities.updateVariable", zxzqVariableBO);
    }
    //查询等级表
    public ZxzqMemberLevel queryMemberLevel(ZxzqCommonBO zxzqCommonBO)throws Exception{
        return  (ZxzqMemberLevel)dao.findForObject("Securities.queryMemberLevel",zxzqCommonBO);

    }
    //计算出杠杆
    //memberid ,id(杠杆率id),securities（原先加现有证券数）
    public  ZxzqMemberLevel  setLevered (ZxzqCommonBO zxzqCommonBO)throws Exception{
        BigDecimal i =new BigDecimal("0"); //杠杆
        int   k =0; //对应good id
        //如果大于等于
        if(zxzqCommonBO.getSecurities().compareTo(new BigDecimal("150000"))== 1  || zxzqCommonBO.getSecurities().compareTo(new BigDecimal("150000"))==0){
            i =new BigDecimal("5");
            k =5;
        }else if (zxzqCommonBO.getSecurities().compareTo(new BigDecimal("100000"))== 1  || zxzqCommonBO.getSecurities().compareTo(new BigDecimal("100000"))==0){
            i =new BigDecimal("4");
            k =4;
        }else if (zxzqCommonBO.getSecurities().compareTo(new BigDecimal("50000"))== 1  || zxzqCommonBO.getSecurities().compareTo(new BigDecimal("50000"))==0){
            i =new BigDecimal("3.5");
            k =3;
        }else if (zxzqCommonBO.getSecurities().compareTo(new BigDecimal("10000"))== 1  || zxzqCommonBO.getSecurities().compareTo(new BigDecimal("10000"))==0){
            i =new BigDecimal("3");
            k =2;
        }else if(zxzqCommonBO.getSecurities().compareTo(new BigDecimal("1000"))== 1  || zxzqCommonBO.getSecurities().compareTo(new BigDecimal("1000"))==0){
            i =new BigDecimal("2");
            k =1;
        }else{
            i =new BigDecimal("0");
            k =0;
        }
        //查询原先等级
        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);

        //制作新对象
        ZxzqMemberLevel zxzqMemberLevelNew =new ZxzqMemberLevel();

        //计算出共多少钱
        ZxzqDrawCashBO zxzqDrawCashBO = this.queryRate(zxzqCommonBO);
        BigDecimal money  =zxzqCommonBO.getSecurities().multiply(zxzqDrawCashBO.getMoney());
        //判断等级 如果原先等级大于现在等级 则不处理

        if(zxzqMemberLevel.getMember_level()>=k){
            zxzqMemberLevelNew.setMember_level(zxzqMemberLevel.getMember_level());
            zxzqMemberLevelNew.setMember_id(zxzqCommonBO.getMemberid());
            zxzqMemberLevelNew.setMember_realprice(money);
            //找到原先id 对应杠杆
            zxzqCommonBO.setId(zxzqMemberLevel.getMember_level());
            ZxzqGoodsBo  zxzqGoodsBo=  this.queryZxzqGoods(zxzqCommonBO);
            zxzqMemberLevelNew.setLevered(zxzqGoodsBo.getLevered());
        }else{
            zxzqMemberLevelNew.setMember_level(k);
            zxzqMemberLevelNew.setMember_id(zxzqCommonBO.getMemberid());
            zxzqMemberLevelNew.setMember_realprice(money);
            zxzqMemberLevelNew.setLevered(i);

            //当新的订单对应等级大于原先等级 等级改变
            this.setZxzqMemberLevel(zxzqMemberLevelNew);

        }
        return zxzqMemberLevelNew;


    }


    /**
     * 从新确认zxzq_member_lever
     *
     * @return
     */
    public void setZxzqMemberLevel(ZxzqMemberLevel zxzqMemberLevelNew)throws Exception{
        dao.update("Securities.setZxzqMemberLevel", zxzqMemberLevelNew);

    }


    /**
     * 购买证券生成的流水号(订单号)
     *
     * @return
     */
    public String getSecuritiesid() {
        int j = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;//产生1000-9999的随机数
        String i = Tools.getSysDateString("yyyyMMddHHmmss") + j;
        return i;
    }


    /**
     * 更新该用户推荐人 福利 积分 可提现证券数量  并生成记录
     *
     * @param zxzqReward
     * @param securitiesid
     * @throws Exception
     */
    public void saveInformation(ZxzqReward zxzqReward, String securitiesid) throws Exception {
        /**************************更新该推荐人 待分配证券数(information 表)********************************************/
        ZxzqInformationBO zxzqInformationBO = new ZxzqInformationBO();
        // 查询该推荐人现有福利 积分 可提现证券
        zxzqInformationBO = queryInformation(zxzqReward);
        // 判断该推荐人现有福利积分可提现证券是否为空
        if (zxzqInformationBO != null) {
            if (zxzqInformationBO.getAssigned() != null || "".equals(zxzqInformationBO.getAssigned())) {
                zxzqInformationBO.setAssigned(zxzqInformationBO.getAssigned().add(zxzqReward.getRewardnum())); // 待分配证券
            } else {
                zxzqInformationBO.setAssigned(zxzqReward.getRewardnum());     // 待分配奖金
            }
            zxzqInformationBO.setMemberid(zxzqReward.getInvitedid());        // 该推荐人id
            // 更新该推荐人 待分配证券数
            dao.findForObject("Securities.updateInformation", zxzqInformationBO);
        }
    }

	/**
	 * @methodName 购买证券判断是否有欠款
	 * @Author 刘洋
	 * @param
	 * @return
	 * @throws
	 * @Date: 2016-12-01
	 * @Time: 17:59
	 */
	public List<ZxzqRecordBO> checkRecordRealprice(ZxzqCommonBO zxzqCommonBO)throws Exception{
		return (List<ZxzqRecordBO>) dao.findForList("Securities.checkRecordRealprice",zxzqCommonBO);
	}

    public ZxzqRecordBO checkRecordRealpriceNew(ZxzqCommonBO zxzqCommonBO)throws Exception{
        return (ZxzqRecordBO) dao.findForObject("Securities.checkRecordRealpriceNew", zxzqCommonBO);
    }



    /**
     * 获取information信息
     *
     * @param zxzqReward
     * @return
     * @throws Exception
     */
    public ZxzqInformationBO queryInformation(ZxzqReward zxzqReward) throws Exception {
        return (ZxzqInformationBO) dao.findForObject("Securities.queryInformation", zxzqReward);
    }

    //查询 zxzq_drawcash 表中是否含有该推荐人信息
    public ZxzqDrawCashBO checkDrawCash(ZxzqReward zxzqReward) throws Exception {
        return (ZxzqDrawCashBO) dao.findForObject("Securities.checkDrawCash", zxzqReward);
    }

    // 查询可购买证券总数
    public ZxzqVariableBO ckeckVariable(ZxzqVariableBO zxzqVariableBO) throws Exception {
        return (ZxzqVariableBO) dao.findForObject("Securities.ckeckVariable", zxzqVariableBO);
    }

    //  查询rate 最新购买率
    public ZxzqDrawCashBO queryRate(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (ZxzqDrawCashBO) dao.findForObject("Securities.queryRate", zxzqCommonBO);
    }

    /**
     * ****************************************************************************************************
     */


    //证券登陆
    public ZxzqMemberBo login(ZxzqLoginVo zxzqLoginVo) throws Exception {
        return (ZxzqMemberBo) dao.findForObject("Securities.login", zxzqLoginVo);
    }
    //更新登录时间
    public void updateTime(ZxzqLoginVo zxzqLoginVo) throws Exception {
        dao.update("Securities.updateTime",zxzqLoginVo);
    }

    public ZxzqDrawCashBO queryZxzqDrawcash(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (ZxzqDrawCashBO) dao.findForObject("Securities.queryZxzqDrawcash", zxzqCommonBO);
    }

    //证券提现
    public ZxzqMemberBo withdrawCash(ZxzqLoginVo zxzqLoginVo) throws Exception {
        return (ZxzqMemberBo) dao.findForObject("Securities.login", zxzqLoginVo);
    }

    //校验用户名是否存在
    public UserInfoBO isExitMemberName(DoRegisterVo doRegisterVo) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.queryMemberName", doRegisterVo);
    }
	//校验银行卡是否存在
	public UserInfoBO isEixtMemberCard(DoRegisterVo doRegisterVo) throws Exception {
		return (UserInfoBO)dao.findForObject("Securities.queryBankCard", doRegisterVo);
	}



    public Map<String, Object> doRegister(DoRegisterVo doRegisterVo, ZxzqMemberBo zxzqMemberBo) throws Exception {
        Map<String, Object> map = Tools.errMessageSystem();
        //注册
        try {

            List<ZxzqMemberBo> list = (List<ZxzqMemberBo>) dao.findForList("Securities.queryMemberByNetWorkList", zxzqMemberBo);
            if (list.size() == 0) {
                String network_id = String.valueOf(zxzqMemberBo.getNetwork_team() + "01");
                doRegisterVo.setNetwork_team(network_id);
                doRegisterVo.setNetwork_id(doRegisterVo.getNetwork_id());
            } else if (list.size() == 1) {
                ZxzqMemberBo bo = list.get(0);
                if ("01".equals(bo.getNetwork_team().substring(bo.getNetwork_team().length() - 2, bo.getNetwork_team().length()))) {
                    String str = bo.getNetwork_team().substring(0, bo.getNetwork_team().length() - 2);
                    String network_team = str + "02";
                    doRegisterVo.setNetwork_team(network_team);
                    doRegisterVo.setNetwork_id(doRegisterVo.getNetwork_id());
                } else {
                    String str = bo.getNetwork_team().substring(0, bo.getNetwork_team().length() - 2);
                    String network_team = str + "01";
                    doRegisterVo.setNetwork_team(network_team);
                    doRegisterVo.setNetwork_id(doRegisterVo.getNetwork_id());
                }
            } else if (list.size() == 2) {
                map.put("message", "该管理者ID下级用户已满，请更换管理者ID");
                map.put("code", "1");
                map.put("data", null);
                return map;
            }


            doRegisterVo.setPassWord(MD5.md5(doRegisterVo.getPassWord()));
            dao.save("Securities.doRegister", doRegisterVo);
            //初始化数据
            UserInfoBO bo = this.isExitMemberName(doRegisterVo);
            BalanceVo bv = new BalanceVo();
            bv.setIntbalance(0);
            bv.setAccbalance(0);
            bv.setReward((float) 0.0);
            bv.setUserid(bo.getMember_id());
            bv.setRemainbalance(100000);
            dao.save("Securities.doInitBalance", bv);
            //初始化健康状况数据

//            try {
//                dao.save("Member.initHealthCondition",bo.getMember_id());
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("这个方法有问题");
//            }
            this.updateMemberSn();
            initializtionZxzq(bo.getMember_id());
            map.put("message", "注册成功");
            map.put("code", "0");
            map.put("data", null);


        } catch (Exception e) {
            e.printStackTrace();
            return map;

        }
        return map;
    }

    //******************************************************************************

    //注册
    public void userRegister(UserInfoVO userInfoVO) throws Exception {
        dao.findForObject("Securities.userRegister", userInfoVO);
    }

    //查询证券额表
    public ZxzqGoodsBo queryZxzqGoods(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (ZxzqGoodsBo) dao.findForObject("Securities.queryZxzqGoods", zxzqCommonBO);
    }

    //查询钱包
    public ZxzqWalletBo queryZxzqWallet(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (ZxzqWalletBo) dao.findForObject("Securities.queryZxzqWallet", zxzqCommonBO);
    }

    //查询孝币钱包
    public ZxzqXbWalletBo queryZxzqXbWallet(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (ZxzqXbWalletBo) dao.findForObject("Securities.queryZxzqXbWallet", zxzqCommonBO);
    }



    /**
     * 初始化zxzq数据
     *
     * @throws Exception
     */
    public void initializtionZxzq(int memberid) throws Exception {

        ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
        zxzqCommonBO.setMemberid(memberid);
        ZxzqWalletBo zxzqWalletBo = (ZxzqWalletBo) dao.findForObject("Securities.queryZxzqWallet", zxzqCommonBO);
        if (zxzqWalletBo == null) {
            dao.save("Securities.initializtionDrawcash", memberid);
            dao.save("Securities.initializtionInformation", memberid);
            UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Securities.queryByMemberid", memberid);
//		userInfoBO.setMember_id(memberid);
            dao.save("Securities.initializtionWallet", userInfoBO);

            dao.save("Securities.initializtionMemberLevel" ,userInfoBO);

            //

        }
    }

    public void deleteMember(ZxzqCommonBO zxzqCommonBO) throws Exception {
        dao.delete("Securities.deleteUserbalance", zxzqCommonBO);
        dao.delete("Securities.deleteScorerecord", zxzqCommonBO);
        dao.delete("Securities.deleteAmountrecord", zxzqCommonBO);
        dao.delete("Securities.deleteZxzqWallet", zxzqCommonBO);
        dao.delete("Securities.deleteZxzqInformation", zxzqCommonBO);
        dao.delete("Securities.deleteZxzqDrawcash", zxzqCommonBO);
    }


    /**
     * *************************************购买证券*************************************************
     */

    public void automaticRelease()throws Exception{
		dao.save("Securities.automaticRelease1",null);
		dao.update("Securities.automaticRelease2",null);
		dao.update("Securities.automaticRelease3", null);
		dao.update("Securities.automaticRelease4",null);
		dao.update("Securities.automaticRelease5",null);


    }


    /**
     * 获取每天定时生成上三级推荐人奖励 的用户
     *
     * @return
     * @throws Exception
     */
    public List<ZxzqRecordBO> queryScuritiesEveryday() throws Exception {
        return (List<ZxzqRecordBO>) dao.findForList("Securities.queryScuritiesEveryday", null);
    }

    /**
     * 生成指定用户的上三级推荐人奖励  获取用户信息
     *
     * @param zxzqCommonBO
     * @return
     * @throws Exception
     */
    public List<ZxzqRecordBO> queryScuritiesById(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (List<ZxzqRecordBO>) dao.findForList("Securities.queryScuritiesById", zxzqCommonBO);
    }

    // 生成上三级推荐人奖励
    public void updateThreeSeCuritiesReward(List<ZxzqRecordBO> userList) throws Exception {
        /**************************保存报单推荐人奖励记录**************************************/

        if (userList != null && userList.size() > 0) {

            for (int i = 0; i < userList.size(); i++) {
                // 获取当前用户购买证券花实际金额
                BigDecimal realprice = userList.get(i).getRealprice();
                // 获取当前用户都买证券数
                BigDecimal securities = userList.get(i).getSecurities();
                // 获取当前用户的订单流水号
                String securitiesid = userList.get(i).getSecuritiesid();
                // 获取当前用户member_id
                Integer memberId = userList.get(i).getMemberid();
                boolean result = true;
                result = Tools.isZero(realprice);
                if (result == false) {


                    ZxzqGoodsBo zxzqGoodsBo = new ZxzqGoodsBo();
                    // 根据当前用户够没证券数获取投资额度信息
                    zxzqGoodsBo = (ZxzqGoodsBo) dao.findForObject("Securities.findZxzqGoods", securities);
                    if (zxzqGoodsBo != null) {
                        UserInfoBO userInfoBO = new UserInfoBO();
                        userInfoBO.setMember_id(memberId);
                        // 根据当前用户id 找寻推荐人
                        userInfoBO = (UserInfoBO) dao.findForObject("Securities.queryMemberById", userInfoBO);
                        // 判断该用户上级推荐人是否存在
                        if (StringUtil.isNotEmpty(userInfoBO.getSecurities_id()) && StringUtil.isNotEmpty(userInfoBO.getNetwork_id())) {
                            //获取该用户一级推荐人 id
                            UserInfoBO usrinfo = new UserInfoBO();
                            usrinfo = (UserInfoBO) dao.findForObject("Securities.queryIdByUserName", userInfoBO);
                            if (usrinfo != null) {

                                ZxzqReward zxzqReward = new ZxzqReward();
                                BigDecimal t_1 = zxzqGoodsBo.getT_1();      // 一级推荐人奖励系数
                                BigDecimal rewardnum1 = t_1.multiply(realprice);        //一级推荐人获得的总的奖励证券数

                                //保存一级推荐人信息
                                zxzqReward.setMemberid(memberId);
                                zxzqReward.setInvitedid(usrinfo.getMember_id());
                                zxzqReward.setSecuritiesid(securitiesid);
                                zxzqReward.setInvitelevel(1);
                                zxzqReward.setRewardpoint(t_1);
                                zxzqReward.setRewardnum(rewardnum1);
                                dao.findForObject("Securities.insertReward", zxzqReward);

                                // 更新该用户 一级推荐人 福利 积分 可提现证券数量  并生成记录
                                saveInformation(zxzqReward, securitiesid);

                                //获取二级推荐人id
                                UserInfoBO user = new UserInfoBO();
                                user = (UserInfoBO) dao.findForObject("Securities.queryIdByUserName", usrinfo);
                                if (user != null) {
                                    ZxzqReward zReward = new ZxzqReward();
                                    BigDecimal t_2 = zxzqGoodsBo.getT_2();      // 二级推荐人奖励系数
                                    BigDecimal rewardnum2 = t_2.multiply(realprice);        // 二级推荐人获得的总的奖励证券数

                                    //保存二级推荐人信息
                                    zReward.setMemberid(memberId);
                                    zReward.setInvitedid(user.getMember_id());
                                    zReward.setSecuritiesid(securitiesid);
                                    zReward.setInvitelevel(2);
                                    zReward.setRewardpoint(t_2);
                                    zReward.setRewardnum(rewardnum2);
                                    dao.findForObject("Securities.insertReward", zReward);

                                    // 更新该用户 二级推荐人 福利 积分 可提现证券数量  并生成记录
                                    saveInformation(zReward, securitiesid);

                                    //获取三级推荐人id
                                    UserInfoBO infoBO = new UserInfoBO();
                                    infoBO = (UserInfoBO) dao.findForObject("Securities.queryIdByUserName", user);
                                    if (infoBO != null) {
                                        ZxzqReward reward = new ZxzqReward();
                                        BigDecimal t_3 = zxzqGoodsBo.getT_3();      // 三级推荐人奖励系数
                                        BigDecimal rewardnum3 = t_3.multiply(realprice);        //三级推荐人获得的总的奖励证券数

                                        //保存三级推荐人信息
                                        reward.setMemberid(memberId);
                                        reward.setInvitedid(infoBO.getMember_id());
                                        reward.setSecuritiesid(securitiesid);
                                        reward.setInvitelevel(3);
                                        reward.setRewardpoint(t_3);
                                        reward.setRewardnum(rewardnum3);
                                        dao.findForObject("Securities.insertReward", reward);

										// 更新该用户 三级推荐人 福利 积分 可提现证券数量  并生成记录
										saveInformation(reward,securitiesid);
									}
								}
							}
							ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
							zxzqCommonBO.setMemberid(memberId);
							zxzqCommonBO.setSecuritiesid(securitiesid);
							zxzqCommonBO.setRealprice(realprice);
//							manager(zxzqCommonBO);
						}
					}
                } else {
                    dao.findForObject("Securities.updateRecordStatus", memberId);
                }


            }

        }
    }


    /**
     * 设置证券团队标识
     *
     * @param securities_id
     * @return
     */
    public String setSecuritiesTeam(String securities_id) throws Exception {

        String teamSign = "";

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setSecurities_id(securities_id);

        if ("".equals(securities_id) || securities_id == null) {
            // 推荐人为空
            List<String> listMax = (List<String>) dao.findForList("Securities.getTopTeamSign", null);
            if (listMax != null && listMax.size() > 0) {
                if (listMax.get(0) == null || "" == listMax.get(0)) {
                    teamSign = "0001";
                } else {
                    String str = listMax.get(0);  // 团队标示
                    int len = str.length();    // 字符串长度
                    int index = 0;   // 预定义第一个不为0自负的位置
                    char strs[] = str.toCharArray();    // 将字符串转换成字符数组
                    for (int i = 0; i < len; i++) {
                        if ('0' != strs[i]) {
                            index = i;
                            break;
                        }
                    }

                    teamSign = str.substring(index, len);    // 截取字符串
                    int i = Integer.valueOf(teamSign) + 1;
                    if (i < 10) {
                        teamSign = "000" + i;
                    } else if (i < 100) {
                        teamSign = "00" + i;
                    } else if (i < 1000) {
                        teamSign = "0" + i;
                    } else {
                        teamSign = "" + i;
                    }

                }
            }
        } else {


            // 推荐人不为空
            List<UserInfoBO> list = (List<UserInfoBO>) dao.findForList("Securities.getTeamSignBySecuritiesId", userInfoVO);

            // 该人员有下级团队人员
            if (list != null && list.size() > 0) {
                UserInfoBO userInfoBO = list.get(0);

                if (userInfoBO == null) {
                    // 检查这个推荐人是否存在
                    UserInfoBO userInfo = findMemberByName(userInfoVO.getSecurities_id());
                    if (userInfo != null) {
                        List<UserInfoBO> listBase = (List<UserInfoBO>) dao.findForList("Securities.getTeamSignByMemberName", userInfoVO);

                        UserInfoBO userInfoBO1 = listBase.get(0);
                        if (userInfoBO1 != null) {
                            teamSign = listBase.get(0).getSecurities_team() + "0001";
                        } else {
                            return "3";
                        }
                    } else {
                        UserInfoBO userInfoBO2 = (UserInfoBO) dao.findForObject("Securities.querySecuritiesTeamBysId", userInfoVO);
                        teamSign = userInfoBO2.getSecurities_team();
                        teamSign = teamSign + "0001";

                    }

                } else {
                    String str = list.get(0).getSecurities_team();    // 团队标识符
                    String str1 = str.substring(str.length() - 4, str.length());   // 该级人员团队标识符
                    String strFather = str.substring(0, str.length() - 4);   // 该级人员父节点团队标识符

                    int len = str1.length();    // 取得字符串长度
                    int index = 0;   // 预定义一个非0字符的位置
                    char strs[] = str1.toCharArray();   // 将字符串转换成字符数组
                    for (int i = 0; i < len; i++) {
                        if ('0' != strs[i]) {
                            index = i;
                            break;
                        }
                    }

                    teamSign = str1.substring(index, len);   // 截取字符串
                    int i = Integer.valueOf(teamSign) + 1;
                    if (i < 10) {
                        teamSign = "000" + i;
                    } else if (i < 100) {
                        teamSign = "00" + i;
                    } else if (i < 1000) {
                        teamSign = "0" + i;
                    } else {
                        teamSign = "" + i;
                    }
                    teamSign = strFather + teamSign;
                }
            } else {

                // 该推荐人之下没有人
                UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Securities.querySecuritiesTeamBysId", userInfoVO);
                teamSign = userInfoBO.getTeam_sign();
                teamSign = teamSign + "0001";
            }
        }
        return teamSign;
    }

    /**
     * 更新用户的证券团队标识和证券推荐人
     *
     * @param member_name
     * @param securities_id
     * @return
     */
    public String updateSecuritiesTeam(String member_name, String securities_id) {

        try {

            UserInfoBO userInfoBO = new UserInfoBO();
            userInfoBO.setMember_name(member_name);
            userInfoBO.setSecurities_id(securities_id);

            // 生成证券团队标识
            String teamSign = setSecuritiesTeam(securities_id);
            if (teamSign == null || "".equals(teamSign)) {
                return "2";
            } else if ("3".equals(teamSign)) {
                return "3";
            }

            userInfoBO.setSecurities_team(teamSign);
            dao.update("Securities.udateSecuritiesTeam", userInfoBO);
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }

        return "0";
    }


    //根据token查询用户id
    public UserInfoBO queryByMemberid(TokenBO tokenBO) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.queryByid", tokenBO);
    }

    /*
    * 修改信息查询银行卡
    */
    public UserInfoBO findBankCardForUpdate(UpdateUserInfoVo updateUserInfoVo) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.findBankCardForUpdate", updateUserInfoVo);
    }

    /**
     * 修改基本信息
     *
     * @param userInfoBO
     * @throws Exception
     */
    public void updateUserInfo(UserInfoBO userInfoBO) throws Exception {
        dao.update("Securities.updateUserInfo", userInfoBO);
    }

    /**
     * 校验推荐人是否存在
     *
     * @param member_id
     * @return
     */
    public UserInfoBO checkInviterByMemberId(Integer member_id) throws Exception {

        UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Securities.checkInviterByMemberId", member_id);

        return userInfoBO;
    }


    /**
     * 通过memberName查询用户信息
     *
     * @param memberName
     * @return
     * @throws Exception
     */
    public UserInfoBO findMemberByName(String memberName) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.queryMemberByName", memberName);
    }

    /**
     * 通过member_id查询订单列表
     *
     * @return
     */
    public List<ZxzqRecordBO> findRecordByMemberId(ZxzqRecordVo recordVo) throws Exception {

        List<ZxzqRecordBO> list = (List<ZxzqRecordBO>) dao.findForList("Securities.findRecordByMemberId", recordVo);

        return list;
    }

    /**
     * 修改密码
     *
     * @param userInfoVO
     * @return
     * @throws Exception
     */
    public UserInfoBO checkMemberForUpdatePassword(UserInfoVO userInfoVO) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.checkMemberForUpdatePassword", userInfoVO);
    }

    public void updatePassword(UserInfoBO userInfoBO) throws Exception {
        dao.findForObject("Securities.updatePassword", userInfoBO);
    }

    /**
     * 证券注册校验推荐人信息
     *
     * @param doRegisterVo
     * @return
     * @throws Exception
     * @Auth 李荣洲
     */
    public Map<String, Object> checkSecurities_teamByInviter(DoRegisterVo doRegisterVo) throws Exception {
        Map<String, Object> map = Tools.errMessageSystem();
        try {
            UserInfoBO bo = (UserInfoBO) dao.findForObject("Securities.checkSecurities_teamByName", doRegisterVo);
            if (bo == null) {
                map.put("message", "推荐人不存在,请重新确认");
                map.put("code", "1");
                map.put("data", null);
                return map;
            } else {
                if (bo.getSecurities_team() == null || "".equals(bo.getSecurities_team())) {
                    map.put("message", "您的推荐人还没有证券团队标识,请重新填写");
                    map.put("code", "1");
                    map.put("data", null);
                    return map;
                }
                map.put("message", "成功");
                map.put("code", "0");
                map.put("data", null);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }
    /**
     * 根据推荐人账号查询网络标识
     *
     * @param
     * @return
     * @throws Exception
     */
    public NetWorkBO findNetworkTeams(Integer id) throws Exception {
        return (NetWorkBO) dao.findForObject("Securities.findNetworkTeams", id);
    }

    /**
     * 查询该推荐人右区是否存在用户
     *
     * @param netWorkBO
     * @return
     * @throws Exception
     */
    public NetWorkBO checkNetworkTeam(NetWorkBO netWorkBO) throws Exception {
        return (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkBO);
    }

    /**
     * 获取该推荐人左区最后一个用户id
     *
     * @param netWorkBO
     * @return
     * @throws Exception
     */
    public List<NetWorkBO> getAllNetworkTeam(NetWorkBO netWorkBO) throws Exception {
        return (List<NetWorkBO>) dao.findForList("Securities.getAllNetworkTeam", netWorkBO);
    }

    /**
     * 获取推荐人下级成员的购买证券数
     *
     * @param netWorkBO
     * @return
     * @throws Exception
     */
    public List<ZxzqRecordBO> getRecordListSecurities(NetWorkBO netWorkBO) throws Exception {
        return (List<ZxzqRecordBO>) dao.findForList("Securities.getRecordListSecurities", netWorkBO);
    }

    /**
     * 判断新用户是否进行过操作
     *
     * @param
     * @return
     * @throws Exception
     */
    public ZxzqRecordBO checkNewUserRecord(NetWorkVO netWorkVO) throws Exception {
        return (ZxzqRecordBO) dao.findForObject("Securities.checkNewUserRecord", netWorkVO);
    }


    /**
     * 通过比较每条线上所有人购买的证券数获取可选网络标识位置(即可选id)
     *
     * @param netWorkBO
     * @return
     * @throws Exception
     * @Auth 刘洋
     */
    public List<Integer> findPlace(NetWorkBO netWorkBO) throws Exception {
        StringBuffer workAllTeam = new StringBuffer(netWorkBO.getNetwork_team());
        String allWorkTeam = workAllTeam.append("%").toString();
        List<Integer> idList = new ArrayList<Integer>();
        netWorkBO.setNetwork_team(allWorkTeam);
        // 获取该推荐人所有下级成员的网络标识
        List<NetWorkBO> list = getAllNetworkTeam(netWorkBO);

        List<NetWorkBO> netWorkBOList = new ArrayList<NetWorkBO>();

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String workTeam = list.get(i).getNetwork_team();
                Integer memberid = list.get(i).getMember_id();
                BigDecimal sum = new BigDecimal("0");
                int teamLength = (int) workTeam.length() / 2;
                for (int j = 1; teamLength >= j; j++) {
                    netWorkBO.setNetwork_team(workTeam);
                    // 根据network_team 获取购买证券数
                    List<ZxzqRecordBO> recordList = getRecordListSecurities(netWorkBO);
                    // 计算同一个id所购买证券总数
                    for (int k = 0; k < recordList.size(); k++) {
                        sum = sum.add(recordList.get(k).getSecurities());
                    }
                    // 获取上一级团队标识
                    int length = workTeam.length() - 2;
                    workTeam = workTeam.substring(0, length);
                }
                // 保存该推荐人所有下级成员的id 以及该下级成员的所有上级成员购买证券数
                NetWorkBO bo = new NetWorkBO();
                bo.setMember_id(memberid);
                bo.setSum(sum);
                netWorkBOList.add(bo);

            }
            // 比较所有分区购买证券数的大小
            List<BigDecimal> bigList = new ArrayList<BigDecimal>();
            for (int a = 0; a < netWorkBOList.size(); a++) {
                bigList.add(netWorkBOList.get(a).getSum());
            }
            // 比较所有分区购买证券数的大小
            BigDecimal max = Collections.max(bigList);
            // 获取可生成网络标识位置(即 上级id)
            for (int b = 0; b < netWorkBOList.size(); b++) {
                if (netWorkBOList.get(b).getSum().equals(max)) {
                    idList.add(netWorkBOList.get(b).getMember_id());
                }
            }
        }
        return idList;
    }


    /**
     * 奖励生成(生成网络标识是计算奖励)
     *
     * @param
     * @return
     * @throws Exception
     * @Auth 张伟
     */
    public void manager(ZxzqCommonBO zxzqCommonBO) throws Exception {
//		/**********************初始化数据********************************************************/
//		this.initializtionZxzq(zxzqCommonBO.getMemberid());
        /**********************准备*************************************************************/
        //查询网络团队
        ZxzqMemberBo zxzqMemberBo = (ZxzqMemberBo) dao.findForObject("Securities.queryZxzqMemberById", zxzqCommonBO);
        //购买人的memberid
        int memberId = zxzqMemberBo.getMember_id();
        //查询网络团队上级层数
        String networkTeam = zxzqMemberBo.getNetwork_team();
        if (StringUtil.isNotEmpty(networkTeam)) {

            int j = (int) zxzqMemberBo.getNetwork_team().length() / 2;

            /**************************************************************************************************/
            ZxzqRecordBO zxzqRecordBO = (ZxzqRecordBO) dao.findForObject("Securities.queryZxzqRecord", zxzqCommonBO);
            if (zxzqRecordBO != null) {
                //查询左右区业绩
                for (int k = 1; j > k; k++) {
                    //左右区顶级人员网络标识
                    String networkTeamNow = networkTeam.substring(0, networkTeam.length() - ((k * 2)));
                    String str1 = networkTeamNow + "01" + "%";//左
                    ZxzqInformationBO leftBO = (ZxzqInformationBO) dao.findForObject("Securities.queryInforMationAchievement1", str1);
                    //查询左右区业绩
                    String str2 = networkTeamNow + "02" + "%";//右
                    ZxzqInformationBO rightBO = (ZxzqInformationBO) dao.findForObject("Securities.queryInforMationAchievement2", str2);

                    if (leftBO == null || rightBO == null) {
                        continue;
                    }

                    if (!Tools.compareTo(leftBO.getSecurities(), rightBO.getSecurities())) {
                        if (networkTeam.substring(0, networkTeam.length() - ((k - 1) * 2)).equals(str1.substring(0, str1.length() - 1))) {
                            //订单号
                            String securitiesid = zxzqRecordBO.getSecuritiesid();
                            //推荐人
                            String Inviter_id = zxzqMemberBo.getInviter_id();
                            //购买证券
                            BigDecimal securities = zxzqRecordBO.getSecurities();
                            //实际支付金额
                            BigDecimal realprice = zxzqRecordBO.getRealprice();

                            int memberid = zxzqRecordBO.getMemberid();
                            //证券对应的奖励等级
                            ZxzqGoodsBo ZxzqGoods = (ZxzqGoodsBo) dao.findForObject("Securities.queryInforGoods", zxzqRecordBO);
                            BigDecimal bred = ZxzqGoods.getT_bred();
                            BigDecimal bred_f = ZxzqGoods.getT_bred_f();
                            BigDecimal level = bred.add(bred_f);
                            //奖金
                            BigDecimal assigned = realprice.multiply(level);
                            //保存奖金
                            ZxzqInformationBO zxzqInformationBO = new ZxzqInformationBO();
                            //通过网络标识查询memberid
                            ZxzqMemberBo zxzqMember = (ZxzqMemberBo) dao.findForObject("Securities.queryMemberBynetworkTeam", networkTeamNow);
                            int member_id = zxzqMember.getMember_id();
                            zxzqInformationBO.setMemberid(member_id);
                            zxzqInformationBO.setAssigned(assigned);
                            dao.update("Securities.setAssigned", zxzqInformationBO);

                            Timestamp time = new Timestamp(new Date().getTime());
                            //保存zxzq_reward数据
                            ZxzqReward zxzqReward = new ZxzqReward();
                            zxzqReward.setMemberid(memberid);
                            zxzqReward.setInvitedid(member_id);
                            zxzqReward.setInvitelevel(4);
                            zxzqReward.setSecuritiesid(securitiesid);
                            zxzqReward.setRewardpoint(level);
                            zxzqReward.setRewardnum(assigned);
                            zxzqReward.setRewardtime(time);
                            dao.save("Securities.saveZxzqReward", zxzqReward);


                            ZxzqRecordBO recordBO = new ZxzqRecordBO();
                            recordBO.setSecurities(assigned);
                            recordBO.setSecuritiesid(securitiesid);
                            recordBO.setMemberid(memberId);
                            this.setManger(recordBO, networkTeamNow);

                        } else {
                            continue;
                        }
                    } else {
                        if (networkTeam.substring(0, networkTeam.length() - ((k - 1) * 2)).equals(str1.substring(0, str1.length() - 1))) {
                            continue;
                        } else {
                            //订单号
                            String securitiesid = zxzqRecordBO.getSecuritiesid();
                            //推荐人
                            String Inviter_id = zxzqMemberBo.getInviter_id();
                            //购买证券
                            BigDecimal securities = zxzqRecordBO.getSecurities();
                            //实际支付金额
                            BigDecimal realprice = zxzqRecordBO.getRealprice();

                            int memberid = zxzqRecordBO.getMemberid();
                            //证券对应的奖励等级
                            ZxzqGoodsBo ZxzqGoods = (ZxzqGoodsBo) dao.findForObject("Securities.queryInforGoods", zxzqRecordBO);
                            BigDecimal bred = ZxzqGoods.getT_bred();
                            BigDecimal bred_f = ZxzqGoods.getT_bred_f();
                            BigDecimal level = bred.add(bred_f);
                            //奖金
                            BigDecimal assigned = realprice.multiply(level);
                            //保存奖金
                            ZxzqInformationBO zxzqInformationBO = new ZxzqInformationBO();
                            //通过网络标识查询memberid
                            ZxzqMemberBo zxzqMember = (ZxzqMemberBo) dao.findForObject("Securities.queryMemberBynetworkTeam", networkTeamNow);
                            int member_id = zxzqMember.getMember_id();
                            zxzqInformationBO.setMemberid(member_id);
                            zxzqInformationBO.setAssigned(assigned);
                            dao.update("Securities.setAssigned", zxzqInformationBO);

                            Timestamp time = new Timestamp(new Date().getTime());
                            //保存zxzq_reward数据
                            ZxzqReward zxzqReward = new ZxzqReward();
                            zxzqReward.setMemberid(memberid);
                            zxzqReward.setInvitedid(member_id);
                            zxzqReward.setInvitelevel(4);
                            zxzqReward.setSecuritiesid(securitiesid);
                            zxzqReward.setRewardpoint(level);
                            zxzqReward.setRewardnum(assigned);
                            zxzqReward.setRewardtime(time);
                            dao.save("Securities.saveZxzqReward", zxzqReward);


                            ZxzqRecordBO recordBO = new ZxzqRecordBO();
                            recordBO.setSecurities(assigned);
                            recordBO.setSecuritiesid(securitiesid);
                            recordBO.setMemberid(memberId);
                            this.setManger(recordBO, networkTeamNow);

                        }
                    }
                }


            }
            /********************* 生成业绩*****************************/
            this.setAchievement(zxzqCommonBO);

        }
    }

    //生成业绩
    public void setAchievement(ZxzqCommonBO zxzqCommonBO) throws Exception {

        try {
            //查询网络团队
            ZxzqMemberBo zxzqMemberBo = (ZxzqMemberBo) dao.findForObject("Securities.queryZxzqMemberById", zxzqCommonBO);
            if (zxzqMemberBo == null) {
                return;
            }
            //查询网络团队上级层数
            String networkTeam = zxzqMemberBo.getNetwork_team();

//		// 当前用户未奖励的订单和
//		ZxzqRecordBO  zxzqRecordBO = (ZxzqRecordBO) dao.findForObject("Securities.queryZxzqRecordSum", zxzqCommonBO);

            // 根据订单号查询订单
            ZxzqRecordBO zxzqRecordBO = (ZxzqRecordBO) dao.findForObject("Securities.queryqueryZxzqRecordBySecuritiesid", zxzqCommonBO);
            if (zxzqRecordBO == null) {
                return;
            }

            // 确定循环次数
            int j = (int) zxzqMemberBo.getNetwork_team().length() / 2;
            //查询该人员为生成奖励的单
            for (int i = 1; i < j; i++) {
                // 构建左右去上级人员网络标识
                String networkTeamNow = networkTeam.substring(0, (networkTeam.length() - 2 * i));

                // 通过networkTeam查找用户的id
                NetWorkBO netWorkBO = new NetWorkBO();
                netWorkBO.setNetwork_team(networkTeamNow);

                NetWorkBO netForId = (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkBO);
                if (netForId == null) {
                    return;
                }

                ZxzqRecordBO recordBO = new ZxzqRecordBO();
                recordBO.setMemberid(netForId.getMember_id());
                recordBO.setRealprice(zxzqRecordBO.getRealprice());
                dao.update("Securities.updateInfomation", recordBO);
                //下单状态

                dao.update("Securities.updatereocrd", zxzqRecordBO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 管理佣金
    // 需要参数：推荐人的id，ZxzqRecordBO
    public void setManger(ZxzqRecordBO zxzqRecordBO, String networkTeamNow) throws Exception {
        try {
            int i = networkTeamNow.length();
//		5%，10%，15%。
            if (i >= 4) {//一级
                BigDecimal rewordNum = zxzqRecordBO.getSecurities();

                Integer integer = null;
                String netWork_s = networkTeamNow.substring(0, networkTeamNow.length() - 2);
                // 根据网络标识获取用户的id
                NetWorkBO netWorkStr = new NetWorkBO();
                netWorkStr.setNetwork_team(netWork_s);
                NetWorkBO netWorkBO = (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkStr);

                // 获取网络上级的id（reward表中的invitedid）
                String networkForInvit = "";
                NetWorkBO invitedId = null;
                ZxzqReward reward = new ZxzqReward();
                if (i > 4) {
                    networkForInvit = netWork_s.substring(0, networkTeamNow.length() - 4);
                    netWorkStr.setNetwork_team(networkForInvit);
                    invitedId = (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkStr);

                    reward.setMemberid(zxzqRecordBO.getMemberid());
                    reward.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                    reward.setInvitedid(netWorkBO.getMember_id());
                    reward.setInvitelevel(5);
                    reward.setRewardpoint(new BigDecimal("0.15"));
                    reward.setRewardtime(new Timestamp(System.currentTimeMillis()));
                    reward.setRewardnum(rewordNum.multiply(new BigDecimal("0.15")));
                    //生成记录  zxzq_reward  ,zxzq_information
                    dao.save("Securities.saveRewordEveyRecord", reward);
                } else {
                    reward.setMemberid(zxzqRecordBO.getMemberid());
                    reward.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                    reward.setInvitedid(netWorkBO.getMember_id());
                    reward.setInvitelevel(5);
                    reward.setRewardpoint(new BigDecimal("0.15"));
                    reward.setRewardtime(new Timestamp(System.currentTimeMillis()));
                    reward.setRewardnum(rewordNum.multiply(new BigDecimal("0.15")));
                    //生成记录  zxzq_reward  ,zxzq_information
                    dao.save("Securities.saveRewordEveyRecord", reward);
                }

                dao.update("Securities.upadteAssigned", reward);

/**************************************************************************************************************************************************************/

                if (i >= 6) {//二级
                    // 获取二级团队的网络表示
                    netWork_s = networkTeamNow.substring(0, networkTeamNow.length() - 4);

                    // 根据网络标识获取用户的id
                    netWorkStr = new NetWorkBO();
                    netWorkStr.setNetwork_team(netWork_s);
                    netWorkBO = (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkStr);

                    // 获取网络上级的id（reward表中的invitedid）
                    if (i > 6) {
                        networkForInvit = netWork_s.substring(0, networkTeamNow.length() - 6);
                        invitedId = (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkStr);

                        reward.setMemberid(zxzqRecordBO.getMemberid());
                        reward.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                        reward.setInvitedid(netWorkBO.getMember_id());
                        reward.setInvitelevel(6);
                        reward.setRewardpoint(new BigDecimal("0.1"));
                        reward.setRewardtime(new Timestamp(System.currentTimeMillis()));
                        reward.setRewardnum(rewordNum.multiply(new BigDecimal("0.1")));

                        //生成记录  zxzq_reward  ,zxzq_information
                        dao.save("Securities.saveRewordEveyRecord", reward);
                    } else {
                        reward.setMemberid(zxzqRecordBO.getMemberid());
                        reward.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                        reward.setInvitedid(netWorkBO.getMember_id());
                        reward.setInvitelevel(6);
                        reward.setRewardpoint(new BigDecimal("0.1"));
                        reward.setRewardtime(new Timestamp(System.currentTimeMillis()));
                        reward.setRewardnum(rewordNum.multiply(new BigDecimal("0.1")));

                        //生成记录  zxzq_reward  ,zxzq_information
                        dao.save("Securities.saveRewordEveyRecord", reward);
                    }


                    dao.update("Securities.upadteAssigned", reward);

/**************************************************************************************************************************************************************/
                    if (i >= 8) {//三级

                        // 获取二级团队的网络表示
                        netWork_s = networkTeamNow.substring(0, networkTeamNow.length() - 6);

                        // 根据网络标识获取用户的id
                        netWorkStr = new NetWorkBO();
                        netWorkStr.setNetwork_team(netWork_s);
                        netWorkBO = (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkStr);

                        // 获取网络上级的id（reward表中的invitedid）
                        if (i > 8) {
                            networkForInvit = netWork_s.substring(0, networkTeamNow.length() - 8);
                            netWorkStr.setNetwork_team(networkForInvit);
                            invitedId = (NetWorkBO) dao.findForObject("Securities.checkNetworkTeam", netWorkStr);

                            reward.setMemberid(zxzqRecordBO.getMemberid());
                            reward.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                            reward.setInvitedid(netWorkBO.getMember_id());
                            reward.setInvitelevel(7);
                            reward.setRewardpoint(new BigDecimal("0.05"));
                            reward.setRewardtime(new Timestamp(System.currentTimeMillis()));
                            reward.setRewardnum(rewordNum.multiply(new BigDecimal("0.05")));

                            //生成记录  zxzq_reward  ,zxzq_information
                            dao.save("Securities.saveRewordEveyRecord", reward);
                        } else {
                            reward.setMemberid(zxzqRecordBO.getMemberid());
                            reward.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                            reward.setInvitedid(netWorkBO.getMember_id());
                            reward.setInvitelevel(7);
                            reward.setRewardpoint(new BigDecimal("0.05"));
                            reward.setRewardtime(new Timestamp(System.currentTimeMillis()));
                            reward.setRewardnum(rewordNum.multiply(new BigDecimal("0.05")));

                            //生成记录  zxzq_reward  ,zxzq_information
                            dao.save("Securities.saveRewordEveyRecord", reward);
                        }
                        //生成记录  zxzq_reward  ,zxzq_information
                        dao.update("Securities.upadteAssigned", reward);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 生成新用户网络标识
     *
     * @param netWorkBO
     * @throws Exception
     */
    public void setNewNetworkTeam(NetWorkBO netWorkBO) throws Exception {
        dao.findForObject("Securities.setNewNetworkTeam", netWorkBO);
    }

    /**
     * 获取推荐人账号
     *
     * @param userInfoBO
     * @return
     * @throws Exception
     */
    public UserInfoBO findUserNameById(UserInfoBO userInfoBO) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.findUserNameById", userInfoBO);
    }

    //	public void test1( DoRegisterVo doRegisterVo)throws Exception{
//		Map<String,Object> map=Tools.errMessageSystem();
//
////		return map;
//
//	}
    public ZxzqMemberBo queryMemberByMemberSn(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (ZxzqMemberBo) dao.findForObject("Securities.queryMemberByMemberSn", zxzqCommonBO);
    }

    public void updateMemberSn() throws Exception {
        dao.update("Securities.updateMemberSn", null);
    }


    /**
     * 校验token获取member
     * @param tokenVo
     * @return
     * @throws Exception
     * @auth 李荣洲
     */
    public UserInfoBO queryByToken(TokenVo tokenVo) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.queryByToken", tokenVo);
    }


    /**
     * 通过memberid查询个人账户积分
     *
     * @param userInfoBO
     * @return
     * @throws Exception
     * @auth 李荣洲
     */
    public Integer queryBalanceByMember(UserInfoBO userInfoBO) throws Exception {
        return (Integer) dao.findForObject("Securities.queryBalanceByMemberid", userInfoBO);
    }

    /**
     * 查询当日净值
     *
     * @return
     * @throws Exception
     * @auth 李荣洲
     */
    public Float queryTodayEquity() throws Exception {
        return (Float) dao.findForObject("Securities.queryTodayEquity", null);
    }

    /**
     * 将钱数转入证券钱包
     *
     * @param userInfoBO
     * @throws Exception
     * @auth 李荣洲
     */
    public void updateMoneyByMemberid(UserInfoBO userInfoBO) throws Exception {
        dao.update("Securities.setMoneyByMemberid", userInfoBO);
    }

    /**
     * 清零个人积分数
     * @param userInfoBO
     * @throws Exception
     * @auth 李荣洲
     */
    public void clearBalanceByMemberid(UserInfoBO userInfoBO) throws Exception {
        dao.update("Securities.clearBalanceByMemberid", userInfoBO);
    }

    /**
     * 现金状态，是否冻结
     *
     * @param zxzqInformationBO
     * @throws Exception
     */
    public void updateStatus(ZxzqInformationBO zxzqInformationBO) throws Exception {
        dao.update("Securities.updateStatus", zxzqInformationBO);
    }

    /**
     * @param
     * @return
     * @throws
     * @methodName 根据id查询用户
     * @Author 刘洋
     * @Date: 2016-11-28
     * @Time: 14:42
     */
    public UserInfoBO findMemberById(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (UserInfoBO) dao.findForObject("Securities.findMemberById", zxzqCommonBO);
    }

    //查询需删除订单是否存在
    public ZxzqRecordBO queryTodeleteRecord(ZxzqCommonBO zxzqCommonBO) throws Exception {
        return (ZxzqRecordBO) dao.findForObject("Securities.queryZxzqRecordCommon", zxzqCommonBO);
    }

    //删除订单是
    public void deleteRecord(ZxzqCommonBO zxzqCommonBO) throws Exception {
        dao.delete("Securities.deleteRecord", zxzqCommonBO);
        dao.delete("Securities.deleteHolding", zxzqCommonBO);
    }

    public void updateRecord(ZxzqCommonBO zxzqCommonBO) throws Exception {
        dao.delete("Securities.updateRecord", zxzqCommonBO);
    }


    public List<ZxzqRecordBO> setAchievementTest() throws Exception {
        return (List<ZxzqRecordBO>) dao.findForList("Securities.findAchievement", null);
    }

    /**
     * 证券钱包申请提现
     *
     * @param withdrawCashVo
     * @return
     * @throws Exception
     * @auth 李荣洲
     */
    public  Map<String, Object>  applyWithdraw(WithdrawCashVo withdrawCashVo) throws Exception {
        Map<String, Object> map = Tools.errMessageSystem();
        //提现单号
        String sn = "";
        try {
            UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Securities.findMemberInfoByMemberid", withdrawCashVo);
            if (userInfoBO == null) {
                map.put("code", "1");
                map.put("message", "用户不存在，申请提现失败");
                map.put("data", null);
                return map;
            }
//获取提现金额
            double money = (Double) dao.findForObject("Securities.findMoneyByMemberid", withdrawCashVo);
            if(money<100.00){
                map.put("code","1");
			map.put("message","钱包余额必须大于 100 才能进行申请提现");
			map.put("data",null);
			return map;
            }
            withdrawCashVo.setCash(money);

            //冻结提现金额
            dao.update("Securities.updateWallet", withdrawCashVo);
            //提现申请记录生成
            ZQCashLogVo zqCashLogVo = new ZQCashLogVo();
            long time = System.currentTimeMillis() / 1000;
            sn = RandomCode.getChar() + time;
            zqCashLogVo.setCash_sn(sn);
//            zqCashLogVo.setCash_true_name(userInfoBO.getMember_truename());
            zqCashLogVo.setCash_member_id(userInfoBO.getMember_id());
            zqCashLogVo.setCash_amount(new BigDecimal(withdrawCashVo.getCash()));
            zqCashLogVo.setCash_add_time(new Timestamp(System.currentTimeMillis()));
            zqCashLogVo.setCash_bank_name(userInfoBO.getMember_bankname());
            zqCashLogVo.setCash_bank_no(userInfoBO.getMember_bankcard());
            zqCashLogVo.setCash_bank_user(userInfoBO.getMember_truename());
            zqCashLogVo.setCash_member_name(userInfoBO.getMember_name());
            zqCashLogVo.setCash_payment_state("0");
            zqCashLogVo.setCash_remark("申请提现");
            dao.save("Securities.saveCash", zqCashLogVo);
            //提现款冻结明细
            ZQpdLogVo zqpdLogVo = new ZQpdLogVo();
            zqpdLogVo.setZqlg_add_time(new Timestamp(System.currentTimeMillis()));
            zqpdLogVo.setZqlg_av_amount(new BigDecimal(withdrawCashVo.getCash() * (-1)));
            zqpdLogVo.setZqlg_freeze_amount(new BigDecimal(withdrawCashVo.getCash()));
            zqpdLogVo.setZqlg_member_id(userInfoBO.getMember_id());
            zqpdLogVo.setZqlg_member_name(userInfoBO.getMember_name());
            zqpdLogVo.setZqlg_type("申请提现");
            zqpdLogVo.setZqlg_desc("申请提现，冻结预存款，提现单号:" + sn);
            dao.save("Securities.savezqpd_log", zqpdLogVo);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        map.put("code", "0");
        map.put("message", "申请提现成功");
        map.put("data", sn);

        return map;
    }

    /**
     * 撤销提现申请
     * @param withdrawCashVo
     * @return
     * @throws Exception
     * @auth 李荣洲
     */
    public Map<String, Object> rollbackWithdraw(WithdrawCashVo withdrawCashVo) throws Exception {
        Map<String, Object> map = Tools.errMessageSystem();
        //查询用户信息
        try {
            UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Securities.findMemberInfoByMemberid", withdrawCashVo);
            if (userInfoBO == null) {
                map.put("code", "1");
                map.put("message", "用户不存在，撤销申请提现失败");
                map.put("data", null);
                return map;
            }
            //通过提现单号查询提现金额
            BigDecimal result = (BigDecimal) dao.findForObject("Securities.findFrozen", withdrawCashVo);
            if(result==null){
                map.put("code", "1");
                map.put("message", "用户没有可撤销的提现申请");
                map.put("data", null);
                return map;
            }
            double frozen = result.doubleValue();
            withdrawCashVo.setCash(frozen);
            if (frozen == 0) {
                map.put("code", "1");
                map.put("message", "撤销提现失败，没有申请提现记录");
                map.put("data", null);
            }
            //提现冻结金额解冻
            dao.update("Securities.rollbackMoney", withdrawCashVo);
            //逻辑删除提现申请记录
            dao.update("Securities.deleteCashInfo", withdrawCashVo);
            //提现款解冻明细
            ZQpdLogVo zqpdLogVo = new ZQpdLogVo();
            zqpdLogVo.setZqlg_add_time(new Timestamp(System.currentTimeMillis()));
            zqpdLogVo.setZqlg_av_amount(new BigDecimal(withdrawCashVo.getCash()));
            zqpdLogVo.setZqlg_freeze_amount(new BigDecimal(withdrawCashVo.getCash() * (-1)));
            zqpdLogVo.setZqlg_member_id(userInfoBO.getMember_id());
            zqpdLogVo.setZqlg_member_name(userInfoBO.getMember_name());
            zqpdLogVo.setZqlg_type("撤销提现");
            zqpdLogVo.setZqlg_desc("撤销提现申请，解冻预存款，提现单号:" + withdrawCashVo.getCash_sn());
            dao.save("Securities.savezqpd_log", zqpdLogVo);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        map.put("code", "0");
        map.put("message", "撤销提现成功");
        map.put("data", null);
        return map;
    }

    /**
     * 确认提现申请
     * @param withdrawCashVo
     * @return
     * @throws Exception
     * @auth 李荣洲
     */
    public Map<String, Object> confirmWithdraw(WithdrawCashVo withdrawCashVo) throws Exception {
        Map<String, Object> map = Tools.errMessageSystem();
        //查询用户信息
        try {
            UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("Securities.findMemberInfoByMemberid", withdrawCashVo);
            if (userInfoBO == null) {
                map.put("code", "1");
                map.put("message", "用户不存在，提现失败");
                map.put("data", null);
                return map;
            }
            withdrawCashVo.setMember_id(userInfoBO.getMember_id());
            //通过提现单号查询提现金额
            BigDecimal result = (BigDecimal) dao.findForObject("Securities.findFrozen", withdrawCashVo);
            if(result==null){
                map.put("code", "1");
                map.put("message", "提现单号有误或没有可以提现金额");
                map.put("data", null);
                return map;
            }
            double frozen = result.doubleValue();
            withdrawCashVo.setCash(frozen);
            if (frozen == 0) {
                map.put("code", "1");
                map.put("message", "提现失败，没有提现金额");
                map.put("data", null);
            }
            //扣除冻结金额
            dao.update("Securities.modifyFrozen", withdrawCashVo);
            //修改个人提现记录
            dao.update("Securities.updatePaymentState", withdrawCashVo);
            //提现款解冻明细
            ZQpdLogVo zqpdLogVo = new ZQpdLogVo();
            zqpdLogVo.setZqlg_add_time(new Timestamp(System.currentTimeMillis()));
            zqpdLogVo.setZqlg_av_amount(new BigDecimal(0.00));
            zqpdLogVo.setZqlg_freeze_amount(new BigDecimal(withdrawCashVo.getCash() * (-1)));
            zqpdLogVo.setZqlg_member_id(userInfoBO.getMember_id());
            zqpdLogVo.setZqlg_member_name(userInfoBO.getMember_name());
            zqpdLogVo.setZqlg_type("提现成功");
            zqpdLogVo.setZqlg_desc("提现成功，扣除预存款，提现单号:" + withdrawCashVo.getCash_sn());
            dao.save("Securities.savezqpd_log", zqpdLogVo);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        map.put("code", "0");
        map.put("message", "提现成功");
        map.put("data", null);
        return map;
    }

	public ZxzqRecordBO queryByRecordBO(ZxzqCommonBO zxzqCommonBO)throws Exception{
		return (ZxzqRecordBO)dao.findForObject("Securities.queryByRecordBO", zxzqCommonBO);
	}
    public List<ZxzqRecordBO> queryByRecordByMember(ZxzqCommonBO zxzqCommonBO)throws Exception{
        return (List<ZxzqRecordBO>)dao.findForList("Securities.queryByRecordByMember", zxzqCommonBO);
    }




	public ZxzqWalletBo queryWallet(ZxzqCommonBO zxzqCommonBO)throws Exception{
		return (ZxzqWalletBo)dao.findForObject("Securities.queryWallet", zxzqCommonBO);
	}

	//金额填满
	public void backSecurities(ZxzqCommonBO zxzqCommonBO)throws Exception{
		dao.update("Securities.backSecurities", zxzqCommonBO);
	}
    //钱包减少
	public void backWallet(ZxzqCommonBO zxzqCommonBO)throws Exception{
		dao.update("Securities.backWallet", zxzqCommonBO);
	}

	public void backWalletRecord(ZxzqCommonBO zxzqCommonBO)throws Exception{
		dao.update("Securities.backWalletRecord", zxzqCommonBO);
	}

   /***********************************张伟结算*****************************************************/

   public List<ZxzqRecordBO> NewQueryZxzqRecord()throws Exception{
	   return (List<ZxzqRecordBO>)dao.findForList("Securities.NewQueryZxzqRecord",null);
   }

    public List<ZxzqRecordBO> NewQueryZxzqRecord1()throws Exception{
        return (List<ZxzqRecordBO>)dao.findForList("Securities.NewQueryZxzqRecord1",null);
    }



    public ZxzqRecordInFormation NewQueryZxzqRecordInFormation(ZxzqRecordBO zxzqRecordBO)throws Exception{
        return (ZxzqRecordInFormation)dao.findForObject("Securities.NewQueryZxzqRecordInFormation", zxzqRecordBO);
    }
    public ZxzqRecordInFormation NewQueryZxzqRecordInFormation1(ZxzqRecordBO zxzqRecordBO)throws Exception{
        return (ZxzqRecordInFormation)dao.findForObject("Securities.NewQueryZxzqRecordInFormation1", zxzqRecordBO);
    }

	public ZxzqRecordThree NewQueryZxzqRecordThree(ZxzqRecordInFormation zxzqRecordInFormation)throws Exception{
		return (ZxzqRecordThree)dao.findForObject("Securities.NewQueryZxzqRecordThree", zxzqRecordInFormation);
	}


	public void setReward(ZxzqRecordThree zxzqRecordThree   ,ZxzqRecordInFormation zxzqRecordInFormation)throws Exception{

        //每笔订单真是金额
        ZxzqRecordInFormation zxzqRecordInFormationNew =(ZxzqRecordInFormation)dao.findForObject("Securities.queryRecordSecuritiesid", zxzqRecordInFormation);



		BigDecimal i =zxzqRecordInFormationNew.getRealprice();

		ZxzqReward  zxzqReward =new ZxzqReward();
		zxzqReward.setMemberid(zxzqRecordInFormation.getMemberid());
		zxzqReward.setSecuritiesid(zxzqRecordInFormation.getSecuritiesid());




		//一级

		zxzqReward.setInvitedid(zxzqRecordThree.getMember_id1());
		zxzqReward.setInvitelevel(1);
		zxzqReward.setRewardpoint(zxzqRecordThree.getT_1());
		zxzqReward.setRewardnum(zxzqRecordThree.getT_1().multiply(i));
		dao.update("Securities.NewUpdateInformation",zxzqReward);
		dao.save("Securities.NewSaveReward",zxzqReward);

		//二级

		zxzqReward.setInvitedid(zxzqRecordThree.getMember_id2());
		zxzqReward.setInvitelevel(2);
		zxzqReward.setRewardpoint(zxzqRecordThree.getT_2());
		zxzqReward.setRewardnum(zxzqRecordThree.getT_2().multiply(i));
		dao.update("Securities.NewUpdateInformation",zxzqReward);
		dao.save("Securities.NewSaveReward",zxzqReward);


		//一级

		zxzqReward.setInvitedid(zxzqRecordThree.getMember_id3());
		zxzqReward.setInvitelevel(3);
		zxzqReward.setRewardpoint(zxzqRecordThree.getT_3());
		zxzqReward.setRewardnum(zxzqRecordThree.getT_3().multiply(i));
		dao.update("Securities.NewUpdateInformation",zxzqReward);
		dao.save("Securities.NewSaveReward",zxzqReward);
	}


    //更新状态
	public void updateRecordStatusNew(ZxzqCommonBO zxzqCommonBO)throws Exception{
		dao.update("Securities.updateRecordStatusNew", zxzqCommonBO);
	}
    public void updateRecordStatusNew1(ZxzqCommonBO zxzqCommonBO)throws Exception{
        dao.update("Securities.updateRecordStatusNew1", zxzqCommonBO);
    }


	//查询上级人员
	public String queryTop(ZxzqRecordInFormation zxzqRecordInFormation)throws Exception{

		String str =zxzqRecordInFormation.getNetwork_team().substring(0,zxzqRecordInFormation.getNetwork_team().length()-2);
		String strTop="";
		int j =str.length()/2;
		for(int i =0 ;i<j ; i++){
			strTop =strTop +"'"+str.substring(0,str.length()-(i*2))+"'"+"," ;
		}
		strTop=strTop.substring(0 ,strTop.length()-1);
		return  strTop;
	}

	//业绩处理
	public void updateZxzqMemberBoList(ZxzqCommonBO zxzqCommonBO)throws Exception{
		dao.update("Securities.updateZxzqMemberBoList", zxzqCommonBO);

	}


	public List<ZxzqRecordBO> backSecuritiesMessage(ZxzqCommonBO zxzqCommonBO)throws Exception{
		return (List<ZxzqRecordBO>)dao.findForList("Securities.backSecuritiesMessage", zxzqCommonBO);

	}
   /**********************************************************************************************************************************************************/
	//查询交易净值
	public ZxzqRateBO queryZxzqRate()throws Exception{
		return (ZxzqRateBO)dao.findForObject("Securities.queryZxzqRate", null);

	}


	public String autoRewordDistribution(ZxzqRateBO zxzqRateBO) {

		try {
            //获取当日兑换率
            ZxzqDrawCashBO zxzqDrawCashBO =  (ZxzqDrawCashBO) dao.findForObject("Securities.queryRate", null);


            MathContext mc = new MathContext(2, RoundingMode.DOWN);

             List<ZxzqJiangJinBO> list = (List<ZxzqJiangJinBO>)dao.findForList("Securities.queryJiangJin", null);
            if(list !=null && list.size()>0){
                for(int i=0;i<list.size();i++){
                    ZxzqJiangJinBO zxzqJiangJinBO =list.get(i);

                    //奖励金额换算成证券
                   BigDecimal a =zxzqJiangJinBO.getAssigned();
                    //奖励金额换算成可提现
                   BigDecimal h =zxzqJiangJinBO.getAssigned().multiply(new BigDecimal("0.75"));
                   //判断 持有证券 -奖金换算证券 是否大于 购买证券
                    int b =(zxzqJiangJinBO.getSecuritiesnum().subtract(h)).compareTo(zxzqJiangJinBO.getSecurities());

                    BigDecimal c= new BigDecimal("0"); //hold剩余证券
                    BigDecimal d= new BigDecimal("0"); //扣除持有证券;
                    BigDecimal f= new BigDecimal("0");//生成可体现证券;
                    BigDecimal g= new BigDecimal("0");//剩余奖金
                    int p =0;
                    if(b ==1 ||b ==0){ //当大于等于时
                        p=0;
                         c=zxzqJiangJinBO.getSecuritiesnum().subtract(h);   //hold剩余证券
                         d=h;  //扣除持有证券;
                         f=h; //生成可体现证券;
                         g=new BigDecimal("0");//剩余奖金

                    }else{ //当小于时
                        int k =zxzqJiangJinBO.getSecuritiesnum().compareTo(zxzqJiangJinBO.getSecurities());
                        if(k ==1 ){
                            p=1;
                             c=zxzqJiangJinBO.getSecurities(); //hold剩余证券
                             d=zxzqJiangJinBO.getSecuritiesnum().subtract(zxzqJiangJinBO.getSecurities());  //扣除持有证券;
                             f=zxzqJiangJinBO.getSecuritiesnum().subtract(zxzqJiangJinBO.getSecurities());  //生成可体现证券;
                             BigDecimal l=f.divide(new BigDecimal("0.75"), 2, BigDecimal.ROUND_DOWN);
                             g = a.subtract(l);//剩余奖金
                             a =l;
                        }else{
                            continue;
                        }
                    }

                    ZxzqJiangJinFaFang zxzqJiangJinFaFang =new ZxzqJiangJinFaFang();
                    zxzqJiangJinFaFang.setMemberid(zxzqJiangJinBO.getMemberid());
                    zxzqJiangJinFaFang.setSecurities(a.multiply(new BigDecimal("0.75")));


                    /****************************奖励回填开始****************************************/
                    //查询该人员需要回填欠款的金额 如果为0 则不欠款  其他为欠款
                    ZxzqRecordBO zxzqRecordBO=(ZxzqRecordBO)dao.findForObject("Securities.queryBack", zxzqJiangJinFaFang);


                    if(zxzqRecordBO ==null){}
                    else{
                        BigDecimal n =new BigDecimal("0");
                        int m =zxzqRecordBO.getPrice().compareTo(n);
                        if(m ==1){ //有欠款
                            //判断a 是否为0 为0 计算无意义
                            int v =a.multiply(new BigDecimal("0.75")).compareTo(n);
                            if(v ==1){
                                //开始判断是否足够50%  如果欠款 大于奖励的1/2 大于欠 则回填一半
                                int qw =zxzqRecordBO.getPrice().compareTo(a.multiply(new BigDecimal("0.75")).multiply(new BigDecimal("0.5")));
                                ZxzqCommonBO zxzqCommonBO  =new ZxzqCommonBO();
                                zxzqCommonBO.setMemberid(zxzqJiangJinBO.getMemberid());
                                if(qw ==1){ //回填一半
                                    zxzqCommonBO.setMoney(a.multiply(new BigDecimal("0.75")).multiply(new BigDecimal("0.5")));
                                    dao.save("Securities.saveZxzqBack", zxzqCommonBO);
                                    a.multiply(new BigDecimal("0.75")).multiply(new BigDecimal("0.5"));

                                    zxzqJiangJinFaFang.setSecurities(a.multiply(new BigDecimal("0.75")).multiply(new BigDecimal("0.5")));

                                }else{//回填实际欠款
                                    zxzqCommonBO.setMoney(zxzqRecordBO.getPrice());
                                    dao.save("Securities.saveZxzqBack",  zxzqCommonBO);
                                    zxzqJiangJinFaFang.setSecurities(a.multiply(new BigDecimal("0.75")).subtract(zxzqRecordBO.getPrice()));
                                }

                            }
                        }

                    }





                    zxzqJiangJinFaFang.setA(a);//奖励算成证券数
                    zxzqJiangJinFaFang.setC(c); //hold剩余证券
                    zxzqJiangJinFaFang.setD(d);  //扣除持有证券;
                    zxzqJiangJinFaFang.setF(f); //生成可体现证券;
                    zxzqJiangJinFaFang.setG(g); //剩余奖金

                    /****************************奖励回填结算****************************************/


                    //奖金转可提现证券表记录生成
                    dao.save("Securities.setDrawcashRecord", zxzqJiangJinFaFang);
                    //奖金转可提现证券表生成
                    dao.update("Securities.setDrawcash", zxzqJiangJinFaFang);
                    //infomation记录表生成记录
                    dao.save("Securities.setInformationRecord", zxzqJiangJinFaFang);
                    //infomation生成
                    dao.update("Securities.setInformation", zxzqJiangJinFaFang);
                    if(p==0){
                        dao.update("Securities.setHoldingRecord", zxzqJiangJinFaFang);
                    }else{
                        dao.update("Securities.setHoldingRecord1", zxzqJiangJinFaFang);
                    }




                }
            }


		}catch (Exception e){
			e.printStackTrace();
			return "";
		}

		return "";
	}

	public Map<String, Object> withdrawCashRecord(ZxzqCommonBO zxzqCommonBO)throws Exception{
		//获取当日交易净值
		ZxzqDrawCashBO bo =this.queryZxzqDrawcash(zxzqCommonBO);
		BigDecimal money =bo.getMoney().multiply(bo.getSecuritiesnum());
		//计算实际金额、手续费
		BigDecimal  a =Tools.saveTwo(new BigDecimal("0.95").multiply(money));
		BigDecimal  b =Tools.saveTwo(new BigDecimal("0.05").multiply(money));

		//回填 是判断金额是否够超出回填部分  超出部分 直接体现
		Map<String, Object> modelMap = Tools.errMessageSystem();
		List<ZxzqRecordBO> list =this.backSecuritiesMessage(zxzqCommonBO);
		if(list!=null && list.size()>0){
			ZxzqRecordBO zxzqRecordBO =list.get(0);

			zxzqCommonBO.setMemberid(zxzqRecordBO.getMemberid());
			zxzqCommonBO.setSecuritiesid(zxzqRecordBO.getSecuritiesid());


			//需求出 实际奖金 乘以百分之二十 是否大于欠款
			BigDecimal c =a.multiply(new BigDecimal("0.2"));
			BigDecimal e =b.multiply(new BigDecimal("0.2"));
			BigDecimal d =a.multiply(new BigDecimal("0.8"));
			BigDecimal f =b.multiply(new BigDecimal("0.8"));
			int  i =zxzqRecordBO.getPrice().compareTo(c);


			if(i==1 || i==0){
				//产生回填记录
                 //回填
				zxzqCommonBO.setMoney(c);
				this.backSecurities(zxzqCommonBO);

				//进入钱包
				zxzqCommonBO.setMoney(d);
				zxzqCommonBO.setPmoney(f);
				/*****************************钱包记录开始***********************************/
				dao.save("Securities.addZxzqWallet", zxzqCommonBO);
				dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);
				/***************************提现记录开始*************************************/
				//可提现记录生成
				dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
				//可提现证券清除0
				dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
				/***************************基本记录开始*************************************/
				dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
				dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
				/***************************基本记录结束*************************************/

			}else{
				//产生回填记录 是  大于需要回填金额
				//回填
				zxzqCommonBO.setMoney(zxzqRecordBO.getPrice());
				this.backSecurities(zxzqCommonBO);

				//进入钱包
				BigDecimal g =a.subtract(zxzqRecordBO.getPrice());
				zxzqCommonBO.setMoney(g);
				/*****************************钱包记录开始***********************************/
				dao.save("Securities.addZxzqWallet", zxzqCommonBO);
				dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);
				/***************************提现记录开始*************************************/
				//可提现记录生成
				dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
				//可提现证券清除0
				dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
				/***************************基本记录开始*************************************/
				dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
				dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
				/***************************基本记录结束*************************************/

			}
		}else{
			zxzqCommonBO.setMoney(a);
			zxzqCommonBO.setPmoney(b);
			/*****************************钱包记录开始***********************************/
			dao.save("Securities.addZxzqWallet", zxzqCommonBO);
			dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);

			/*****************************钱包记录结束***********************************/

			/***************************提现记录开始*************************************/
			//可提现记录生成
			dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
			//可提现证券清除0
			dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
			/*****************************提现记录结束***********************************/

			/***************************基本记录开始*************************************/
			dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
			dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
			/***************************基本记录结束*************************************/


		}
		return modelMap;
	}

    public Map<String, Object> withdrawCashRecord1(ZxzqCommonBO zxzqCommonBO)throws Exception{
        //获取当日交易净值
        ZxzqDrawCashBO bo =this.queryZxzqDrawcash(zxzqCommonBO);
        BigDecimal money =bo.getMoney().multiply(bo.getSecuritiesnum());
        //计算实际金额、手续费
        BigDecimal  a =Tools.saveTwo(new BigDecimal("0.95").multiply(money));
        BigDecimal  b =Tools.saveTwo(new BigDecimal("0.05").multiply(money));

        //回填 是判断金额是否够超出回填部分  超出部分 直接体现
        Map<String, Object> modelMap = Tools.errMessageSystem();
        List<ZxzqRecordBO> list =this.backSecuritiesMessage(zxzqCommonBO);
        if(list!=null && list.size()>0){
            ZxzqRecordBO zxzqRecordBO =list.get(0);

            zxzqCommonBO.setMemberid(zxzqRecordBO.getMemberid());
            zxzqCommonBO.setSecuritiesid(zxzqRecordBO.getSecuritiesid());


            //需求出 实际奖金 乘以百分之二十 是否大于欠款
            BigDecimal c =a.multiply(new BigDecimal("0.2"));
            BigDecimal e =b.multiply(new BigDecimal("0.2"));
            BigDecimal d =a.multiply(new BigDecimal("0.8"));
            BigDecimal f =b.multiply(new BigDecimal("0.8"));
            int  i =zxzqRecordBO.getPrice().compareTo(c);
            if(i==1 || i==0){
                //产生回填记录
                //回填
                zxzqCommonBO.setMoney(c);
                this.backSecurities(zxzqCommonBO);

                //进入钱包
                zxzqCommonBO.setMoney(d);
                zxzqCommonBO.setPmoney(f);
                /*****************************钱包记录开始***********************************/
                dao.save("Securities.addZxzqWallet", zxzqCommonBO);
                dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);
                /***************************提现记录开始*************************************/
                //可提现记录生成
                dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
                //可提现证券清除0
                dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
                /***************************基本记录开始*************************************/
                dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
                dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
                /***************************基本记录结束*************************************/

            }else{
                //产生回填记录 是  大于需要回填金额
                //回填
                zxzqCommonBO.setMoney(zxzqRecordBO.getPrice());
                this.backSecurities(zxzqCommonBO);

                //进入钱包
                BigDecimal g =a.subtract(zxzqRecordBO.getPrice());
                zxzqCommonBO.setMoney(g);
                /*****************************钱包记录开始***********************************/
                dao.save("Securities.addZxzqWallet", zxzqCommonBO);
                dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);
                /***************************提现记录开始*************************************/
                //可提现记录生成
                dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
                //可提现证券清除0
                dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
                /***************************基本记录开始*************************************/
                dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
                dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
                /***************************基本记录结束*************************************/

            }
        }else{

            //百分之80体现 百分之20 回填
            zxzqCommonBO.setMoney(a.multiply(new BigDecimal("0.8")));
            zxzqCommonBO.setPmoney(b.multiply(new BigDecimal("0.8")));
            //80体现
            /*****************************钱包记录开始***********************************/
            dao.save("Securities.addZxzqWallet", zxzqCommonBO);
            dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);

            /*****************************钱包记录结束***********************************/

            /***************************提现记录开始*************************************/
            //可提现记录生成
            dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
            //可提现证券清除0
            dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
            /*****************************提现记录结束***********************************/

            /***************************基本记录开始*************************************/
            dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
            dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
            /***************************基本记录结束*************************************/
           //20%回填
            zxzqCommonBO.setSecurities(a.multiply(new BigDecimal("0.2")));
            zxzqCommonBO.setRemark("提现时复投");
            this.reCast(zxzqCommonBO);


        }
        return modelMap;
    }



    public ZxzqPublicBo withdrawCashMessage(ZxzqCommonBO zxzqCommonBO)throws Exception{

        ZxzqPublicBo  zxzqPublicBo =new ZxzqPublicBo();
        zxzqPublicBo.setMemberid(zxzqCommonBO.getMemberid());
        //获取当日交易净值
        ZxzqDrawCashBO bo =this.queryZxzqDrawcash(zxzqCommonBO);
        zxzqPublicBo.setSecurities(bo.getSecuritiesnum());
        BigDecimal money =bo.getMoney().multiply(bo.getSecuritiesnum());
        zxzqPublicBo.setMoney(money);
        //计算实际金额、手续费
        BigDecimal  a =Tools.saveTwo(new BigDecimal("0.95").multiply(money));
        BigDecimal  b =Tools.saveTwo(new BigDecimal("0.05").multiply(money));
        zxzqPublicBo.setPoundage(b);


        //回填 是判断金额是否够超出回填部分  超出部分 直接体现
        List<ZxzqRecordBO> list =this.backSecuritiesMessage(zxzqCommonBO);
        if(list!=null && list.size()>0){
            ZxzqRecordBO zxzqRecordBO =list.get(0);

            zxzqCommonBO.setMemberid(zxzqRecordBO.getMemberid());
            zxzqCommonBO.setSecuritiesid(zxzqRecordBO.getSecuritiesid());


            //需求出 实际奖金 乘以百分之二十 是否大于欠款
            BigDecimal c =a.multiply(new BigDecimal("0.2"));
            BigDecimal e =b.multiply(new BigDecimal("0.2"));
            BigDecimal d =a.multiply(new BigDecimal("0.8"));
            BigDecimal f =b.multiply(new BigDecimal("0.8"));
            int  i =zxzqRecordBO.getPrice().compareTo(c);


            if(i==1 || i==0){
                zxzqPublicBo.setExtract(d);
                zxzqPublicBo.setBacksecurities(c);
            }else{
                //产生回填记录 是  大于需要回填金额
                //回填
                zxzqCommonBO.setMoney(zxzqRecordBO.getPrice());

                //进入钱包
                BigDecimal g =a.subtract(zxzqRecordBO.getPrice());
                zxzqPublicBo.setExtract(g);
                zxzqPublicBo.setBacksecurities(zxzqRecordBO.getPrice());
            }
        }else{
            zxzqPublicBo.setExtract(a);
            zxzqPublicBo.setBacksecurities(new BigDecimal("0"));
        }
        return zxzqPublicBo;
    }


    public void clearInforMation(ZxzqCommonBO zxzqCommonBO) throws Exception {
		dao.update("Securities.clearInforMation", zxzqCommonBO);

	}
    //查询所有人员
	public  List<ZxzqAllMember>  queryAllMember()throws Exception{
		return (List<ZxzqAllMember>)dao.findForList("Securities.queryAllMember", null);

	}
	public List<ZxzqMemberAchi> queryMemberAchi(ZxzqAllMember zxzqAllMember)throws Exception{
		return (List<ZxzqMemberAchi>)dao.findForList("Securities.queryMemberAchi", zxzqAllMember);
	}
    public List<ZxzqMemberAchi> queryMemberAchi1(ZxzqAllMember zxzqAllMember)throws Exception{
        return (List<ZxzqMemberAchi>)dao.findForList("Securities.queryMemberAchi1", zxzqAllMember);
    }

    public ZxzqMemberAchi queryMemberAchi2(ZxzqMemberAchi zxzqMemberAchi)throws Exception{
        return (ZxzqMemberAchi)dao.findForObject("Securities.queryMemberAchi2", zxzqMemberAchi);
    }




	public ZxzqSumReward queryFinshReward(ZxzqAllMember zxzqAllMember)throws Exception{
		return (ZxzqSumReward)dao.findForObject("Securities.queryFinshReward", zxzqAllMember);
	}

	public ZxzqRewardLevel queryRewardLeval(ZxzqAllMember zxzqAllMember)throws Exception{
		return (ZxzqRewardLevel)dao.findForObject("Securities.queryRewardLeval", zxzqAllMember);
	}


	public void setInfromationReward(ZxzqReward zxzqReward)throws Exception{
		 dao.update("Securities.setInfromationReward", zxzqReward);
	}

	public void setInfromationRewardRecord(ZxzqReward zxzqReward)throws Exception{
		 dao.save("Securities.setInfromationRewardRecord", zxzqReward);
	}

    //培育佣金
    public void setInfromationRewardGL(ZxzqReward zxzqReward)throws Exception{
        dao.update("Securities.setInfromationRewardGL", zxzqReward);
    }

    public void setInfromationRewardRecordGL(ZxzqReward zxzqReward)throws Exception{
        dao.save("Securities.setInfromationRewardRecordGL", zxzqReward);
    }



    public List<ZxzqMemberBo> querythreeLevel(ZxzqAllMember zxzqAllMember)throws Exception{
        List<ZxzqMemberBo> list =new ArrayList<ZxzqMemberBo>();
        ZxzqMemberBo bo =new ZxzqMemberBo();
        bo.setMember_sn(zxzqAllMember.getMember_sn());
         // 获取上一级
        ZxzqMemberBo bo1 =(ZxzqMemberBo)dao.findForObject("Securities.querythreeLevel", bo);
        if(bo1==null){}else{
            list.add(bo1);
            // 获取上二级
            ZxzqMemberBo bo2 =(ZxzqMemberBo)dao.findForObject("Securities.querythreeLevel", bo1);
            if(bo2==null){}else{
                list.add(bo2);
                ZxzqMemberBo bo3 =(ZxzqMemberBo)dao.findForObject("Securities.querythreeLevel", bo2);
                if(bo3==null){}else{
                    list.add(bo3);
                }
            }

        }
        return list;
    }

    //上三级奖励
    public void threeReward(ZxzqAllMember zxzqAllMember ,BigDecimal l)throws Exception{
        List<ZxzqMemberBo>  list =this.querythreeLevel(zxzqAllMember);
        if(list.size()>0){
            //一级奖励
            ZxzqReward  zxzqReward =new ZxzqReward();
            BigDecimal a =l.multiply(new BigDecimal("0.05"));
            zxzqReward.setRewardnum(a);
            zxzqReward.setMemberid(list.get(0).getMember_id());
            zxzqReward.setInvitelevel(5);
            zxzqReward.setRewardpoint(new BigDecimal("0.05"));
            //奖金表
            this.setInfromationRewardGL(zxzqReward);
            //奖金表记录
            this.setInfromationRewardRecordGL(zxzqReward);
            if(list.size()>1){
                //二级级奖励
                BigDecimal b =l.multiply(new BigDecimal("0.1"));
                zxzqReward.setRewardnum(b);
                zxzqReward.setMemberid(list.get(1).getMember_id());
                zxzqReward.setInvitelevel(6);
                zxzqReward.setRewardpoint(new BigDecimal("0.1"));
                //奖金表
                this.setInfromationRewardGL(zxzqReward);
                //奖金表记录
                this.setInfromationRewardRecordGL(zxzqReward);
                if(list.size()>2){
                    BigDecimal c =l.multiply(new BigDecimal("0.15"));
                    zxzqReward.setRewardnum(c);
                    zxzqReward.setMemberid(list.get(2).getMember_id());
                    zxzqReward.setInvitelevel(7);
                    zxzqReward.setRewardpoint(new BigDecimal("0.15"));
                    //奖金表
                    this.setInfromationRewardGL(zxzqReward);
                    //奖金表记录
                    this.setInfromationRewardRecordGL(zxzqReward);

                }

            }

        }

    }


    public  ZxzqRecordBO  queryFuTou(ZxzqCommonBO zxzqCommonBO)throws Exception{
        return (ZxzqRecordBO)dao.findForObject("Securities.queryFuTou", zxzqCommonBO);
    }

    public  ZxzqInformationBO  queryZxzqInformation(ZxzqCommonBO zxzqCommonBO)throws Exception{
        return (ZxzqInformationBO)dao.findForObject("Securities.queryZxzqInformation", zxzqCommonBO);
    }

    public  ZxzqGoodsBo  queryGoodsForId(ZxzqCommonBO zxzqCommonBO)throws Exception{
        return (ZxzqGoodsBo)dao.findForObject("Securities.queryGoodsForId", zxzqCommonBO);
    }




    //开始复投
    public void reCast(ZxzqCommonBO zxzqCommonBO)throws Exception{


        /********************获取投资额信息*****************************/
        //获取当日兑换率
        ZxzqDrawCashBO zxzqDrawCashBO = this.queryRate(zxzqCommonBO);
        //剩余可提现证券
        //计算出需要话费金额
        BigDecimal a =zxzqCommonBO.getSecurities();
        ZxzqRecordBO zxzqRecordBO =new ZxzqRecordBO();
        zxzqRecordBO.setPrice(a);
        zxzqRecordBO.setRealprice(a);
        zxzqRecordBO.setSecurities(zxzqCommonBO.getSecurities());
        String securitiesid = getSecuritiesid();
        zxzqRecordBO.setMemberid(zxzqCommonBO.getMemberid());
        zxzqRecordBO.setSecuritiesid(securitiesid);
        zxzqRecordBO.setRemark(zxzqCommonBO.getRemark());


        //查询原先金额
        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);
        zxzqCommonBO.setId(zxzqMemberLevel.getMember_level());


        //把值存一下 因为与下一句有冲突
        BigDecimal m =zxzqCommonBO.getSecurities();

        //一共证券 可体现+原先
        zxzqCommonBO.setSecurities(zxzqCommonBO.getSecurities().add(zxzqMemberLevel.getMember_realprice()));
        //确认杠杆
        ZxzqMemberLevel zxzqMemberLevelNew =this.setLevered(zxzqCommonBO);

        //重新调整zxzq_memaber_lever
        this.setZxzqMemberLevel(zxzqMemberLevelNew);

        /*********************20170331*******************************/

//        //查询原先等级
//        ZxzqMemberLevel zxzqMemberLevel1 =this.queryMemberLevel(zxzqCommonBO);
//
//        ZxzqRecordBO zxzqRecordBO1 = this.queryByRecordBONew(zxzqCommonBO);
//        if(zxzqRecordBO1 ==null){
//            //确认杠杆
//            zxzqMemberLevel1 =this.setLevered(zxzqCommonBO);
//            //重新调整zxzq_memaber_lever
//            this.setZxzqMemberLevel(zxzqMemberLevel1);
//        }else{
//            zxzqCommonBO.setSecurities(zxzqCommonBO.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel1 =this.setLevered(zxzqCommonBO);
//
//        }


        //查询原先等级
//        ZxzqMemberLevel zxzqMemberLevel1 =this.queryMemberLevel(zxzqCommonBO);




//        ZxzqRecordBO zxzqRecordBO1 = this.queryByRecordBONew(zxzqCommonBO);
//        if(zxzqRecordBO1 ==null){
//            //确认杠杆
//            zxzqMemberLevel1 =this.setLevered(zxzqCommonBO);
//            //重新调整zxzq_memaber_lever
//            this.setZxzqMemberLevel(zxzqMemberLevel1);
//        }else{
//            zxzqCommonBO.setSecurities(zxzqCommonBO.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel1 =this.setLevered(zxzqCommonBO);
//
//        }
          //根据id 查询对应等级
            ZxzqGoodsBo zxzqGoodsBo =this.queryGoodsForId(zxzqCommonBO);


            //以往为投资过相当于从新投资

            zxzqRecordBO.setSecuritiesnum(m.multiply(zxzqGoodsBo.getLevered()));
            zxzqRecordBO.setLevered(zxzqGoodsBo.getLevered());

            //生成记录
            dao.save("Securities.securitiesBackLevel", zxzqRecordBO);
            ZxzqHoldingBO zxzqHoldingBO =(ZxzqHoldingBO)dao.findForObject("Securities.queryHolding", zxzqCommonBO);
            if(zxzqHoldingBO ==null){
                dao.save("Securities.securitiesBackHold", zxzqRecordBO);
            }else{
                dao.save("Securities.updateHolding", zxzqRecordBO);
            }
            //zxzq_information_record
            dao.save("Securities.fuTouInfromationRecord", zxzqRecordBO);
            //zxzq_information
            dao.update("Securities.fuTouInfromation" ,zxzqRecordBO);
            //zxzq_dracw_record
            dao.save("Securities.fuTouDrawcashRecord", zxzqRecordBO);
            //zxzq_dracw
            dao.update("Securities.fuTouDrawcash" ,zxzqRecordBO);

    }

    //开始购买
    public void saveRecordBySecurities(ZxzqCommonBO zxzqCommonBO)throws Exception{


        /********************获取投资额信息*****************************/
        //获取当日兑换率
        ZxzqDrawCashBO zxzqDrawCashBO = this.queryRate(zxzqCommonBO);
        // 通过id 查询购买证券的投资额（取证券数）
        ZxzqGoodsBo  zxzqGoodsBo=  this.queryZxzqGoods(zxzqCommonBO);


        //查询原先金额
        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);
        //根据原先金额算出证券数
        BigDecimal k =zxzqMemberLevel.getMember_realprice().divide(zxzqDrawCashBO.getMoney());

        //一共证券
        zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities().add(k));
        //确认杠杆
        ZxzqMemberLevel zxzqMemberLevelNew =this.setLevered(zxzqCommonBO);
        //重新调整zxzq_memaber_lever
        this.setZxzqMemberLevel(zxzqMemberLevelNew);

        /*********************20170331*******************************/
        /*********************20170331*******************************/
        //查询原先等级
//        ZxzqMemberLevel zxzqMemberLevel1 =this.queryMemberLevel(zxzqCommonBO);
//
//        ZxzqRecordBO zxzqRecordBO = this.queryByRecordBONew(zxzqCommonBO);
//        if(zxzqRecordBO ==null){
//            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel1 =this.setLevered(zxzqCommonBO);
//            //重新调整zxzq_memaber_lever
//            this.setZxzqMemberLevel(zxzqMemberLevel1);
//        }else{
//            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel1 =this.setLevered(zxzqCommonBO);
//
//        }



        //开始下单
        /*******************下单************************************************************/
        ZxzqRecordBO bo = new ZxzqRecordBO();
        //生成当前记录订单号
        String securitiesid = getSecuritiesid();


        //生成证券报单记录
        bo.setMemberid(zxzqCommonBO.getMemberid());//用户id
        bo.setSecuritiesid(securitiesid);//订单号
        bo.setSecurities(zxzqGoodsBo.getSecurities()); //购买证券
        bo.setLevered(zxzqMemberLevelNew.getLevered()); //系数
        // 根据当日兑换比例和购买证券数计算购买所需金额
        bo.setPrice(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities()));// 购买证券所需金额
        bo.setRealprice(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities())); //  真实花费金额
        bo.setSecuritiesnum(zxzqGoodsBo.getSecurities().multiply(zxzqMemberLevelNew.getLevered()));// 真实获得证券数
        bo.setRemark("可提现证券购买");

        //生成记录
        dao.save("Securities.securitiesBackLevel", bo);
        dao.save("Securities.securitiesBackHold", bo);

        //zxzq_information_record
        dao.save("Securities.fuTouInfromationRecord7", bo);
        //zxzq_information
        dao.update("Securities.fuTouInfromation" ,bo);
        //zxzq_dracw_record
        dao.save("Securities.fuTouDrawcashRecord7", bo);
        //zxzq_dracw
        dao.update("Securities.fuTouDrawcash" ,bo);

    }


    public ZxzqRealPrice queryAchiOne(ZxzqMemberAchi zxzqMemberAchi)throws Exception{
        return (ZxzqRealPrice)dao.findForObject("Securities.queryAchiOne" ,zxzqMemberAchi);
    }


    public ZxzqRecordBO queryByRecordBONew(ZxzqCommonBO zxzqCommonBO)throws Exception{
        return (ZxzqRecordBO)dao.findForObject("Securities.queryByRecordBONew", zxzqCommonBO);
    }

    //开始购买
    public void xiaoBi(ZxzqCommonBO zxzqCommonBO)throws Exception{


        /********************获取投资额信息*****************************/
        //获取当日兑换率
        ZxzqDrawCashBO zxzqDrawCashBO = this.queryRate(zxzqCommonBO);
        // 通过id 查询购买证券的投资额（取证券数）
        ZxzqGoodsBo  zxzqGoodsBo=  this.queryZxzqGoods(zxzqCommonBO);


        //查询原先等级
        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);
        //一共证券
        zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities().add(zxzqMemberLevel.getMember_realprice()));
        //确认杠杆
        ZxzqMemberLevel zxzqMemberLevelNew =this.setLevered(zxzqCommonBO);
        //重新调整zxzq_memaber_lever
        this.setZxzqMemberLevel(zxzqMemberLevelNew);


        /*********************20170331*******************************/
//        //查询原先等级
//        ZxzqMemberLevel zxzqMemberLevel =this.queryMemberLevel(zxzqCommonBO);
//
//        ZxzqRecordBO zxzqRecordBO = this.queryByRecordBONew(zxzqCommonBO);
//        if(zxzqRecordBO ==null){
//            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel =this.setLevered(zxzqCommonBO);
//            //重新调整zxzq_memaber_lever
//            this.setZxzqMemberLevel(zxzqMemberLevel);
//        }else{
//            zxzqCommonBO.setSecurities(zxzqGoodsBo.getSecurities());
//            //确认杠杆
//            zxzqMemberLevel =this.setLevered(zxzqCommonBO);
//
//        }

        //开始下单
        /*******************下单************************************************************/
        ZxzqRecordBO bo = new ZxzqRecordBO();
        //生成当前记录订单号
        String securitiesid = getSecuritiesid();


        //生成证券报单记录
        bo.setMemberid(zxzqCommonBO.getMemberid());//用户id
        bo.setSecuritiesid(securitiesid);//订单号
        bo.setSecurities(zxzqGoodsBo.getSecurities()); //购买证券
        bo.setLevered(zxzqMemberLevelNew.getLevered()); //系数
        // 根据当日兑换比例和购买证券数计算购买所需金额
        bo.setPrice(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities()));// 购买证券所需金额
        bo.setRealprice(zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities())); //  真实花费金额
        bo.setSecuritiesnum(zxzqGoodsBo.getSecurities().multiply(zxzqMemberLevelNew.getLevered()));// 真实获得证券数
        bo.setRemark("孝康券购买");

        //生成记录
        dao.save("Securities.securitiesBackLevel", bo);

        ZxzqHoldingBO zxzqHoldingBO =(ZxzqHoldingBO)dao.findForObject("Securities.queryHolding", zxzqCommonBO);
        if(zxzqHoldingBO ==null){
            dao.save("Securities.securitiesBackHold", bo);
        }else{
            dao.save("Securities.updateHolding", bo);
        }
        dao.save("Securities.insertZxzqXbLog",bo);
        dao.update("Securities.updateZxzqXbWallet", bo);






//        //zxzq_information_record
//        dao.save("Securities.fuTouInfromationRecord7", bo);
//        //zxzq_information
//        dao.update("Securities.fuTouInfromation" ,bo);
//        //zxzq_dracw_record
//        dao.save("Securities.fuTouDrawcashRecord7", bo);
//        //zxzq_dracw
//        dao.update("Securities.fuTouDrawcash" ,bo);


    }
//判断网络上级和邀请人是否在一条线上
    public int queryIsConnect(DoRegisterVo doRegisterVo)throws Exception{
        //1不在一条线0是可用2是这个用户不属于证券用户
        UserInfoBO userInfoBO=new UserInfoBO();
        UserInfoBO userInfoBo1=new UserInfoBO();
        userInfoBO= (UserInfoBO) dao.findForObject("Securities.queryNetWorkidByInviter",doRegisterVo);
        userInfoBo1= (UserInfoBO) dao.findForObject("Securities.queryNetWorkidByNetwork_id",doRegisterVo);
        if(userInfoBO==null){
            return 2;
        }
        if(userInfoBo1==null){
            return 3;
        }
        //邀请人网络位置
        String t1=userInfoBO.getNetwork_team();
        //网络上级的网络位置
        String t2=userInfoBo1.getNetwork_team();
        if(t1==null||t2==null){
            return 1;
        }
        if(t2.length()<t1.length()){
            return 1;
        }
        if(t1.equals(t2.substring(0,t1.length()))){
            return 0;
        }else{
            return 1;
        }

    }

    /**
     * 通过networkid判断网络上级是否有下过单
     * @param doRegisterVo
     * @return
     * @throws Exception
     */
    public boolean queryNetworkIdIsable(DoRegisterVo doRegisterVo) throws Exception {
        //查出network对应的id
        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Securities.queryMemberidByMembersn",doRegisterVo);
        List<ZxzqRecordBO> list= (List<ZxzqRecordBO>) dao.findForList("Securities.queryOrderSecuritiesnum",userInfoBO);
        if(list==null||list.size()==0){
            return false;
        }else {
            return true;
        }
    }

    public int insertDataByList(List<String> list) throws Exception {
        int i=0;
        DoRegisterVo doRegisterVo=new DoRegisterVo();
        UserInfoBO userInfoBO=new UserInfoBO();
        ZxzqRecordBO zxzqRecordBO =new ZxzqRecordBO();
        for (String s : list) {
            doRegisterVo.setNetwork_id(s);
            userInfoBO= (UserInfoBO) dao.findForObject("Securities.queryMemberidByMembersn", doRegisterVo);
            if(userInfoBO==null){
                continue;
            }
            zxzqRecordBO.setMemberid(userInfoBO.getMember_id());
            zxzqRecordBO.setSecuritiesid(this.getSecuritiesid());
            dao.save("Securities.insertIntoRecord",zxzqRecordBO);
            dao.save("Securities.insertIntoHolding",zxzqRecordBO);
            i=i+1;
        }

        return i;
    }
    public void updateNoticeStatus(ZxzqMemberBo zxzqMemberBo) throws Exception {
        dao.update("Securities.modifyNoticeStatus",zxzqMemberBo);
    }

    public void  deleteZxzqReward()throws Exception{
        dao.delete("Securities.deleteZxzqReward" ,null);
    }
    public void  automatic()throws Exception{
        List<ZxzqInformationBO>  list=(List<ZxzqInformationBO>)dao.findForList("Securities.queryAutomatic" ,null);
        if(list!=null && list.size()>0){
            for(int i=0 ; i<list.size();i++){
                //取出需要自动复投的人员
                ZxzqInformationBO zxzqInformationBO =list.get(i);
                //判断该人员是否有欠款 有欠款不可以自动复投
                ZxzqRecordBO  zxzqRecordBO =(ZxzqRecordBO)dao.findForObject("Securities.queryAutomatic1", zxzqInformationBO);
                BigDecimal a =zxzqRecordBO.getPrice();
                BigDecimal b =zxzqRecordBO.getRealprice();
                    int c=a.compareTo(b);
                    //无欠款可复投
                    if(c==0 || c== -1){
                        ZxzqCommonBO zxzqCommonBO =new ZxzqCommonBO();
                        zxzqCommonBO.setMemberid(zxzqInformationBO.getMemberid());
                        zxzqCommonBO.setSecurities(zxzqInformationBO.getSecurities());
                        zxzqCommonBO.setRemark("自动复投");
                        this.reCast(zxzqCommonBO);
                    }
            }
        }

    }
    public void  confirmAutomatic(ZxzqCommonBO zxzqCommonBO)throws Exception{
            dao.update("Securities.confirmAutomatic", zxzqCommonBO);
    }
  public List<Integer> updateid() throws Exception {

        this.doUpdateSecuritiesId();
      return this.doUpdateNetworkId();
  }
//更新network_id
    public List<Integer> doUpdateNetworkId() throws Exception {
        List<ZxzqMemberBo> oldList=this.queryOldMember();
        List<ZxzqMemberBo> newList=this.queryNewMember();
        List<Integer> list=new ArrayList<Integer>();

        int n=0;
        int isbreak=0;
        for (int i=1;i<newList.size();i++ ){
            for (int j=0;j<oldList.size();j++ ){
                if(oldList.get(i).getNetwork_id().equals(oldList.get(j).getMember_sn())){
                    newList.get(i).setNetwork_id(newList.get(j).getMember_sn());
                    //满足条件后跳出循环
                    isbreak=1;
                    break;
                }
            }
            if(isbreak==0){
                list.add(newList.get(i).getMember_id());
            }
            //更新这个人的network_id
            this.updateMemberNetwork_Id(newList.get(i));
        }
        return list;
    }

    //更新SecuritiesId
    public void doUpdateSecuritiesId() throws Exception {
        List<ZxzqMemberBo> oldList=this.queryOldMember();
        List<ZxzqMemberBo> newList=this.queryNewMember();
//        int n=0;
////        int isbreak=0;
        for (int i=1;i<newList.size();i++ ){
            newList.get(i).setSecurities_id(newList.get(0).getSecurities_id());
            for (int j=0;j<oldList.size();j++ ){
                if(oldList.get(i).getSecurities_id().equals(oldList.get(j).getMember_sn())){
                    newList.get(i).setSecurities_id(newList.get(j).getMember_sn());
                    //满足条件后跳出循环
                    break;
                }
            }
            //更新这个人的network_id
            this.updateMemberSecuritiesId(newList.get(i));
        }

    }
public List<ZxzqMemberBo> queryOldMember() throws Exception {
    List<ZxzqMemberBo> list= (List<ZxzqMemberBo>) dao.findForList("Securities.queryOldMember",null);
    return  list;
}

    public List<ZxzqMemberBo> queryNewMember() throws Exception {
        List<ZxzqMemberBo> list= (List<ZxzqMemberBo>) dao.findForList("Securities.queryNewMember",null);
        return  list;
    }

    public void updateMemberNetwork_Id(ZxzqMemberBo zxzqMemberBo) throws Exception {
        dao.update("Securities.updateMemberNetWorkId",zxzqMemberBo);
    }
    public void updateMemberSecuritiesId(ZxzqMemberBo zxzqMemberBo) throws Exception {
        dao.update("Securities.updateMemberSecuritiesId",zxzqMemberBo);
    }

//插入新的record
    public void insertNewRecord() throws Exception {
        List<ZxzqMemberBo> oldList=this.queryOldMember();
        List<ZxzqMemberBo> newList=this.queryNewMember();

//        int n=0;
////        int isbreak=0;
        for (int i=0;i<newList.size();i++ ){
            List<ZxzqRecordBO> list=this.queryRecordById(oldList.get(i));
            for (int j=0;j<list.size();j++ ){
                list.get(j).setMemberid(newList.get(i).getMember_id());
                list.get(j).setSecuritiesid(list.get(j).getSecuritiesid() + "0");
                BigDecimal bigDecimal=new BigDecimal("1");
                list.get(j).setRealprice(list.get(j).getRealprice().subtract(bigDecimal));
                this.insertByRecordBo(list.get(j));
                }

            //更新这个人的network_id
//            this.updateMemberSecuritiesId(newList.get(i));
        }

    }
//根据ID查record
    public List<ZxzqRecordBO> queryRecordById(ZxzqMemberBo zxzqMemberBo) throws Exception {
        List<ZxzqRecordBO> list= (List<ZxzqRecordBO>) dao.findForList("Securities.queryRecordByOldID",zxzqMemberBo);
        return list;
    }


    public void insertByRecordBo(ZxzqRecordBO zxzqRecordBO) throws Exception {
            dao.save("Securities.insertNewRecord",zxzqRecordBO);
    }
    public void deleteRecordByMember(ZxzqMemberBo zxzqMemberBo) throws Exception {
        dao.delete("Securities.deleteAllRecord",zxzqMemberBo);
    }
//批量删除record
    public void deleteNewRecord() throws Exception {
        List<ZxzqMemberBo> newList=this.queryNewMember();

        for (int i=0;i<newList.size();i++ ){
    this.deleteRecordByMember(newList.get(i));
        }

    }


    //查询holding
    public ZxzqHoldingBO queryOldHolding(ZxzqMemberBo zxzqMemberBo) throws Exception {
        ZxzqHoldingBO zxzqHoldingBO= (ZxzqHoldingBO) dao.findForObject("Securities.queryHoldingByOldID",zxzqMemberBo);
        return zxzqHoldingBO;
    }

    //插入新的holding
    public void insertNewHolding() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {
            ZxzqHoldingBO zxzqHoldingBO = this.queryOldHolding(oldList.get(i));
            if(zxzqHoldingBO!=null) {
                zxzqHoldingBO.setMemberid(newList.get(i).getMember_id());
                BigDecimal a = new BigDecimal("10");
                zxzqHoldingBO.setSecuritiesid(zxzqHoldingBO.getSecuritiesid().multiply(a));
                this.inserNewHolding(zxzqHoldingBO);
            }

        }
    }

    public void inserNewHolding(ZxzqHoldingBO zxzqHoldingBO) throws Exception {
      dao.save("Securities.saveNewHolding",zxzqHoldingBO);
    }

    public ZxzqMemberLevel queryMemberLevel(ZxzqMemberBo zxzqMemberBo) throws Exception {
        ZxzqMemberLevel zxzqMemberLevel= (ZxzqMemberLevel) dao.findForObject("Securities.queryOldMemberLevel",zxzqMemberBo);
        return zxzqMemberLevel;
    }

    public void insertMemberLevel(ZxzqMemberLevel zxzqMemberLevel) throws Exception {
        dao.save("Securities.saveNewMemberLevel",zxzqMemberLevel);
    }

    public void insertNewMemberLevel() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();

        for (int i = 0; i < newList.size(); i++) {
            ZxzqMemberLevel zxzqMemberLevel = this.queryMemberLevel(oldList.get(i));
            if(zxzqMemberLevel!=null) {
                zxzqMemberLevel.setMember_id(newList.get(i).getMember_id());
               zxzqMemberLevel.setMember_sn(newList.get(i).getMember_sn());
                this.insertMemberLevel(zxzqMemberLevel);
            }

        }
    }
//??????
    public void insertNewReward() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();

        for (int i = 0; i < newList.size(); i++) {
           List <ZxzqReward> list = this.queryOldReward(oldList.get(i));
            if(list!=null || list.size()>0) {
               for(int j=0;j<list.size();j++){
                   list.get(j).setSecuritiesid(list.get(j).getSecuritiesid()+"0");
                   list.get(j).setMemberid(newList.get(i).getMember_id());
                   this.insertRewardByNewReward(list.get(j));
               }
            }

        }
    }

    public void insertRewardByNewReward(ZxzqReward zxzqReward) throws Exception {
        dao.save("Securities.saveNewRewardByOld", zxzqReward);
    }

    public List<ZxzqReward> queryOldReward(ZxzqMemberBo zxzqMemberBo) throws Exception {
       List<ZxzqReward> list = (List<ZxzqReward>) dao.findForList("Securities.queryOldRewardByMember",zxzqMemberBo);
        return  list;
    }

    public void insertDrawcashByNew(ZxzqDrawCashBO zxzqDrawCashBO) throws Exception {
        dao.save("Securities.saveNewDrawCashRecordByOld",zxzqDrawCashBO);
    }

    public List<ZxzqDrawCashBO> queryOldDrawcash(ZxzqMemberBo zxzqMemberBo) throws Exception {
        List<ZxzqDrawCashBO> list = (List<ZxzqDrawCashBO>) dao.findForList("Securities.queryOldDrawCashRecordByMember",zxzqMemberBo);
        return  list;
    }

    public void insertNewDrawcash() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {
           // 获取老用户的信息
            List <ZxzqDrawCashBO> list = this.queryOldDrawcash(oldList.get(i));
            if(list!=null && list.size()>0) {
                for(int j=0;j<list.size();j++){
                    list.get(j).setMemberid(newList.get(i).getMember_id());
                    if(list.get(j).getSecuritiesid()!=null){
                        list.get(j).setSecuritiesid(list.get(j).getSecuritiesid()+"0");
                    }

                    this.insertDrawcashByNew(list.get(j));
                }
            }

        }
    }

public void insertNewInformationByOld(ZxzqInformationBO zxzqInformationBO) throws Exception {
    dao.save("Securities.saveNewInformationByOld",zxzqInformationBO);
}
    public ZxzqInformationBO queryOldInfomation(ZxzqMemberBo zxzqMemberBo) throws Exception {
        ZxzqInformationBO zxzqInformationBO= (ZxzqInformationBO) dao.findForObject("Securities.queryOldInformationByMember",zxzqMemberBo);
        return zxzqInformationBO;
    }

    public void insertNewInformation() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {
            // 获取老用户的信息
            ZxzqInformationBO zxzqInformationBO = this.queryOldInfomation(oldList.get(i));
            if(zxzqInformationBO !=null ) {
              zxzqInformationBO.setMemberid(newList.get(i).getMember_id());
                    this.insertNewInformationByOld(zxzqInformationBO);
                }
            }
        }

    public void insertNewInformationRecordByOld(ZxzqInformationBO zxzqInformationBO) throws Exception {
        dao.save("Securities.saveNewInformationRecordByOld",zxzqInformationBO);
    }
    public List<ZxzqInformationBO> queryOldInfomationRecord(ZxzqMemberBo zxzqMemberBo) throws Exception {
        List<ZxzqInformationBO> list= (List<ZxzqInformationBO>) dao.findForList("Securities.queryOldInformationRecordByMember", zxzqMemberBo);
        return list;
    }

    public void insertNewInformationRecord() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {
            // 获取老用户的信息
            List<ZxzqInformationBO> list = this.queryOldInfomationRecord(oldList.get(i));
            if(list !=null && list.size()>0) {
                for(int j=0; j<list.size();j++) {
                    list.get(j).setMemberid(newList.get(i).getMember_id());
                    if(list.get(j).getSecuritiesid()!=null) {
                        list.get(j).setSecuritiesid(list.get(j).getSecuritiesid() + "0");
                    }
                    this.insertNewInformationRecordByOld(list.get(j));
                }
            }
        }
    }

    public void insertNewWalletByOld(ZxzqWalletBo zxzqWalletBo) throws Exception {
        dao.save("Securities.saveNewWalletByOld",zxzqWalletBo);
    }
    public ZxzqWalletBo queryOldWallet(ZxzqMemberBo zxzqMemberBo) throws Exception {
        ZxzqWalletBo zxzqWalletBo= (ZxzqWalletBo) dao.findForObject("Securities.queryOldWalletByMember",zxzqMemberBo);
        return zxzqWalletBo;
    }

    public void insertWallet() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {
            // 获取老用户的信息
            ZxzqWalletBo zxzqWalletBo = this.queryOldWallet(oldList.get(i));
            if(zxzqWalletBo !=null ) {

                    zxzqWalletBo.setMemberid(newList.get(i).getMember_id());

                    this.insertNewWalletByOld(zxzqWalletBo);
                }
            }
        }

    public void insertNewWalletRecordByOld(ZxzqWalletBo zxzqWalletBo) throws Exception {
        dao.save("Securities.saveNewWalletRecordByOld",zxzqWalletBo);
    }
    public List<ZxzqWalletBo> queryOldWalletRecord(ZxzqMemberBo zxzqMemberBo) throws Exception {
        List<ZxzqWalletBo> list= (List<ZxzqWalletBo>) dao.findForList("Securities.queryOldWalletRecordByMember", zxzqMemberBo);
        return list;
    }

    public void insertWalletRecord() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {
            // 获取老用户的信息
            List<ZxzqWalletBo> list = this.queryOldWalletRecord(oldList.get(i));
            if(list !=null && list.size()>0) {
                for(int j=0; j<list.size();j++) {
                    list.get(j).setMemberid(newList.get(i).getMember_id());
                    list.get(j).setPaysn(list.get(j).getPaysn()+"0");
                    this.insertNewWalletByOld(list.get(j));
                }
            }
        }
    }

    public void insertNewXBByOld(ZxzqXbWalletBo zxzqXbWalletBo) throws Exception {
        dao.save("Securities.saveNewXBByOld",zxzqXbWalletBo);
    }
    public ZxzqXbWalletBo queryOldXB(ZxzqMemberBo zxzqMemberBo) throws Exception {
        ZxzqXbWalletBo zxzqXbWalletBo  = (ZxzqXbWalletBo) dao.findForList("Securities.queryOldXBByMember", zxzqMemberBo);
        return zxzqXbWalletBo;
    }

    public void insertXB() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {

            ZxzqXbWalletBo zxzqXbWalletBo = this.queryOldXB(oldList.get(i));
            if(zxzqXbWalletBo !=null ) {
                zxzqXbWalletBo.setMember_id(newList.get(i).getMember_id());
                    this.insertNewXBByOld(zxzqXbWalletBo);

            }
        }
    }

    public void insertNewDrawCashRecord(ZxzqDrawCashBO zxzqDrawCashBO) throws Exception {
        dao.save("Securities.saveNewDrawCashByOld",zxzqDrawCashBO);
    }
    public ZxzqDrawCashBO queryOldDrawCashRecord(ZxzqMemberBo zxzqMemberBo) throws Exception {
        ZxzqDrawCashBO  zxzqDrawCashBO= (ZxzqDrawCashBO) dao.findForList("Securities.queryOldDrawCashByMember", zxzqMemberBo);
        return zxzqDrawCashBO;
    }

    public void insertNewDrawCashRecord() throws Exception {
        List<ZxzqMemberBo> oldList = this.queryOldMember();
        List<ZxzqMemberBo> newList = this.queryNewMember();
        for (int i = 0; i < newList.size(); i++) {

            ZxzqXbWalletBo zxzqXbWalletBo = this.queryOldXB(oldList.get(i));
            if(zxzqXbWalletBo !=null ) {
                zxzqXbWalletBo.setMember_id(newList.get(i).getMember_id());
                this.insertNewXBByOld(zxzqXbWalletBo);

            }
        }
    }

    public Map<String, Object> extractRecord(ZxzqExtract zxzqExtract)throws Exception{
        //获取当日交易净值
//        ZxzqDrawCashBO bo =this.queryZxzqDrawcash(zxzqCommonBO);
//        BigDecimal money =bo.getMoney().multiply(bo.getSecuritiesnum());
        //计算实际金额、手续费
//        BigDecimal  a =Tools.saveTwo(new BigDecimal("0.95").multiply(zxzqExtract.getCash()));
//        BigDecimal  b =Tools.saveTwo(new BigDecimal("0.05").multiply(zxzqExtract.getCash()));
//
//        //回填 是判断金额是否够超出回填部分  超出部分 直接体现
        Map<String, Object> modelMap = Tools.errMessageSystem();
//        ZxzqCommonBO zxzqCommonBO=new ZxzqCommonBO();
//        zxzqCommonBO.setMemberid(zxzqExtract.getMemberid());
//        List<ZxzqRecordBO> list =this.backSecuritiesMessage(zxzqCommonBO);
//        if(list!=null && list.size()>0){
//            ZxzqRecordBO zxzqRecordBO =list.get(0);
//
//            zxzqCommonBO.setMemberid(zxzqRecordBO.getMemberid());
//            zxzqCommonBO.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
//
//
//            //需求出 实际奖金 乘以百分之二十 是否大于欠款
//            BigDecimal c =a.multiply(new BigDecimal("0.2"));
//            BigDecimal e =b.multiply(new BigDecimal("0.2"));
//            BigDecimal d =a.multiply(new BigDecimal("0.8"));
//            BigDecimal f =b.multiply(new BigDecimal("0.8"));
//            int  i =zxzqRecordBO.getPrice().compareTo(c);
//
//            if(i==1 || i==0){
//                //产生回填记录
//                //回填
//                zxzqCommonBO.setMoney(c);
//                this.backSecurities(zxzqCommonBO);
//
//                //进入钱包
//                zxzqCommonBO.setMoney(d);
//                zxzqCommonBO.setPmoney(f);
//                /*****************************钱包记录开始***********************************/
//                dao.save("Securities.addZxzqWallet", zxzqCommonBO);
//                dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);
//                /***************************提现记录开始*************************************/
//                //可提现记录生成
//                dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
//                //可提现证券清除0
//                dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
//                /***************************基本记录开始*************************************/
//                dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
//                dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
//                /***************************基本记录结束*************************************/
//
//            }else{
//                //产生回填记录 是  大于需要回填金额
//                //回填
//                zxzqCommonBO.setMoney(zxzqRecordBO.getPrice());
//                this.backSecurities(zxzqCommonBO);
//
//                //进入钱包
//                BigDecimal g =a.subtract(zxzqRecordBO.getPrice());
//                zxzqCommonBO.setMoney(g);
//                /*****************************钱包记录开始***********************************/
//                dao.save("Securities.addZxzqWallet", zxzqCommonBO);
//                dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);
//                /***************************提现记录开始*************************************/
//                //可提现记录生成
//                dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
//                //可提现证券清除0
//                dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
//                /***************************基本记录开始*************************************/
//                dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
//                dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
//                /***************************基本记录结束*************************************/
//
//            }
//        }else{
//            zxzqCommonBO.setMoney(a);
//            zxzqCommonBO.setPmoney(b);
//            /*****************************钱包记录开始***********************************/
//            dao.save("Securities.addZxzqWallet", zxzqCommonBO);
//            dao.save("Securities.addZxzqWalletRecord" ,zxzqCommonBO);
//
//            /*****************************钱包记录结束***********************************/
//
//            /***************************提现记录开始*************************************/
//            //可提现记录生成
//            dao.save("Securities.withdrawCashRecord", zxzqCommonBO);
//            //可提现证券清除0
//            dao.save("Securities.clearWithdrawCash" ,zxzqCommonBO);
//            /*****************************提现记录结束***********************************/
//
//            /***************************基本记录开始*************************************/
//            dao.save("Securities.clearInformationRecordSecurities" ,zxzqCommonBO);
//            dao.save("Securities.clearInformationSecurities" ,zxzqCommonBO);
//            /***************************基本记录结束*************************************/
//
//
//        }
        try {
            dao.update("Securities.deductDrawcash",zxzqExtract);
            dao.update("Securities.deductInformation",zxzqExtract);
            dao.save("Securities.insertExtract",zxzqExtract);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }

    public void confirmExtract(ZxzqExtract zxzqExtract) throws Exception {
        ZxzqRecordVo zxzqRecordVo=new ZxzqRecordVo();
        zxzqRecordVo.setMemberid(zxzqExtract.getMemberid());
        String address=this.queryAddress(zxzqRecordVo);
        zxzqExtract.setModifyAddress(address);
        dao.update("Securities.updateExtract",zxzqExtract);
    }

    public Map<String, Object> cancelExtract(ZxzqExtract zxzqExtract) throws Exception {
        Map<String, Object> map=Tools.errMessageSystem();
        zxzqExtract = (ZxzqExtract) dao.findForObject("Securities.queryExtractById",zxzqExtract);
        if(zxzqExtract==null){
            map.put("code","1");
            map.put("message","用户ID或订单编号有误");
            map.put("data",null);
            return map;
        }
        if(zxzqExtract.getStatus()==1){
            map.put("code","1");
            map.put("message","撤销失败，您的申请已处理");
            map.put("data","null");
            return map;
        }
        dao.update("Securities.deleteExtract",zxzqExtract);
        dao.update("Securities.cancelDrawcash",zxzqExtract);
        dao.update("Securities.cancelInformation",zxzqExtract);
        map.put("code","0");
        map.put("message","撤销成功");
        map.put("data","null");
        return map;
    }

    public void deleteExtract(ZxzqExtract zxzqExtract) throws Exception {
        dao.delete("Securities.deleteExtractByOddnumbers",zxzqExtract);
    }

    public ZxzqMemberBo queryNetwork_teamByMember_name(ZxzqMemberBo zxzqMemberBo) throws Exception {
        zxzqMemberBo = (ZxzqMemberBo) dao.findForObject("Securities.queryNetwork_team", zxzqMemberBo);
        return zxzqMemberBo;
    }

    public BigDecimal queryYeJi(ZxzqMemberBo zxzqMemberBo) throws Exception {
        BigDecimal bigDecimal= (BigDecimal) dao.findForObject("Securities.queryYejiByNetwork_team",zxzqMemberBo);
        return bigDecimal;
    }

    public BigDecimal queryAllYeJi(ZxzqMemberBo zxzqMemberBo) throws Exception {
        BigDecimal bigDecimal= (BigDecimal) dao.findForObject("Securities.queryAllYeji",zxzqMemberBo);
        return bigDecimal;
    }

    /**
     * 查询用户是否是4月1号以后注册并且等级是2的会员
     * @param zxzqMemberBo
     * @return
     * @throws Exception
     */
    public ZxzqMemberBo queryNewMemberById(ZxzqMemberBo zxzqMemberBo) throws Exception {
        zxzqMemberBo= (ZxzqMemberBo) dao.findForObject("Securities.queryMemberIsNew",zxzqMemberBo);
        return zxzqMemberBo;
    }


    public String queryAddress(ZxzqRecordVo zxzqRecordVo) throws Exception {

        String address= (String) dao.findForObject("Securities.queryWalletAddress",zxzqRecordVo);
        return address;
    }


    public void saveYj(ZxzqYjBo zxzqYjBo)throws Exception{
        dao.save("Securities.zxzqYjBo",zxzqYjBo);
    }


}