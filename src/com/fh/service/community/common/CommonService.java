package com.fh.service.community.common;

import com.fh.dao.DaoSupport;
import com.fh.entity.bo.*;
import com.fh.entity.bo.MessageInfoBo;
import com.fh.entity.vo.*;
import com.fh.util.HttpSender;
import com.fh.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
@Service("commonService")
public class CommonService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
     * 注册短信验证
    */
    public TokenVo validateForegister (TokenVo vo)throws Exception{
        TokenVo  reVo=new TokenVo();//返回前台值
        MessageBO bo =new MessageBO(); //短信接口调用
        SmsBO sms =new SmsBO();//信息存储
        //生成6为随机数
        String str =String.valueOf((int)((Math.random()*9+1)*100000));
        reVo.setCode(str);
        String message="";
        String time = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
        if("1".equals(vo.getType())){
            message="您于"+time+"申请注册用户，验证码："+str+" ,30分钟内有效。";
        }else if("3".equals(vo.getType())){
            message="您于"+time+"申请重置登录密码，验证码："+str+",30分钟内有效。";
        }else if("2".equals(vo.getType())){
            message="您于"+time+"设置支付密码，验证码："+str+",30分钟内有效。";
        }else if("4".equals(vo.getType())){
            message="您于"+time+"解绑手机号，验证码："+str+",30分钟内有效。";
        }else if("5".equals(vo.getType())){
            message="您于"+time+"绑定手机号，验证码："+str+",30分钟内有效。";
        }else if("6".equals(vo.getType())){
            message="您于"+time+"修改注册信息，验证码："+str+",30分钟内有效。";
        }

        bo.setMobiles(vo.getPhone());//获取电话号
        bo.setContent(message);//获取电话号
        String[] code = HttpSender.message(bo).split(",");
        if("0".equals(code[1].substring(0,1))){//发送成功
            message="中孝社区"+message;
            sms.setLog_captcha(str);
            sms.setLog_phone(vo.getPhone());
            if("1".equals(vo.getType())){
                sms.setLog_msg(message);
                sms.setLog_type(1);
            }else if("3".equals(vo.getType())){
                sms.setLog_msg(message);
                sms.setLog_type(3);
            }else if("2".equals(vo.getType())){
                sms.setLog_msg(message);
                sms.setLog_type(2);
            }else if("4".equals(vo.getType())){
                sms.setLog_msg(message);
                sms.setLog_type(4);
            }else if("5".equals(vo.getType())){
                sms.setLog_msg(message);
                sms.setLog_type(5);
            }else if("6".equals(vo.getType()))
                sms.setLog_msg(message);
                sms.setLog_type(6);
            dao.save("Common.setSms", sms);
        }else{
            reVo.setCode("000000");
            return reVo;
        }
        return reVo;
    }
