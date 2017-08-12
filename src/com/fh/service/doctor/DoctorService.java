//package com.fh.service.doctor;
//
//import com.fh.dao.DaoSupport;
//import com.fh.entity.bo.*;
//
//import com.fh.entity.system.User;
//
//
//import com.fh.entity.vo.FeedBackVo;
//
//import com.fh.entity.vo.TokenVo;
//import com.fh.entity.vo.UserInfoVO;
//import com.fh.entity.zxys.*;
//import com.fh.huanxin.model.TalkNode;
//import com.fh.huanxin.service.TalkDataService;
//import com.fh.huanxin.service.impl.TalkDataServiceImpl;
//import com.fh.huanxin.service.impl.TalkHttpServiceImplApache;
//import com.fh.huanxin.tool.JsonTool;
//import com.fh.util.*;
//import com.google.gson.Gson;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by admin on 2016/12/12.
// */
//@Service("doctorService")
//public class DoctorService {
//
//    @Resource(name = "daoSupport")
//    private DaoSupport dao;
//
//    /**
//     * 通过手机号查询用户信息
//     *
//     * @param userInfoVO
//     * @return
//     */
//    public UserInfoBO checkMobileIsExist(UserInfoVO userInfoVO) throws Exception {
//        UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("DoctorMapper.queryMemberByMobile", userInfoVO);
//        return userInfoBO;
//    }
//
//    /**
//     * 医生注册
//     *
//     * @param userInfoVO
//     * @return
//     */
//    public boolean registerDocter(UserInfoVO userInfoVO) {
//        try {
//            dao.save("DoctorMapper.saveBaseDoctor", userInfoVO);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 校验验证码
//     *
//     * @param vo
//     * @return
//     */
//    public SmsBO confirmValidate(TokenVo vo) throws Exception {
//        SmsBO smsBO = (SmsBO) dao.findForObject("DoctorMapper.confirmValidate", vo);
//        return smsBO;
//    }
//
//    /*
//     * 注册短信验证
//    */
//    public TokenVo validateForegister(TokenVo vo) throws Exception {
//        TokenVo reVo = new TokenVo();//返回前台值
//        MessageBO bo = new MessageBO(); //短信接口调用
//        SmsBO sms = new SmsBO();//信息存储
//        //生成6为随机数
//        String str = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//        reVo.setCode(str);
//        String message = "";
//        String time = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
//        if ("1".equals(vo.getType())) {
//            message = "您于" + time + "申请注册用户，验证码：" + str + " ,30分钟内有效。";
//        } else if ("3".equals(vo.getType())) {
//            message = "您于" + time + "申请重置登录密码，验证码：" + str + ",30分钟内有效。";
//        }
////        else if("2".equals(vo.getType())){
////            message="您于"+time+"设置支付密码，验证码："+str+",30分钟内有效。";
////        }else if("4".equals(vo.getType())){
////            message="您于"+time+"解绑手机号，验证码："+str+",30分钟内有效。";
////        }else if("5".equals(vo.getType())){
////            message="您于"+time+"绑定手机号，验证码："+str+",30分钟内有效。";
////        }
//
//        bo.setMobiles(vo.getPhone());//获取电话号
//        bo.setContent(message);//获取电话号
//        String[] code = HttpSender.message(bo).split(",");
//        if ("0".equals(code[1].substring(0, 1))) {//发送成功
//            message = "【中孝医生】" + message;
//            sms.setLog_captcha(str);
//            sms.setLog_phone(vo.getPhone());
//            if ("1".equals(vo.getType())) {
//                sms.setLog_msg(message);
//                sms.setLog_type(1);
//            } else if ("3".equals(vo.getType())) {
//                sms.setLog_msg(message);
//                sms.setLog_type(3);
//            }
////              else if("2".equals(vo.getType())){
////                sms.setLog_msg(message);
////                sms.setLog_type(2);
////            }else if("4".equals(vo.getType())){
////                sms.setLog_msg(message);
////                sms.setLog_type(4);
////            }else if("5".equals(vo.getType())){
////                sms.setLog_msg(message);
////                sms.setLog_type(5);
////            }
//
//            dao.save("DoctorMapper.setSms", sms);
//        } else {
//            reVo.setCode("000000");
//            return reVo;
//        }
//        return reVo;
//    }
//
//    /**
//     * 查询固定值列表
//     *
//     * @param type
//     * @return
//     */
//    public List<ZxysVariableBO> queryVariable(int type) throws Exception {
//        List<ZxysVariableBO> list = (List<ZxysVariableBO>) dao.findForList("DoctorMapper.queryVariable", type);
//        return list;
//    }
//
//    /**
//     * 保存医生认证信息
//     *
//     * @param doctorVO
//     */
//    public boolean authenticationDoctor(ZxysDoctorVO doctorVO) throws Exception {
//        try {
//            // 保存基本信息
//            dao.update("DoctorMapper.updateBaseDoctor", doctorVO);
//            // 保存认证信息
//            dao.save("DoctorMapper.saveAuthentication", doctorVO);
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 模糊查询医院
//     *
//     * @param hospitalVO
//     * @return
//     */
//    public List<ZxysHospitalBO> queryHospitalFuzzy(ZxysHospitalVO hospitalVO) throws Exception {
//        List<ZxysHospitalBO> list = (List<ZxysHospitalBO>) dao.findForList("DoctorMapper.queryHospitalFuzzy", hospitalVO);
//        return list;
//    }
//
//    /**
//     * 通过一生的用户名查询医生信息
//     *
//     * @param userInfoVO
//     * @return
//     */
//    public UserInfoBO queryDoctorByUserName(UserInfoVO userInfoVO) throws Exception {
//        UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("DoctorMapper.queryDoctorByUserName", userInfoVO);
//        return userInfoBO;
//    }
//
//    /**
//     * 绑定银行卡
//     *
//     * @param doctorVO
//     * @return
//     */
//    public boolean saveBankCardInfo(ZxysDoctorVO doctorVO) {
//        try {
//            dao.update("DoctorMapper.saveBankCardInfo", doctorVO);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 通过token查询用户信息
//     *
//     * @param tokenVo
//     * @return
//     */
//    public UserInfoBO checkMemberByToken(TokenVo tokenVo) throws Exception {
//        UserInfoBO userInfoBO = (UserInfoBO) dao.findForObject("DoctorMapper.checkMemberByToken", tokenVo);
//        return userInfoBO;
//    }
//
//    /**
//     * 校验医生是否已经认证
//     * @param doctorVO
//     * @return
//     */
//    public ZxysDoctorBO checkAuthentication(ZxysDoctorVO doctorVO) throws Exception {
//        ZxysDoctorBO doctorBO = (ZxysDoctorBO) dao.findForObject("DoctorMapper.checkAuthentication", doctorVO);
//        return doctorBO;
//    }
//
//    /**
//     * 开通服务
//     *
//     * @param serviceVO
//     * @return
//     */
//    public boolean updateService(ZxysServiceVO serviceVO) {
//        try {
//            dao.update("DoctorMapper.updateService", serviceVO);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 查询科室列表
//     *
//     * @param officeVO
//     * @return
//     */
//    public List<ZxysOfficeBO> queryOfficeByParentId(ZxysOfficeVO officeVO) throws Exception {
//        List<ZxysOfficeBO> list = (List<ZxysOfficeBO>) dao.findForList("DoctorMapper.queryOfficeByParentId", officeVO);
//        return list;
//    }
//
//    /**
//     * 记录token信息
//     *
//     * @param tokenBo
//     */
//    public void saveTokenInfo(TokenBO tokenBo) throws Exception {
//        dao.save("DoctorMapper.saveTokenInfo", tokenBo);
//    }
//
//    /**
//     * 手机号和密码查询用户
//     *
//     * @param userInfoVO
//     * @return
//     */
//    public UserInfoBO queryMemberByUserNameAndPwd(UserInfoVO userInfoVO) throws Exception {
//        UserInfoBO user = (UserInfoBO) dao.findForObject("DoctorMapper.queryMemberByUserNameAndPwd", userInfoVO);
//        return user;
//    }
//
//    /**
//     * 更新用户登录信息
//     *
//     * @param userInfoBO
//     */
//    public void updateMemberLoginInfo(UserInfoBO userInfoBO) throws Exception {
//
//        dao.update("DoctorMapper.updateMemberLoginInfo", userInfoBO);
//
//    }
//
//    /**
//     * 保存登录记录
//     *
//     * @param loginLogBo
//     */
//    public void saveLoginLog(LoginLogBo loginLogBo) throws Exception {
//        dao.save("DoctorMapper.saveLoginLog", loginLogBo);
//    }
//
//    /**
//     * 通过用户id查询token记录
//     *
//     * @param userInfoBO
//     * @return
//     */
//    public TokenBO queryTokenByMemberId(UserInfoBO userInfoBO) throws Exception {
//        TokenBO tokenBO = (TokenBO) dao.findForObject("DoctorMapper.queryTokenByMemberId", userInfoBO);
//        return tokenBO;
//    }
//
//    /**
//     * 更新用户的token
//     *
//     * @param tokenBO
//     */
//    public void updateToken(TokenBO tokenBO) throws Exception {
//        dao.update("DoctorMapper.updateToken", tokenBO);
//    }
//
//    /**
//     * 通过手机号码和验证码查询登录记录
//     *
//     * @param tokenVo
//     * @return
//     */
//    public SmsBO findSMSLogByPhoneAndCode(TokenVo tokenVo) throws Exception {
//        SmsBO bo = (SmsBO) dao.findForObject("DoctorMapper.findSMSLogByPhoneAndCode", tokenVo);
//        return bo;
//    }
//
//
//    /**
//     * 修改用户密码
//     *
//     * @param tokenVo
//     */
//    public void resetMemberPwd(TokenVo tokenVo) throws Exception {
//        dao.update("DoctorMapper.resetMemberPwd", tokenVo);
//    }
//
//    /**
//     * 查询医生未完成订单记录
//     *
//     * @param userInfoBO
//     * @return
//     */
//    public List<ZxysFinishRecordList> queryUncompleteRecord(UserInfoBO userInfoBO) throws Exception {
//
//        if (userInfoBO.getPageIndex() != 0 && userInfoBO.getPageSize() != 0) {
//            userInfoBO.setStartIndex((userInfoBO.getPageIndex() - 1) * userInfoBO.getPageSize());
//        }
//        List<ZxysFinishRecordList> list = (List<ZxysFinishRecordList>) dao.findForList("DoctorMapper.queryUncompleteRecord", userInfoBO);
//        return list;
//    }
//
//    /**
//     * 查询医生完成订单记录
//     *
//     * @param userInfoBO
//     * @return
//     */
//    public List<ZxysFinishRecordList> queryCompletedRecord(UserInfoBO userInfoBO) throws Exception {
//        if (userInfoBO.getPageIndex() != 0 && userInfoBO.getPageSize() != 0) {
//            userInfoBO.setStartIndex((userInfoBO.getPageIndex() - 1) * userInfoBO.getPageSize());
//        }
//        List<ZxysFinishRecordList> list = (List<ZxysFinishRecordList>) dao.findForList("DoctorMapper.queryCompletedRecord", userInfoBO);
//        return list;
//    }
//
//    /**
//     * 通过memberid查询医生信息
//     *
//     * @param userInfoBO
//     * @return
//     */
//    public ZxysDoctorBO queryDoctorByMemberId(UserInfoBO userInfoBO) throws Exception {
//        ZxysDoctorBO doctorBO = (ZxysDoctorBO) dao.findForObject("DoctorMapper.queryDoctorByMemberId", userInfoBO);
//        return doctorBO;
//    }
//
//
//    /**
//     * 查询成就
//     *
//     * @param achievement
//     * @return
//     */
//    public int queryAchievementCount(ZxysAchievement achievement) throws Exception {
//        int count = (Integer) dao.findForObject("DoctorMapper.queryAchievementCount", achievement);
//        return count;
//    }
//
//    /**
//     * 医生成就集合
//     *
//     * @param achievement
//     * @return
//     */
//    public Map<String, Object> queryAchievement(ZxysAchievement achievement) throws Exception {
//        achievement.setAchievement("热门医生");
//        int hotCount = this.queryAchievementCount(achievement);
//        achievement.setAchievement("好评医生");
//        int praiseCount = this.queryAchievementCount(achievement);
//        achievement.setAchievement("劳模医生");
//        int workerCount = this.queryAchievementCount(achievement);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("hot", hotCount);
//        map.put("praise", praiseCount);
//        map.put("worker", workerCount);
//
//        return map;
//    }
//
//    /**
//     * 更新医生擅长
//     *
//     * @param doctorVO
//     */
//    public void updateAdept(ZxysDoctorVO doctorVO) throws Exception {
//        dao.update("DoctorMapper.updateAdept", doctorVO);
//
//    }
//
//    /**
//     * 获取医生近期成就
//     *
//     * @param achievement
//     * @return
//     */
//    public int queryCurrentAchievementCount(ZxysAchievement achievement) throws Exception {
//        int count = (Integer) dao.findForObject("DoctorMapper.queryCurrentAchievement", achievement);
//        return count;
//    }
//
//    /**
//     * 查询近期成就
//     *
//     * @param achievement
//     * @return
//     * @throws Exception
//     */
//    public Map<String, Object> queryCurrentAchievement(ZxysAchievement achievement) throws Exception {
//        achievement.setAchievement("热门医生");
//        int hotCount = this.queryCurrentAchievementCount(achievement);
//        achievement.setAchievement("好评医生");
//        int praiseCount = this.queryCurrentAchievementCount(achievement);
//        achievement.setAchievement("劳模医生");
//        int workerCount = this.queryCurrentAchievementCount(achievement);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        map.put("hot", hotCount);
//        map.put("praise", praiseCount);
//        map.put("worker", workerCount);
//
//        return map;
//    }
//
//    /**
//     * 上传头像
//     *
//     * @param doctorVO
//     */
//    public void updateAvatar(ZxysDoctorVO doctorVO) throws Exception {
//        dao.update("DoctorMapper.updateAvatar", doctorVO);
//    }
//
//    /**
//     * 上传胸牌
//     *
//     * @param doctorVO
//     */
//    public void updateCredentials(ZxysDoctorVO doctorVO) throws Exception {
//        dao.update("DoctorMapper.updateCredentials", doctorVO);
//    }
//
//    /**
//     * 医生评价列表
//     *
//     * @param recordVO
//     * @return
//     */
//    public List<ZxysCommentContentBO> queryCommentList(ZxysRecordVO recordVO) throws Exception {
//
//        // 获取查看医生的评论内容
//        List<ZxysCommentContentBO> commentContentList = (List<ZxysCommentContentBO>) dao.findForList("DoctorMapper.queryCommentContent", recordVO);
//
//        if (commentContentList != null && commentContentList.size() > 0) {
//            for (int i = 0; i < commentContentList.size(); i++) {
//                // 评价内容不为空，做处理
//                if (commentContentList.get(i).getComment_content() != null || !"".equals(commentContentList.get(i).getComment_content())
//                        || commentContentList.get(i).getLabel() != null || !"".equals(commentContentList.get(i).getLabel())
//                        || commentContentList.get(i).getStart_level() != null || !"".equals(commentContentList.get(i).getStart_level())) {
//                    String createTime = commentContentList.get(i).getCreate_time();
//                    createTime = StringUtils.substringBefore(createTime, " ");
//                    commentContentList.get(i).setCreate_time(createTime);
//
//                    String label = commentContentList.get(i).getLabel();
//                    if (label != null && !"".equals(label) && !"(null)".equals(label)) {
//                        String str[] = label.split(",");
//                        List<ZxysVariableBO> variableBoList = new ArrayList<ZxysVariableBO>();
//                        for (int k = 0; k < str.length; k++) {
//                            ZxysVariableBO variableBO = new ZxysVariableBO();
//                            Integer id = Integer.parseInt(str[k]);
//                            // 获取评论内容中的评论标签
//                            variableBO = (ZxysVariableBO) dao.findForObject("Treatment.queryVariableValue", id);
//                            variableBoList.add(variableBO);
//                        }
//                        commentContentList.get(i).setVariableBOList(variableBoList);
//                    }
//                }
//            }
//        }
//        return commentContentList;
//    }
//
//
//    /**
//     * 查看医生的认证信息
//     *
//     * @param userInfoBO
//     * @return
//     * @throws Exception
//     */
//    public ZxysDoctorBO queryDoctorIsauthentication(UserInfoBO userInfoBO) throws Exception {
//        ZxysDoctorBO zxysDoctorBO = (ZxysDoctorBO) dao.findForObject("DoctorMapper.queryAuthenticationByMemberid", userInfoBO);
//        return zxysDoctorBO;
//    }
//
//    public void bindingBankCard(UserInfoBO userInfoBO) throws Exception {
//        dao.update("DoctorMapper.bindingBankCard", userInfoBO);
//    }
//
//
//    /**
//     * 查询所有医院
//     *
//     * @return
//     */
//    public List<ZxysHospitalBO> queryHospital() throws Exception {
//        List<ZxysHospitalBO> list = (List<ZxysHospitalBO>) dao.findForList("DoctorMapper.queryHospital", null);
//        return list;
//    }
//
//    /**
//     * 解绑银行
//     *
//     * @param userInfoBO
//     * @throws Exception
//     */
//    public void unBindingBankCard(UserInfoBO userInfoBO) throws Exception {
//        dao.update("DoctorMapper.unBindingBankCard", userInfoBO);
//    }
//
//    public List<BindBankCardBO> queryBindingBankInfo(UserInfoBO userInfoBO) throws Exception {
//        List<BindBankCardBO> list = (List<BindBankCardBO>) dao.findForList("DoctorMapper.queryBanksByDoctorid", userInfoBO);
//        return list;
//    }
//
//    /**
//     * 联系我们
//     *
//     * @return
//     */
//    public List<ConnectionBO> tellUs() throws Exception {
//        List<ConnectionBO> list = (List<ConnectionBO>) dao.findForList("DoctorMapper.queryConnection", null);
//        return list;
//    }
//
//    /**
//     * 修改/设置支付密码
//     */
//    public void modifyMemberpaypwd(UserInfoBO userInfoBO) throws Exception {
//        dao.update("DoctorMapper.modifyMemberpaypwd", userInfoBO);
//
//    }
//
//    public UserInfoBO queryPaypwd(UserInfoBO userInfoBO) throws Exception {
//        userInfoBO = (UserInfoBO) dao.findForObject("Common.queryMemberPaypwdByMemberId", userInfoBO);
//        return userInfoBO;
//    }
//
//
//    /**
//     * 通过订单号更新订单状态
//     *
//     * @param recordVO
//     */
//    public void updateRecordStatusByOutTradeNo(ZxysRecordVO recordVO) throws Exception {
//        dao.update("DoctorMapper.updateRecordStatusByOutTradeNo", recordVO);
//    }
//
//    public List<UserInfoBO> queryMemberByIshuanxin() throws Exception {
//        List<UserInfoBO> list = (List<UserInfoBO>) dao.findForList("DoctorMapper.queryMemberByIshuanxin", null);
//        return list;
//    }
//
//    /**
//     * 批量注册环信
//     *
//     * @param list
//     * @return
//     * @throws Exception
//     */
//    public List<Integer> registerhuanxin(List<UserInfoBO> list) throws Exception {
//        //初始化注册业务服务
//        TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
//        //修改数据业务Token
////        service.setToken();
//        List<Integer> listuid = new ArrayList<Integer>();
//        //环信注册IDlist
//        List<String> listid = new ArrayList<String>();
//        //环信注册密码list
//        List<String> listpasswd = new ArrayList<String>();
//        //存储memberid的list
//        List<Integer> ids = new ArrayList<Integer>();
//        //遍历所有未注册用户的memberid
//        for (UserInfoBO userInfoBO : list) {
//            //每循环一次记录下操作的memberid
//            ids.add(userInfoBO.getMember_id());
//            //拼接memberid
//            listid.add("zxkj_" + userInfoBO.getMember_id());
//            listpasswd.add(MD5.md5("123456"));
//            //环信最多一次批量注册20条
//            if (listid.size() == 20) {
//                String[] arrayid = listid.toArray(new String[listid.size()]);
//                String[] arraypasswd = listpasswd.toArray(new String[listpasswd.size()]);
//                //批量注册
//                String str = JsonTool.write(service.userSave(arrayid, arraypasswd, null));
//                System.out.println(str);
//                Gson gson = new Gson();
//                TalkNode tn = new TalkNode();
//                //gson解析成TalkNode
//                tn = gson.fromJson(str, TalkNode.class);
//                //判断批量注册请求是否成功
//                if (tn.getStatusCode() !=null) {
//                    //不成功记录下此次请求的所有memberid
//                    for (Integer id : ids) {
//                        listuid.add(id);
//                    }
//                } else {
//                    for (TalkUser talkUser : tn.getEntities()) {
//                        if (talkUser.getActivated() != true) {
//                            //把注册失败activated=false的用户截取出memberid存入listuid
//                            listuid.add(Integer.parseInt(talkUser.getUsername().substring(5).trim()));
//                        }
//                    }
//                }
//                listid.clear();
//                listpasswd.clear();
//                ids.clear();
//
//            }
//
//        }
//        if (listid.size() > 0) {
//            //剩下不足20的批量注册
//            String[] arrayid = listid.toArray(new String[listid.size()]);
//            String[] arraypasswd = listpasswd.toArray(new String[listpasswd.size()]);
//            String str = JsonTool.write(service.userSave(arrayid, arraypasswd, null));
//            System.out.println(str);
//            Gson gson = new Gson();
//            TalkNode tn = new TalkNode();
//            //gson解析成TalkNode
//            tn = gson.fromJson(str, TalkNode.class);
//            //判断批量注册请求是否成功
//            if (tn.getStatusCode()!=null ) {
//                //不成功记录下此次请求的所有memberid
//                for (Integer id : ids) {
//                    listuid.add(id);
//                }
//            } else {
//                for (TalkUser talkUser : tn.getEntities()) {
//                    if (talkUser.getActivated() != true) {
//                        //把注册失败activated=false的用户截取出memberid存入listuid
//                        listuid.add(Integer.parseInt(talkUser.getUsername().substring(5).trim()));
//                    }
//                }
//            }
//        }
//        //先全部把is_huanxin字段状态置成1
//            this.updateIshuanxinTrue();
//        //判断listuid注册失败的用户有没有
//        if (listuid.size() != 0) {
//            //有注册失败的
//            this.updateIshuanxinFalse(listuid);
//            //将注册失败的用户id返回
//            return listuid;
//        }
//        return null;
//    }
//
//    /**
//     * 全部成员is_huanxin置1
//     * @throws Exception
//     */
//    public void updateIshuanxinTrue() throws Exception {
//        dao.update("DoctorMapper.modifyIshuanxinTrue",null);
//    }
//
//    /**
//     * 根据
//     * @param list
//     * @throws Exception
//     */
//    public void updateIshuanxinFalse(List<Integer> list) throws Exception {
//        List<UserInfoBO> memberids=new ArrayList<UserInfoBO>();
//        UserInfoBO user=new UserInfoBO();
//        for (Integer member_id : list) {
//            //根据memberid置0用户的环信状态
//            dao.update("DoctorMapper.modifyIshuanxinFalse",member_id);
//        }
////        dao.update("DoctorMapper.modifyIshuanxinFalse",memberids);
//    }
//public ZxysDoctorBO queryDoctorByID(ZxysDoctorBO zxysDoctorBO) throws Exception {
//    zxysDoctorBO= (ZxysDoctorBO) dao.findForObject("DoctorMapper.queryDoctorById",zxysDoctorBO);
//    return zxysDoctorBO;
//}
//
//    public void deleteDoctor(ZxysDoctorBO zxysDoctorBO) throws Exception {
//        dao.delete("DoctorMapper.deleteDoctorSms",zxysDoctorBO);
//        dao.delete("DoctorMapper.deleteDoctorAuthentication",zxysDoctorBO);
//        dao.delete("DoctorMapper.deleteDoctorAchievement",zxysDoctorBO);
//        dao.delete("DoctorMapper.deleteDoctorRecord",zxysDoctorBO);
//        dao.delete("DoctorMapper.deleteDoctorToken",zxysDoctorBO);
//        dao.delete("DoctorMapper.deleteDoctor",zxysDoctorBO);
//
//    }
//    /**
//     * 自动计算医生的成就
//     */
//    public void automaticUdpateAchievement() throws Exception {
//        dao.save("DoctorMapper.updateAchievementhot",null);
//        dao.save("DoctorMapper.updateAchievementpraise",null);
//        dao.save("DoctorMapper.updateAchievementworker",null);
//
//    }
//
//
//    public AdminUserBO adminLogin(UserInfoBO userInfoBO) throws Exception {
////        userInfoBO.setMember_passwd(MD5.md5(userInfoBO.getMember_passwd()));
//        AdminUserBO adminUserBO=null;
//        adminUserBO= (AdminUserBO) dao.findForObject("DoctorMapper.adminLogin",userInfoBO);
//        return adminUserBO;
//    }
//
//    public void renzheng(UserInfoBO userInfoBO) throws Exception {
//        dao.update("DoctorMapper.confirmAuthentication",userInfoBO);
//
//    }
//
//    /**
//     * 意见反馈
//     */
//    public void feedBack(FeedBackVo feedBackVo) throws Exception {
//        dao.save("DoctorMapper.feedBack",feedBackVo);
//    }
//
//    /**
//     * 修改密码
//     * @param userInfoBO
//     * @return
//     */
//    public void updatePWD(UserInfoBO userInfoBO) throws Exception {
//        dao.update("DoctorMapper.updatePWD", userInfoBO);
//    }
//
//    public List<AdminDoctorListBo> queryDoctors(AdminDoctorListBo adminDoctorListBo) throws Exception {
//        List<AdminDoctorListBo> list=null;
//        list= (List<AdminDoctorListBo>) dao.findForList("DoctorMapper.queryDoctorsInfo",adminDoctorListBo);
//        return list;
//    }
//
//    /**
//     * 查找没有注册环信的医生
//     * @return
//     * @throws Exception
//     */
//    public List<UserInfoBO> queryDoctorIsNotHuanxin() throws Exception {
//
//        List<UserInfoBO> list = (List<UserInfoBO>) dao.findForList("DoctorMapper.queryDoctorIsNotHuanxin", null);
//        return list;
//    }
//
//    /**
//     * 环信批量注册（医生）
//     * @param list
//     * @return
//     */
//    public List<Integer> huanxinRegisterGroup(List<UserInfoBO> list) throws Exception {
//        //初始化注册业务服务
//        TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
//        //修改数据业务Token
////        service.setToken();
//        List<Integer> listuid = new ArrayList<Integer>();
//        //环信注册IDlist
//        List<String> listid = new ArrayList<String>();
//        //环信注册密码list
//        List<String> listpasswd = new ArrayList<String>();
//        //存储memberid的list
//        List<Integer> ids = new ArrayList<Integer>();
//        //遍历所有未注册用户的memberid
//        for (UserInfoBO userInfoBO : list) {
//            //每循环一次记录下操作的memberid
//            ids.add(userInfoBO.getMember_id());
//            //拼接memberid
//            listid.add("zxys_" + userInfoBO.getMember_id());
//            listpasswd.add(MD5.md5("123456"));
//            //环信最多一次批量注册20条
//            if (listid.size() == 20) {
//                String[] arrayid = listid.toArray(new String[listid.size()]);
//                String[] arraypasswd = listpasswd.toArray(new String[listpasswd.size()]);
//                //批量注册
//                String str = JsonTool.write(service.userSave(arrayid, arraypasswd, null));
//                System.out.println(str);
//                Gson gson = new Gson();
//                TalkNode tn = new TalkNode();
//                //gson解析成TalkNode
//                tn = gson.fromJson(str, TalkNode.class);
//                //判断批量注册请求是否成功
//                if (tn.getStatusCode() !=null) {
//                    //不成功记录下此次请求的所有memberid
//                    for (Integer id : ids) {
//                        listuid.add(id);
//                    }
//                } else {
//                    for (TalkUser talkUser : tn.getEntities()) {
//                        if (talkUser.getActivated() != true) {
//                            //把注册失败activated=false的用户截取出memberid存入listuid
//                            listuid.add(Integer.parseInt(talkUser.getUsername().substring(5).trim()));
//                        }
//                    }
//                }
//                listid.clear();
//                listpasswd.clear();
//                ids.clear();
//
//            }
//
//        }
//        if (listid.size() > 0) {
//            //剩下不足20的批量注册
//            String[] arrayid = listid.toArray(new String[listid.size()]);
//            String[] arraypasswd = listpasswd.toArray(new String[listpasswd.size()]);
//            String str = JsonTool.write(service.userSave(arrayid, arraypasswd, null));
//            System.out.println(str);
//            Gson gson = new Gson();
//            TalkNode tn = new TalkNode();
//            //gson解析成TalkNode
//            tn = gson.fromJson(str, TalkNode.class);
//            //判断批量注册请求是否成功
//            if (tn.getStatusCode()!=null ) {
//                //不成功记录下此次请求的所有memberid
//                for (Integer id : ids) {
//                    listuid.add(id);
//                }
//            } else {
//                for (TalkUser talkUser : tn.getEntities()) {
//                    if (talkUser.getActivated() != true) {
//                        //把注册失败activated=false的用户截取出memberid存入listuid
//                        listuid.add(Integer.parseInt(talkUser.getUsername().substring(5).trim()));
//                    }
//                }
//
//            }
//        }
//        //先全部把is_huanxin字段状态置成1
//        this.updateIshuanxinTrueForDcotor();
//        //判断listuid注册失败的用户有没有
//        if (listuid.size() != 0) {
//            //有注册失败的
//            this.updateIshuanxinFalseForDcotor(listuid);
//
//            String reslult = "";
//            for (int i = 0;i< listuid.size(); i++){
//                reslult = this.registerHuanxinOne(listuid.get(i));
//                if(reslult != null && !"".equals(reslult)) {
//                    if(reslult.equals("0")){
//                        listuid.remove(i);
//                    }else if(reslult.equals("1")){
//                        // 已经注册
//                    }else if(reslult.equals("2")){
//                        // 注册失败
//                    }else {
//                        //其他
//                    }
//                }
//            }
//
//            //将注册失败的用户id返回
//            return listuid;
//        }
//        return null;
//    }
//
//    /**
//     * 环信批量注册（医生）
//     * @param list
//     * @return
//     */
//    public List<Integer> huanxinRegisterList(List<UserInfoBO> list) throws Exception {
//        //初始化注册业务服务
//        TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
//        //修改数据业务Token
////        service.setToken();
//        List<Integer> listuid = new ArrayList<Integer>();
////        //环信注册IDlist
////        List<String> listid = new ArrayList<String>();
////        //环信注册密码list
////        List<String> listpasswd = new ArrayList<String>();
////        //存储memberid的list
////        List<Integer> ids = new ArrayList<Integer>();
////        //遍历所有未注册用户的memberid
//
//        for (UserInfoBO doctorBO : list) {
//            //每循环一次记录下操作的memberid
//            int doctorid=doctorBO.getMember_id();
//                String str = JsonTool.write(service.userSave("zxkj_"+doctorid, MD5.md5("123456"), null));
//                System.out.println(str);
//                Gson gson = new Gson();
//                TalkNode tn = new TalkNode();
//                //gson解析成TalkNode
//                tn = gson.fromJson(str, TalkNode.class);
//                //判断批量注册请求是否成功
//                if (tn.getStatusCode() !=null) {
//                    //不成功记录下此次请求的所有memberid
//
//                        listuid.add(doctorid);
//
//                } else {
//                    for (TalkUser talkUser : tn.getEntities()) {
//                        if (talkUser.getActivated() != true) {
//                            //把注册失败activated=false的用户截取出memberid存入listuid
//                            listuid.add(Integer.parseInt(talkUser.getUsername().substring(5).trim()));
//                        }
//                    }
//                }
////                listid.clear();
////                listpasswd.clear();
////                ids.clear();
//
//
//
//        }
////        if (listid.size() > 0) {
////            //剩下不足20的批量注册
////            String[] arrayid = listid.toArray(new String[listid.size()]);
////            String[] arraypasswd = listpasswd.toArray(new String[listpasswd.size()]);
////            String str = JsonTool.write(service.userSave(arrayid, arraypasswd, null));
////            System.out.println(str);
////            Gson gson = new Gson();
////            TalkNode tn = new TalkNode();
////            //gson解析成TalkNode
////            tn = gson.fromJson(str, TalkNode.class);
////            //判断批量注册请求是否成功
////            if (tn.getStatusCode()!=null ) {
////                //不成功记录下此次请求的所有memberid
////                for (Integer id : ids) {
////                    listuid.add(id);
////                }
////            } else {
////                for (TalkUser talkUser : tn.getEntities()) {
////                    if (talkUser.getActivated() != true) {
////                        //把注册失败activated=false的用户截取出memberid存入listuid
////                        listuid.add(Integer.parseInt(talkUser.getUsername().substring(5).trim()));
////                    }
////                }
////
////            }
////        }
////        //先全部把is_huanxin字段状态置成1
////        this.updateIshuanxinTrueForDcotor();
////        //判断listuid注册失败的用户有没有
////        if (listuid.size() != 0) {
////            //有注册失败的
////            this.updateIshuanxinFalseForDcotor(listuid);
////
////            String reslult = "";
////            for (int i = 0;i< listuid.size(); i++){
////                reslult = this.registerHuanxinOne(listuid.get(i));
////                if(reslult != null && !"".equals(reslult)) {
////                    if(reslult.equals("0")){
////                        listuid.remove(i);
////                    }else if(reslult.equals("1")){
////                        // 已经注册
////                    }else if(reslult.equals("2")){
////                        // 注册失败
////                    }else {
////                        //其他
////                    }
////                }
////            }
//
//
//            //将注册失败的用户id返回
//            return listuid;
////        }
////        return null;
//    }
//
//    /**
//     * 所有用户的环信注册状态码置1
//     * @throws Exception
//     */
//    public void updateIshuanxinTrueForDcotor() throws Exception {
//        dao.update("DoctorMapper.updateIshuanxinTrueForDcotor", null);
//    }
//
//    public void updateIshuanxinFalseForDcotor(List<Integer> list) throws Exception {
//        for (Integer member_id : list) {
//            //根据memberid置0用户的环信状态
//            dao.update("DoctorMapper.updateDoctorIsHuanxin",member_id);
//        }
//    }
//
//    /**
//     * 环信单个注册
//     * @return
//     */
//    public String registerHuanxinOne(int member_id) {
//        try {
//            //初始化注册业务服务
//            TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
//            TalkNode node = service.userSave("zxys_" + member_id, MD5.md5("123456"), null);
//            if(node != null) {
//                // 环信注册成功
//                if(node.getEntities() != null) {
//                    if(node.getEntities().get(0).getActivated() == true) {
//
//                        dao.update("DoctorMapper.updateDoctorIsHuanxin", member_id);
//                        // 注册成功
//                        return "0";
//                    }
//                }else {
//                    if(node.getStatusCode() == 400) {
//                        // 用户以及注册
//                        return "1";
//                    }
//                }
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return "2";// 系统内部错误
//        }
//        return null;
//    }
//
//
//}