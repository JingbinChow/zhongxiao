package com.fh.controller.community.securities;

import com.fh.entity.bo.*;
import com.fh.entity.vo.*;
import com.fh.entity.zxzq.*;
import com.fh.service.community.securities.SecuritiesService;
import com.fh.util.MD5;
import com.fh.util.RandomCode;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Controller
@RequestMapping(value = "/securities")

public class SecuritiesController {
    @Resource(name = "secutitiesService")
    private SecuritiesService secutitiesService;
    private double changNum= 0.07;//t_berd_f降了多少


    //孝币购买
    @RequestMapping(value = "saveRecordBySecurities")
    @ResponseBody

    public Map<String, Object> xiaoBi(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {

            String str= Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
            String str1= str.substring(11,13);
            if(str1.equals("00")||str1.equals("01")){
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "平台结算中，预计开放时间为凌晨 2 点，请您稍后访问");
                return modelMap;
            }
            //判断是否有孝币
            ZxzqXbWalletBo zxzqXbWalletBo = secutitiesService.queryZxzqXbWallet(zxzqCommonBO);
            if (zxzqXbWalletBo == null) {
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "您的白拿积分账户不存在，请及时联系客服");
                return modelMap;
            } else {
                // 通过id 查询购买证券的投资额（取证券数）
                ZxzqGoodsBo zxzqGoodsBo = secutitiesService.queryZxzqGoods(zxzqCommonBO);
                //获取当日兑换率
                ZxzqDrawCashBO zxzqDrawCashBO = secutitiesService.queryRate(zxzqCommonBO);
                //购买该档位所需要金额
                BigDecimal i = zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities());// 购买证券所需金额
                //持有所证券换算成接
                BigDecimal k = zxzqXbWalletBo.getXb().divide(zxzqDrawCashBO.getMoney());
                int j = k.compareTo(i);
                if (j == -1) {
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    modelMap.put("message", "您持有的白拿积分余额不足");
                    return modelMap;
                } else {

                    //判断是否有需要回填的的订单
                    ZxzqRecordBO zxzqRecordBO = secutitiesService.queryFuTou(zxzqCommonBO);
                    if (zxzqRecordBO == null) {

                    } else {
                        int g = zxzqRecordBO.getPrice().compareTo(BigDecimal.ZERO);
                        if (g != 0) {
                            modelMap.put("code", "1");
                            modelMap.put("data", null);
                            modelMap.put("message", "您的账户尚有白拿欠款，请回填欠款后再进行购买");
                            return modelMap;
                        }
                    }
                    //开始购买
                    secutitiesService.xiaoBi(zxzqCommonBO);
                    modelMap.put("code", "0");
                    modelMap.put("data", null);
                    modelMap.put("message", "购买成功");
                    return modelMap;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }


    /**
     * 登陆
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody ZxzqLoginVo zxzqLoginVo) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            zxzqLoginVo.setPassWord(MD5.md5(zxzqLoginVo.getPassWord()));
            ZxzqMemberBo bo = secutitiesService.login(zxzqLoginVo);
            if (bo == null) {
                modelMap.put("message", "用户名或密码错误");
                modelMap.put("code", "1");
                modelMap.put("data", null);
            } else {
                //更改公告状态
//                secutitiesService.updateNoticeStatus(bo);
                secutitiesService.updateTime(zxzqLoginVo);
                modelMap.put("message", "登陆成功");
                modelMap.put("code", "0");
                modelMap.put("data", bo);

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
     * 证券订单生成
     * memberid 和 id 对应zxzq_goods 里id
     */
    @RequestMapping(value = "saveRecord")
    @ResponseBody
    public Map<String, Object> saveRecord(@RequestBody ZxzqRecordVo zxzqRecordVo) throws Exception {
        Map<String, Object> map = Tools.errMessageSystem();
        try {
            String str= Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
            String str1= str.substring(11,13);
            if(str1.equals("00")||str1.equals("01")){
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "平台结算中，预计开放时间为凌晨 2 点，请您稍后访问");
                return map;
            }

            ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
            zxzqCommonBO.setId(zxzqRecordVo.getId());
            zxzqCommonBO.setMemberid(zxzqRecordVo.getMemberid());

            // 购买证券判断是否有欠款
            ZxzqRecordBO zxzqRecordBO = secutitiesService.checkRecordRealpriceNew(zxzqCommonBO);
            if (zxzqRecordBO == null) {
                //未下过订单
            } else {
                //下过订单
                int i = zxzqRecordBO.getPrice().compareTo(BigDecimal.ZERO);
                if (i == 1) {
                    map.put("code", "1");
                    map.put("data", null);
                    map.put("message", "您的账户存在未结清的购买记录，请结清该笔订单后再进行购买");
                    return map;
                }
            }

            //获取当日兑换率
            ZxzqDrawCashBO zxzqDrawCashBO = secutitiesService.queryRate(zxzqCommonBO);
            // 通过id 查询购买证券的投资额
            ZxzqGoodsBo zxzqGoodsBo = secutitiesService.queryZxzqGoods(zxzqCommonBO);

            //判断钱包余额是否充值
            ZxzqWalletBo zxzqWalletBo = secutitiesService.queryZxzqWallet(zxzqCommonBO);
            if (zxzqWalletBo == null) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "您的钱包账户不存在，请及时联系客服");
                return map;
            } else {
                //判断钱包金额是否足够购买证券
                int j = zxzqWalletBo.getMoney().compareTo((zxzqGoodsBo.getSecurities().multiply(zxzqDrawCashBO.getMoney())));
                //当钱包余额大于等于时可以提现
                if (j == -1) {
                    map.put("code", "1");
                    map.put("data", null);
                    map.put("message", "您的钱包余额不足，请及时充值");
                    return map;
                } else {
                    //购买证券开始
                    secutitiesService.saveRecord(zxzqCommonBO);
                    map.put("code", "0");
                    map.put("data", null);
                    map.put("message", "购买成功");
                    return map;

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            map = Tools.errMessageSystem();
            return map;
        }
    }


    //可提现证券购买