//提现群发短信
    public String MessageToSend (SendMessageVO sendMessageVO)throws Exception{
        String result="0";
        System.out.println(sendMessageVO.getSend());
        TokenVo  reVo=new TokenVo();//返回前台值
        MessageBO bo =new MessageBO(); //短信接口调用
        SmsBO sms =new SmsBO();//信息存储
        //生成6为随机数

        String phones=sendMessageVO.getSend();

        phones=phones.substring(0,phones.length());
        String str =String.valueOf((int)((Math.random()*9+1)*100000));
        reVo.setCode(str);
        String message="";
//        String time = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
//        if("1".equals(vo.getType())){
//            message="您于"+time+"申请注册用户，验证码："+str+" ,30分钟内有效。";
//        }else if("3".equals(vo.getType())){
//            message="您于"+time+"申请重置登录密码，验证码："+str+",30分钟内有效。";
//        }else if("2".equals(vo.getType())){
//            message="您于"+time+"设置支付密码，验证码："+str+",30分钟内有效。";
//        }else if("4".equals(vo.getType())){
//            message="您于"+time+"解绑手机号，验证码："+str+",30分钟内有效。";
//        }else if("5".equals(vo.getType())){
//            message="您于"+time+"绑定手机号，验证码："+str+",30分钟内有效。";
//        }else if("6".equals(vo.getType())){
//            message="您于"+time+"修改注册信息，验证码："+str+",30分钟内有效。";
//        }
        List<String> list = Arrays.asList(phones.split(","));
        for (String s : list) {
            bo.setMobiles(s);
            bo.setContent(sendMessageVO.getMessage());
            String[] code = HttpSender.message(bo).split(",");
            if(!"0".equals(code[1].substring(0,1))){
                result="1";
            }
        }
return result;
    }

    /*
     * 注册短信验证
    */
    public SmsBO confirmValidate (TokenVo vo)throws Exception{
        return   (SmsBO)dao.findForObject("Common.confirmValidate",vo);

    }
    /*
     * 注册短信验证(30分钟)
    */
    public SmsBO confirmValidateTime (TokenVo vo)throws Exception{
        return   (SmsBO)dao.findForObject("Common.confirmValidateTime",vo);
    }
    public void test ()throws Exception{
        List<TbAmountrecordBO> list=(List<TbAmountrecordBO>)dao.findForList("Common.findAmountrecord",null);
        if(list !=null && list.size()>0){
            for(int i=0 ;i<list.size();i++){
                TbAmountrecordBO bo =list.get(i);
                dao.findForList("Common.updateReward",bo);
            }
        }
    }
    //验证用户
    public UserInfoBO checkMember(TokenVo vo) throws Exception {
        return (UserInfoBO)dao.findForObject("Common.confirmMember",vo);
    }
    //确认注册类型(php ,iso ,andrion)
    public void setType (SmsBO bo)throws Exception{
        dao.update("Common.setType", bo);
    }

    public UserInfoBO checkUserForModifyPassWord(UserInfoBO userInfoBO)throws Exception{
        return (UserInfoBO) dao.findForObject("Common.checkUserForModifyPassWord",userInfoBO);
    }
    /**
     * 修改密码短信验证
     */
    public TokenVo validateFindBackPassword(TokenVo vo)throws Exception{
        TokenVo  reVo=new TokenVo();//返回前台值
        MessageBO bo =new MessageBO(); //短信接口调用
        SmsBO sms =new SmsBO();//信息存储
        //生成6为随机数
        String str =String.valueOf((int)((Math.random()*9+1)*100000));
        reVo.setCode(str);
        String message="";
        String time = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
        if("1".equals(vo.getType())){
            message="您于"+time+"申请注册用户，验证码："+str+" ,30分钟内有效。";
        }else if("3".equals(vo.getType())){
            message="您于"+time+"申请重置登录密码，验证码："+str+",30分钟内有效。";
        }else if("2".equals(vo.getType())){
            message="您于"+time+"设置支付密码，验证码："+str+",30分钟内有效。";
        }else if("4".equals(vo.getType())){
            message="您于"+time+"绑定手机号，验证码："+str+",30分钟内有效。";
        }



        bo.setMobiles(vo.getPhone());//获取电话号
        bo.setContent(message);//获取电话号
        String[] code = HttpSender.message(bo).split(",");
        if("0".equals(code[1].substring(0,1))){//发送成功
            message="中孝社区"+message;
            sms.setLog_captcha(str);
            sms.setLog_phone(vo.getPhone());
            if("1".equals(vo.getType())){
                sms.setLog_msg(message);
                sms.setLog_type(1);
            }else if("3".equals(vo.getType())){
                sms.setLog_msg(message);
                sms.setLog_type(3);
            }
            sms.setMember_name(vo.getName());
            sms.setMember_id(Integer.parseInt(vo.getUserid().trim()));
            dao.save("Common.setSmsForLogin", sms);
        }else{
            reVo.setCode("000000");
            return reVo;
        }
        return reVo;
    }
    /**
     *
     * 修改密码验证
     */
    public SmsBO confirmPassWord (TokenVo vo)throws Exception{
        return (SmsBO)dao.findForObject("Common.confirmForPassword",vo);

    }
    /**
     *
     * 修改密码
     */
    public void modifyPassWord (UserInfoBO userInfoBO)throws Exception{
        dao.update("Common.modifyPassWord", userInfoBO);


    }

    /**
     * 分页查询商场咨询
     * 校验token是否失效
     */
    public UserInfoBO checkTokenIsGone(QueryPageVo queryPageVo) throws Exception {
        UserInfoBO userInfoBO= (UserInfoBO) dao.findForObject("Common.QueryMember", queryPageVo);
        return userInfoBO;
    }
    /**
     * 分页查查询商场咨询
     * @param queryPageVo
     * @return
     * @throws Exception
     */
    public List<QueryMessageMallBo> queryMessageMall(QueryPageVo queryPageVo) throws Exception {
        if(queryPageVo.getPageIndex()!=0&&queryPageVo.getPageSize()!=0){
            queryPageVo.setPageIndex((queryPageVo.getPageIndex()-1)*queryPageVo.getPageSize());
        }
        List<QueryMessageMallBo> list= (List<QueryMessageMallBo>) dao.findForList("Common.queryMessageMall", queryPageVo);
        for (QueryMessageMallBo queryMessageMallBo : list) {
            queryMessageMallBo.setId(queryMessageMallBo.getHref());
        }
        return list;
    }

    /**
     * 分页查询咨询信息
     * @param queryPageVo
     * @return
     * @throws Exception
     */
    public List<MessageInfoBo> queryMessageInfo(QueryPageVo queryPageVo) throws Exception {
        if(queryPageVo.getPageIndex()!=0&&queryPageVo.getPageSize()!=0){
            queryPageVo.setPageIndex((queryPageVo.getPageIndex()-1)*queryPageVo.getPageSize());
        }
        List<MessageInfoBo> list = (List<MessageInfoBo>) dao.findForList("Common.queryMessageInfo",queryPageVo);
        return list;
    }
    /**
     * 通过member表中的一些特定字段查询咨询信息
     * @param commonMemberVo
     * @return
     * @throws Exception
     */
    public UserInfoBO queryMemberInfo(CommonMemberVo commonMemberVo) throws Exception {
        UserInfoBO memberInfo = (UserInfoBO) dao.findForObject("Common.queryMemberInfo", commonMemberVo);
        return memberInfo;
    }
    //token 校验
    public List<TokenBO> checkToken(TokenBO tokenBO) throws Exception{
        return (List<TokenBO>) dao.findForList("Common.checkToken",tokenBO);
    }
    /*
    * 用户登录确认
    */
    public MemberBO userLogin (UserInfoVO user)throws Exception{
        return (MemberBO)dao.findForObject("Member.userLogin", user);
    }

    /**
     * 根据id查询token
     * @param userInfoBO
     * @return
     * @throws Exception
     */
    public TokenBO findTokenById(UserInfoBO userInfoBO)throws Exception{
        return (TokenBO) dao.findForObject("Member.findTokenById",userInfoBO);
    }

    /**
     * 根据ID修改token
     * @param tokenBO
     * @throws Exception
     */
    public void updateTokenById(TokenBO tokenBO)throws Exception{
        dao.update("Member.updateTokenById",tokenBO);

    }
    /**
     * 添加token
     * @param tokenBO
     * @throws Exception
     */
    public void saveToken(TokenBO tokenBO)throws Exception{
        dao.findForObject("Member.setToken", tokenBO);
    }

    public TokenVo checkUserinfo(TokenVo tokenVo)throws Exception{
        return (TokenVo) dao.findForObject("Member.checkUserinfo",tokenVo);
    }

    /**
     * 获取主页推荐商品信息
     * @auth 李荣洲
     * @return
     * @throws Exception
     */
    public List<IndexInfoBo> queryIndexInfoGood() throws Exception {
        String type="1";
        return (List<IndexInfoBo>) dao.findForList("Common.queryIndexInfo", type);
    }
    /**
     * 获取主页推荐医生信息
     * @auth 李荣洲
     * @return
     * @throws Exception
     */
    public List<IndexInfoBo> queryIndexInfoDoctor() throws Exception {
        String type="2";
        return (List<IndexInfoBo>) dao.findForList("Common.queryIndexInfo", type);
    }
    /**
     * 获取主页推荐直播信息
     * @auth 李荣洲
     * @return
     * @throws Exception
     */
    public List<IndexInfoBo> queryIndexInfoVideo() throws Exception {
        String type="3";
        return (List<IndexInfoBo>) dao.findForList("Common.queryIndexInfo", type);
    }

    /**
     * 从数据库获取版本号
     * @param vo
     * @return
     */
    public TokenVo queryVersion(TokenVo vo) throws Exception {

        String version = (String) dao.findForObject("Common.queryVersion", vo);
        String url = (String) dao.findForObject("Common.queryUrl", vo);

        TokenVo tokenVo = new TokenVo();

        tokenVo.setVersion(version);
        tokenVo.setUrl(url);

        return tokenVo;
    }

    public UserInfoBO queryPaypwd(UserInfoBO userInfoBO) throws Exception {
        userInfoBO= (UserInfoBO) dao.findForObject("Common.queryMemberPaypwdByMemberId",userInfoBO);
        return userInfoBO;
    }

    public List<MemberListVO> queryMemberListByTime(AdminQueryMemberVo queryMemberVo) throws Exception {

        List<MemberListVO> list= (List<MemberListVO>) dao.findForList("Common.queryMemberListByCashTime",queryMemberVo);
        return list;
    }

}
