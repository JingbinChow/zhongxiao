//package com.fh.controller.doctor;
//
//import com.fh.controller.base.BaseController;
//import com.fh.entity.bo.*;
//
//import com.fh.entity.vo.FeedBackVo;
//
//import com.fh.entity.vo.MessageVO;
//import com.fh.entity.vo.TokenVo;
//import com.fh.entity.vo.UserInfoVO;
//import com.fh.entity.zxys.*;
//import com.fh.huanxin.model.TalkNode;
//import com.fh.huanxin.service.impl.TalkDataServiceImpl;
//import com.fh.service.doctor.DoctorService;
//import com.fh.util.*;
//import com.sun.deploy.net.HttpResponse;
//import org.apache.jasper.tagplugins.jstl.core.Out;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.File;
//import java.io.PrintWriter;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by admin on 2016/12/12.
// */
//@Controller
//@RequestMapping("/doctor")
//public class DoctorController extends BaseController{
//
//    @Resource(name = "doctorService")
//    private DoctorService doctorService;
//
//    @Resource(name = "talkDataService")
//    private TalkDataServiceImpl huanxinService;
//
//    private Logger log = Logger.getLogger(DoctorController.class);
////    protected void out(Object o) {
////        HttpServletResponse response = null;
////        PrintWriter pw = null;
////        try {
////            response = ServletActionContext.getResponse();
////            pw = response == null ? null : response.getWriter();
////            pw.print(o);
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            if (null != pw)
////                pw.close();
////        }
////    }
//
//    @RequestMapping("doctor_register")
//    @ResponseBody
//    public Map<String, Object> doctorRegister(@RequestBody UserInfoVO userInfoVO){
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            // 空校验
//            if(userInfoVO == null) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_PerfectInfo);
//                return map;
//            }
//
//            // 校验手机号
//            if(userInfoVO.getMobile() == null) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_NoMobile);
//                return map;
//            }
//
//            // 校验手机号格式
////            boolean flag = Tools.checkMobileNumber(userInfoVO.getMobile());
////            if(!flag) {
////                map.put("code", "1");
////                map.put("data", null);
////                map.put("message", MessageTemplate.register_ErrModuleForMobile);
////                return map;
////            }
//
//            // 校验手机号是否重复注册(测试时屏蔽)
//            UserInfoBO userInfoBO = doctorService.checkMobileIsExist(userInfoVO);
//            if(userInfoBO != null) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_IsExistMobile);
//                return map;
//            }
//
//            // 校验注册码
//            if(userInfoVO.getCode() == null || "".equals(userInfoVO.getCode())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.verifyCode_input);
//                return map;
//            }
////
//            TokenVo vo = new TokenVo();
//            vo.setPhone(userInfoVO.getMobile());
//            vo.setCode(userInfoVO.getCode());
//            SmsBO bo= doctorService.confirmValidate(vo);
//            if(bo == null) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.vefiryCode_Err);
//                return map;
//            }else {
//                int i =bo.getAdd_time(); //生成验证码时间戳
//                int seconds =Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000));//当前时间戳
//                int interval =seconds-i;
//                if(interval>1800){
//                    map.put("message", MessageTemplate.verifyCode_Expire);
//                    map.put("code","1");
//                    map.put("data",null);
//                    return map;
//                }
//            }
//            // 密码不能为空
//            if(userInfoVO.getPassWord() == null || "".equals(userInfoVO.getPassWord())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_NoPwd);
//                return map;
//            }
//
//            // 校验密码的格式
//            if(!Tools.checkPassword(userInfoVO.getPassWord())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_ResetPassword);
//                return map;
//            }
//
//            // 密码加密
//            userInfoVO.setPassWord(MD5.md5(userInfoVO.getPassWord()));
//            userInfoVO.setMember_time(String.valueOf(System.currentTimeMillis() / 1000));
//
//            // 手机号用作用户名
//            userInfoVO.setUserName(userInfoVO.getMobile());
//
//            // 注册
//            boolean result = doctorService.registerDocter(userInfoVO);
//            if(result) {
//
//                //　 产生token
//                String token = Tools.getToken();
//                System.out.println(token + "**********************************************************");
//                UserInfoBO userInfo = doctorService.queryDoctorByUserName(userInfoVO);
//                //获取当前时间戳
//                String time=String.valueOf(System.currentTimeMillis()/1000);
//
//                TokenBO tokenBo = new TokenBO();
//                tokenBo.setToken(token);
//                tokenBo.setLogin_time(time);
//                tokenBo.setMember_name(userInfoVO.getUserName());
//                tokenBo.setClient_type("wap");
//                tokenBo.setMember_id(userInfo.getMember_id());
//                // 产生该用户的token记录
//                doctorService.saveTokenInfo(tokenBo);
//
//                // 通过用户名和密码获取用户的id
//                UserInfoBO userInfoBO1 = doctorService.queryMemberByUserNameAndPwd(userInfoVO);
//
//                MessageVO messageVO = new MessageVO();
//                messageVO.setToken(token);
//                messageVO.setUid(String.valueOf(userInfoBO1.getMember_id()));
//                messageVO.setUsername(userInfoBO1.getMember_name());
//
//                // 环信注册
//                TalkNode node = huanxinService.userSave("zxys_" + messageVO.getUid(), MD5.md5("123456"), null);
//                if(node != null) {
//                    // 环信注册成功
//                    if(node.getEntities() != null) {
//                        if(node.getEntities().get(0).getActivated() == true) {
//                            map.put("code", "0");
//                            map.put("data", messageVO);
//                            map.put("message", MessageTemplate.register_Success);
//                            return map;
//                        }
//                    }else {
//                        if(node.getStatusCode() == 400) {
//                            map.put("code", "0");
//                            map.put("data", messageVO);
//                            map.put("message", "请不要重复注册");
//
//                            return map;
//                        }
//                    }
//
//                }
//
//            }else {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_Failure);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code", "1");
//            map.put("data", null);
//            map.put("message", MessageTemplate.common_SystemErr);
//
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 登录
//     * @param userInfoVO
//     * @return
//     */
//    @RequestMapping("login")
//    @ResponseBody
//    public Map<String, Object> login(@RequestBody UserInfoVO userInfoVO) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//       try {
//           // 空校验
//           if(userInfoVO != null) {
//               // 手机号为空
//               if(userInfoVO.getMobile() == null || "".equals(userInfoVO.getMobile())) {
//                   map.put("code", "1");
//                   map.put("date", null);
//                   map.put("message", MessageTemplate.verifyCode_InputMobile);
//                   return map;
//               }
//               // 密码为空
//               if (userInfoVO.getPassWord() == null || "".equals(userInfoVO.getPassWord())){
//                   map.put("code", "1");
//                   map.put("date", null);
//                   map.put("message", MessageTemplate.login_NoPassword);
//                   return map;
//               }
//           }
//
//           // 用户名和密码
//           userInfoVO.setUserName(userInfoVO.getMobile());
//           userInfoVO.setPassWord(MD5.md5(userInfoVO.getPassWord()));
//           UserInfoBO userInfoBO = doctorService.queryMemberByUserNameAndPwd(userInfoVO);
//
//           // 环信登录
//           huanxinService.login(userInfoVO.getMobile(), userInfoVO.getPassWord());
//           //登录失败
//           if(userInfoBO == null) {
//               map.put("code", "1");
//               map.put("date", null);
//               map.put("message", MessageTemplate.login_mobileOrPwdErr);
//               return map;
//           }
//
//           // 成功
//           // 登录时间
//           //获取当前时间戳
//           String time=String.valueOf(System.currentTimeMillis()/1000);
//           userInfoBO.setMember_old_login_time(userInfoBO.getMember_login_time());
//           userInfoBO.setMember_login_time(time);
//           // 登录次数加1
//           userInfoBO.setMember_login_num(userInfoBO.getMember_login_num() + 1);
//           doctorService.updateMemberLoginInfo(userInfoBO);
//
//           // 记录登录记录
//           LoginLogBo loginLogBo = new LoginLogBo();
//           loginLogBo.setMember_id(userInfoBO.getMember_id());
//           //获取当前时间
//           String loginTime= Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
//           loginLogBo.setLogin_time(loginTime);
//           // 1 安卓 2 IOS  3 php
//           loginLogBo.setType(userInfoVO.getMobile_type());
//
//           doctorService.saveLoginLog(loginLogBo);
//
//           TokenBO tokenBO = doctorService.queryTokenByMemberId(userInfoBO);
//           String newToken = Tools.getToken();
//           // 该用户是否有token记录
//           if(tokenBO == null) {
//               // 不存在，添加
//               TokenBO token = new TokenBO();
//               token.setToken(newToken);
//               token.setLogin_time(time);
//               token.setClient_type("wap");
//               token.setMember_name(userInfoBO.getMember_name());
//               token.setMember_id(userInfoBO.getMember_id());
//
//                doctorService.saveTokenInfo(token);
//           }else {
//               // 存在，更新
//               tokenBO.setToken(newToken);
//               tokenBO.setLogin_time(time);
//               tokenBO.setMember_id(userInfoBO.getMember_id());
//
//               doctorService.updateToken(tokenBO);
//           }
//
//           MessageVO vo = new MessageVO();
//           vo.setToken(newToken);
//           vo.setUid(String.valueOf(userInfoBO.getMember_id()));
//           vo.setUsername(userInfoBO.getMember_name());
//
//           map.put("code", "0");
//           map.put("data", vo);
//           map.put("message", MessageTemplate.login_Success);
//
//       }catch (Exception e){
//           e.printStackTrace();
//           map.put("code", "1");
//           map.put("data", null);
//           map.put("message", MessageTemplate.common_SystemErr);
//
//           return map;
//       }
//        return map;
//    }
//
//    /**
//     * 获取短信验证
//     * @param vo
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value="validateForegister")
//    @ResponseBody
//    public Map<String, Object> validateForegister(@RequestBody TokenVo vo)throws Exception {
//        Map<String, Object> modelMap =Tools.errMessage();
//
//        if(StringUtil.isNotEmpty(vo.getPhone())){//注册
//            TokenVo reVo=doctorService.validateForegister(vo);
//            if("000000".equals(reVo.getCode())){
//                modelMap.put("message", MessageTemplate.verifyCode_getFailure);
//                modelMap.put("code","1");
//                modelMap.put("data",null);
//            }else{
//                modelMap.put("message", MessageTemplate.verifyCode_getSuccess);
//                modelMap.put("code","0");
//                modelMap.put("data",reVo);
//            }
//        }else{
//            modelMap.put("message", MessageTemplate.verifyCode_InputMobile);
//            modelMap.put("code","1");
//            modelMap.put("data",null);
//        }
//
//        return modelMap;
//    }
//
//    /**
//     * 忘记密码
//     * @param tokenVo
//     * @return
//     */
//    @RequestMapping("findBackPwd")
//    @ResponseBody
//    public Map<String, Object> findBackPwd(@RequestBody TokenVo tokenVo) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            if(tokenVo.getCode() == null || "".equals(tokenVo.getCode())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.verifyCode_input);
//                return map;
//            }
//
//            // 验证码校验
//            SmsBO bo = doctorService.findSMSLogByPhoneAndCode(tokenVo);
//            if(bo == null) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.vefiryCode_Err);
//                return map;
//            }else {
//                int i =bo.getAdd_time(); //生成验证码时间戳
//                int seconds =Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000));//当前时间戳
//                int interval =seconds-i;
//                if(interval>1800){
//                    map.put("message", MessageTemplate.verifyCode_Expire);
//                    map.put("code","1");
//                    map.put("data",null);
//                    return map;
//                }
//            }
//
//            // 设置新密码
//            if(tokenVo.getPassWord() == null || "".equals(tokenVo.getPassWord())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.login_NoPassword);
//                return map;
//            }
//
//            // 密码加密
//            tokenVo.setPassWord(MD5.md5(tokenVo.getPassWord()));
//
//            doctorService.resetMemberPwd(tokenVo);
//
//            map.put("code", "0");
//            map.put("data", null);
//            map.put("message", "密码重置成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code", "1");
//            map.put("data", null);
//            map.put("message", MessageTemplate.common_SystemErr);
//
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 医生认证
//     * @param doctorVO
//     * @return
//     */
//    @RequestMapping("authenticationDoctor")
//    @ResponseBody
//    public Map<String, Object> authenticationDoctor(@RequestBody ZxysDoctorVO doctorVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            // token校验
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(doctorVO.getToken());
//            UserInfoBO user = doctorService.checkMemberByToken(tokenVo);
//            if(user == null) {
//                Tools.errMessage();
//            }
//
//            // 获取医生的id
////            UserInfoVO userInfoVO = new UserInfoVO();
////            userInfoVO.setMobile(user.getMember_mobile());
////            UserInfoBO userInfo = doctorService.queryDoctorByMobile(userInfoVO);
//
//            // 校验用户是否已经认证
//            UserInfoBO userInfoBO = new UserInfoBO();
//            userInfoBO.setMember_id(user.getMember_id());
//            ZxysDoctorBO doctorBO = doctorService.queryDoctorIsauthentication(userInfoBO);
//            if(doctorBO != null) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", "您已经认证");
//                return map;
//            }
//
//            // 真实姓名验证
//            if(Tools.isEmpty(doctorVO.getDoctorName())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_PerfectTrueName);
//                return map;
//            }
//            // 性别校验
//            if (Tools.isEmpty(doctorVO.getSex())){
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.register_SexIsNone);
//                return map;
//            }
//            // 性别参数处理
//            if("男".equals(doctorVO.getSex())) {
//                doctorVO.setSex("1");
//            }else if("女".equals(doctorVO.getSex())) {
//                doctorVO.setSex("2");
//            }else if("保密".equals(doctorVO.getSex())) {
//                doctorVO.setSex("0");
//            }
//
//            //医院
//            if(Tools.isEmpty(doctorVO.getHospitalName())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.treatment_NoHospital);
//                return map;
//            }
//            // 通过医院名称查询医院的等级
//            ZxysHospitalVO hospitalVO = new ZxysHospitalVO();
//            hospitalVO.setHospitalName(doctorVO.getHospitalName());
//            List<ZxysHospitalBO> hospitalList = doctorService.queryHospitalFuzzy(hospitalVO);
//            if(hospitalList!= null && hospitalList.size() > 0) {
//                doctorVO.setHospitalLevel(hospitalList.get(0).getHospital_level());
//            }
//
//            doctorVO.setDoctorId(user.getMember_id());
//
//            // 科室
//            if(Tools.isEmpty(doctorVO.getOffice())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.treatment_NoOffice);
//                return map;
//            }
//
//            // 职称
//            if(Tools.isEmpty(doctorVO.getTitle())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.treatment_NoTitle);
//                return map;
//            }
//
//            // 城市
//            if(Tools.isEmpty(doctorVO.getArea())) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.treatment_NoCity);
//                return map;
//            }
//
//            // 近期照片
////            if(Tools.isEmpty(doctorVO.getCurrentPic())) {
////                map.put("code", "1");
////                map.put("data", null);
////                map.put("message", "请上传您的近期照片");
////                return map;
////            }
////            // 胸牌
////            if(Tools.isEmpty(doctorVO.getMemberAvatar())) {
////                map.put("code", "1");
////                map.put("data", null);
////                map.put("message", MessageTemplate.treatment_NOCredentials);
////                return map;
////            }
////
////            // 上传图片
////            // 近期照片
////            String currentPic = doctorVO.getCurrentPic();
////            // 胸牌
////            String credentials = doctorVO.getCredentials();
////
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
////            String date = sdf.format(new Date());
////
////            // 生成保存图片的文件路径
////            String currentPicPath = "/var/apache-tomcatNew/webapps/zxys_picture/" + userInfoBO.getMember_id() + "/";
////            String credentialPath = "/var/apache-tomcatNew/webapps/zxys_picture/" + userInfoBO.getMember_id() + "/";
////
////            // 文件夹不存在，创建一个
////            File fileAvater = new File(currentPicPath);
////            if(!fileAvater.exists()){
////                fileAvater.mkdirs();
////            }
////
////            File fileCredential = new File(credentialPath);
////            if(!fileCredential.exists()){
////                fileCredential.mkdirs();
////            }
////
////            if(currentPic != null && !"".equals(currentPic)) {
////                // 近期照片
////                currentPic = currentPic.replace("\"","");
////                currentPic = currentPic.replaceAll("\\\\", "");
////                String picName = "currentPic_" + userInfoBO.getMember_id() + ".jpg";
////                String imgPathDisk = currentPicPath + picName;
////
////                ImageAnd64Binary.generateImage(currentPic, imgPathDisk);
////
////                String url = "http://106.75.24.52:81/zxys_picture/" + userInfoBO.getMember_id() + "/" + picName;
////
////                doctorVO.setMemberAvatar(url);
////
////                doctorService.updateAvatar(doctorVO);
////
////
////            }
////
////            if(credentials != null && !"".equals(credentials)) {
////                //  上传胸牌
////                credentials = credentials.replace("\"","");
////                credentials = credentials.replaceAll("\\\\", "");
////                String picName = "credentials_" + userInfoBO.getMember_id() + ".jpg";
////                String imgPathDisk = credentialPath + "/"+ picName;
////
////                ImageAnd64Binary.generateImage(credentials, imgPathDisk);
////
////                String url = "http://106.75.24.52:81/zxys_picture/" + userInfoBO.getMember_id() + "/" + picName;
////                doctorVO.setCredentials(url);
////
////                doctorService.updateCredentials(doctorVO);
////            }
//
//            // 认证开始
//            doctorVO.setDoctorId(user.getMember_id());
//            if(doctorService.authenticationDoctor(doctorVO)) {
//                map.put("code", "0");
//                map.put("data", null);
//                map.put("message", "您的认证信息已提交,请耐心等待");
//            }else {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.treatment_AuthFailure);
//                return map;
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code", "1");
//            map.put("data", null);
//            map.put("message", MessageTemplate.common_SystemErr);
//
//            return map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 医生头像
//     * @param doctorVO
//     * @return
//     */
//    @RequestMapping("updateImageForDoctor")
//    @ResponseBody
//    public Map<String, Object> updateImageForDoctor(@RequestBody ZxysDoctorVO doctorVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            // token验证
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(doctorVO.getToken());
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                Tools.errMessage();
//            }
//
//            doctorVO.setDoctorId(userInfoBO.getMember_id());
//            // 上传图片
////            // 头像
//            String avatar = doctorVO.getMemberAvatar();
//
////            // 生成保存图片的文件路径
//            String avaterPath = "/var/apache-tomcatNew/webapps/zxys_picture/" + userInfoBO.getMember_id() + "/";
////            // 文件夹不存在，创建一个
//            File fileAvater = new File(avaterPath);
////                File fileParent = file.getParentFile();
//            if(!fileAvater.exists()){
//                fileAvater.mkdirs();
//            }
//
//
////                // 上传头像
//                avatar = avatar.replace("\"","");
//                avatar = avatar.replaceAll("\\\\", "");
//                String picName ="avatar_" + userInfoBO.getMember_id() + ".jpg";
//                String imgPathDisk = avaterPath + picName;
//
//                ImageAnd64Binary.generateImage(avatar, imgPathDisk);
//
//                // 数据库存放的路径
//                String url = "http://106.75.24.52:81/zxys_picture/" + userInfoBO.getMember_id() + "/" + picName;
//
//                doctorVO.setMemberAvatar(url);
//
//                doctorService.updateAvatar(doctorVO);
//                map.put("data",url);
//            map.put("code", "0");
//            map.put("message", "图片上传成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     * 医生擅长
//     * @param doctorVO
//     * @return
//     */
//    @RequestMapping("udpateAdeptInfo")
//    @ResponseBody
//    public Map<String, Object> udpateAdeptInfo(@RequestBody ZxysDoctorVO doctorVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            // token验证
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(doctorVO.getToken());
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if (userInfoBO == null){
//                Tools.errMessage();
//            }
//
//            doctorVO.setDoctorId(userInfoBO.getMember_id());
//
//            doctorService.updateAdept(doctorVO);
//
//            map.put("code", "0");
//            map.put("data", null);
//            map.put("message", "擅长信息更新成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     *
//     * 获取职称列表
//     * @return
//     */
//    @RequestMapping("queryTitleList")
//    @ResponseBody
//    public Map<String, Object> queryTitleList() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            List<ZxysVariableBO> list = doctorService.queryVariable(0);
//            if(list == null || list.size() <= 0) {
//                map.put("code", "1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.common_NoDataFailure);
//                return map;
//            }
//
//            map.put("code", "0");
//            map.put("data", list);
//            map.put("message", MessageTemplate.common_QueryDataSuccess);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code", "1");
//            map.put("data", null);
//            map.put("message", MessageTemplate.common_SystemErr);
//
//            return map;
//        }
//
//        return map;
//    }
//
//
//    /**
//     * 模糊查询医院
//     * @param hospitalVO
//     * @return
//     */
//    @RequestMapping("queryHospitalFuzzy")
//    @ResponseBody
//    public Map<String, Object> queryHospitalFuzzy(@RequestBody ZxysHospitalVO hospitalVO) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
////            List<ZxysHospitalBO> hospitalList = doctorService.queryHospitalFuzzy(hospitalVO);
//            List<ZxysHospitalBO> hospitalList = doctorService.queryHospital();
//            if(hospitalList == null || hospitalList.size() <= 0) {
//                map.put("code", "0");
//                map.put("data", hospitalList);
//                map.put("message", MessageTemplate.common_NoDataFailure);
//            }else {
//                map.put("code", "0");
//                map.put("data", hospitalList);
//                map.put("message", MessageTemplate.common_QueryDataSuccess);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code", "1");
//            map.put("data", null);
//            map.put("message", MessageTemplate.common_SystemErr);
//
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 开通服务（需要参数：token，开通咨询的种类，价格设定）
//     * @param serviceVO
//     * @return
//     */
//    @RequestMapping("openService")
//    @ResponseBody
//    public Map<String, Object> openService(@RequestBody ZxysServiceVO serviceVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            // token验证
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(serviceVO.getToken());
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO== null) {
//                return Tools.errMessage();
//            }
//
//            // 校验用户是否已经认证
//            ZxysDoctorVO doctorVO = new ZxysDoctorVO();
//            doctorVO.setDoctorId(userInfoBO.getMember_id());
//            ZxysDoctorBO doctorBO = doctorService.checkAuthentication(doctorVO);
//            if(doctorBO != null) {
//                // 正在认证
//                if(doctorBO.getStatus() == 1) {
//                    map.put("code","0");
//                    map.put("data", null);
//                    map.put("message", "正在认证，请您认证通过后再开通服务");
//                    return map;
//                }else if(doctorBO.getStatus() == 0) {// 认证中
//                    map.put("code","0");
//                    map.put("data", null);
//                    map.put("message", "您还没用完成认证，请认证通过后再开通服务");
//                    return map;
//                }
//            }
//
//            // 开通服务
//            serviceVO.setDoctorId(userInfoBO.getMember_id());
//            boolean flag = doctorService.updateService(serviceVO);
//            if(flag){
//                if("1".equals(serviceVO.getStatus())) {
//                    map.put("code","0");
//                    map.put("data", null);
//                    map.put("message", MessageTemplate.treatment_OpenServiceSuccess);
//                }else if("0".equals(serviceVO.getStatus())) {
//                    map.put("code","0");
//                    map.put("data", null);
//                    map.put("message", "关闭服务成功");
//                }
//            }else {
//                map.put("code","1");
//                map.put("data", null);
//                map.put("message", MessageTemplate.treatment_OpenServiceFailure);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code", "1");
//            map.put("data", null);
//            map.put("message", MessageTemplate.common_SystemErr);
//
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 获取所有科室列表
//     * @return
//     */
//    @RequestMapping("queryAllOffice")
//    @ResponseBody
//    public Map<String, Object> queryAllOffice() {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        try {
//            ZxysOfficeVO vo = new ZxysOfficeVO();
//            vo.setOfficeParentid(0);
//            List<ZxysOfficeBO> officeList = doctorService.queryOfficeByParentId(vo);
//            List<ZxysOfficeBO> list = null;
//            ZxysOfficeVO officeVO = new ZxysOfficeVO();
//            for(ZxysOfficeBO bo : officeList) {
//                officeVO.setOfficeParentid(bo.getId());
//                list = doctorService.queryOfficeByParentId(officeVO);
//                if(list != null && list.size() > 0) {
//                    bo.setList(list);
//                }
//            }
//            map.put("code","0");
//            map.put("data", officeList);
//            map.put("message", MessageTemplate.treatment_GetOfficeSuccess);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     * 未完成订单记录
//     * @param userInfoVO
//     * @return
//     */
//    @RequestMapping("uncompleteOrder")
//    @ResponseBody
//    public Map<String, Object> uncompleteOrder(@RequestBody UserInfoVO userInfoVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            // token验证
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(userInfoVO.getToken());
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                return Tools.errMessage();
//            }
//            // 分页参数处理
////            if(userInfoVO.getPageIndex() == 0) {
////                userInfoBO.setPageIndex(1);
////            }
////            if(userInfoVO.getPageSize() == 0) {
////                userInfoBO.setPageSize(10);
////            }
//
//            userInfoBO.setPageIndex(userInfoVO.getPageIndex());
//            userInfoBO.setPageSize(userInfoVO.getPageSize());
//
//            List<ZxysFinishRecordList> recordList = doctorService.queryUncompleteRecord(userInfoBO);
//            if(recordList == null || recordList.size() <= 0) {
//                map.put("code", "0");
//                map.put("data", null);
//                map.put("message", MessageTemplate.common_NoDataFailure);
//                return map;
//            }
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            // 数据处理
//            for (ZxysFinishRecordList unFinish: recordList){
//                unFinish.setOrder_time(sdf.format(sdf.parse(unFinish.getOrder_time())));
//                if(unFinish.getPatient_sex() != null && !"".equals(unFinish.getPatient_sex())) {
//                    switch (Integer.parseInt(unFinish.getPatient_sex())){
//                        case 1:
//                            unFinish.setPatient_sex("男");
//                            break;
//                        case 2:
//                            unFinish.setPatient_sex("女");
//                            break;
//                        default:
//                            unFinish.setPatient_sex("未填");
//                            break;
//                    }
//                }
//                if(unFinish.getPatient_area() == null || "".equals(unFinish.getPatient_area())) {
//                    unFinish.setPatient_area("地址不详");
//                }
//            }
//
//            map.put("code", "0");
//            map.put("data", recordList);
//            map.put("message", MessageTemplate.common_QueryDataSuccess);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     * 已完成订单记录
//     * @param userInfoVO
//     * @return
//     */
//    @RequestMapping("completedOrder")
//    @ResponseBody
//    public Map<String, Object> completedOrder (@RequestBody UserInfoVO userInfoVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            // token验证
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(userInfoVO.getToken());
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                return Tools.errMessage();
//            }
//
//            // 分页参数处理
////            if(userInfoVO.getPageIndex() <= 0) {
////                userInfoBO.setPageIndex(1);
////            }
////            if(userInfoVO.getPageSize() <= 0) {
////                userInfoBO.setPageSize(10);
////            }
//
//            userInfoBO.setPageIndex(userInfoVO.getPageIndex());
//            userInfoBO.setPageSize(userInfoVO.getPageSize());
//
//            List<ZxysFinishRecordList> recordList = doctorService.queryCompletedRecord(userInfoBO);
//
//            if(recordList == null || recordList.size() <= 0) {
//                map.put("code", "0");
//                map.put("data", null);
//                map.put("message", MessageTemplate.common_NoDataFailure);
//                return map;
//            }
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            // 数据处理
//            for (ZxysFinishRecordList finish: recordList){
//                finish.setOrder_time(sdf.format(sdf.parse(finish.getOrder_time())));
//                switch (Integer.parseInt(finish.getPatient_sex())){
//                    case 1:
//                        finish.setPatient_sex("男");
//                        break;
//                    case 2:
//                        finish.setPatient_sex("女");
//                        break;
//                    default:
//                        finish.setPatient_sex("未填");
//                        break;
//                }
//
//                if(finish.getPatient_area() == null || "".equals(finish.getPatient_area())) {
//                    finish.setPatient_area("地址不详");
//                }
//            }
//
//            map.put("code", "0");
//            map.put("data", recordList);
//            map.put("message", MessageTemplate.common_QueryDataSuccess);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     * 查询医生信息
//     * @param tokenVo
//     * @return
//     */
//    @RequestMapping("queryDoctorInfo")
//    @ResponseBody
//    public Map<String, Object> queryDoctorInfo(@RequestBody TokenVo tokenVo) {
//        Map<String,Object> map = new HashMap<String, Object>();
//        try {
//
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                return Tools.errMessage();
//            }
//
//            // 已认证的医生
//            ZxysDoctorBO doctorBO = doctorService.queryDoctorByMemberId(userInfoBO);
//            if(doctorBO == null) {
//                map.put("code", "0");
//                map.put("data", null);
//                map.put("message", MessageTemplate.common_NoDataFailure);
//                return map;
//            }
//
//            Map<String, Object> resultMap =new HashMap<String, Object>();
//            resultMap.put("trueName", doctorBO.getDoctor_name());
//            resultMap.put("office", doctorBO.getOffice());
//            // 服务开通状态处理
//            BigDecimal zero = new BigDecimal("0.00");
//            if(doctorBO.getPicture_text_consult().equals(zero)) {
//                resultMap.put("pictureText", "未开通");
//            }else {
//                resultMap.put("pictureText", doctorBO.getPicture_text_consult());
//            }
//
//            if(doctorBO.getMobile_consult().equals(zero)) {
//                resultMap.put("mobileConsult", "未开通");
//            }else {
//                resultMap.put("mobileConsult", doctorBO.getMobile_consult());
//            }
//            resultMap.put("picture_text_status", doctorBO.getPicture_text_status()); // 图文
//            resultMap.put("mobile_status", doctorBO.getMobile_status()); // 电话
//            resultMap.put("video_status", doctorBO.getVideo_status());  // 视频
////            resultMap.put("private_doctor_status", doctorBO.getPrivate_doctor_status());
////            resultMap.put("hospital_post_status", doctorBO.getHospital_post_status());
////            resultMap.put("appointment_status", doctorBO.getAppointment_status());
//
//            if (doctorBO.getVideo_consult().equals(zero)){
//                resultMap.put("videoConsult", "未开通");
//            }else {
//                resultMap.put("videoConsult", doctorBO.getVideo_consult());
//            }
//
//            resultMap.put("avatar", doctorBO.getMember_avatar());
//            if(doctorBO.getMember_sex() == 1) {
//                resultMap.put("sex", "男");
//            }else if(doctorBO.getMember_sex() == 2) {
//                resultMap.put("sex", "女");
//            }else {
//                resultMap.put("sex", "保密");
//            }
//
//            // 0：未认证，1:正在认证，2：已认证
//            if(doctorBO.getStatus() == 0) {
//                resultMap.put("status", "未认证");
//            }else if (doctorBO.getStatus() == 1){
//                resultMap.put("status", "认证中");
//            }else if (doctorBO.getStatus() == 2){
//                resultMap.put("status", "已认证");
//            }
//
//            resultMap.put("mobile", doctorBO.getMember_mobile());
//            resultMap.put("title", doctorBO.getTitle());
//            resultMap.put("hospital", doctorBO.getHospital_name());
//            if(doctorBO.getAdept() != null && !"".equals(doctorBO.getAdept())) {
//                resultMap.put("adept", doctorBO.getAdept());
//            }else {
//                resultMap.put("adept", "请不全您的擅长信息，以便更多的患者看到");
//            }
//            resultMap.put("area", doctorBO.getArea());
//
//            // 成就
//            ZxysAchievement achievement = new ZxysAchievement();
//            achievement.setDoctor_id(doctorBO.getDoctor_id());
//            resultMap.put("achievement", doctorService.queryAchievement(achievement));
//
//            map.put("code", "0");
//            map.put("data", resultMap);
//            map.put("message", "获取医生信息成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     * 获取医生成就
//     * @param tokenVo
//     * @return
//     */
//    @RequestMapping("queryAchievement")
//    @ResponseBody
//    public Map<String, Object> queryAchievement(@RequestBody TokenVo tokenVo) {
//        Map<String,Object> map = new HashMap<String, Object>();
//        try {
//            // token验证
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                return Tools.errMessage();
//            }
//
//            ZxysAchievement achievement = new ZxysAchievement();
//            achievement.setDoctor_id(userInfoBO.getMember_id());
//
//            // 近期成就
//            Map<String, Object> currentAch = doctorService.queryCurrentAchievement(achievement);
//
//            // 历史成就
//            Map<String, Object> oldAch = doctorService.queryAchievement(achievement);
//
//            Map<String,Object> achievementMap = new HashMap<String, Object>();
//            achievementMap.put("currentAch", currentAch);
//            achievementMap.put("historyAch", oldAch);
//
//            map.put("code", "0");
//            map.put("data", achievementMap);
//            map.put("message", "获取成就信息成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//
//    /**
//     *绑定银行卡
//     * @param tokenVo
//     * @return
//     */
//    @RequestMapping("doctorBindingBankCard")
//    @ResponseBody
//    public Map<String, Object> doctorBindingBankCard(@RequestBody TokenVo tokenVo) {
//        Map<String,Object> map = new HashMap<String, Object>();
//        if(!Tools.checkBankCard(tokenVo.getBankCard())){
//            map.put("message","输入的身份证号有误");
//            map.put("code","1");
//            map.put("data",null);
//            return map;
//        }
//        try {
//            // token验证
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                map=Tools.errMessage();
//                return map;
//            }
//            ZxysDoctorBO zxysDoctorBO = doctorService.queryDoctorIsauthentication(userInfoBO);
//            if(zxysDoctorBO==null){
//                map.put("message","您还没有认证用户信息");
//                map.put("code","1");
//                map.put("data",null);
//                return map;
//            }if(zxysDoctorBO.getDoctor_name()!=null){
//                if(zxysDoctorBO.getDoctor_name().equals(tokenVo.getTrueName())){
//                    userInfoBO.setMember_truename(tokenVo.getTrueName());
//                    userInfoBO.setMember_bankcard(tokenVo.getBankCard());
//                    userInfoBO.setMember_bankname(tokenVo.getBankName());
//                    userInfoBO.setMember_cardtype(tokenVo.getCardType());
//                    userInfoBO.setMember_mobile(tokenVo.getPhone());
//                    doctorService.bindingBankCard(userInfoBO);
//                }else{
//                    map.put("message","持卡人姓名与用户姓名不符");
//                    map.put("code","1");
//                    map.put("data",null);
//                    return map;
//                }
//            }else{
//                map.put("message","认证用户姓名为空，请重新认证后再绑定");
//                map.put("code","1");
//                map.put("data",null);
//                return map;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        map.put("message","绑定银行卡成功");
//        map.put("code","0");
//        map.put("data",null);
//        return map;
//    }
//    /**
//     * 获取银行卡信息
//     * @param tokenVo
//     * @return
//     */
//    @RequestMapping("queryBankCardInfo")
//    @ResponseBody
//    public Map<String, Object> queryBankCardInfo(@RequestBody TokenVo tokenVo) {
//        Map<String,Object> map = new HashMap<String, Object>();
//        try {
//            // token验证
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                map=Tools.errMessage();
//                return map;
//            }
//            userInfoBO.setMember_bankcard(tokenVo.getBankCard());
//            List<BindBankCardBO> list=doctorService.queryBindingBankInfo(userInfoBO);
//            if(list==null||list.size()==0){
//                map.put("message","您还没有绑定银行卡");
//                map.put("code","1");
//                map.put("data",null);
//                return map;
//            }
//            map.put("message","获取银行卡信息成功");
//            map.put("code","0");
//            map.put("data", list);
//        }catch (Exception e){
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     *  解绑银行卡
//     * @param tokenVo
//     * @return
//     */
//    @RequestMapping("doctorUnBindingBankCard")
//    @ResponseBody
//    public Map<String, Object> doctorUnBindingBankCard(@RequestBody TokenVo tokenVo) {
//        Map<String,Object> map = new HashMap<String, Object>();
//        try {
//            // token验证
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                map=Tools.errMessage();
//                return  map;
//            }
//            if(userInfoBO.getMember_paypwd()==null){
//                map.put("message","未设置支付密码");
//                map.put("code","1");
//                map.put("data",null);
//                return map;
//            }
//            if(!MD5.md5(tokenVo.getPaypwd()).equals(userInfoBO.getMember_paypwd())){
//                map.put("message","支付密码错误");
//                map.put("code","1");
//                map.put("data",null);
//                return map;
//            }
//            userInfoBO.setMember_bankcard(tokenVo.getBankCard());
//           doctorService.unBindingBankCard(userInfoBO);
//        }catch (Exception e){
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        map.put("message","解绑银行卡成功");
//        map.put("code","0");
//        map.put("data",null);
//        return map;
//    }
//
//    /**
//     * 医生评价
//     * @param recordVO
//     * @return
//     */
//    @RequestMapping("queryCommentList")
//    @ResponseBody
//    public Map<String, Object> queryCommentList(@RequestBody ZxysRecordVO recordVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            // token验证
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(recordVO.getToken());
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//               return Tools.errMessage();
//            }
//            // 分页数据处理
//            if(recordVO.getPageIndex() == 0) {
//                recordVO.setPageIndex(1);
//            }
////            if(recordVO.getPageSize() == 0) {
////                recordVO.setPageSize(10);
////            }
//            // 评价列表
//            recordVO.setDoctorId(userInfoBO.getMember_id());
//            List<ZxysCommentContentBO> commentList = doctorService.queryCommentList(recordVO);
//            map.put("code", "0");
//            map.put("data", commentList);
//            map.put("message", "获取数据成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 联系我们获取tel和email
//     * @return
//     */
//    @RequestMapping("tellUs")
//    @ResponseBody
//    public Map<String, Object> tellUs() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//           List<ConnectionBO> list= doctorService.tellUs();
//            map.put("code", "0");
//            map.put("data",list);
//            map.put("message", "获取数据成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//           map= Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 设置、修改支付密码
//     * @return
//     */
//    @RequestMapping("settingPaypwd")
//    @ResponseBody
//    public Map<String, Object> settingPaypwd(@RequestBody UserInfoBO userInfoBO) {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            TokenVo tokenVo = new TokenVo();
////            System.out.println(userInfoBO.getToken());
//            tokenVo.setToken(userInfoBO.getToken());
//            UserInfoBO bo = doctorService.checkMemberByToken(tokenVo);
//            if(bo==null){
//                return map;
//            }
//            userInfoBO.setMember_id(bo.getMember_id());
//            userInfoBO.setMember_paypwd(MD5.md5(userInfoBO.getMember_paypwd()));
//            doctorService.modifyMemberpaypwd(userInfoBO);
//            map.put("code", "0");
//            map.put("data",null);
//            map.put("message", "设置支付密码成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//
//    /**
//     * 结束会话
//     * @param recordVO
//     * @return
//     */
//    @RequestMapping("endSession")
//    @ResponseBody
//    public Map<String, Object> endSession(@RequestBody ZxysRecordVO recordVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            // token校验
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(recordVO.getToken());
//            UserInfoBO bo = doctorService.checkMemberByToken(tokenVo);
//            if(bo==null){
//                return Tools.errMessage();
//            }
//
//            // 更新订单状态
//            recordVO.setStatus("4");
//            doctorService.updateRecordStatusByOutTradeNo(recordVO);
//
//            map.put("code", "0");
//            map.put("data", null);
//            map.put("message", "结束咨询成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     * 批量注册环信
//     * @return
//     */
//    @RequestMapping("huanxinRegister")
//    @ResponseBody
//    public Map<String, Object> huanxinRegister() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//           //查询未注册环信的用户
//            List<UserInfoBO> list=doctorService.queryMemberByIshuanxin();
//            if(list==null||list.size()==0){
//                map.put("message","没有未注册环信的用户");
//                map.put("code","1");
//                map.put("data",null);
//                return map;
//            }
//            //调用批量注册业务
//            List<Integer> members=doctorService.registerhuanxin(list);
//            //判断是否有注册失败的用户
//            if(members!=null){
//                map.put("message","注册环信的用户成功，返回注册失败的memberid");
//                map.put("code","0");
//                map.put("data",members);
//
//            }else{
//                map.put("message","注册环信的用户成功,无注册失败账户");
//                map.put("code","0");
//                map.put("data",null);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//    }
//
//    /**
//     * 每周日定时计算医生成就
//     * @throws Exception
//     */
//    @Scheduled
//    public void automaticUdpateAchievement() throws Exception {
//        doctorService.automaticUdpateAchievement();
//    }
//
//
//
//
////    /**
////     * 请求登录，验证用户
////     */
////    @RequestMapping(value="/admin_login")
////    public ModelAndView login(HttpSession session)throws Exception{
////        mv.clear();
////        String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);
////        String errInfo = "";
////
////        pd = this.getPageData();
////        PageData pdm = new PageData();
////        pdm = this.getPageData();
////
////        String code = pd.getString("code");
////
////        if(null == code || "".equals(code)){
////            mv.setViewName("redirect:/");
////        }else{
////
////            String USERNAME = pd.get("loginname").toString();
////            String PASSWORD  = pd.get("password").toString();
////            pd.put("USERNAME", USERNAME);
//////            if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){
////                String passwd = MD5.md5(PASSWORD);
////                pd.put("PASSWORD", passwd);
//////                pd = userService.getUserByNameAndPwd(pd);
////                pd=doctorService.adminLogin(pd);
////                if(pd != null){
//////                    pd.put("LAST_LOGIN",new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()).toString());
//////                    userService.updateLastLogin(pd);
////
////                    User user = new User();
////
////                    user.setUSER_ID(pd.getString("aid"));
////                    user.setUSERNAME(pd.getString("username"));
//////                    user.setPASSWORD(pd.getString("PASSWORD"));
//////                    user.setNAME(pd.getString("NAME"));
//////                    user.setRIGHTS(pd.getString("RIGHTS"));
//////                    user.setROLE_ID(pd.getString("ROLE_ID"));
//////                    user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
//////                    user.setIP(pd.getString("IP"));
//////                    user.setSTATUS(pd.getString("STATUS"));
////
////                    session.setAttribute(Const.SESSION_USER, user);
////                    session.removeAttribute(Const.SESSION_SECURITY_CODE);
////                }else{
////                    errInfo = "用户名或密码有误！";
////                }
//////            }else{
//////                errInfo = "验证码输入有误！";
//////            }
////            if(Tools.isEmpty(errInfo)){
////                mv.setViewName("redirect:login_index.do");
////            }else{
////                mv.addObject("errInfo", errInfo);
////                mv.addObject("loginname",USERNAME);
////                mv.addObject("password",PASSWORD);
////                mv.setViewName("system/admin/login");
////            }
////            mv.addObject("pd",pdm);
////        }
////        return mv;
////    }
//    /**
//     * 请求登录，验证用户
//     */
//    @RequestMapping(value="/admin_login")
//    @ResponseBody
//    public String login(String username,String password)throws Exception{
//        System.out.println(username+"-----"+password);
//        UserInfoBO userInfoBO=new UserInfoBO();
//        userInfoBO.setMember_name(username);
//        userInfoBO.setMember_passwd(MD5.md5(MD5.md5(password)));
//        AdminUserBO adminUserBO=doctorService.adminLogin(userInfoBO);
//        if (userInfoBO==null){
//            return "fail";
//        }
//        return  "1";
//    }
//
//    /**
//     *
//     * @param adminDoctorListBo
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value="/admin_queryDoctors")
//    @ResponseBody
//    public List<AdminDoctorListBo> admin_queryDoctors(@RequestBody AdminDoctorListBo adminDoctorListBo)throws Exception{
////        AdminDoctorListBo adminDoctorListBo=new AdminDoctorListBo();
//        List<AdminDoctorListBo> list=doctorService.queryDoctors(adminDoctorListBo);
//    return list;
//    }
//
//    /**
//     * 认证
//     * @param memberid
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value="/admin_renzheng")
//    @ResponseBody
//    public String admin_renzheng(String memberid)throws Exception{
////        AdminDoctorListBo adminDoctorListBo=new AdminDoctorListBo();
//
//        String code="0";
//
//        if (memberid==null||"".equals(memberid)){
//            return "1";
//        }
//        UserInfoBO userInfoBO=new UserInfoBO();
//        userInfoBO.setMember_id(Integer.parseInt(memberid.trim()));
//        try {
//            doctorService.renzheng(userInfoBO);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "1";
//        }
//        return code;
//    }
//
//    /**
//     * 用户意见反馈
//     */
//    @RequestMapping(value = "memberFeedBack")
//    @ResponseBody
//    public Map<String,Object> memberFeedBack(@RequestBody FeedBackVo feedBackVo) throws Exception {
//        Map<String,Object> map=Tools.errMessage();
//        TokenVo tv=new TokenVo();
//        tv.setToken(feedBackVo.getToken());
//        try {
//            UserInfoBO user =doctorService.checkMemberByToken(tv);
//            if(user==null){
//                return  map;
//            }
////            UserInfoBO user =doctorService.findMemberNameById(tv);
//
//            if(feedBackVo.getSource()==null){
//                feedBackVo.setSource("1");
//            }
//            feedBackVo.setMember_id(user.getMember_id());
//            feedBackVo.setFtime(System.currentTimeMillis()/1000);
//            feedBackVo.setMember_name(user.getMember_name());
//            doctorService.feedBack(feedBackVo);
//            map.put("message","您的意见已反馈到我们的平台，谢谢");
//            map.put("code","0");
//            map.put("data",null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 修改密码
//     */
//    @RequestMapping(value="updatePassword")
//    @ResponseBody
//    public Map<String, Object> updatePassword(@RequestBody UserInfoVO userInfoVO) throws Exception {
//        Map<String, Object> map=Tools.errMessage();
//        TokenVo tokenVo = new TokenVo();
//        tokenVo.setToken(userInfoVO.getToken());
//        try {
//            UserInfoBO userInfoBO = doctorService.checkMemberByToken(tokenVo);
//            if(userInfoBO!=null){
//                userInfoVO.setPassWord(MD5.md5(userInfoVO.getPassWord()));
//                userInfoVO.setUserName(userInfoBO.getMember_name());
//                userInfoBO = doctorService.queryMemberByUserNameAndPwd(userInfoVO);
//
//                if(userInfoBO!=null){
//                    userInfoBO.setMember_passwd(MD5.md5(userInfoVO.getNewpassWord()));
//                    doctorService.updatePWD(userInfoBO);
//                    map.put("code","0");
//                    map.put("data",null);
//                    map.put("message","修改密码成功");
//                }else{
//                    map.put("code","1");
//                    map.put("data",null);
//                    map.put("message","用户名或密码不正确");
//                    return map;
//                }
//            }else {
//                map.put("code","2");
//                map.put("data",null);
//                map.put("message","token已过期");
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 环信医生批量注册
//     * @return
//     */
//    @RequestMapping("huanxinRegisterForDcotor")
//    @ResponseBody
//    public  Map<String, Object> huanxinRegisterForDcotor() {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            List<UserInfoBO> list = doctorService.queryDoctorIsNotHuanxin();
//            if(list == null || list.size() <= 0) {
//                map.put("code", "0");
//                map.put("message", "所有用户都已注册环信");
//                map.put("data", null);
//                return map;
//            }
//            // 环信批量
//            List<Integer> failerIdList = doctorService.huanxinRegisterGroup(list);
//            if(failerIdList == null) {
//                map.put("code", "0");
//                map.put("message", "所有用户注册完成");
//                map.put("data", null);
//                return map;
//            }else {
//                map.put("code","0");
//                map.put("message", "返回注册失败的用户id");
//                map.put("data", failerIdList);
//                return map;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//
//    }
//
//
//    /**
//     * 李荣洲
//     * 环信医生单条循环注册
//     * @return
//     */
//    @RequestMapping("huanxinRegisterForDcotorList")
//    @ResponseBody
//    public  Map<String, Object> huanxinRegisterForDcotorList() {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            List<UserInfoBO> list = doctorService.queryDoctorIsNotHuanxin();
//            if(list == null || list.size() <= 0) {
//                map.put("code", "0");
//                map.put("message", "所有用户都已注册环信");
//                map.put("data", null);
//                return map;
//            }
//            // 环信批量
//            List<Integer> failerIdList = doctorService.huanxinRegisterList(list);
//            if(failerIdList == null) {
//                map.put("code", "0");
//                map.put("message", "所有用户注册完成");
//                map.put("data", null);
//                return map;
//            }else {
//                map.put("code","0");
//                map.put("message", "返回注册失败的用户id");
//                map.put("data", failerIdList);
//                return map;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//
//    }
//}
//
