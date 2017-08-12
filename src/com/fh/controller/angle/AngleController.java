package com.fh.controller.angle;

import com.fh.entity.angle.*;
import com.fh.entity.bo.TokenBO;
import com.fh.entity.bo.UserInfoBO;
import com.fh.entity.vo.MessageVO;
import com.fh.entity.vo.MessageVoNew;
import com.fh.entity.vo.TokenVo;
import com.fh.entity.zxys.ZxysDoctorBO;
import com.fh.entity.zxys.ZxysDirectSeedingBo;
import com.fh.entity.zxys.ZxysDoctorVO;
import com.fh.huanxin.model.Authentic;
import com.fh.service.angle.AngleService;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.MD5;
import com.fh.util.Tools;
import com.fh.util.zhibo.CreateDirectseeding;
import com.fh.util.zhibo.DirectSeeding;
import com.sun.javafx.geom.AreaOp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.print.attribute.standard.MediaName;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/13.
 */
@Controller
@RequestMapping("/angle")
public class AngleController {

    @Resource(name = "angleService")
    private AngleService angleService;

    /**
     * 登录
     * @param memberLoginVO
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody AngleMemberLoginVO memberLoginVO) {

        Map<String, Object> map = Tools.errMessage();
        if(memberLoginVO.getMobile()==null || "".equals(memberLoginVO.getMobile())){
            map.put("code", "1");
            map.put("data", null);
            map.put("message", "请输出用户名");
            return map;
        }
        if(memberLoginVO.getPassword()==null || "".equals(memberLoginVO.getPassword())){
            map.put("code", "1");
            map.put("data", null);
            map.put("message", "请输入密码");
            return map;
        }
        try {
            // 登录校验
            memberLoginVO.setPassword(MD5.md5(memberLoginVO.getPassword()));
            AngleMemberLoginBO loginBo = angleService.findMemberByPwd(memberLoginVO);
            if (loginBo == null) {
                map.put("code", "0");
                map.put("data", null);
                map.put("message", "用户名或密码错误");
                return map;
            } else {
                // 产生token
                TokenBO token = angleService.checkTokenExist(loginBo);
                String newToken = Tools.getToken();
                //获取当前时间戳
                String time = String.valueOf(System.currentTimeMillis() / 1000);
                if (token != null) {
                    //如果存在修改用户token值
                    token.setMember_id(loginBo.getMember_id());
                    token.setToken(newToken);
                    token.setLogin_time(time);
                    angleService.updateTokenById(token);
                } else {
                    //如果不存在添加新的token
                    token = new TokenBO();
                    token.setMember_id(loginBo.getMember_id());
                    token.setToken(newToken);
                    token.setClient_type("wap");
                    token.setLogin_time(time);

                    if (loginBo.getMember_nickname() != null && !"".equals(loginBo.getMember_nickname())) {
                        token.setMember_name(loginBo.getMember_nickname());
                    } else {
                        token.setMember_name(loginBo.getMember_mobile());
                    }
                    angleService.savaToken(token);
                }
                MessageVoNew vo = new MessageVoNew();
                vo.setToken(newToken);
                vo.setUid(loginBo.getMember_id() + "");
                if (loginBo.getMember_nickname() != null && !"".equals(loginBo.getMember_nickname())) {
                    vo.setUsername(loginBo.getMember_nickname());
                } else {
                    vo.setUsername(loginBo.getMember_mobile());
                }

                map.put("code", "0");
                map.put("data", vo);
                map.put("message", "登录成功");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }

    // 注册
    @RequestMapping("reg")
    @ResponseBody
    public Map<String, Object> reg(@RequestBody AngleMemberLoginVO memberLoginVO) {
        Map<String, Object> map = Tools.errMessage();
        try {
            // 注册校验
            if (memberLoginVO.getMobile() == null || "".equals(memberLoginVO.getMobile())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "请填写您的手机号");
                return map;
            }
            if (memberLoginVO.getPassword() == null || "".equals(memberLoginVO.getPassword())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "请设置您的登录密码");
                return map;
            }
            if (!Tools.checkPassword(memberLoginVO.getPassword())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "密码只能由6-16位的字母和数字组成");
                return map;
            }

            // 重复注册校验
            AngleMemberLoginBO memberLoginBO = angleService.findMemberByMobile(memberLoginVO);
            if (memberLoginBO != null) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "您的手机号已经被注册");
                return map;
            }

            // 开始注册
            memberLoginVO.setPassword(MD5.md5(memberLoginVO.getPassword()));
            boolean result = angleService.reg(memberLoginVO);
            if (result) {

                // 产生token
                //　 产生token
                String token = Tools.getToken();
                //获取当前时间戳
                String time = String.valueOf(System.currentTimeMillis() / 1000);

                TokenBO tokenBo = new TokenBO();
                tokenBo.setToken(token);
                tokenBo.setLogin_time(time);
                if (memberLoginVO.getNickname() != null && !"".equals(memberLoginVO.getNickname())) {
                    tokenBo.setMember_name(memberLoginVO.getNickname());
                } else {
                    tokenBo.setMember_name(memberLoginVO.getMobile());
                }

                tokenBo.setClient_type("wap");
                tokenBo.setMember_type(2);
                AngleMemberLoginBO member = angleService.findMemberByMobile(memberLoginVO);
                if (member != null) {
                    tokenBo.setMember_id(member.getMember_id());
                }

                // 产生该用户的token记录
                angleService.savaToken(tokenBo);

                MessageVO messageVO = new MessageVO();
                messageVO.setToken(token);
                messageVO.setUid(member.getMember_id() + "");
                messageVO.setUsername(member.getMember_mobile());

                map.put("code", "0");
                map.put("data", messageVO);
                map.put("message", "注册成功");
                return map;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }

        return map;
    }

    /**
     * 热门主播
     *
     * @return
     */
    @RequestMapping("hotLive")
    @ResponseBody
    public Map<String, Object> hotLive(@RequestBody AnglePageVO page) {
        Map<String, Object> map = Tools.errMessage();
        try {
            // token校验
            TokenVo tokenVo = new TokenVo();
            tokenVo.setToken(page.getToken());
            TokenBO token = angleService.checkTokenOver(tokenVo);
            if (token == null) {
                return map;
            }
            // 请求热门直播数据
            List<AngleLiveBO> hotList = angleService.findHotLive(page);
            if (hotList == null || hotList.size() == 0) {
                map.put("code", "0");
                map.put("data", null);
                map.put("message", "暂无数据");
                return map;
            }

            map.put("code", "0");
            map.put("data", hotList);
            map.put("message", "数据加载成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }


    /**
     * 频道列表
     *
     * @param tokenVo
     * @return
     */
    @RequestMapping("channelList")
    @ResponseBody
    public Map<String, Object> channelList(@RequestBody TokenVo tokenVo) {
        Map<String, Object> map = Tools.errMessage();
        try {
            // token校验
            TokenBO token = angleService.checkTokenOver(tokenVo);
            if (token == null) {
                return map;
            }
            // 频道数据
            List<AngleChannelBO> channelList = angleService.findChannelList();
            if (channelList == null || channelList.size() == 0) {
                map.put("code", "0");
                map.put("data", null);
                map.put("message", "敬请期待");
                return map;
            }
            map.put("code", "0");
            map.put("data", channelList);
            map.put("message", "数据加载成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }

    /**
     * 礼物列表
     *
     * @param page
     * @return
     */
    @RequestMapping("giftList")
    @ResponseBody
    public Map<String, Object> giftList(@RequestBody AnglePageVO page) {
        Map<String, Object> map = Tools.errMessage();
        try {
            // token校验
            TokenVo tokenVo = new TokenVo();
            tokenVo.setToken(page.getToken());
            TokenBO token = angleService.checkTokenOver(tokenVo);
            if (token == null) {
                return map;
            }
            List<AngleGiftBO> giftList = angleService.findGiftList(page);
            if (giftList == null || giftList.size() == 0) {
                map.put("code", "0");
                map.put("data", null);
                map.put("message", "敬请期待");
                return map;
            }

            map.put("code", "0");
            map.put("data", giftList);
            map.put("message", "加载数据成功");
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
    }

    /**
     * 直播间人数
     *
     * @param liveVO
     * @return
     */
    @RequestMapping("roomMemberNum")
    @ResponseBody
    public Map<String, Object> roomMemberNum(@RequestBody AngleLiveVO liveVO) {
        Map<String, Object> map = Tools.errMessage();
        try {
            // token校验
            TokenVo tokenVo = new TokenVo();
            tokenVo.setToken(liveVO.getToken());
            TokenBO token = angleService.checkTokenOver(tokenVo);
            if (token == null) {
                return map;
            }

            int roomNum = angleService.liveRoomNum(liveVO);
            map.put("code", "0");
            map.put("data", roomNum);
            map.put("message", "数据加载成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }

    /**
     * 直播间观众列表
     *
     * @param liveVO
     * @return
     */
    @RequestMapping("roomMemberList")
    @ResponseBody
    public Map<String, Object> roomMemberList(@RequestBody AngleLiveVO liveVO) {
        Map<String, Object> map = Tools.errMessage();
        try {
            // token校验
            TokenVo tokenVo = new TokenVo();
            tokenVo.setToken(liveVO.getToken());
            TokenBO token = angleService.checkTokenOver(tokenVo);
            if (token == null) {
                return map;
            }

            List<AngleMemberBO> memberList = angleService.roomMemberList(liveVO);
            if (memberList == null || memberList.size() == 0) {
                map.put("code", "0");
                map.put("data", null);
                map.put("message", "暂无数据");
                return map;
            }

            map.put("code", "0");
            map.put("data", memberList);
            map.put("message", "数据加载成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }

    /**
     * 田鹏飞
     * @param tokenVo
     * @return
     */
    @RequestMapping("memberInfo")
    @ResponseBody
    public Map<String, Object> memberInfo(@RequestBody TokenVo tokenVo) {
        Map<String, Object> map = Tools.errMessage();
        try {

            // token校验
            TokenBO token = angleService.checkTokenOver(tokenVo);
            if (token == null) {
                return map;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
        return map;
    }
///**
// * 李荣洲
// */
    @RequestMapping("memberAuthentication")
    public Map<String,Object> memberAuthentication(@RequestBody AuthenticationBO authenticationBO) {
        Map<String, Object> map = Tools.errMessage();
        try {
            //验证身份证号是否为空且格式是否正确
            if (Tools.isEmpty(authenticationBO.getMember_idcard())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "请填写您的身份证编号");
                return map;
            } else {
                if (Tools.checkidCard(authenticationBO.getMember_idcard())) {
                    map.put("code", "1");
                    map.put("data", null);
                    map.put("message", "请填写正确的身份证编号");
                    return map;
                }
            }
            //验证真实姓名是否为空
            if (Tools.isEmpty(authenticationBO.getMember_truename())) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "请填写您的真实姓名");
                return map;
            }
            //检测用户是否认证过
            AuthenticationBO result = angleService.isAuthentication(authenticationBO);
            if (result != null) {
                map.put("code", "1");
                map.put("data", null);
                map.put("message", "您已经认证");
                return map;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    //开启直播
    @RequestMapping("saveOrUpdateDirectseeding")
    @ResponseBody
    public Map<String, Object> saveOrUpdateDirectseeding(@RequestBody AngleCommonVO angleCommonVO) {
        Map<String, Object> map = Tools.errMessage();
        try {
            AngleLiveBO angleLiveBO = angleService.queryDirectseeding(angleCommonVO);
            if (angleLiveBO == null) {//如果对象为空 则新开频道
                //开启频道
                CreateDirectseeding createDirectseeding = new CreateDirectseeding();
                ZxysDirectSeedingBo zxysDirectSeedingBo = createDirectseeding.createDirectseeding(angleCommonVO);
                zxysDirectSeedingBo.setMemberid(angleCommonVO.getMemberid());
                //数据库生成频道
                angleService.saveDirectseeding(zxysDirectSeedingBo);
                //从新查询返回
                AngleLiveBO angleLiveBO1 = angleService.queryDirectseeding(angleCommonVO);
                map.put("message", "查询成功");
                map.put("code", "0");
                map.put("data", angleLiveBO1);
            } else {//如果不为空 则该为直播状态
//                angleService.updateDirectSeeding(angleCommonVO);
                map.put("message", "查询成功");
                map.put("code", "0");
                map.put("data", angleLiveBO);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("queryLiveingList")
    @ResponseBody
    public Map<String, Object> queryLiveingList() {
        Map<String, Object> map = Tools.errMessage();
        try {
            List<AngleLiveBO> list =angleService.queryLiveingList();
            map.put("message", "查询成功");
            map.put("code", "0");
            map.put("data", list);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }
    //开启直播间
    @RequestMapping("startLiving")
    @ResponseBody
    public Map<String, Object> startLiving(@RequestBody AngleCommonVO angleCommonVO) {
        Map<String, Object> map = Tools.errMessage();
        try {
             angleService.savePicture(angleCommonVO);
            map.put("message", "开启直播");
            map.put("code", "0");
            map.put("data", null);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }

    //关闭直播间
    @RequestMapping("closeLiving")
    @ResponseBody
    public Map<String, Object> closeLiving(@RequestBody AngleCommonVO angleCommonVO) {
        Map<String, Object> map = Tools.errMessage();
        try {
            angleService.closeLiving(angleCommonVO);
            map.put("message", "已关闭直播间");
            map.put("code", "0");
            map.put("data", null);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }

    //    //直播间登陆
    @RequestMapping("loginLiving")
    @ResponseBody
    public Map<String, Object> loginLiving(@RequestBody AngleCommonVO angleCommonVO) {
        Map<String, Object> map = Tools.errMessage();
        try {
            AngleCommonVO angleCommonVO1 =angleCommonVO;
            angleCommonVO1.setMember_password(MD5.md5(angleCommonVO1.getMember_password()));
            AngleMemberLoginBO angleMemberLoginBO=angleService.loginLiving(angleCommonVO1);
            if(angleMemberLoginBO ==null){
                map.put("message", "用户未注册直播账号");
                map.put("code", "1");
                map.put("data", null);
                return map;
            }else{
                map.put("message", "登陆成功");
                map.put("code", "0");
                map.put("data", angleMemberLoginBO);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Tools.errMessageSystem();
        }
    }
}