//    @RequestMapping(value="saveRecordBySecurities")
//    @ResponseBody

    public Map<String, Object> saveRecordBySecurities(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {

            //判断是否有可提现证券
            ZxzqInformationBO zxzqInformationBO = secutitiesService.queryZxzqInformation(zxzqCommonBO);
            // 通过id 查询购买证券的投资额（取证券数）
            ZxzqGoodsBo zxzqGoodsBo = secutitiesService.queryZxzqGoods(zxzqCommonBO);
            //获取当日兑换率
            ZxzqDrawCashBO zxzqDrawCashBO = secutitiesService.queryRate(zxzqCommonBO);


            //购买该档位所需要金额
            BigDecimal i = zxzqDrawCashBO.getMoney().multiply(zxzqGoodsBo.getSecurities());// 购买证券所需金额
            //持有所证券换算成接
            BigDecimal k = zxzqInformationBO.getSecurities().divide(zxzqDrawCashBO.getMoney());
            int j = k.compareTo(i);

            if (j == -1) {
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "您的可提现证券数额不足");
                return modelMap;
            } else {

                //判断是否有需要回填的的订单
                ZxzqRecordBO zxzqRecordBO = secutitiesService.queryFuTou(zxzqCommonBO);
                if (zxzqRecordBO == null) {

                } else {
                    int g = zxzqRecordBO.getPrice().compareTo(BigDecimal.ZERO);
                    if (g != 0) {
                        modelMap.put("code", "1");
                        modelMap.put("data", null);
                        modelMap.put("message", "请先回填完欠款,在进行购买");
                        return modelMap;
                    }
                }
                //开始购买
                secutitiesService.saveRecordBySecurities(zxzqCommonBO);
                modelMap.put("code", "0");
                modelMap.put("data", null);
                modelMap.put("message", "购买成功");
                return modelMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }


    /**
     * 复刻证券注册
     */
    @RequestMapping(value = "doRegister")
    @ResponseBody
    public Map<String, Object> doRegister(@RequestBody DoRegisterVo doRegisterVo) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        UserInfoBO userInfoBO = null;
        //判断用户名是否被占用
        if (Tools.isEmpty(doRegisterVo.getUserName())) {
            modelMap.put("message", "用户名不允许为空");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        if (Tools.isEmpty(doRegisterVo.getPassWord())) {
            modelMap.put("message", "密码不允许为空");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        if (Tools.isEmpty(doRegisterVo.getInviter())) {
            modelMap.put("message", "邀请人不允许为空");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        if (Tools.isEmpty(doRegisterVo.getMobile())) {
            modelMap.put("message", "手机号不允许为空");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        doRegisterVo.setAddress(Tools.filterNull(doRegisterVo.getAddress()));
        doRegisterVo.setCardType(Tools.filterNull(doRegisterVo.getCardType()));
        if (Tools.checkBankCard(doRegisterVo.getBankcard())) {
            doRegisterVo.setBankcard(Tools.filterNull(doRegisterVo.getBankcard()));
        } else {
            modelMap.put("message", "您的银行卡号格式错误，请重新输入");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }

        doRegisterVo.setBankName(Tools.filterNull(doRegisterVo.getBankName()));
        doRegisterVo.setInviter(Tools.filterNull(doRegisterVo.getInviter()));
        doRegisterVo.setCardType(Tools.filterNull(doRegisterVo.getCardType()));
        doRegisterVo.setEmail(Tools.filterNull(doRegisterVo.getEmail()));

        if (Tools.checkidCard(doRegisterVo.getIdcard())) {
            doRegisterVo.setIdcard(Tools.filterNull(doRegisterVo.getIdcard()));
        } else {
            modelMap.put("message", "您的身份证号格式错误，请重新输入");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }

        doRegisterVo.setCardType(Tools.filterNull(doRegisterVo.getCardType()));
        doRegisterVo.setName(Tools.filterNull(doRegisterVo.getName()));
        userInfoBO = secutitiesService.isExitMemberName(doRegisterVo);
        if (userInfoBO != null) {
            modelMap.put("message", "您输入的用户名已存在，请重新输入");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }

        //判断银行卡号是否被绑定
        userInfoBO = secutitiesService.isEixtMemberCard(doRegisterVo);
        if (userInfoBO != null) {
            if (doRegisterVo.getIdcard().equals(userInfoBO.getMember_idcard())) {
                if (doRegisterVo.getBankName().equals(userInfoBO.getMember_bankname())) {
                    if (doRegisterVo.getCardType().equals(userInfoBO.getMember_cardtype())) {
                    } else {
                        modelMap.put("message", "银行卡类型不匹配");
                        modelMap.put("code", "1");
                        modelMap.put("data", null);
                        return modelMap;
                    }
                } else {
                    modelMap.put("message", "您的银行卡与所属银行不匹配，请确认");
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    return modelMap;
                }
            } else {
                modelMap.put("message", "您的银行卡已被绑定，请更换一张未绑定的银行卡");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }


        }

        /**********************临时修改开始**时间不够以后再调整*********************************************/

        //判断邀请人和网络上级是否在一条线上
//        if(secutitiesService.queryIsConnect(doRegisterVo)){
//            modelMap.put("message", "您填写的推荐人ID和管理者ID不在一条网络上");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }

        switch (secutitiesService.queryIsConnect(doRegisterVo)){
            case 1:
                modelMap.put("message", "您填写的推荐人ID和管理者ID不在一条网络上");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;

            case 2:
                modelMap.put("message", "您填写的推荐人ID不属于白拿积分用户");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            case 3:
                modelMap.put("message", "您填写的管理者ID不属于白拿积分用户");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            default:
                break;
        }
        //判断网络上级是否下过单
        if(!secutitiesService.queryNetworkIdIsable(doRegisterVo)){
            modelMap.put("message", "您填写的管理员ID没有下过订单,暂时无法使用请更换");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
        zxzqCommonBO.setMember_sn(doRegisterVo.getInviter());
        ZxzqMemberBo zxzqMemberBo = secutitiesService.queryMemberByMemberSn(zxzqCommonBO);

        if (zxzqMemberBo == null) {
            modelMap.put("message", "该推荐人ID不存在，请输入有效的推荐人ID");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
//          if(StringUtil.isEmpty(zxzqMemberBo.getSecurities_team())){
//              modelMap.put("message","该推荐人ID网络信息无效，请输入有效的推荐人ID");
//              modelMap.put("code","1");
//              modelMap.put("data",null);
//              return modelMap;
//          }
        //网络上级
        zxzqCommonBO.setMember_sn(doRegisterVo.getNetwork_id());
        ZxzqMemberBo zxzqMemberBo1 = secutitiesService.queryMemberByMemberSn(zxzqCommonBO);
        if (zxzqMemberBo1 == null) {
            modelMap.put("message", "该管理者ID不存在，请输入有效的管理者ID");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        if (StringUtil.isEmpty(zxzqMemberBo1.getNetwork_team())) {
            modelMap.put("message", "该管理者ID网络信息无效，请输入有效的管理者ID");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        //这里差个生成团队标识
//        doRegisterVo.setTeamSign(secutitiesService.setSecuritiesTeam(doRegisterVo.getInviter()));
        modelMap = secutitiesService.doRegister(doRegisterVo, zxzqMemberBo1);
        return modelMap;
    }



//    /**
//     * 备份复刻证券注册
//     */
//    @RequestMapping(value = "doRegister")
//    @ResponseBody
//    public Map<String, Object> doRegister(@RequestBody DoRegisterVo doRegisterVo) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        UserInfoBO userInfoBO = null;
//        //判断用户名是否被占用
//        if (Tools.isEmpty(doRegisterVo.getUserName())) {
//            modelMap.put("message", "用户名不允许为空");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        if (Tools.isEmpty(doRegisterVo.getPassWord())) {
//            modelMap.put("message", "密码不允许为空");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        if (Tools.isEmpty(doRegisterVo.getInviter())) {
//            modelMap.put("message", "邀请人不允许为空");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        if (Tools.isEmpty(doRegisterVo.getMobile())) {
//            modelMap.put("message", "手机号不允许为空");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        doRegisterVo.setAddress(Tools.filterNull(doRegisterVo.getAddress()));
//        doRegisterVo.setCardType(Tools.filterNull(doRegisterVo.getCardType()));
//        if (Tools.checkBankCard(doRegisterVo.getBankcard())) {
//            doRegisterVo.setBankcard(Tools.filterNull(doRegisterVo.getBankcard()));
//        } else {
//            modelMap.put("message", "请填写正确的银行卡号");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//
//        doRegisterVo.setBankName(Tools.filterNull(doRegisterVo.getBankName()));
//        doRegisterVo.setInviter(Tools.filterNull(doRegisterVo.getInviter()));
//        doRegisterVo.setCardType(Tools.filterNull(doRegisterVo.getCardType()));
//        doRegisterVo.setEmail(Tools.filterNull(doRegisterVo.getEmail()));
//
//        if (Tools.checkidCard(doRegisterVo.getIdcard())) {
//            doRegisterVo.setIdcard(Tools.filterNull(doRegisterVo.getIdcard()));
//        } else {
//            modelMap.put("message", "请填写正确的身份证号");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//
//        doRegisterVo.setCardType(Tools.filterNull(doRegisterVo.getCardType()));
//        doRegisterVo.setName(Tools.filterNull(doRegisterVo.getName()));
//        userInfoBO = secutitiesService.isExitMemberName(doRegisterVo);
//        if (userInfoBO != null) {
//            modelMap.put("message", "用户名已存在");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//
//        //判断银行卡号是否被绑定
//        userInfoBO = secutitiesService.isEixtMemberCard(doRegisterVo);
//        if (userInfoBO != null) {
//            if (doRegisterVo.getIdcard().equals(userInfoBO.getMember_idcard())) {
//                if (doRegisterVo.getBankName().equals(userInfoBO.getMember_bankname())) {
//                    if (doRegisterVo.getCardType().equals(userInfoBO.getMember_cardtype())) {
//                    } else {
//                        modelMap.put("message", "银行卡类型不匹配");
//                        modelMap.put("code", "1");
//                        modelMap.put("data", null);
//                        return modelMap;
//                    }
//                } else {
//                    modelMap.put("message", "银行卡与银行名称不匹配");
//                    modelMap.put("code", "1");
//                    modelMap.put("data", null);
//                    return modelMap;
//                }
//            } else {
//                modelMap.put("message", "银行卡已经被绑定");
//                modelMap.put("code", "1");
//                modelMap.put("data", null);
//                return modelMap;
//            }
//
//        }
//
//        /**********************临时修改开始**时间不够以后再调整*********************************************/
//        //判断邀请人和网络上级是否在一条线上
//        if(!secutitiesService.queryIsConnect(doRegisterVo)){
//            modelMap.put("message", "您填写的推荐人ID和管理者ID不在一条网络上");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
//        zxzqCommonBO.setMember_sn(doRegisterVo.getInviter());
//        ZxzqMemberBo zxzqMemberBo = secutitiesService.queryMemberByMemberSn(zxzqCommonBO);
//
//        if (zxzqMemberBo == null) {
//            modelMap.put("message", "该推荐人ID不存在，请输入有效的推荐人ID");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
////          if(StringUtil.isEmpty(zxzqMemberBo.getSecurities_team())){
////              modelMap.put("message","该推荐人ID网络信息无效，请输入有效的推荐人ID");
////              modelMap.put("code","1");
////              modelMap.put("data",null);
////              return modelMap;
////          }
//        //网络上级
//        zxzqCommonBO.setMember_sn(doRegisterVo.getNetwork_id());
//        ZxzqMemberBo zxzqMemberBo1 = secutitiesService.queryMemberByMemberSn(zxzqCommonBO);
//        if (zxzqMemberBo1 == null) {
//            modelMap.put("message", "该管理者ID不存在，请输入有效的管理者ID");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        if (StringUtil.isEmpty(zxzqMemberBo1.getNetwork_team())) {
//            modelMap.put("message", "该管理者ID网络信息无效，请输入有效的管理者ID");
//            modelMap.put("code", "1");
//            modelMap.put("data", null);
//            return modelMap;
//        }
//        //这里差个生成团队标识
//        doRegisterVo.setTeamSign(secutitiesService.setSecuritiesTeam(doRegisterVo.getInviter()));
//        modelMap = secutitiesService.doRegister(doRegisterVo, zxzqMemberBo1);
//        return modelMap;
//    }

    /**
     * 周一到周五定时释放证券定时器
     *
     * @throws Exception
     */
    @Scheduled
    public void automaticRelease() throws Exception {

        try {
            secutitiesService.automaticRelease();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 每天零点定时生成上三级推荐人奖励
    @Scheduled
    public void updateThreeSeCuritiesReward() throws Exception {
        try {

            List<ZxzqRecordBO> userList = secutitiesService.queryScuritiesEveryday();
            secutitiesService.updateThreeSeCuritiesReward(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成上三级推荐人奖励
     *
     * @param zxzqCommonBO
     * @return
     * @throws Exception
     */
    @RequestMapping("updateThreeSecurities")
    @ResponseBody
    public Map<String, Object> updateThreeSecurities(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            UserInfoBO userInfoBO = new UserInfoBO();
            // 根据当前用户id 找寻推荐人
            userInfoBO = secutitiesService.findMemberById(zxzqCommonBO);
            if (userInfoBO == null) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "用户不存在");
                return map;
            }
            if (StringUtil.isEmpty(userInfoBO.getSecurities_id())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "用户没有推荐人");
                return map;
            }
            if (StringUtil.isEmpty(userInfoBO.getNetwork_id())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "用户没有网络上级");
                return map;
            }
            if (StringUtil.isEmpty(userInfoBO.getNetwork_team())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "用户没有网络标识");
                return map;
            }

            List<ZxzqRecordBO> userList = secutitiesService.queryScuritiesById(zxzqCommonBO);
            if (userList != null && userList.size() > 0) {
                secutitiesService.updateThreeSeCuritiesReward(userList);
                map.put("code", "0");
                map.put("data", null);
                map.put("message", "上三级推荐人奖励生成成功");
                return map;
            } else {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "用户没有符合条件的订单记录");
                return map;
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "1");
            map.put("data", null);
            map.put("message", "系统内部错误");
            return map;
        }
    }

    /**
     * 更新用户证券推荐人和证券团队标识符
     *
     * @param userInfoVO
     * @return
     */
    @RequestMapping("updateSecuritiesTeam")
    @ResponseBody
    public Map<String, Object> updateSecuritiesTeam(@RequestBody UserInfoVO userInfoVO) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            /******************时间不够 临时修改************************************/
            ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
            zxzqCommonBO.setMember_sn(userInfoVO.getSecurities_id());
            ZxzqMemberBo zxzqMemberBo = secutitiesService.queryMemberByMemberSn(zxzqCommonBO);
            if (zxzqMemberBo == null) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "该推荐人不存在");
            } else {
                userInfoVO.setSecurities_id(zxzqMemberBo.getMember_sn());
                /*********************时间不够 临时修改**********************************/
                // 检查用户是否存在
                DoRegisterVo doRegisterVo = new DoRegisterVo();
                doRegisterVo.setUserName(userInfoVO.getUserName());
                UserInfoBO userInfoBO = secutitiesService.isExitMemberName(doRegisterVo);
                if (userInfoBO == null) {
                    map.put("code", "1");
                    map.put("data", null);
                    map.put("message", "用户名不存在");
                    return map;
                }

                // 校验用户是否下过订单
                UserInfoBO userInfo = secutitiesService.findMemberByName(userInfoVO.getUserName());

                ZxzqRecordVo zxzqRecordVo = new ZxzqRecordVo();
                zxzqRecordVo.setMemberid(userInfo.getMember_id());
                List<ZxzqRecordBO> recordList = secutitiesService.findRecordByMemberId(zxzqRecordVo);
                if (recordList != null && recordList.size() > 0) {
                    map.put("code", "1");
                    map.put("data", null);
                    map.put("message", "用户已经下过订单，不允许修改推荐人信息");

                    return map;
                }

                // 更新用户推荐人信息
                String result = secutitiesService.updateSecuritiesTeam(userInfoVO.getUserName(), userInfoVO.getSecurities_id());

                // 初始化（drawcash,information, wallet）
                secutitiesService.initializtionZxzq(userInfo.getMember_id());

                if ("0".equals(result)) {
                    map.put("code", "0");
                    map.put("data", null);
                    map.put("message", "用户信息更新成功");
                } else if ("1".equals(result)) {
                    map.put("code", "1");
                    map.put("data", null);
                    map.put("message", "系统内部错误");
                } else if ("2".equals(result)) {
                    map.put("code", "2");
                    map.put("data", null);
                    map.put("message", "该推荐人不存在");
                } else if ("3".equals(result)) {
                    map.put("code", "2");
                    map.put("data", null);
                    map.put("message", "您的推荐人没有团队标识");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "1");
            map.put("data", null);
            map.put("message", "系统内部错误");

            return map;
        }

        return map;
    }

    /**
     * 修改个人基本信息
     */
    @RequestMapping("userModifyUserInfo")
    @ResponseBody
    public Map<String, Object> updateMemberBasic(@RequestBody UpdateUserInfoVo updateUserInfoVo) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        TokenBO tokenBO = new TokenBO();
        try {
            //tokenBO.setToken(updateUserInfoVo.getToken());
            tokenBO.setMember_id(updateUserInfoVo.getMember_id());

            UserInfoBO userInfoBO = secutitiesService.queryByMemberid(tokenBO);
            //验证member_id是否存在
            if (userInfoBO != null) {
                //验证要修改的银行卡账号是否和原有的银行卡账号一样
//                if(!updateUserInfoVo.getBankcard().equals(userInfoBO.getMember_bankcard())){
//                    //验证修改后的银行卡是否存在
//                    UserInfoBO infoBO = secutitiesService.findBankCardForUpdate(updateUserInfoVo);
//                    if(infoBO!=null){
//                        modelMap.put("message", "输入的银行卡号已存在 ");
//                        modelMap.put("code","1");
//                        modelMap.put("data",null);
//                        return modelMap;
//                    }
//                }
                if (StringUtil.isNotEmpty(updateUserInfoVo.getSex())) {
                    try {
                        int sexId = Integer.parseInt(updateUserInfoVo.getSex());
                        if (sexId == 0 || sexId == 1 || sexId == 2 || sexId == 3) {
                            userInfoBO.setMember_sex(sexId);
                        } else {
                            modelMap.put("message", "性别格式错误 0:没填 1:男 2:女 3:保密");
                            modelMap.put("code", "1");
                            modelMap.put("data", null);
                            return modelMap;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        modelMap.put("message", "性别格式错误 0:没填1:男 2:女 3:保密");
                        modelMap.put("code", "1");
                        modelMap.put("data", null);
                        return modelMap;
                    }
                } else {
                    userInfoBO.setMember_sex(0);
                }
                userInfoBO.setMember_truename(updateUserInfoVo.getName());
                if (Tools.checkidCard(updateUserInfoVo.getIdcard())) {
                    userInfoBO.setMember_idcard(updateUserInfoVo.getIdcard());
                } else {
                    modelMap.put("code", "1");
                    modelMap.put("message", "您的身份证号格式错误，请重新输入");
                    modelMap.put("data", null);
                    return modelMap;
                }

                //判断银行卡号是否被绑定
                DoRegisterVo doRegisterVo = new DoRegisterVo();
                doRegisterVo.setBankcard(updateUserInfoVo.getBankcard());
                UserInfoBO userInfo = new UserInfoBO();
                userInfo = secutitiesService.isEixtMemberCard(doRegisterVo);
                if (userInfo != null) {
                    if (!"".equals(userInfo.getMember_idcard()) && userInfo.getMember_idcard() != null && userInfo.getMember_cardtype() != null && !"".equals(userInfo.getMember_cardtype()) && !"".equals(userInfo.getMember_bankname()) && userInfo.getMember_bankname() != null) {
                        if (updateUserInfoVo.getIdcard().equals(userInfo.getMember_idcard())) {
                            if (updateUserInfoVo.getBankname().equals(userInfo.getMember_bankname())) {
                                if (updateUserInfoVo.getCardType().equals(userInfo.getMember_cardtype())) {
                                } else {
                                    modelMap.put("message", "银行卡类型不匹配");
                                    modelMap.put("code", "1");
                                    modelMap.put("data", null);
                                    return modelMap;
                                }
                            } else {
                                modelMap.put("message", "您的银行卡与所属银行不匹配，请确认");
                                modelMap.put("code", "1");
                                modelMap.put("data", null);
                                return modelMap;
                            }
                        } else {
                            modelMap.put("message", "您的银行卡已被绑定，请更换一张未绑定的银行卡");
                            modelMap.put("code", "1");
                            modelMap.put("data", null);
                            return modelMap;
                        }
                    }
                }
                userInfoBO.setMember_bankname(updateUserInfoVo.getBankname());
                userInfoBO.setMember_mobile(updateUserInfoVo.getMobile());
                userInfoBO.setMember_area(updateUserInfoVo.getArea());
                userInfoBO.setMember_address(updateUserInfoVo.getAddress());
                userInfoBO.setMember_email(updateUserInfoVo.getEmail());
                userInfoBO.setMember_cardtype(updateUserInfoVo.getCardType());
                if (Tools.checkBankCard(updateUserInfoVo.getBankcard())) {
                    userInfoBO.setMember_bankcard(updateUserInfoVo.getBankcard());
                } else {
                    modelMap.put("code", "1");
                    modelMap.put("message", "您的银行卡号格式错误，请重新输入");
                    modelMap.put("data", null);
                    return modelMap;
                }
                //修改信息
                secutitiesService.updateUserInfo(userInfoBO);
                modelMap.put("message", "修改信息成功 ");
                modelMap.put("code", "0");
                modelMap.put("data", null);
            } else {
                modelMap.put("code", "2");
                modelMap.put("message", "用户名不存在");
                modelMap.put("data", null);
                return modelMap;
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
     * 修改密码
     */
    @RequestMapping("securities_updatePassword")
    @ResponseBody
    public Map<String, Object> updatePassword(@RequestBody UserInfoVO userInfoVO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            userInfoVO.setPassWord(MD5.md5(userInfoVO.getPassWord()));
            UserInfoBO userInfoBO = secutitiesService.checkMemberForUpdatePassword(userInfoVO);
            if (userInfoBO != null) {
                userInfoBO.setMember_passwd(MD5.md5(userInfoVO.getNewpassWord()));
                secutitiesService.updatePassword(userInfoBO);
                modelMap.put("message", "修改密码成功");
                modelMap.put("code", "0");
                modelMap.put("data", null);
                return modelMap;
            } else {
                modelMap.put("message", "原密码不正确");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
    }

    /**
     * 培育佣金
     */
    @RequestMapping("doByNetworkTeam")
    @ResponseBody
    public Map<String, Object> doByNetworkTeam(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            secutitiesService.manager(zxzqCommonBO);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 查找可选网络标识位置
     *
     * @param
     * @return
     * @throws Exception
     * @Auth 刘洋
     */
    @RequestMapping("securities_checkNetWork")
    @ResponseBody
    public Map<String, Object> checkNetWork(@RequestBody NetWorkVO netWorkVO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            String a = "01";
            String b = "02";
            // 查询新用户
            Integer id = netWorkVO.getNewUserId();
            NetWorkBO nbo = secutitiesService.findNetworkTeams(id);
            if (nbo == null) {
                modelMap.put("message", "新用户不存在");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }

            if (StringUtil.isNotEmpty(nbo.getNetwork_team())) {
                modelMap.put("message", "新用户已存在网络标识");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }


            // 根据推荐人账号查询网络标识
            NetWorkBO netWorkBO = secutitiesService.findNetworkTeams(netWorkVO.getMember_id());
            if (netWorkBO != null) {
                if (netWorkBO.getNetwork_team() == null || "".equals(netWorkBO.getNetwork_team())) {
                    modelMap.put("message", "推荐人还没有生成网络标识");
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    return modelMap;
                } else {
                    NetWorkBO bo = new NetWorkBO();
                    StringBuffer networkTeam = new StringBuffer(netWorkBO.getNetwork_team());  // 当前登陆用户网络标识
                    // 拼接一个01  判断左区是否有下级成员
                    String leftTeam = networkTeam.append(a).toString();
                    // 拼接后的左区下级成员的网络标识
                    bo.setNetwork_team(leftTeam);
                    // 获取该推荐人左区第一个用户
                    NetWorkBO leftWorkTeam = secutitiesService.checkNetworkTeam(bo);
                    if (leftWorkTeam != null) {
                        StringBuffer networkTeam1 = new StringBuffer(netWorkBO.getNetwork_team()); // 当前登陆用户网络标识
                        // 拼接一个02  判断右区是否有下级成员
                        String rightWorkTeam = networkTeam1.append(b).toString();
                        // 拼接后的右区下级成员的网络标识
                        bo.setNetwork_team(rightWorkTeam);
                        // 查询该推荐人右区是否存在用户
                        NetWorkBO netWork = secutitiesService.checkNetworkTeam(bo);
                        if (netWork != null) {
                            //通过比较每条线上所有人购买的证券数获取可选网络标识位置
                            List<Integer> idList = secutitiesService.findPlace(netWorkBO);
                            List<NetWorkVO> voList = new ArrayList<NetWorkVO>();
                            NetWorkBO wbo = new NetWorkBO();
                            // 循环遍历出可选位置的上一级id  以及新生成的可选位置的网络标识
                            for (int i = 0; i < idList.size(); i++) {
                                netWorkVO.setMember_id(idList.get(i));
                                // 获取可选位置的网络标识
                                wbo = secutitiesService.findNetworkTeams(idList.get(i));

                                NetWorkVO vo = new NetWorkVO();
                                // 保存可选位置id(左区)
                                vo.setUpMemberId(idList.get(i));
                                // 保存可选位置 用户账号(member_name)
                                vo.setMember_name(wbo.getMember_name());
                                // 生成新用户网络标识（左区）
                                StringBuffer wboNetworkTeam = new StringBuffer(wbo.getNetwork_team());
                                vo.setNewNetworkTeam(wboNetworkTeam.append(a).toString());
                                voList.add(vo);
                            }


                            modelMap.put("message", "该用户右区存在用户,至少有一个可选位置");
                            modelMap.put("code", "0");
                            modelMap.put("data", voList);
                            return modelMap;
                        } else {
                            //通过比较每条线上所有人购买的证券数获取可选网络标识位置
                            List<Integer> idList = secutitiesService.findPlace(netWorkBO);
                            List<NetWorkVO> voList = new ArrayList<NetWorkVO>();
                            NetWorkBO wbo = new NetWorkBO();
                            // 循环遍历出可选位置的上一级id  以及新生成的可选位置的网络标识
                            for (int i = 0; i < idList.size(); i++) {
                                // 获取可选位置的网络标识
                                wbo = secutitiesService.findNetworkTeams(idList.get(i));

                                NetWorkVO vo = new NetWorkVO();
                                // 保存可选位置id(左区)
                                vo.setUpMemberId(idList.get(i));
                                // 保存可选位置 用户账号(member_name)
                                vo.setMember_name(wbo.getMember_name());
                                // 生成新用户网络标识（左区）
                                StringBuffer wboNetworkTeam = new StringBuffer(wbo.getNetwork_team());
                                vo.setNewNetworkTeam(wboNetworkTeam.append(a).toString());
                                voList.add(vo);
                            }

                            List<NetWorkVO> list = new ArrayList<NetWorkVO>();
                            // 添加所有的可选位置上一级id以及新生成的网络标识到集合
                            list.addAll(voList);

                            NetWorkVO workVO = new NetWorkVO();
                            // 保存可选位置id(右区)
                            workVO.setUpMemberId(netWorkVO.getMember_id());

                            // 保存可选位置 用户账号(member_name)
                            TokenBO tokenbo = new TokenBO();
                            tokenbo.setMember_id(netWorkVO.getMember_id());
                            UserInfoBO userinfbo = secutitiesService.queryByMemberid(tokenbo);
                            // 保存可选位置 用户账号(member_name)
                            workVO.setMember_name(userinfbo.getMember_name());
                            // 生成新用户网络标识（右区）
                            StringBuffer rightTeam = new StringBuffer(netWorkBO.getNetwork_team());
                            workVO.setNewNetworkTeam(rightTeam.append(b).toString());

                            // 添加所有的可选位置上一级id以及新生成的网络标识到集合
                            list.add(workVO);
                            modelMap.put("message", "该用户右区还没有用户,至少有两个可选位置");
                            modelMap.put("code", "0");
                            modelMap.put("data", list);
                            return modelMap;

                        }
                    } else {
                        NetWorkVO workVO = new NetWorkVO();
                        // 保存可选位置id(右区)
                        workVO.setUpMemberId(netWorkVO.getMember_id());

                        // 保存可选位置 用户账号(member_name)
                        TokenBO tokenbo = new TokenBO();
                        tokenbo.setMember_id(netWorkVO.getMember_id());
                        UserInfoBO userinfbo = secutitiesService.queryByMemberid(tokenbo);
                        // 保存可选位置 用户账号(member_name)
                        workVO.setMember_name(userinfbo.getMember_name());

                        // 生成新用户网络标识（右区）
                        StringBuffer rightTeam = new StringBuffer(netWorkBO.getNetwork_team());
                        workVO.setNewNetworkTeam(rightTeam.append(a).toString());
                        modelMap.put("message", "该推荐人左,右区都没有用户将默认分配到左区");
                        modelMap.put("code", "0");
                        modelMap.put("data", workVO);
                        return modelMap;
                    }
                }
            } else {
                modelMap.put("message", "推荐人不存在");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }

    }

    /**
     * 保存新用户网络标识
     *
     * @param netWorkVO
     * @return
     * @throws Exception
     * @Auth 刘洋
     */
    @RequestMapping("securities_setNetWork")
    @ResponseBody
    public Map<String, Object> setNetWork(@RequestBody NetWorkVO netWorkVO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            NetWorkBO netWorkBO = secutitiesService.findNetworkTeams(netWorkVO.getNewUserId());
            if (netWorkBO != null) {
                if (netWorkBO.getNetwork_team() == null || "".equals(netWorkBO.getNetwork_team())) {
                    UserInfoBO userInfoBO = new UserInfoBO();
                    userInfoBO.setMember_id(netWorkVO.getUpMemberId());
                    // 获取网络上级账号即(network_id)
                    userInfoBO = secutitiesService.findUserNameById(userInfoBO);
                    // 生成新用户网络标识
                    netWorkBO.setMember_id(netWorkVO.getNewUserId());
                    netWorkBO.setNetwork_id(userInfoBO.getMember_sn());
                    netWorkBO.setNetwork_team(netWorkVO.getNewNetworkTeam());

                    // 保存网络标识
                    secutitiesService.setNewNetworkTeam(netWorkBO);
                    modelMap.put("message", "新用户网络标识保存成功");
                    modelMap.put("code", "0");
                    modelMap.put("data", null);
                    return modelMap;
                } else {
                    modelMap.put("message", "新用户已存在网络标识");
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    return modelMap;
                }
            } else {
                modelMap.put("message", "新用户不存在");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }

    }

    /**
     * 积分转到证券钱包
     *
     * @param tokenvo
     * @return
     * @throws Exception
     * @auth 李荣洲
     */
    @RequestMapping("balanceWithdrawCash")
    @ResponseBody
    public Map<String, Object> balanceWithdrawCash(@RequestBody TokenVo tokenvo) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        //通过token查询member同时校验token是否失效
        try {
            UserInfoBO user = secutitiesService.queryByToken(tokenvo);
            if (user == null) {
                return modelMap;
            }
            //通过memberid查询用户积分数
            int balance = secutitiesService.queryBalanceByMember(user);
            if (balance == 0) {
                modelMap.put("message", "您没有可以转到证券钱包的积分");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }
            //通过memberid校验用户证券数据是否初始化没有初始化数据就直接初始化数据
            secutitiesService.initializtionZxzq(user.getMember_id());
            //获取当日净值
            float equity = secutitiesService.queryTodayEquity();
            double money = (double) (balance * equity);
            user.setMoney(money);
            //将换算的钱数转入证券钱包
            secutitiesService.updateMoneyByMemberid(user);
            //清零当前用户的积分数
            secutitiesService.clearBalanceByMemberid(user);
            modelMap.put("message", "积分转入钱包成功");
            modelMap.put("code", "0");
            modelMap.put("data", null);
        } catch (Exception e) {
            modelMap = Tools.errMessageSystem();
            return modelMap;
        }
        return modelMap;
    }


    @RequestMapping("deleteMember")
    @ResponseBody
    public Map<String, Object> deleteMember(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            List<ZxzqRecordBO> list =secutitiesService.queryByRecordByMember(zxzqCommonBO);
            if(list!=null&&list.size()>0){
                modelMap.put("message", "此用户已经下过订单,无法删除");
                modelMap.put("code", "0");
                modelMap.put("data", null);
                return modelMap;
            }else{
                secutitiesService.deleteMember(zxzqCommonBO);
                modelMap.put("message", "删除成功");
                modelMap.put("code", "0");
                modelMap.put("data", null);
                return modelMap;

            }

        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }

    }

    /**
     * 现金状态，是否冻结
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestBody ZxzqInformationBO zxzqInformationBO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            secutitiesService.updateStatus(zxzqInformationBO);
            modelMap.put("message", "现金冻结状态更改完成");
            modelMap.put("code", "0");
            modelMap.put("data", null);
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
    }


    //特殊情况下单删除
    @RequestMapping(value = "deleteRecord")
    @ResponseBody

    public Map<String, Object> deleteRecord(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        ZxzqRecordBO zxzqRecordBO = secutitiesService.queryTodeleteRecord(zxzqCommonBO);
        if (zxzqRecordBO == null) {
            modelMap.put("message", "该笔订单不存在,无法撤销");
            modelMap.put("code", "1");
            modelMap.put("data", null);
        } else {
            String time = String.valueOf(zxzqRecordBO.getOtime()).substring(0, 10);
            String now = Tools.getSysDate("yyyy-MM-dd HH:mm:ss").substring(0, 10);
            if (StringUtil.isEmpty(zxzqRecordBO.getRemark())) {
                modelMap.put("message", "该笔订单为正常下单,无法撤销");
                modelMap.put("code", "1");
                modelMap.put("data", null);
            } else if (time.equals(now)) {
                secutitiesService.deleteRecord(zxzqCommonBO);
                modelMap.put("message", "撤销成功");
                modelMap.put("code", "0");
                modelMap.put("data", null);
            } else {
                modelMap.put("message", "奖励已发放，无法撤销");
                modelMap.put("code", "1");
                modelMap.put("data", null);
            }

        }
        return modelMap;
    }

    //特殊情况下单修改
    @RequestMapping(value = "updateRecord")
    @ResponseBody
    public Map<String, Object> updateRecord(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        ZxzqRecordBO zxzqRecordBO = secutitiesService.queryTodeleteRecord(zxzqCommonBO);
        if (zxzqRecordBO == null) {
            modelMap.put("message", "该笔订单不存在,无法修改");
            modelMap.put("code", "1");
            modelMap.put("data", null);
        } else {
            String time = String.valueOf(zxzqRecordBO.getOtime()).substring(0, 10);
            String now = Tools.getSysDate("yyyy-MM-dd HH:mm:ss").substring(0, 10);
            if (StringUtil.isEmpty(zxzqRecordBO.getRemark())) {
                modelMap.put("message", "该笔订单为正常下单,无法修改");
                modelMap.put("code", "1");
                modelMap.put("data", null);
            } else if (time.equals(now)) {
                String remark = "特殊情况下实际花费金额" + String.valueOf(zxzqCommonBO.getMoney());
                zxzqCommonBO.setRemark(remark);
                secutitiesService.updateRecord(zxzqCommonBO);
                modelMap.put("message", "修改成功");
                modelMap.put("code", "0");
                modelMap.put("data", null);
            } else {
                modelMap.put("message", "奖励已发放，无法撤销");
                modelMap.put("code", "1");
                modelMap.put("data", null);
            }


        }
        return modelMap;
    }

    //转换业绩
    @RequestMapping(value = "setAchievementTest")
    @ResponseBody
    //获取短信验证
    public Map<String, Object> setAchievementTest() throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();

        try {
            List<ZxzqRecordBO> list = secutitiesService.setAchievementTest();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ZxzqRecordBO zxzqRecordBO = list.get(i);
                    ZxzqCommonBO zxzqCommonBONow = new ZxzqCommonBO();
                    zxzqCommonBONow.setMemberid(zxzqRecordBO.getMemberid());
                    zxzqCommonBONow.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                    secutitiesService.setAchievement(zxzqCommonBONow);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }


    /**
     * @return
     * @throws Exception
     * @auth:李荣洲 证券钱包申请提现
     */
    @RequestMapping(value = "applyWithdrawCash")
    @ResponseBody
    public Map<String, Object> applyWithdrawCash(@RequestBody WithdrawCashVo withdrawCashVo) throws Exception {
        Map<String, Object> modelMap = Tools.errMessage();
        modelMap = secutitiesService.applyWithdraw(withdrawCashVo);
        return modelMap;
    }

    /**
     * @param withdrawCashVo
     * @return
     * @throws Exception
     * @auth:李荣洲 撤销证券钱包提现申请
     */
    @RequestMapping(value = "rollbackWithdraw")
    @ResponseBody
    public Map<String, Object> rollbackWithdraw(@RequestBody WithdrawCashVo withdrawCashVo) throws Exception {
        Map<String, Object> modelMap = Tools.errMessage();
        modelMap = secutitiesService.rollbackWithdraw(withdrawCashVo);
        return modelMap;
    }

    /**
     * @param withdrawCashVo
     * @return
     * @throws Exception
     * @auth:李荣洲 确认证券钱包提现申请
     */
    @RequestMapping(value = "confirmWithdraw")
    @ResponseBody
    public Map<String, Object> confirmWithdraw(@RequestBody WithdrawCashVo withdrawCashVo) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {
            modelMap = secutitiesService.confirmWithdraw(withdrawCashVo);
        } catch (Exception e) {
            e.printStackTrace();
            return modelMap;
        }
        return modelMap;
    }

    @Scheduled
    //定时释放奖金
    @RequestMapping(value = "memberautoRewordDistribution")
    @ResponseBody
    public void memberautoRewordDistribution() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            ZxzqRateBO zxzqRateBO = secutitiesService.queryZxzqRate();
            if (zxzqRateBO == null) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "无法获得交易净值");
            } else {
                secutitiesService.autoRewordDistribution(zxzqRateBO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 证券提现显示
     */
    @RequestMapping(value = "withdrawCashMessage")
    @ResponseBody
    public Map<String, Object> withdrawCashMessage(@RequestBody ZxzqRecordVo zxzqRecordVo) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {
            ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
            zxzqCommonBO.setMemberid(zxzqRecordVo.getMemberid());
            ZxzqDrawCashBO bo = secutitiesService.queryZxzqDrawcash(zxzqCommonBO);
            if (bo == null) {
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "该用户不存在");
                return modelMap;
            } else {
                int i = bo.getSecuritiesnum().compareTo(new BigDecimal("100"));
                if (i == 1 || i == 0) {
                    ZxzqPublicBo zxzqPublicBo = secutitiesService.withdrawCashMessage(zxzqCommonBO);
                    modelMap.put("code", "0");
                    modelMap.put("data", zxzqPublicBo);
                    modelMap.put("message", "白拿金提取成功");
                    return modelMap;
                } else {
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    modelMap.put("message", "您的白拿金余额不足，无法提取");
                    return modelMap;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap = Tools.errMessageSystem();
            return modelMap;
        }

    }

    /**
     * 证券提现
     */
    @RequestMapping(value = "withdrawCash")
    @ResponseBody
    public Map<String, Object> withdrawCash(@RequestBody ZxzqRecordVo zxzqRecordVo) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {
            ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
            zxzqCommonBO.setMemberid(zxzqRecordVo.getMemberid());
            ZxzqDrawCashBO bo = secutitiesService.queryZxzqDrawcash(zxzqCommonBO);
            if (bo == null) {
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "该用户不存在");
                return modelMap;
            } else {
                int i = bo.getSecuritiesnum().compareTo(new BigDecimal("100"));
                if (i == 1 || i == 0) {
                    modelMap = secutitiesService.withdrawCashRecord(zxzqCommonBO);
                    //不欠款的时候80%体现20%复投 放开即可
                    //modelMap = secutitiesService.withdrawCashRecord1(zxzqCommonBO);
                    modelMap.put("code", "0");
                    modelMap.put("data", null);
                    modelMap.put("message", "白拿金提取成功");
                    return modelMap;
                } else {
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    modelMap.put("message", "您的白拿金余额不足，无法提取");
                    return modelMap;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap = Tools.errMessageSystem();
            return modelMap;
        }

    }

    //新结算
    @RequestMapping(value = "NewJieSuan")
    @ResponseBody

    public Map<String, Object> NewJieSuan() throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();

        try {
            //查找所有没结算的订单
            List<ZxzqRecordBO> list = secutitiesService.NewQueryZxzqRecord();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ZxzqRecordBO zxzqRecordBO = list.get(i);
                    //#2.根据订单id查询订单及订单人信息   id = 变量   status为0寻找未结算的，1为已结算的 不管结算没结算都想查询，去掉status条件
                    ZxzqRecordInFormation zxzqRecordInFormation = secutitiesService.NewQueryZxzqRecordInFormation(zxzqRecordBO);
                    //#3.根据订单id及订单人网络编号，查找上三代所有人，以及应给的提成比率   id=173 是变量，memeber_sn='cn51284323'是变量
                    ZxzqRecordThree zxzqRecordThree = secutitiesService.NewQueryZxzqRecordThree(zxzqRecordInFormation);
                    //#4.发放奖金 特别注意，是assigned=assigned+奖金  #5.保存奖金发放记录 sql3语句中，将3个上级用户寸草一个数组，3个比率存到一个数组，就可以用for循环搞定
                    secutitiesService.setReward(zxzqRecordThree, zxzqRecordInFormation);
                    /*****************新增业绩**************************/
                    //#7。查找所有上级用户 例如当前用户网络标识为01010102010201  ()括号里的内容应该是动态循环加入的 注意，第一次就减去2个字符   #8.更新业绩 500是变量，应为本订单的真实金额
                    String str = secutitiesService.queryTop(zxzqRecordInFormation);   //查询上级
                    ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
                    zxzqCommonBO.setRemark(str);
                    zxzqCommonBO.setMoney(zxzqRecordBO.getRealprice());
                    secutitiesService.updateZxzqMemberBoList(zxzqCommonBO);
                    //更改状态
                    zxzqCommonBO.setSecuritiesid(zxzqRecordBO.getSecuritiesid());
                    secutitiesService.updateRecordStatusNew(zxzqCommonBO);

                }
            }
            /************培育佣金***********************/
//            this.NewPeiyu();
//            this.NewPeiyu1();
            this.NewPeiyu2();
            secutitiesService.deleteZxzqReward();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }
    public Map<String, Object> NewPeiyu() throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();

        try {
            //1.选择所有证券用户，并取得他们培育佣金的提成比率   （这样会漏掉zxkj，可以再加一个条件 or member_name='zxkj'
            List<ZxzqAllMember> list = secutitiesService.queryAllMember();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ZxzqAllMember zxzqAllMember = list.get(i);
                    //查询该人员 应的奖励系数
                    ZxzqRewardLevel zxzqqRewardLevel = secutitiesService.queryRewardLeval(zxzqAllMember);
                    if (zxzqqRewardLevel == null) {

                    } else {
                        //查询该人员下级人员业绩总和
                        List<ZxzqMemberAchi> listAchi = secutitiesService.queryMemberAchi(zxzqAllMember);
                        if (listAchi != null && listAchi.size() > 0) {
                            //当两面都有业绩才处理
                            if (listAchi.size() == 2) {
                                BigDecimal leftMoney = new BigDecimal("0");
                                BigDecimal rightMoney = new BigDecimal("0");
                                ZxzqRealPrice zxzqRealPrice0 = secutitiesService.queryAchiOne(listAchi.get(0));
                                ZxzqRealPrice zxzqRealPrice1 = secutitiesService.queryAchiOne(listAchi.get(1));
                                if (zxzqRealPrice0 == null) {
                                } else {
                                    leftMoney = zxzqRealPrice0.getRealprice();
                                }
                                if (zxzqRealPrice1 == null) {
                                } else {
                                    rightMoney = zxzqRealPrice1.getRealprice();
                                }
                                leftMoney = leftMoney.add(listAchi.get(0).getAchievement());
                                rightMoney = rightMoney.add(listAchi.get(1).getAchievement());
                                //处理业绩小的那面
                                int m = leftMoney.compareTo(BigDecimal.ZERO);
                                int n = rightMoney.compareTo(BigDecimal.ZERO);
                                BigDecimal h = new BigDecimal("0");
                                if (m == 1 && n == 1) {
                                    int j = leftMoney.compareTo(rightMoney);
                                    if (j == 1 || j == 0) {// 第一条大于大二条 处理第二条
                                        h = rightMoney;
                                    } else { // 第一条大于小于二条 处理第一条
                                        h = leftMoney;
                                    }
                                    //开始处理 查询该人员以前发放过的业绩奖励
                                    ZxzqSumReward zxzqSumReward = secutitiesService.queryFinshReward(zxzqAllMember);
                                    if (zxzqSumReward == null) {
                                        //为空 直接发放全部
                                        BigDecimal b = h.multiply(zxzqqRewardLevel.getBred());//需发放奖励
                                        ZxzqReward zxzqReward = new ZxzqReward();
                                        zxzqReward.setMemberid(zxzqAllMember.getMember_id());
                                        zxzqReward.setRewardnum(b);
                                        zxzqReward.setRewardpoint(zxzqqRewardLevel.getBred());
                                        //奖金表
                                        secutitiesService.setInfromationReward(zxzqReward);
                                        //奖金表记录
                                        secutitiesService.setInfromationRewardRecord(zxzqReward);
                                        //上三级
                                        secutitiesService.threeReward(zxzqAllMember, b);
                                    } else {
                                        //判断发放过奖金时候等于或小于 要发放奖励
                                        //判断发放过奖金 和 要发放奖金
                                        BigDecimal a = zxzqSumReward.getRewardnum();//发放过奖励
                                        BigDecimal b = h.multiply(zxzqqRewardLevel.getBred());//需发放奖励
                                        //比较
                                        int k = b.compareTo(a);
                                        if (k == 1) {
                                            BigDecimal l = b.subtract(a);
                                            ZxzqReward zxzqReward = new ZxzqReward();
                                            zxzqReward.setMemberid(zxzqAllMember.getMember_id());
                                            zxzqReward.setRewardnum(l);
                                            zxzqReward.setRewardpoint(zxzqqRewardLevel.getBred());
//                                    //奖金表
                                            secutitiesService.setInfromationReward(zxzqReward);
//                                    //奖金表记录
                                            secutitiesService.setInfromationRewardRecord(zxzqReward);
//                                    //上三级
                                            secutitiesService.threeReward(zxzqAllMember, l);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }
    public Map<String, Object> NewPeiyu1() throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();

        try {
            //1.选择所有证券用户，并取得他们培育佣金的提成比率   （这样会漏掉zxkj，可以再加一个条件 or member_name='zxkj'
            List<ZxzqAllMember> list = secutitiesService.queryAllMember();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ZxzqAllMember zxzqAllMember = list.get(i);
                    //查询该人员 应的奖励系数
                    ZxzqRewardLevel zxzqqRewardLevel = secutitiesService.queryRewardLeval(zxzqAllMember);
                    if (zxzqqRewardLevel == null) {

                    } else {
                        //查询两面是否有人
                        List<ZxzqMemberAchi> listAchi = secutitiesService.queryMemberAchi1(zxzqAllMember);
                        if (listAchi != null && listAchi.size() > 0) {
                            //当两面都有业绩才处理
                            if (listAchi.size() == 2) {
                                //查出两面network_team
                                ZxzqMemberAchi   listAchi0=secutitiesService.queryMemberAchi2(listAchi.get(0));
                                ZxzqMemberAchi   listAchi1=secutitiesService.queryMemberAchi2(listAchi.get(1));

                                if(listAchi0 == null && listAchi1==null){
                                }else{
                                    BigDecimal leftMoney  = listAchi0.getAchievement();
                                    BigDecimal rightMoney = listAchi1.getAchievement();
                                    //处理业绩小的那面
                                    int m = leftMoney.compareTo(BigDecimal.ZERO);
                                    int n = rightMoney.compareTo(BigDecimal.ZERO);

                                    BigDecimal h = new BigDecimal("0");
                                    if (m == 1 && n == 1) {
                                        int j = leftMoney.compareTo(rightMoney);
                                        if (j == 1 || j == 0) {// 第一条大于大二条 处理第二条
                                            h = rightMoney;
                                        } else { // 第一条大于小于二条 处理第一条
                                            h = leftMoney;
                                        }
                                        //开始处理 查询该人员以前发放过的业绩奖励
                                        ZxzqSumReward zxzqSumReward = secutitiesService.queryFinshReward(zxzqAllMember);
                                        if (zxzqSumReward == null) {
                                            //为空 直接发放全部
                                            BigDecimal b = h.multiply(zxzqqRewardLevel.getBred());//需发放奖励
                                            ZxzqReward zxzqReward = new ZxzqReward();
                                            zxzqReward.setMemberid(zxzqAllMember.getMember_id());
                                            zxzqReward.setRewardnum(b);
                                            zxzqReward.setRewardpoint(zxzqqRewardLevel.getBred());
                                            //奖金表
                                            secutitiesService.setInfromationReward(zxzqReward);
                                            //奖金表记录
                                            secutitiesService.setInfromationRewardRecord(zxzqReward);
                                            //上三级
                                            secutitiesService.threeReward(zxzqAllMember, b);
                                        } else {
                                            //判断发放过奖金时候等于或小于 要发放奖励
                                            //判断发放过奖金 和 要发放奖金
                                            BigDecimal a = zxzqSumReward.getRewardnum();//发放过奖励
                                            BigDecimal b = h.multiply(zxzqqRewardLevel.getBred());//需发放奖励
                                            //比较
                                            int k = b.compareTo(a);
                                            if (k == 1) {
                                                BigDecimal l = b.subtract(a);
                                                ZxzqReward zxzqReward = new ZxzqReward();
                                                zxzqReward.setMemberid(zxzqAllMember.getMember_id());
                                                zxzqReward.setRewardnum(l);
                                                zxzqReward.setRewardpoint(zxzqqRewardLevel.getBred());
//                                    //奖金表
                                                secutitiesService.setInfromationReward(zxzqReward);
//                                    //奖金表记录
                                                secutitiesService.setInfromationRewardRecord(zxzqReward);
//                                    //上三级
                                                secutitiesService.threeReward(zxzqAllMember, l);
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }

    public Map<String, Object> NewPeiyu2() throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();

        try {
            //1.选择所有证券用户，并取得他们培育佣金的提成比率   （这样会漏掉zxkj，可以再加一个条件 or member_name='zxkj'
            List<ZxzqAllMember> list = secutitiesService.queryAllMember();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ZxzqAllMember zxzqAllMember = list.get(i);
                    //查询该人员 应的奖励系数
                    ZxzqRewardLevel zxzqqRewardLevel = secutitiesService.queryRewardLeval(zxzqAllMember);
                    if (zxzqqRewardLevel == null) {

                    } else {
                        //查询两面是否有人
                        List<ZxzqMemberAchi> listAchi = secutitiesService.queryMemberAchi1(zxzqAllMember);
                        if (listAchi != null && listAchi.size() > 0) {
                            //当两面都有业绩才处理
                            if (listAchi.size() == 2) {
                                //查出两面network_team
                                ZxzqMemberAchi   listAchi0=secutitiesService.queryMemberAchi2(listAchi.get(0));
                                ZxzqMemberAchi   listAchi1=secutitiesService.queryMemberAchi2(listAchi.get(1));

                                if(listAchi0 == null && listAchi1==null){
                                }else{
                                    BigDecimal leftMoney  = listAchi0.getAchievement();
                                    BigDecimal rightMoney = listAchi1.getAchievement();
                                    //处理业绩小的那面
                                    int m = leftMoney.compareTo(BigDecimal.ZERO);
                                    int n = rightMoney.compareTo(BigDecimal.ZERO);

                                    BigDecimal h = new BigDecimal("0");
                                    if (m == 1 && n == 1) {
                                        int j = leftMoney.compareTo(rightMoney);
                                        if (j == 1 || j == 0) {// 第一条大于大二条 处理第二条
                                            h = rightMoney;
                                        } else { // 第一条大于小于二条 处理第一条
                                            h = leftMoney;
                                        }
                                        //开始处理 查询该人员以前发放过的业绩奖励
                                        ZxzqSumReward zxzqSumReward = secutitiesService.queryFinshReward(zxzqAllMember);
                                        if (zxzqSumReward == null) {
                                            //为空 直接发放全部
                                            double result=h.multiply(zxzqqRewardLevel.getBred()).doubleValue();
                                            result=result-(result*(changNum/zxzqqRewardLevel.getBred().doubleValue()));
                                            BigDecimal b = new BigDecimal(result);//需发放奖励
                                            ZxzqReward zxzqReward = new ZxzqReward();
                                            zxzqReward.setMemberid(zxzqAllMember.getMember_id());
                                            zxzqReward.setRewardnum(b);
                                            zxzqReward.setRewardpoint(new BigDecimal(zxzqqRewardLevel.getBred().doubleValue()-changNum));
                                            //奖金表
                                            secutitiesService.setInfromationReward(zxzqReward);
                                            //奖金表记录
                                            secutitiesService.setInfromationRewardRecord(zxzqReward);
                                            //上三级
                                            secutitiesService.threeReward(zxzqAllMember, b);
                                        } else {
                                            //判断发放过奖金时候等于或小于 要发放奖励
                                            //判断发放过奖金 和 要发放奖金
                                            BigDecimal a = zxzqSumReward.getRewardnum();//发放过奖励
                                            BigDecimal b = h.multiply(zxzqqRewardLevel.getBred());//需发放奖励
                                            //比较
                                            int k = b.compareTo(a);
                                            if (k == 1) {
                                                double result=b.subtract(a).doubleValue();
                                                result=result-(result*(changNum/zxzqqRewardLevel.getBred().doubleValue()));
                                                BigDecimal l = new BigDecimal(result);
                                                ZxzqReward zxzqReward = new ZxzqReward();
                                                zxzqReward.setMemberid(zxzqAllMember.getMember_id());
                                                zxzqReward.setRewardnum(l);
                                                zxzqReward.setRewardpoint(new BigDecimal(zxzqqRewardLevel.getBred().doubleValue()-changNum));
//                                    //奖金表
                                                secutitiesService.setInfromationReward(zxzqReward);
//                                    //奖金表记录
                                                secutitiesService.setInfromationRewardRecord(zxzqReward);
//                                    //上三级
                                                secutitiesService.threeReward(zxzqAllMember, l);
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }

    //复投
    @RequestMapping(value = "reCast")
    @ResponseBody

    public Map<String, Object> reCast(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {

            String str= Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
            String str1= str.substring(11,13);
            if(str1.equals("00")||str1.equals("01")){
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "平台结算中，预计开放时间为凌晨 2 点，请您稍后访问");
                return modelMap;
            }
            //判断是否有可提现证券
            ZxzqInformationBO zxzqInformationBO = secutitiesService.queryZxzqInformation(zxzqCommonBO);
            int i = zxzqInformationBO.getSecurities().compareTo(new BigDecimal("10"));
//            zxzqCommonBO.setSecurities(zxzqInformationBO.getSecurities());
            if (i == -1) {
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "您的白拿金余额未达到复投起投点，无法复投");
                return modelMap;
            } else {
                int j = zxzqInformationBO.getSecurities().compareTo(zxzqCommonBO.getSecurities());
                if (j == -1) {
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    modelMap.put("message", "您的白拿金余额不足");
                    return modelMap;
                } else {
                    //判断是否有需要回填的的订单
                    ZxzqRecordBO zxzqRecordBO = secutitiesService.queryFuTou(zxzqCommonBO);
                    if (zxzqRecordBO == null) {
                        modelMap.put("code", "1");
                        modelMap.put("data", null);
                        modelMap.put("message", "您未下过订单,无法使用复投功能");
                        return modelMap;
                    } else {
                        int k = zxzqRecordBO.getPrice().compareTo(BigDecimal.ZERO);
                        if (k != 0) {
                            modelMap.put("code", "1");
                            modelMap.put("data", null);
                            modelMap.put("message", "您的账户尚有白条欠款，请回填欠款后再进行复投");
                            return modelMap;
                        }
                    }
                    //开始复投
                    zxzqCommonBO.setRemark("复投");
                    secutitiesService.reCast(zxzqCommonBO);
                    modelMap.put("code", "0");
                    modelMap.put("data", null);
                    modelMap.put("message", "复投成功");
                    return modelMap;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelMap;
    }

    /**
     * 校验邀请人和上级的网络位置是否在一条线上
     */
    @RequestMapping("checkNetworkteam")
    @ResponseBody
    public Map<String, Object> checkNetworkteam(@RequestBody DoRegisterVo doRegisterVo) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {

            if (secutitiesService.queryIsConnect(doRegisterVo)==0){
                modelMap.put("message", "邀请人ID和管理者ID网络位置可用");
                modelMap.put("code", "0");
                modelMap.put("data", null);
                return modelMap;
            }else{
                modelMap.put("message", "邀请人ID和管理者ID网络位置不可用");
                modelMap.put("code", "1");
                modelMap.put("data", null);
                return modelMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("message", "系统内部错误");
            modelMap.put("code", "1");
            modelMap.put("data", null);
            return modelMap;
        }
    }

    /**
     * 校验邀请人和上级的网络位置是否在一条线上
     */
    @RequestMapping("test0")
    @ResponseBody
    public Map<String, Object> test0() throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        try {
            List<String> list = new ArrayList<String>();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream("C:\\Users\\Administrator\\Desktop\\abc.txt")));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                list.add(line);
            }
            br.close();


            int i = secutitiesService.insertDataByList(list);
            System.out.println(i);
            modelMap.put("data", i);
            modelMap.put("message", "完成");
            modelMap.put("code", "0");
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }

        return modelMap;
    }

    /**
     * 自动复投
     */
    @RequestMapping("automatic")
    @ResponseBody
    public Map<String, Object> automatic() throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            //考虑后台自动升级的情况可能欠款
            secutitiesService.automatic();

            modelMap.put("message", "复投成功");
            modelMap.put("code", "0");
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }

        return modelMap;
    }


    /**
     * 自动复投
     */
    @RequestMapping("confirmAutomatic")
    @ResponseBody
    public Map<String, Object> confirmAutomatic(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            //考虑后台自动升级的情况可能欠款
            secutitiesService.confirmAutomatic(zxzqCommonBO);
            modelMap.put("message", "操作成功");
            modelMap.put("code", "0");
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
        return modelMap;
    }

    /**
     * 初始化注册数据接口
     * app入口注册
     */



    @RequestMapping("memberdata")
    @ResponseBody
    public Map<String, Object> memberdata(@RequestBody ZxzqCommonBO zxzqCommonBO) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {

            secutitiesService.initializtionZxzq(zxzqCommonBO.getMemberid());
            modelMap.put("message", "初始化成功");
            modelMap.put("code", "0");
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
        return modelMap;
    }



    /**
     * 证券订单生成
     * memberid 和 id 对应zxzq_goods 里id
     */
    @RequestMapping(value = "newSaveRecord")
    @ResponseBody
    public Map<String, Object> newSaveRecord(@RequestBody ZxzqRecordVo zxzqRecordVo) throws Exception {
        Map<String, Object> map = Tools.errMessageSystem();
        try {
            String str= Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
            String str1= str.substring(11,13);
            if(str1.equals("00")||str1.equals("01")){
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "平台结算中，预计开放时间为凌晨 2 点，请您稍后访问");
                return map;
            }

            ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
            zxzqCommonBO.setId(zxzqRecordVo.getId());
            zxzqCommonBO.setMemberid(zxzqRecordVo.getMemberid());

            // 购买证券判断是否有欠款
//            ZxzqRecordBO zxzqRecordBO = secutitiesService.checkRecordRealpriceNew(zxzqCommonBO);
//            if (zxzqRecordBO == null) {
//                //未下过订单
//            } else {
//                //下过订单
//                int i = zxzqRecordBO.getPrice().compareTo(BigDecimal.ZERO);
//                if (i == 1) {
//                    map.put("code", "1");
//                    map.put("data", null);
//                    map.put("message", "您的账户存在未结清的购买记录，请结清该笔订单后再进行购买");
//                    return map;
//                }
//            }

            //获取当日兑换率
//            ZxzqDrawCashBO zxzqDrawCashBO = secutitiesService.queryRate(zxzqCommonBO);
            // 通过id 查询购买证券的投资额
//            ZxzqGoodsBo zxzqGoodsBo = secutitiesService.queryZxzqGoods(zxzqCommonBO);

            //判断钱包余额是否充值
//            ZxzqWalletBo zxzqWalletBo = secutitiesService.queryZxzqWallet(zxzqCommonBO);
//            if (zxzqWalletBo == null) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", "您的钱包账户不存在，请及时联系客服");
//                return map;
//            } else {
                //判断钱包金额是否足够购买证券
//                int j = zxzqWalletBo.getMoney().compareTo((zxzqGoodsBo.getSecurities().multiply(zxzqDrawCashBO.getMoney())));
                //当钱包余额大于等于时可以提现
//                if (j == -1) {
//                    map.put("code", "1");
//                    map.put("data", null);
//                    map.put("message", "您的钱包余额不足，请及时充值");
//                    return map;
//                } else {
                    //购买证券开始
                    secutitiesService.newSaveRecord(zxzqCommonBO);
                    map.put("code", "0");
                    map.put("data", null);
                    map.put("message", "购买成功");
                    return map;

//                }

//            }
        } catch (Exception e) {
            e.printStackTrace();
            map = Tools.errMessageSystem();
            return map;
        }
    }

    /**
     * 新流通币提现
     * @param zxzqRecordVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "extract")
    @ResponseBody
    public Map<String, Object> extract(@RequestBody ZxzqRecordVo zxzqRecordVo) throws Exception {

        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {
            ZxzqCommonBO zxzqCommonBO = new ZxzqCommonBO();
            zxzqCommonBO.setMemberid(zxzqRecordVo.getMemberid());
            ZxzqDrawCashBO bo = secutitiesService.queryZxzqDrawcash(zxzqCommonBO);

            if (bo == null) {
                modelMap.put("code", "1");
                modelMap.put("data", null);
                modelMap.put("message", "该用户不存在");
                return modelMap;
            } else {
                ZxzqExtract zxzqExtract=new ZxzqExtract();
                String address=secutitiesService.queryAddress(zxzqRecordVo);
                zxzqExtract.setCreateAddress(address);
                //除法保留两位小数
//                zxzqExtract.setAddress(address);
               BigDecimal cash= new BigDecimal(String.valueOf(bo.getSecuritiesnum().divide(bo.getMoney(), 2, RoundingMode.HALF_DOWN)));
                zxzqExtract.setCash(cash.multiply(new BigDecimal(0.95)).setScale(2,RoundingMode.HALF_DOWN));

                zxzqExtract.setMemberid(zxzqRecordVo.getMemberid());
                zxzqExtract.setFrozen(bo.getSecuritiesnum());
                zxzqExtract.setRate(bo.getMoney());
                long time = System.currentTimeMillis() / 1000;
                zxzqExtract.setOddnumbers(RandomCode.getChar() + time);

                int i = cash.compareTo(new BigDecimal("100"));
                if (i == 1 || i == 0) {
                    modelMap = secutitiesService.extractRecord(zxzqExtract);
                    //不欠款的时候80%体现20%复投 放开即可
                    //modelMap = secutitiesService.withdrawCashRecord1(zxzqCommonBO);
                    ZxzqExtractBO zxzqExtractBO =new ZxzqExtractBO();
                    zxzqExtractBO.setMoney(zxzqExtract.getCash());
                    zxzqExtractBO.setSecuritiesnum(bo.getSecuritiesnum());
                    modelMap.put("code", "0");
                    modelMap.put("data", zxzqExtractBO);
                    modelMap.put("message", "流通币提取成功");
                    return modelMap;
                } else {
                    modelMap.put("code", "1");
                    modelMap.put("data", null);
                    modelMap.put("message", "您的可提取余额不足，无法提取");
                    return modelMap;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap = Tools.errMessageSystem();
            return modelMap;
        }

    }

    /**
     * 流通币确认提现
     * @param zxzqExtract
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "confirmExtract")
    @ResponseBody
    public Map<String, Object> confirmExtract(@RequestBody ZxzqExtract zxzqExtract) throws Exception {

        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {
            if(zxzqExtract.getStatus()==0) {
                //确认申请


                secutitiesService.confirmExtract(zxzqExtract);
                modelMap.put("code", "0");
                modelMap.put("data", null);
                modelMap.put("message", "确认提现成功");
            }else{
                //撤销申请
              return   secutitiesService.cancelExtract(zxzqExtract);

            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap = Tools.errMessageSystem();
            return modelMap;
        }
            return modelMap;
    }

    @RequestMapping(value = "writeyeji")
    @ResponseBody
    public Map<String, Object> writeyeji(@RequestBody ZxzqMemberBo date) throws Exception {
        List<String> list=new ArrayList<String>();
        list.add("李菊华");
        list.add("颜世杰");
        list.add("张鑫鑫");
        list.add("smw6699");
        list.add("刘玉芝");
        list.add("wfa");
        list.add("huiping");
        list.add("weijun");
        list.add("liangyinghua");
        list.add("lt888");
        list.add("wang_17");
        list.add("zhoulili");
        list.add("wang_01");
        list.add("wang999");
        list.add("lxh666");
        list.add("meiling");
        list.add("zyl2343");
        list.add("马炳章2");
        list.add("dxp170116");
        list.add("李淑琴");
        Map<String, Object> modelMap = Tools.errMessageSystem();
        BufferedWriter writer=null;
        ZxzqMemberBo zxzqMemberBo=new ZxzqMemberBo();
        BigDecimal bigDecimal=null;
        try {
            File file=new File("C:\\Users\\Administrator\\Desktop\\yeji.txt");
            OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(file));
            writer=new BufferedWriter(out);
            for (String s : list) {
                zxzqMemberBo.setMember_name(s);
                zxzqMemberBo=secutitiesService.queryNetwork_teamByMember_name(zxzqMemberBo);
                if(zxzqMemberBo==null){
                    continue;
                }
                zxzqMemberBo.setStartDate(date.getStartDate());
                zxzqMemberBo.setEndDate(date.getEndDate());
                zxzqMemberBo.setNetwork_team(zxzqMemberBo.getNetwork_team() + "%");
                bigDecimal=secutitiesService.queryYeJi(zxzqMemberBo);
                if(bigDecimal==null){
                    bigDecimal=new BigDecimal(0.00);
                }
                writer.write(s+"的业绩："+bigDecimal.toString()+"\n");
            }
            bigDecimal=secutitiesService.queryAllYeJi(date);
            writer.write("\n上周不含复投总业绩："+bigDecimal.toString());
//            writer.write();
        } catch (Exception e) {
            e.printStackTrace();
            modelMap = Tools.errMessageSystem();
            return modelMap;
        }finally {
            writer.close();
        }
        modelMap.put("code","0");
        modelMap.put("message","导出成功");
        modelMap.put("data","null");
        return modelMap;
    }
    /**
     * 证券提现显示
     */
    @RequestMapping(value = "updateMemberNetwork_id")
    @ResponseBody
    public Map<String, Object> updateMemberNetwork_id() throws Exception {
        Map<String, Object> modelMap = Tools.errMessageSystem();
        try {
            List<Integer> list=secutitiesService.updateid();
            modelMap.put("message","完成");
            modelMap.put("code","1");
            modelMap.put("data",list);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap = Tools.errMessageSystem();
            return modelMap;
        }
            return modelMap;
    }

    //查询业绩
    @RequestMapping(value = "queryYj")
    @ResponseBody
    public void queryYj() throws Exception {
        try {
            //查询所有 有推荐上级和管理上级
            List<ZxzqAllMember> list=secutitiesService.queryAllMember();
            //遍历所有人员

            for(int i = 0 ; i<list.size() ;i++){
                ZxzqAllMember zxzqAllMember =list.get(i);
                //查询两面是否有人
                List<ZxzqMemberAchi> listAchi = secutitiesService.queryMemberAchi1(zxzqAllMember);
                if (listAchi != null && listAchi.size() > 0) {
                    //当两面都有业绩才处理
                    if (listAchi.size() == 2) {
                        //查出两面network_team
                        ZxzqMemberAchi   listAchi0=secutitiesService.queryMemberAchi2(listAchi.get(0));
                        ZxzqMemberAchi   listAchi1=secutitiesService.queryMemberAchi2(listAchi.get(1));

                        if(listAchi0 == null && listAchi1==null){
                        }else{
                            BigDecimal leftMoney  = listAchi0.getAchievement();
                            BigDecimal rightMoney = listAchi1.getAchievement();
                            //处理业绩小的那面
                            int m = leftMoney.compareTo(BigDecimal.ZERO);
                            int n = rightMoney.compareTo(BigDecimal.ZERO);
                            BigDecimal h = new BigDecimal("0");//小区
                            BigDecimal k = new BigDecimal("0");//大区
                            if (m == 1 && n == 1) {
                                int j = leftMoney.compareTo(rightMoney);
                                if (j == 1 || j == 0) {
                                    h = rightMoney;
                                    k =leftMoney;
                                } else {
                                    h = leftMoney;
                                    k =rightMoney;
                                }
                            }
                            //判断大区是否有小区5分之2
                            BigDecimal l =new BigDecimal("0.4");
                            int b =k.multiply(l).compareTo(h);
                            if(b == 1){
                                ZxzqYjBo zxzqYjBo =new ZxzqYjBo();
                                zxzqYjBo.setBigyj(k);
                                zxzqYjBo.setSmallyj(h);
                                zxzqYjBo.setMember_id(zxzqAllMember.getMember_id());
                                zxzqYjBo.setMember_name(zxzqAllMember.getMember_name());
                                zxzqYjBo.setMember_sn(zxzqAllMember.getMember_sn());
                                //将取出值存入近数据库
                                secutitiesService.saveYj(zxzqYjBo);
                            }
                        }

                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
