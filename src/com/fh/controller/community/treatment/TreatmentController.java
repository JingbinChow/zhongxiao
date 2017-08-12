//package com.fh.controller.community.treatment;
//
//
//import com.fh.entity.bo.DoctorBo;
//import com.fh.entity.bo.UserInfoBO;
//
//import com.fh.entity.zxys.ZxysOfficeBO;
//import com.fh.entity.zxys.ZxysOfficeVO;
//import com.fh.entity.zxys.ZxysDoctorBO;
//import com.fh.entity.vo.*;
//import com.fh.entity.zxys.*;
//import com.fh.huanxin.model.Authentic;
//import com.fh.service.community.treatment.TreatmentService;
//import com.fh.util.*;
//import com.fh.util.alipay.util.pay;
//import com.fh.util.util.AlipayNotify;
//import com.fh.util.weixinPay.ConstantUtil;
//import com.fh.util.weixinPay.WXUtil;
//import com.fh.util.weixinPay.client.ClientResponseHandler;
//import com.fh.util.weixinPay.client.TenpayHttpClient;
//import com.fh.util.weixinPay.handler.*;
//import com.mysql.fabric.xmlrpc.base.Array;
//import com.sun.org.apache.bcel.internal.generic.NEW;
//import org.apache.james.mime4j.field.datetime.DateTime;
//import org.apache.poi.util.SystemOutLogger;
//import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.math.BigDecimal;
//import java.net.URLEncoder;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
///**
// * Created by Administrator on 2016/10/25 0025.
// */
//@Controller
//@RequestMapping(value="/treatment")
//
//public class TreatmentController {
//    @Resource(name = "treatmentService")
//    private TreatmentService treatmentService;
//
//    private Logger log = Logger.getLogger(TreatmentController.class);
//
//    /**
//     * 带条件的分页查询
//     */
//    @RequestMapping(value = "getDoctors")
//    @ResponseBody
//    public Map<String, Object> getDoctors(@RequestBody QueryDoctorVo queryDoctorVo) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(queryDoctorVo);
//            if (userInfoBO == null) {
//                return map;
//            } else {
//                if(queryDoctorVo.getDid()!=0){
//                    queryDoctorVo.setDepartment(treatmentService.queryDepartment(queryDoctorVo));
//                }
//                List<DoctorBo> list = treatmentService.queryDoctors(queryDoctorVo);
//                if (list != null && list.size() > 0) {
//                    map.put("data", list);
//                    map.put("message", "获取数据成功");
//                    map.put("code", 0);
//                } else {
//                    map.put("data", null);
//                    map.put("message", "暂无数据");
//                    map.put("code", 0);
//                }
//                map.put("data", list);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map = Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//
//    }
//
//    /**
//     * 医生详情
//     */
//    @RequestMapping(value = "getDoctorsById")
//    @ResponseBody
//    public Map<String, Object> getDoctorsById(@RequestBody QueryDoctorVo queryDoctorVo) {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(queryDoctorVo);
//            if (userInfoBO == null) {
//                return map;
//            } else {
////                int memberId = userInfoBO.getMember_id();
//                DoctorBo bo = treatmentService.queryDoctorsById(queryDoctorVo);
//                if (bo != null) {
//                    map.put("message", "获取数据成功");
//                    map.put("code", "0");
//                    map.put("data", bo);
////                    map.put("id",memberId);
//                } else {
//                    map.put("message", "没有匹配到数据");
//                    map.put("code", "0");
//                    map.put("data", null);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("message", "系统内部错误");
//            map.put("code", "1");
//            map.put("data", null);
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 添加交流记录
//     */
//    @RequestMapping(value = "addCommunicate")
//    @ResponseBody
//    public Map<String, Object> addCommunicate(@RequestBody QueryDoctorVo queryDoctorVo) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(queryDoctorVo);
//            if (userInfoBO == null) {
//                return map;
//            } else {
//                //生成记录
//                CommunicateRecordVo communicateRecordVo = new CommunicateRecordVo();
//                communicateRecordVo.setCommunicate_id(Tools.getSysDateString("yyyyMMddHHmmss") + String.valueOf((int) (Math.random() * (9999 - 1000 + 1)) + 1000));
//                communicateRecordVo.setMember_name(userInfoBO.getMember_name());
//                communicateRecordVo.setDoctor_id(queryDoctorVo.getId());
//                Date date = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                communicateRecordVo.setCreate_time(sdf.format(date));
//                communicateRecordVo.setMember_id(userInfoBO.getMember_id());
//                communicateRecordVo.setDoctor_name(queryDoctorVo.getDoctor_name());
//                communicateRecordVo.setMember_truename(userInfoBO.getMember_truename());
//                //医生金额 从数据库取得
//                DoctorBo Dobo = treatmentService.queryDoctorsById(queryDoctorVo);
//                communicateRecordVo.setMoney(Dobo.getPrice());
//                communicateRecordVo.setStatus("2");
//                communicateRecordVo.setBespoke_time(queryDoctorVo.getBespokeTime());
//                communicateRecordVo.setReason(queryDoctorVo.getReason());
//                treatmentService.addCommunicateRecord(communicateRecordVo);
//                CommunicateRecordVo vo = treatmentService.findCommunicateRecord(communicateRecordVo);
//
//                //支付宝alipay
////            String orderInfo =pay.alipay(vo);
////            vo.setOrderInfo(orderInfo);
//                map.put("data", vo);
//                map.put("message", "预约订单已生成");
//                map.put("code", 0);
//
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
//     * 返回支付码
//     */
//    @RequestMapping(value = "getAlipay")
//    @ResponseBody
//    public Map<String, Object> getAlipay(@RequestBody CommunicateRecordVo communicateRecordVo) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        QueryDoctorVo queryDoctorVo = new QueryDoctorVo();
//        queryDoctorVo.setToken(communicateRecordVo.getToken());
//        UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(queryDoctorVo);
//        if (userInfoBO == null) {
//            return map;
//        }
//        communicateRecordVo = treatmentService.findCommunicateById(communicateRecordVo.getCommunicate_id());
//        if (communicateRecordVo == null) {
//            map.put("data", null);
//            map.put("message", "数据传输错误");
//            map.put("code", 1);
//        }
//        String orderInfo = new String(pay.alipay(communicateRecordVo).getBytes("gb2312"), "utf-8");
//        System.out.println(Tools.getEncoding(orderInfo));
//        map.put("data", orderInfo);
//        map.put("message", "获取支付码");
//        map.put("code", 0);
//        return map;
//    }
//
//    /**
//     * 查询交流记录
//     */
//    @RequestMapping(value = "queryCommunicate")
//    @ResponseBody
//    public Map<String, Object> queryCommunicate(@RequestBody QueryCommunicateVO queryCommunicateVO) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(queryCommunicateVO);
//            if (userInfoBO == null) {
//                return map;
//            } else {
//                int memberId = userInfoBO.getMember_id();
//                queryCommunicateVO.setMember_id(memberId);
////                int index = queryCommunicateVO.getPageIndex();
////
////                System.out.println(index);
////                queryCommunicateVO.setPageIndex((index - 1) * queryCommunicateVO.getPageSize());
//
//                List<CommunicateRecordVo> list = treatmentService.queryCommunicate(queryCommunicateVO);
//
//                if (list != null && list.size() > 0) {
//                    map.put("data", list);
//                    map.put("message", "获取数据成功");
//                    map.put("code", 0);
//                } else {
//                    map.put("data", null);
//                    map.put("message", "暂无数据");
//                    map.put("code", 0);
//                }
//                map.put("data", list);
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
//     * 评价等级和评价内容
//     */
//    @RequestMapping(value = "modifyCommunicate")
//    @ResponseBody
//    public Map<String, Object> modifyCommunicate(@RequestBody ModifyCommunicateVO modifyCommunicateVO) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(modifyCommunicateVO);
//            if (userInfoBO != null) {
//                modifyCommunicateVO.setCommunicate_id(modifyCommunicateVO.getCommunicate_id());
//                modifyCommunicateVO.setScore(modifyCommunicateVO.getScore());
//                modifyCommunicateVO.setComment(modifyCommunicateVO.getComment());
//                treatmentService.modifyCommunicate(modifyCommunicateVO);
//                map.put("code", 0);
//                map.put("data", null);
//                map.put("message", "修改记录成功");
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
//     * 获取客服电话等数据
//     */
//    @RequestMapping(value = "getPhone")
//    @ResponseBody
//    public Map<String, Object> getPhone(@RequestBody QueryCommunicateVO queryCommunicateVO) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//
//            UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(queryCommunicateVO);
//            if (userInfoBO == null) {
//                return map;
//            } else {
//                String phone = Constants.phone;
//                map.put("data", phone);
//                map.put("message", "获取数据成功");
//                map.put("code", 0);
//
//
////                int member_id = userInfoBO.getMember_id();
////                queryCommunicateVO.setMember_id(member_id);
////                int index = queryCommunicateVO.getPageIndex();
////                System.out.println(index);
////                queryCommunicateVO.setPageIndex((index - 1) * queryCommunicateVO.getPageSize());
////                List<QueryCommunicateVO> newvO  = new ArrayList<QueryCommunicateVO>();
////                List<QueryCommunicateVO> list = treatmentService.getPhone(queryCommunicateVO);
////                if(list!=null && list.size()>0){
////                    for(int i=0 ;i<list.size();i++){
////                        QueryCommunicateVO vo =list.get(i);
////                        vo.setPhone(Constants.phone);
////                        newvO.add(vo);
////                    }
////                }
////                if (list != null && list.size() > 0) {
////                    map.put("data", newvO);
////                    map.put("message", "获取数据成功");
////                    map.put("code", 0);
////                } else {
////                    map.put("data", null);
////                    map.put("message", "暂无数据");
////                    map.put("code", 0);
////                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map = Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
////    /**
////     * 存入支付宝返回数据
////     * 异步校验支付数据
////     */
////    @RequestMapping(value = "saveAlipayTransaction")
////    @ResponseBody
////    public String saveAlipayTransaction(HttpServletRequest request,HttpServletResponse response) throws Exception {
////        Map<String,String> params = new HashMap<String,String>();
////        Map requestParams = request.getParameterMap();
////        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
////            String name = (String) iter.next();
////            String[] values = (String[]) requestParams.get(name);
////            String valueStr = "";
////            for (int i = 0; i < values.length; i++) {
////                valueStr = (i == values.length - 1) ? valueStr + values[i]
////                        : valueStr + values[i] + ",";
////            }
////            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
////            valueStr = new String(valueStr.getBytes("gb2312"), "utf-8");
////            params.put(name, valueStr);
////        }
////
////        System.out.println(alipayTransactionVo);
////            //将异步通知中收到的待验证所有参数都存放到map中
////            Map<String, String> param = OrderInfoUtil2_0.getMapInfo(alipayTransactionVo);
////            //调用SDK验证签名
////            boolean signVerified = AlipaySignature.rsaCheckV1(param, pay.ALIPAY_PUBLIC_KEY, "utf-8");
////        System.out.println("111111111111111111111111111111111111");
////        System.out.println(alipayTransactionVo.getNotify_time());
////             System.out.println(signVerified);
////            if (signVerified) {
////                try {
////                    CommunicateRecordVo communicateRecordVo = treatmentService.findCommunicateById(alipayTransactionVo.getOut_trade_no());
////                    if (communicateRecordVo == null) {
////                        return "failure";
////                    } else {
////                        if ("pay.APPID".equals(alipayTransactionVo.getApp_id())) {
////                            return "failure";
////                        }
////                        if ("alipayResultVo.getTotal_amount()".equals(communicateRecordVo.getMoney())) {
////                            return "failure";
////                        } else {
////                            communicateRecordVo.setTrade_no(alipayTransactionVo.getTrade_no());
////                            treatmentService.updateTrade_no(communicateRecordVo);
////                            System.out.println("222222222222222222222222222222222222222");
////                            treatmentService.saveAlipayTransaction(alipayTransactionVo);
////                            return "success";
////                        }
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    return "failure";
////                }
////            } else {
////                return "failure";
////            }
////}
//
//    /**
//     * 支付宝调用的异步验签回调
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("aliPostCallBack")
//    @ResponseBody
//
//    public String aliPostCallBack(HttpServletRequest request ,HttpServletResponse response) throws Exception {
//        //获取支付宝POST过来反馈信息
//
//
////        System.out.println("11111111111111111111111111111111111111111");
//        Map<String,String> params = new HashMap<String,String>();
//        Map requestParams = request.getParameterMap();
//
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//
//                if ("subject".equals(name)){
//                    valueStr=new String(request.getParameter("subject").getBytes("gb2312"),"UTF-8");
//                    System.out.println(Tools.getEncoding(valueStr));
//                    System.out.println("subject:"+valueStr);
//                    continue;
//                }
//                if ("body".equals(name)){
//                    valueStr=new String(request.getParameter("body").getBytes("gb2312"),"UTF-8");
//                    System.out.println(Tools.getEncoding(valueStr));
//                    System.out.println("body:"+valueStr);
//                    continue;
//                }
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//            valueStr = new String(valueStr.getBytes("gb2312"), "utf-8");
//            params.put(name, valueStr);
//        }
//        Collection<String> keyset= params.keySet();
//        List list=new ArrayList<String>(keyset);
//        Collections.sort(list);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("key键---值: "+list.get(i)+","+params.get(list.get(i)));
//        }
//        String sign=new String(request.getParameter("sign").getBytes("gb2312"),"UTF-8");
//        System.out.println("sign:"+sign);
////        params= AlipayCore.paraFilter(params);
//        Tools.writeObject(params);
//        //获取支付宝的通知返回参数
//        //通知时间
//        String notify_time=new String (request.getParameter("notify_time").getBytes("gb2312"),"UTF-8");
//        //通知类型
//        String notify_type = new String(request.getParameter("notify_type").getBytes("gb2312"),"UTF-8");
//        //通知校验ID
//        String notify_id = new String(request.getParameter("notify_id").getBytes("gb2312"),"UTF-8");
//        //支付宝分配给开发者的应用Id
//        String app_id = new String(request.getParameter("app_id").getBytes("gb2312"),"UTF-8");
//        //编码格式
//        String charset=new String(request.getParameter("charset").getBytes("gb2312"),"UTF-8");
//        //接口版本
//        String version=new String(request.getParameter("version").getBytes("gb2312"),"UTF-8");
//        //支付宝交易号
//        String trade_no = new String(request.getParameter("trade_no").getBytes("gb2312"),"UTF-8");
//        //商户订单号
//        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("gb2312"),"UTF-8");
//        //商户业务号
//        String out_biz_no = null;
////                new String(request.getParameter("out_biz_no").getBytes("gb2312"),"UTF-8");
//        //买家支付宝用户号
//        String buyer_id = new String(request.getParameter("buyer_id").getBytes("gb2312"),"UTF-8");
//        //买家支付宝账号
//        String buyer_logon_id = new String(request.getParameter("buyer_logon_id").getBytes("gb2312"),"UTF-8");
//       // 卖家支付宝用户号
//        String seller_id = new String(request.getParameter("seller_id").getBytes("gb2312"),"UTF-8");
//        //卖家支付宝账号
//        String seller_email = new String(request.getParameter("seller_email").getBytes("gb2312"),"UTF-8");
//        //交易状态
//        String trade_status = new String(request.getParameter("trade_status").getBytes("gb2312"),"UTF-8");
//        //订单金额
//        String total_amount = new String(request.getParameter("total_amount").getBytes("gb2312"),"UTF-8");
//        //实收金额
//        String receipt_amount = new String(request.getParameter("receipt_amount").getBytes("gb2312"),"UTF-8");
//        //开票金额
//        String invoice_amount = new String(request.getParameter("invoice_amount").getBytes("gb2312"),"UTF-8");
//        //付款金额
//        String buyer_pay_amount = new String(request.getParameter("buyer_pay_amount").getBytes("gb2312"),"UTF-8");
//        //集分宝金额
//        String point_amount = new String(request.getParameter("point_amount").getBytes("gb2312"),"UTF-8");
//        //总退款金额
//        String refund_fee = null;
//        //订单标题
//        String subject = new String(request.getParameter("subject").getBytes("gb2312"),"UTF-8");
//        //商品描述
//        String body = new String(request.getParameter("body").getBytes("gb2312"),"UTF-8");
//        //交易创建时间
//        String gmt_create = new String(request.getParameter("gmt_create").getBytes("gb2312"),"UTF-8");
//        //交易付款时间
//        String gmt_payment = new String(request.getParameter("gmt_payment").getBytes("gb2312"),"UTF-8");
//        //交易退款时间
//        String gmt_refund = request.getParameter("gmt_refund")==null?null:new String(request.getParameter("gmt_refund").getBytes("gb2312"),"UTF-8");
//        //交易结束时间
//        String gmt_close = request.getParameter("gmt_close")==null?null:new String(request.getParameter("gmt_close").getBytes("gb2312"),"UTF-8");
//        //支付金额信息
//        String fund_bill_list = request.getParameter("fund_bill_list")==null?null:new String(request.getParameter("fund_bill_list").getBytes("gb2312"),"UTF-8");
//        //回传参数
//        String passback_params = request.getParameter("passback_params")==null?null:new String(request.getParameter("passback_params").getBytes("gb2312"),"UTF-8");
//        //优惠券信息
//        String voucher_detail_list = request.getParameter("voucher_detail_list")==null?null:new String(request.getParameter("voucher_detail_list").getBytes("gb2312"),"UTF-8");
//        AlipayTransactionVo alipayTransactionVo=new AlipayTransactionVo(notify_time,notify_type,notify_id,app_id,trade_no,out_trade_no,out_biz_no,buyer_id,buyer_logon_id,seller_id,seller_email,trade_status,total_amount,receipt_amount,invoice_amount,buyer_pay_amount,point_amount,refund_fee,subject,body,gmt_create,gmt_payment,gmt_refund,gmt_close,fund_bill_list);
////        AlipayTransactionVo alipayTransactionVo=new AlipayTransactionVo(notify_time,notify_type,notify_id,app_id,trade_no,out_trade_no,out_biz_no,buyer_id,buyer_logon_id,seller_id,seller_email,trade_status,total_amount,receipt_amount,invoice_amount,buyer_pay_amount,point_amount,refund_fee,subject,body,gmt_create,gmt_payment,gmt_refund,gmt_close,fund_bill_list
////        )
////        String notify_time, String notify_type, String notify_id, String app_id, String trade_no, String out_trade_no, String out_biz_no, String buyer_id, String buyer_logon_id, String seller_id, String seller_email, String trade_status, String total_amount, String receipt_amount, String invoice_amount, String buyer_pay_amount, String point_amount, String refund_fee, String subject, String body, String gmt_create, String gmt_payment, String gmt_refund, String gmt_close, String fund_bill_list
////        System.out.println("回调参数列表:"+"out_trade_no:"+out_trade_no+
////                "remarks"+remarks+"trade_no"+trade_no+"money"+money+"trade_status"+trade_status);
////        System.out.println("这个是>?:"+params);
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表
////        AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "utf-8")
////        System.out.println(AlipayNotify.verify(params));
//        if(AlipayNotify.getSignVeryfy(params,sign)){//验证成功
//            //////////////////////////////////////////////////////////////////////////////////////////
//            //记录用户的消费记录入库
//            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
//
////            if(trade_status.equals("TRADE_FINISHED")){
////
////                System.out.println("post:TRADE_FINISHED");
////                //注意：
////                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
////            } else if (trade_status.equals("TRADE_SUCCESS")){
////
////                System.out.println("post:TRADE_SUCCESS");
////
////            }
//
//            CommunicateRecordVo communicateRecordVo = treatmentService.findCommunicateById(alipayTransactionVo.getOut_trade_no());
//                    if (communicateRecordVo == null) {
//                        return "failure";
//                    } else {
//                        if ("pay.APPID".equals(alipayTransactionVo.getApp_id())) {
//                            return "failure";
//                        }
//                        if ("alipayResultVo.getTotal_amount()".equals(communicateRecordVo.getMoney())) {
//                            return "failure";
//                        } else {
//                            communicateRecordVo.setTrade_no(alipayTransactionVo.getTrade_no());
//                            treatmentService.updateTrade_no(communicateRecordVo);
////                            System.out.println("222222222222222222222222222222222222222");
//                            treatmentService.saveAlipayTransaction(alipayTransactionVo);
//                            return "success";
//                        }
//                    }
//            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//
//
//
//            //out.println("fail");
//
//        }else{
//            System.out.println("3333333333333333333333333333333");
//            return "failure";
//        }
//    }
//    /**
//     * 通过订单号返回支付是否成功
//     */
//    @RequestMapping(value = "checkCommunicateById")
//    @ResponseBody
//    public Map<String, Object> checkCommunicateById(@RequestBody AlipayResultVo alipayResultVo ) throws Exception {
//        Map<String, Object> map=Tools.errMessage();
////        alipayResultVo.getToken();
////        QueryDoctorVo vo=new QueryDoctorVo();
////        vo.setToken(alipayResultVo.getToken());
//        boolean isPay=false;
//        boolean flag=true;
//
//        try {
//
//            isPay=treatmentService.checkCommunicateById(alipayResultVo);
//        } catch (Exception e) {
//            map=Tools.errMessageSystem();
//            e.printStackTrace();
//            return map;
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        if (isPay){
//            map.put("data", null);
//            map.put("message", "支付成功");
//            map.put("code", 0);
//        }else{
//            map.put("data", null);
//            map.put("message", "支付失败");
//            map.put("code", 1);
//        }
//        return map;
//    }
//    /**
//     * 校验支付是否成功
//     */
//    @RequestMapping(value = "checkAlipay")
//    @ResponseBody
//    public Map<String, Object> checkAlipay(@RequestBody AlipayResultVo alipayResultVo ) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
////            AlipayTransactionVo alipayTransactionVo=new AlipayTransactionVo();
////            alipayTransactionVo.setApp_id(alipayResultVo.getApp_id());
//
////            Map<String, String> param = OrderInfoUtil2_0.getMapInfo(alipayResultVo);
////            boolean signVerified = AlipaySignature.rsaCheckV2(param,pay.ALIPAY_PUBLIC_KEY,"utf-8");
////            if(signVerified) {
////            System.out.println("666666666666666666666666666666");
//                QueryDoctorVo vo = new QueryDoctorVo();
//                vo.setToken(alipayResultVo.getToken());
//                UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(vo);
//
//                if (userInfoBO == null) {
//                    return map;
//                }
//
//                CommunicateRecordVo communicateRecordVo = treatmentService.findCommunicateById(alipayResultVo.getOut_trade_no());
//
//                if (communicateRecordVo == null) {
//                    map.put("data", null);
//                    map.put("message", "订单号有误");
//                    map.put("code", 1);
//                    return map;
//                } else {
//                    if ("pay.APPID".equals(alipayResultVo.getApp_id())) {
//                        map.put("data", null);
//                        map.put("message", "密匙有误");
//                        map.put("code", 1);
//                        return map;
//                    }
//                    if ("alipayResultVo.getTotal_amount()".equals(communicateRecordVo.getMoney())) {
//                        map.put("data", null);
//                        map.put("message", "订单金额有误");
//                        map.put("code", 1);
//                        return map;
//                    } else {
//                        communicateRecordVo.setTrade_no(alipayResultVo.getTrade_no());
//                        treatmentService.updateTrade_no(communicateRecordVo);
//                        treatmentService.saveAlipayResult(alipayResultVo);
//                        map.put("data", null);
//                        map.put("message", "支付成功");
//                        map.put("code", 0);
//                    }
//                }
////            }else{
////                map.put("data", null);
////                map.put("message", "验签失败");
////                map.put("code", 1);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//
//
//    /**
//     * 获取科室数据
//     */
//    @RequestMapping(value = "queryDepartment")
//    @ResponseBody
//    public Map<String,Object> queryDepartment() throws Exception{
//        Map<String, Object> map = Tools.errMessage();
//        try {
//        List<DepartmentVo> list = treatmentService.queryDepartment();
//            if (list != null && list.size() > 0) {
//                map.put("data", list);
//                map.put("message", "获取数据成功");
//                map.put("code", 0);
//            } else {
//                map.put("data", null);
//                map.put("message", "暂无数据");
//                map.put("code", 0);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//            map = Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//    /**
//     * 通过订单号查询记录
//     */
//    @RequestMapping(value = "findCommunicateById")
//    @ResponseBody
//    public Map<String,Object> findCommunicateById(@RequestBody CommunicateRecordVo communicateRecordVo) throws Exception{
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            String id = communicateRecordVo.getCommunicate_id();
//            CommunicateRecordVo vo = treatmentService.findCommunicateById(id);
//            if (vo != null ) {
//                map.put("data", vo);
//                map.put("message", "获取数据成功");
//                map.put("code", 0);
//            } else {
//                map.put("data", null);
//                map.put("message", "暂无数据");
//                map.put("code", 0);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//            map = Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 删除订单记录
//     * (修改isdelete字段的状态)
//     */
//    @RequestMapping(value = "modifyIsDelete")
//    @ResponseBody
//    public Map<String, Object> modifyIsDelete(@RequestBody QueryCommunicateVO queryCommunicateVO) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            UserInfoBO userInfoBO=treatmentService.checkTokenIsGone(queryCommunicateVO);
//            if(userInfoBO!=null){
//                queryCommunicateVO.setCommunicate_id(queryCommunicateVO.getCommunicate_id());
//                treatmentService.modifyIsDelete(queryCommunicateVO);
//                map.put("code",0);
//                map.put("data",null);
//                map.put("message","删除记录成功");
//            }else{
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
////    /**
////     * 支付成功之后的订单详情
////     */
////    @RequestMapping(value = "queryAlipayTransaction")
////    @ResponseBody
////    public Map<String, Object> queryAlipayTransaction(@RequestBody AlipayTransactionVo alipayTransactionVo) throws Exception {
////        Map<String, Object> map = Tools.errMessage();
////        try {
////            UserInfoBO userInfoBO=treatmentService.checkToken(alipayTransactionVo);
////            if(userInfoBO!=null){
////
////                treatmentService.queryAlipayTransaction(alipayTransactionVo);
////                map.put("code",0);
////                map.put("data",null);
////                map.put("message","删除记录成功");
////            }else{
////                return map;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            map=Tools.errMessageSystem();
////            return map;
////        }
////        return map;
////    }
//
//
//
//    //微信支付
//    /**
//     * 校验
//     * @param weiXinPayVo
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "weixin")
//    @ResponseBody
//    public Map<String, Object> weixin(@RequestBody WeiXinPayVo weiXinPayVo,HttpServletRequest request ,HttpServletResponse response) throws Exception {
//        Map<String, Object> map = Tools.errMessageSystem();
//        Map<String, Object> resInfo = new HashMap<String, Object>();
//        //接收财付通通知的URL
//        String notify_url = "http://106.75.37.114:8081/treatment/weiXinCallBack.do";
//
//        //---------------生成订单号 开始------------------------
//        //当前时间 yyyyMMddHHmmss
////        String currTime = TenpayUtil.getCurrTime();
////        //8位日期
////        String strTime = currTime.substring(8, currTime.length());
////        //四位随机数
////        String strRandom = TenpayUtil.buildRandom(4) + "";
////        //10位序列号,可以自行调整。
////        String strReq = strTime + strRandom;
////        //订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
////        String out_trade_no = strReq;
//        //---------------生成订单号 结束------------------------
//
//        PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);//生成package的请求类
//        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
//        ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);//返回客户端支付参数的请求类
//        packageReqHandler.setKey(ConstantUtil.PARTNER_KEY);
//
//        int retcode;
//        String retmsg = "";
//        String xml_body = "";
//        //获取token值
//
//        String token = AccessTokenRequestHandler.getAccessToken();
//
//        log.info("获取token------值 " + token);
//
//        if (!"".equals(token)) {
//            //设置package订单参数
//            packageReqHandler.setParameter("bank_type", "WX");//银行渠道
//            packageReqHandler.setParameter("body", "测试"); //商品描述
//            packageReqHandler.setParameter("notify_url", notify_url); //接收财付通通知的URL
//            packageReqHandler.setParameter("partner", ConstantUtil.PARTNER); //商户号
//            packageReqHandler.setParameter("out_trade_no", weiXinPayVo.getOut_trade_no()); //商家订单号
//            packageReqHandler.setParameter("total_fee", weiXinPayVo.getTotal_fee()); //商品金额,以分为单位
//            packageReqHandler.setParameter("spbill_create_ip", request.getRemoteAddr()); //订单生成的机器IP，指用户浏览器端IP
//            packageReqHandler.setParameter("fee_type", "66"); //币种，1人民币   66
//            packageReqHandler.setParameter("input_charset", "UTF-8"); //字符编码
//
//            //获取package包
//            String packageValue = packageReqHandler.getRequestURL();
//            resInfo.put("package", packageValue);
//
//            log.info("获取package------值 " + packageValue);
//
//            String noncestr = WXUtil.getNonceStr();
//            String timestamp = WXUtil.getTimeStamp();
//            String traceid = "";
//            ////设置获取prepayid支付参数
//            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
//            prepayReqHandler.setParameter("appkey", ConstantUtil.APP_KEY);
//            prepayReqHandler.setParameter("noncestr", noncestr);
//            prepayReqHandler.setParameter("package", packageValue);
//            prepayReqHandler.setParameter("timestamp", timestamp);
//            prepayReqHandler.setParameter("traceid", traceid);
//
//            //生成获取预支付签名
//            String sign = prepayReqHandler.createSHA1Sign();
//            //增加非参与签名的额外参数
//            prepayReqHandler.setParameter("app_signature", sign);
//            prepayReqHandler.setParameter("sign_method",
//                    ConstantUtil.SIGN_METHOD);
//            String gateUrl = ConstantUtil.GATEURL + token;
//            prepayReqHandler.setGateUrl(gateUrl);
//
//            //获取prepayId
//            String prepayid = prepayReqHandler.sendPrepay();
//
//            log.info("获取prepayid------值 " + prepayid);
//
//            //吐回给客户端的参数
//            if (null != prepayid && !"".equals(prepayid)) {
//                //输出参数列表
//                clientHandler.setParameter("appid", ConstantUtil.APP_ID);
//                clientHandler.setParameter("appkey", ConstantUtil.APP_KEY);
//                clientHandler.setParameter("noncestr", noncestr);
//                //clientHandler.setParameter("package", "Sign=" + packageValue);
//                clientHandler.setParameter("package", "Sign=WXPay");
//                clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
//                clientHandler.setParameter("prepayid", prepayid);
//                clientHandler.setParameter("timestamp", timestamp);
//                //生成签名
//                sign = clientHandler.createSHA1Sign();
////                clientHandler.setParameter("sign", sign);
//
////                xml_body = clientHandler.getXmlBody();
////                resInfo.put("entity", xml_body);
//                resInfo.put("sign", sign);
//                retcode = 0;
//                retmsg = "OK";
//                resInfo.put("retcode", retcode);
//                resInfo.put("retmsg", retmsg);
//                map.put("message", "成功");
//                map.put("code", "1");
//                map.put("data", resInfo);
//                return map;
//
//            } else {
//
//                retcode = -2;
//                retmsg = "错误：获取prepayId失败";
//                map.put("retcode", retcode);
//                resInfo.put("retmsg", retmsg);
//                resInfo.put("sign", null);
//                map.put("message", "失败");
//                map.put("code", "1");
//                map.put("data", resInfo);
//                return map;
//            }
//        } else {
//            retcode = -1;
//            retmsg = "错误：获取不到Token";
//            resInfo.put("retcode", retcode);
//            resInfo.put("retmsg", retmsg);
//            resInfo.put("sign", null);
//            map.put("message", "失败");
//            map.put("code", "1");
//            map.put("data", resInfo);
//            return map;
//        }
//
//
//    }
//    /**
//     * 微信支付微信异步回调验签方法
//     * 待完善
//     * @auth 李荣洲
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping("weiXinCallBack")
//    @ResponseBody
//    public void weiXinCallBack(HttpServletRequest request ,HttpServletResponse response) throws Exception {
//        String partner = "1900000109";
//
////密钥
//        String key = "c4dbc3329c4d161ebe4d75e2080077b2";
//
////创建支付应答对象
//        ResponseHandler resHandler = new ResponseHandler(request, response);
//        resHandler.setKey(key);
//
////判断签名
//        if(resHandler.isTenpaySign()) {
//
//            //通知id
//            String notify_id = resHandler.getParameter("notify_id");
//
//            //创建请求对象
//            RequestHandler queryReq = new RequestHandler(null, null);
//            //通信对象
//            TenpayHttpClient httpClient = new TenpayHttpClient();
//            //应答对象
//            ClientResponseHandler queryRes = new ClientResponseHandler();
//
//            //通过通知ID查询，确保通知来至财付通
//            queryReq.init();
//            queryReq.setKey(key);
//            queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
//            queryReq.setParameter("partner", partner);
//            queryReq.setParameter("notify_id", notify_id);
//
//            //通信对象
//            httpClient.setTimeOut(5);
//            //设置请求内容
//            httpClient.setReqContent(queryReq.getRequestURL());
//            System.out.println("queryReq:" + queryReq.getRequestURL());
//            //后台调用
//            if(httpClient.call()) {
//                //设置结果参数
//                queryRes.setContent(httpClient.getResContent());
//                System.out.println("queryRes:" + httpClient.getResContent());
//                queryRes.setKey(key);
//
//
//                //获取返回参数
//                String retcode = queryRes.getParameter("retcode");
//                String trade_state = queryRes.getParameter("trade_state");
//                String trade_mode = queryRes.getParameter("trade_mode");
//
//                //判断签名及结果
//                if(queryRes.isTenpaySign()&& "0".equals(retcode) && "0".equals(trade_state) && "1".equals(trade_mode)) {
//                    System.out.println("订单查询成功");
//                    //取结果参数做业务处理
//                    System.out.println("out_trade_no:" + queryRes.getParameter("out_trade_no")+
//                            " transaction_id:" + queryRes.getParameter("transaction_id"));
//                    System.out.println("trade_state:" + queryRes.getParameter("trade_state")+
//                            " total_fee:" + queryRes.getParameter("total_fee"));
//                    //如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
//                    System.out.println("discount:" + queryRes.getParameter("discount")+
//                            " time_end:" + queryRes.getParameter("time_end"));
//                    //------------------------------
//                    //处理业务开始
//                    //------------------------------
//                    PaymentWechat paymentWechat=new PaymentWechat();
//                    paymentWechat.setOut_trade_no(queryRes.getParameter("out_trade_no"));
//                    paymentWechat.setOut_trade_no(queryRes.getParameter("transaction_id"));
//                    paymentWechat.setTrade_state(queryRes.getParameter("trade_state"));
//                    if(queryRes.getParameter("total_fee")!=null) {
//                        paymentWechat.setTotal_fee(Integer.parseInt(queryRes.getParameter("total_fee").trim()));
//                    }
//                    paymentWechat.setTime_end(queryRes.getParameter("time_end"));
//                    paymentWechat.setTrade_state(queryRes.getParameter("trade_state"));
//                    paymentWechat.setReturn_code(queryRes.getParameter("return_code"));
//
//                    //处理数据库逻辑
//                    treatmentService.savePaymentInfo(paymentWechat);
//                    //注意交易单不要重复处理
//
//                    //注意判断返回金额
//
//                    //------------------------------
//                    //处理业务完毕
//                    //------------------------------
//                    resHandler.sendToCFT("Success");
//                }
//                else{
//                    //错误时，返回结果未签名，记录retcode、retmsg看失败详情。
//                    System.out.println("查询验证签名失败或业务错误");
//                    System.out.println("retcode:" + queryRes.getParameter("retcode")+
//                            " retmsg:" + queryRes.getParameter("retmsg"));
//                }
//            } else {
//                System.out.println("后台调用通信失败");
//                System.out.println(httpClient.getResponseCode());
//                System.out.println(httpClient.getErrInfo());
//                //有可能因为网络原因，请求已经处理，但未收到应答。
//            }
//        }
//        else{
//            System.out.println("通知签名验证失败");
//        }
//    }
//    /********************************************中孝医生***********************************************************************************/
//    /**
//     * 获取患者页面的医生列表（按接诊量排序），最热门的四位医生
//     * @param
//     * @return
//     */
//    @RequestMapping(value = "queryDoctorsTopFourOrdered")
//    @ResponseBody
//    public Map<String, Object> queryDoctorsTopFourOrdered() throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//       try {
//           // token验证
////           if(officeVO != null && officeVO.getToken() != null && "".equals(officeVO.getToken())) {
////               TokenVo tokenVo = new TokenVo();
////               tokenVo.setToken(officeVO.getToken());
////               UserInfoBO userInfoBO = (UserInfoBO) treatmentService.queryMemberByToken(tokenVo);
////               if(userInfoBO == null) {
////                   map.put("code", "2");
////                   map.put("data", null);
////                   map.put("message", "token已过期");
////
////                   return map;
////               }
////
////           }
//
//           List list= new ArrayList();
//           // 获取所有可是列表
//           List<ZxysOfficeBO> officeList = treatmentService.queryAllOfficeOrderRecptionNum();
//           Map<String, Object> allOfficeMap = new HashMap<String, Object>();
//           // 全科
//           ZxysOfficeVO zxysOfficeVO = new ZxysOfficeVO();
//           zxysOfficeVO.setOfficeName("全科");
//           List<ZxysDoctorBO> allOfficeList = treatmentService.queryDoctorsTopFourOrdered(zxysOfficeVO);
//
//           allOfficeMap.put("officeName", "全科");
//           allOfficeMap.put("doctorList", allOfficeList);
//
//           list.add(allOfficeMap);
//           int index = 6;
//           if(index > officeList.size()) {
//               index = officeList.size();
//           }
//
//           for(int i = 0; i < index;i++) {
//               Map<String, Object> eveyOfficeMap = new HashMap<String, Object>();
//               zxysOfficeVO.setOfficeName(officeList.get(i).getOffice_name());
//               List<ZxysDoctorBO> doctorList = treatmentService.queryDoctorsTopFourOrdered(zxysOfficeVO);
//               if(doctorList != null && doctorList.size() > 0) {
//                   eveyOfficeMap.put("officeName", officeList.get(i).getOffice_name());
//                   eveyOfficeMap.put("doctorList", doctorList);
//                   list.add(eveyOfficeMap);
//               }else {
//
//                   if(index < officeList.size()) {
//                       index++;
//                   }
//               }
//           }
//
//           map.put("code","0");
//           map.put("data", list);
//           map.put("message", "获取医生列表成功");
//       }catch (Exception e){
//           e.printStackTrace();
//           map.put("code","1");
//           map.put("data",null);
//           map.put("message","系统内部错误");
//           return  map;
//       }
//
//        return map;
//
//    }
//
//    /**
//     * 获取患者页面的医生列表(未分页)（按接诊量排序）
//     * @param officeVO
//     * @return
//     */
//    @RequestMapping(value = "queryDoctorsOrdered")
//    @ResponseBody
//    public Map<String, Object> queryDoctorsOrdered(@RequestBody ZxysOfficeVO officeVO ) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            Map<String, Object> containMap = new HashMap<String, Object>();
//
//            // token验证
////            if(officeVO != null && officeVO.getToken() != null && "".equals(officeVO.getToken())) {
////                TokenVo tokenVo = new TokenVo();
////                tokenVo.setToken(officeVO.getToken());
////                UserInfoBO userInfoBO = (UserInfoBO) treatmentService.queryMemberByToken(tokenVo);
////                if(userInfoBO == null) {
////                    map.put("code", "2");
////                    map.put("data", null);
////                    map.put("message", "token已过期");
////
////                    return map;
////                }
////
////            }
//
//           if(officeVO != null && !"全科".equals(officeVO.getOfficeName()) ) {
//               // 通过offceid查询科室名称
//               String officeName = (String) treatmentService.queryOfficeByName(officeVO);
//
//               if(!"".equals(officeName) && officeName != null) {
//                   containMap.put("officeName", officeName);
//               }else {
//                   containMap.put("officeName", "暂未开通此科室");
//               }
//
//               // 默认图文咨询
//               if(officeVO.getConsult() == null || "".equals(officeVO.getConsult())) {
//                   officeVO.setConsult("图文咨询");
//               }
//
//               // 查询当前科室热度最高医生列表
//               List<ZxysDoctorBO> doctorList = treatmentService.queryDoctorsOrdered(officeVO);
//               if(doctorList != null && doctorList.size() > 0) {
//
//                   for (ZxysDoctorBO doctorBO : doctorList){
//                       BigDecimal[] array = {doctorBO.getPicture_text_consult(),
//                               doctorBO.getMobile_consult(),
//                               doctorBO.getPrivate_doctor_consult(),
//                               doctorBO.getHospital_post_consult(),
//                               doctorBO.getVideo_consult()};
//
//                       BigDecimal minPrice = Tools.getMinBigDecimalButZero(array);
//                       doctorBO.setMin_price(minPrice);
//                   }
//
//                   containMap.put("doctorList", doctorList);
//
//                   map.put("code", "0");
//                   map.put("data",containMap);
//                   map.put("message","查询医生列表成功");
//
//
//               }else {
//
//                   containMap.put("doctorList", null);
//
//                   map.put("code", "0");
//                   map.put("data",containMap);
//                   map.put("message","暂无该科室医生坐诊");
//               }
//           }else {
//               // 查询当前科室热度最高医生列表
//               List<ZxysDoctorBO> doctorList = treatmentService.queryAllDoctorsOrdered();
//               containMap.put("officeName", "全科");
//               if(doctorList != null && doctorList.size() > 0) {
//
//                   // 设置最低价格
//                   for (ZxysDoctorBO doctorBO : doctorList){
//                       BigDecimal[] array = {doctorBO.getPicture_text_consult(),
//                               doctorBO.getMobile_consult(),
//                               doctorBO.getPrivate_doctor_consult(),
//                               doctorBO.getHospital_post_consult(),
//                               doctorBO.getVideo_consult()};
//
//                       BigDecimal minPrice = Tools.getMinBigDecimalButZero(array);
//                       doctorBO.setMin_price(minPrice);
//                   }
//
//                   containMap.put("doctorList", doctorList);
//
//                   map.put("code", "0");
//                   map.put("data",containMap);
//                   map.put("message","查询医生列表成功");
//
//
//               }else {
//
//                   containMap.put("doctorList", null);
//
//                   map.put("code", "0");
//                   map.put("data",containMap);
//                   map.put("message","暂无医生");
//               }
//           }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 获取患者页面的医生列表(分页)（按浏览量排序）
//     * @param pageVO
//     * @return
//     */
//    @RequestMapping(value = "queryDoctorsOrderedByPage")
//    @ResponseBody
//    public Map<String, Object> queryDoctorsOrderedByPage(@RequestBody ZxysDoctorPageVO pageVO ) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//
//            // token验证
////            if(pageVO != null && pageVO.getToken() != null && "".equals(pageVO.getToken())) {
////                TokenVo tokenVo = new TokenVo();
////                tokenVo.setToken(pageVO.getToken());
////                UserInfoBO userInfoBO = (UserInfoBO) treatmentService.queryMemberByToken(tokenVo);
////                if(userInfoBO == null) {
////                    map.put("code", "2");
////                    map.put("data", null);
////                    map.put("message", "token已过期");
////
////                    return map;
////                }
////
////            }
//
//            int pageSize = pageVO.getPageSize();
//            if(pageSize == 0) {
//                pageSize = 5;
//                pageVO.setPageSize(pageSize);
//            }
//
//            // 数据总条数
//
//            int totalCount = treatmentService.queryDoctorsCount(pageVO);
//            pageVO.setTotalCount(totalCount);
//
//            // 当前页码
//            int index = pageVO.getPageIndex();
//            if(index == 0 ) {
//                index = 1;
//                pageVO.setPageIndex(index);
//            }
//            // 计算开始显示的条数
//            int startIndex = (index - 1) * pageSize;
//
//            // 组装数据
//            pageVO.setStartIndex(startIndex);
//            // 分页开始
//            List<ZxysDoctorBO> doctorList = treatmentService.queryDoctorsOrderedByPage(pageVO);
//            if(doctorList != null && doctorList.size() > 0 && doctorList.size() > pageSize) {
//                // 设置最低价格
//                for (ZxysDoctorBO doctorBO : doctorList){
//                    BigDecimal[] array = {doctorBO.getPicture_text_consult(),
//                            doctorBO.getMobile_consult(),
//                            doctorBO.getPrivate_doctor_consult(),
//                            doctorBO.getHospital_post_consult(),
//                            doctorBO.getVideo_consult()};
//
//                    BigDecimal minPrice = Tools.getMinBigDecimalButZero(array);
//                    doctorBO.setMin_price(minPrice);
//                }
//
//                map.put("code","0");
//                map.put("data", doctorList);
//                map.put("message", "获取医生分页列表成功");
//            }else {
//                // 设置最低价格
//                for (ZxysDoctorBO doctorBO : doctorList){
//                    BigDecimal[] array = {doctorBO.getPicture_text_consult(),
//                            doctorBO.getMobile_consult(),
//                            doctorBO.getPrivate_doctor_consult(),
//                            doctorBO.getHospital_post_consult(),
//                            doctorBO.getVideo_consult()};
//
//                    BigDecimal minPrice = Tools.getMinBigDecimalButZero(array);
//                    doctorBO.setMin_price(minPrice);
//                }
//
//                map.put("code","0");
//                map.put("data", doctorList);
//                map.put("message", "已经是最后一页了");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 获取科室列表
//     * @return
//     */
//    @RequestMapping("queryAllOffice")
//    @ResponseBody
//    public Map<String, Object> queryAllOffice() {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//
//            List<ZxysOfficeBO> officeList = treatmentService.queryAllOffice();
//
//            if(officeList != null && officeList.size() > 0) {
//                map.put("code","0");
//                map.put("data",officeList);
//                map.put("message","获取科室信息成功");
//            }else {
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","暂无科室信息");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return  map;
//    }
//
//
//    /**
//     * @methodName 获取评论列表
//     * @Author 刘洋
//     * @param
//     * @return
//     * @throws
//     * @Date: 2016-12-02
//     * @Time: 9:41
//     */
//    @RequestMapping("queryAllCommentContent")
//    @ResponseBody
//    public Map<String, Object> queryAllCommentContent(@RequestBody ZxysCommentContentVO zxysCommentContentVO)throws Exception{
//        Map<String,Object> map = new HashMap<String, Object>();
//        try {
//            // 注释的是token验证
////            UserInfoBO userInfoBO = new UserInfoBO();
////            String token = zxysCommentContentVO.getToken();
////            // 验证token
////            userInfoBO = treatmentService.checkToken(token);
////            if(userInfoBO!=null){
//                // 获取所有评论
//                List<ZxysCommentContentBO> commentContentBOList = treatmentService.queryAllComment(zxysCommentContentVO);
//                map.put("code","0");
//                map.put("data",commentContentBOList);
//                map.put("message","获取医生评价成功");
//                return  map;
////            }else {
////                map = Tools.errMessage();
////                return  map;
////            }
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//    }
//
//    /**
//     * 搜索医生
//     * @return
//     */
//    @RequestMapping("searchDoctors")
//    @ResponseBody
//    public Map<String, Object> searchDoctors(@RequestBody ZxysSearchVO searchVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            if(searchVO.getCondition() == null || "(null)".equals(searchVO.getCondition())) {
//                searchVO.setCondition("");
//            }
//
//            // 分页数据处理
//            if(searchVO.getPageIndex() == 0) {
//                searchVO.setPageIndex(1);
//            }
////            if(searchVO.getPageSize() == 0) {
////                searchVO.setPageSize(5);
////            }
//
//            List<ZxysDoctorBO> list = treatmentService.searchDoctors(searchVO);
//
//            // 对医生的科室信息做处理
//            if(list != null && list.size() > 0) {
//                for (ZxysDoctorBO zxysDoctorBO : list){
//                    if(zxysDoctorBO.getOffice().contains("-")) {
//                        String[] officeArr = zxysDoctorBO.getOffice().split("-");
//                        zxysDoctorBO.setOffice(officeArr[0]);
//                    }
//                }
//            }
//
//            if(list == null || list.size() == 0) {
//                map.put("code", "0");
//                map.put("data", null);
//                map.put("message", "暂无医生信息");
//                return  map;
//            }
//
//            // 设置最低价格
//            for (ZxysDoctorBO doctorBO : list){
//                BigDecimal[] array = {doctorBO.getPicture_text_consult(),
//                        doctorBO.getMobile_consult(),
//                        doctorBO.getPrivate_doctor_consult(),
//                        doctorBO.getHospital_post_consult(),
//                        doctorBO.getVideo_consult()};
//
//                BigDecimal minPrice = Tools.getMinBigDecimalButZero(array);
//                doctorBO.setMin_price(minPrice);
//            }
//
//            map.put("code", "0");
//            map.put("data", list);
//            map.put("message", "搜索医生信息成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//        return map;
//    }
//
//    /**
//     * 获取医生和对应的评论列表
//     * @param doctorVO
//     * @return
//     */
//    @RequestMapping("queryDoctorAndComments")
//    @ResponseBody
//    public Map<String, Object> queryDoctorAndComments(@RequestBody ZxysDoctorVO doctorVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//
//        try {
//
//            // 验证token
////            UserInfoBO userInfoBO = treatmentService.checkToken(doctorVO.getToken());
////            if(userInfoBO == null) {
////                map.put("code", "2");
////                map.put("data", null);
////                map.put("message", "token已过期");
////
////                return map;
////            }
//
//            // 根据doctor_id查询医生
//            ZxysDoctorBO doctorBO = treatmentService.queryDoctorById(doctorVO);
//
//            // 对医生的科室信息做处理
//            if(doctorBO != null) {
//                if(doctorBO.getOffice().contains("-")) {
//                    String[] officeArr = doctorBO.getOffice().split("-");
//                    doctorBO.setOffice(officeArr[0]);
//                }
//            }
//
//            // 获取该医生的评论条数
//            int commentSum = treatmentService.queryCommentSum(doctorVO.getDoctorId());
//
//            // 获取医生评论的前五条
//            List<ZxysCommentContentBO> commentList = treatmentService.queryCommentContent(doctorVO.getDoctorId());
//
//            Map<String, Object> resultMap = new HashMap<String, Object>();
//            resultMap.put("doctor", doctorBO);
//            resultMap.put("commentSum", commentSum);
//            resultMap.put("commentList", commentList);
//
//            // 更新认证表中的浏览次数
//            treatmentService.updateBrowsingNum(doctorVO);
//
//            map.put("code", "0");
//            map.put("data", resultMap);
//            map.put("message", "获取医生和评论列表信息成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//        return map;
//    }
//
//    /**
//     * 保存用户评价
//     * @param commentContentVO
//     * @return
//     */
//    @RequestMapping("saveCommentRecord")
//    @ResponseBody
//    public Map<String, Object> saveCommentRecord(@RequestBody ZxysCommentContentVO commentContentVO){
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            treatmentService.saveCommentRecord(commentContentVO);
//
//            map.put("code","0");
//            map.put("data",null);
//            map.put("message","评价成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//        return map;
//    }
//
//    /**
//     * 获取高发疾病列表
//     * @return
//     */
//    @RequestMapping("queryHotDisease")
//    @ResponseBody
//    public Map<String, Object> queryHotDisease() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            int type = 3;
//            List<ZxysVariableBO> list = treatmentService.queryVariable(type);
//
//            map.put("code","0");
//            map.put("data",list);
//            map.put("message","获取高发疾病信息成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return  map;
//    }
//
//
//    /**
//     * 获取所有评论标签
//     * @return
//     */
//    @RequestMapping("queryAllCommentLabel")
//    @ResponseBody
//    public Map<String, Object> queryAllCommentLabel() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            List<ZxysVariableBO> list = treatmentService.queryVariable(1);
//
//            map.put("code", "0");
//            map.put("data", list);
//            map.put("message", "获取评价表情列表成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//        return map;
//    }
//
//
//    /**
//     * 创建免费问诊记录(未完成：不知道咨询的医生是谁)
//     * @param recordVO
//     * @return
//     */
//    @RequestMapping("createFreeConsultRecord")
//    @ResponseBody
//    public Map<String, Object> createFreeConsultRecord(@RequestBody ZxysRecordVO recordVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            if(recordVO == null) {
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","请输入完整的咨询信息");
//                return map;
//            }
//
//            // 生成订单号
//            String trdeNo = System.currentTimeMillis()/1000 + RandomCode.getChar();
//            recordVO.setTradeNo(trdeNo);
//            // 根据科室查找医生 列表
//            ZxysOfficeVO officeVO = new ZxysOfficeVO();
//            officeVO.setOfficeName(recordVO.getOffice());
//            List<ZxysDoctorBO> doctorList = treatmentService.queryDoctorsByOffice(officeVO);
//
//
//
//            treatmentService.saveFreeConsultRecord(recordVO);
//
//            map.put("code","0");
//            map.put("data",null);
//            map.put("message","免费问诊记录创建成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 添加新的就诊人
//     * @return
//     */
//    @RequestMapping("addNewFriend")
//    @ResponseBody
//    public Map<String, Object> addNewFriend(@RequestBody ZxysUserVO userVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(userVO.getToken());
//            UserInfoBO userInfoBO = treatmentService.findMemberByToken(tokenVo);
//
//            if(userInfoBO == null) {
//                map.put("code", "2");
//                    map.put("data", null);
//                    map.put("message", "token已过期");
//
//                    return map;
//            }
//            userVO.setUserId(userInfoBO.getMember_id());
//
//            // 用户添加的就诊人信息校验
//            if (userVO.getTrueName() == null || "".equals(userVO.getTrueName())){
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","就诊人姓名不能为空");
//                return  map;
//            }
//            if (userVO.getAge() == 0){
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","就诊人年龄不能为空");
//                return  map;
//            }
//
//            // 组装参数
//            if(userVO != null){
//                if("男".equals(userVO.getSex())) {
//                    userVO.setSex("0");
//                }else if("女".equals(userVO.getSex())) {
//                    userVO.setSex("1");
//                }else {
//                    userVO.setSex("2");   // 保密
//                }
//            }
//            userVO.setType(1);
//            userVO.setFriend_type(1);
//
//            treatmentService.addNewFriend(userVO);
//
//            map.put("code","0");
//            map.put("data",null);
//            map.put("message","新增就诊人完成");
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 获取当前用户的就诊人列表（包含自己）
//     * @param userVO
//     * @return
//     */
//    @RequestMapping("queryFriendListByUserId")
//    @ResponseBody
//    public Map<String, Object> queryFriendListByUserId(@RequestBody ZxysUserVO userVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(userVO.getToken());
//            UserInfoBO userInfoBO = treatmentService.findMemberByToken(tokenVo);
//
//            if(userInfoBO == null) {
//                map.put("code", "2");
//                map.put("data", null);
//                map.put("message", "token已过期");
//
//                return map;
//            }
//            userVO.setUserId(userInfoBO.getMember_id());
//
//            List<ZxysUserBO> friendlist = treatmentService.queryFriendListByUserId(userVO);
//
//            // 将当前用户加入到列表中
//            ZxysUserBO userBO = treatmentService.queryMemberById(userVO.getUserId());
//
//            Map<String, Object> resltMap = new HashMap<String, Object>();
//            resltMap.put("currentUser", userBO);
//            resltMap.put("friendList", friendlist);
//
//            map.put("code","0");
//            map.put("data",resltMap);
//            map.put("message","获取当前用户好友列表成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//        return map;
//    }
//
//    /**
//     * 通过就诊人姓名删除就诊人
//     * @param userVO
//     * @return
//     */
//    @RequestMapping("deleteFriendByName")
//    @ResponseBody
//    public Map<String, Object> deleteFriendByName(@RequestBody ZxysUserVO userVO) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(userVO.getToken());
//            UserInfoBO userInfoBO = treatmentService.findMemberByToken(tokenVo);
//
//            if(userInfoBO == null) {
//                map.put("code", "2");
//                map.put("data", null);
//                map.put("message", "token已过期");
//
//                return map;
//            }
//            userVO.setUserId(userInfoBO.getMember_id());
//
//            treatmentService.deleteFriendByName(userVO);
//            map.put("code","0");
//            map.put("data",null);
//            map.put("message","删除好友成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 获取患者的订单列表
//     * @param recordVO
//     * @return
//     */
//    @RequestMapping("queryConsultRecordForPatient")
//    @ResponseBody
//    public Map<String, Object> queryConsultRecordForPatient(@RequestBody ZxysRecordVO recordVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(recordVO.getToken());
//            UserInfoBO userInfoBO = treatmentService.findMemberByToken(tokenVo);
//
//            if(userInfoBO == null) {
//                map.put("code", "2");
//                map.put("data", null);
//                map.put("message", "token已过期");
//
//                return map;
//            }
//            recordVO.setPatientId(userInfoBO.getMember_id());
//
//            // 订单状态处理
//            if("全部".equals(recordVO.getStatus())) {
//                recordVO.setStatus("");
//            }else if ("待付款".equals(recordVO.getStatus())){
//                recordVO.setStatus("2");
//            }else if ("待咨询".equals(recordVO.getStatus())){
//                recordVO.setStatus("3");
//            }else if ("待评价".equals(recordVO.getStatus())){
//                recordVO.setStatus("4");
//            }else if ("已完成".equals(recordVO.getStatus())){
//                recordVO.setStatus("5");
//            }
//
//            // 分页参数处理
//            if(recordVO.getPageIndex() == 0) {
//                recordVO.setPageIndex(1);
//            }
//            if(recordVO.getPageSize() == 0) {
//                recordVO.setPageSize(50);
//            }
//
//            List<ZxysRecordBO> recordList = treatmentService.queryConsultRecordForPatient(recordVO);
//
//            if(recordList != null && recordList.size() > 0) {
//                for (ZxysRecordBO recordBO : recordList){
//
//                    if("1".equals(recordBO.getStatus())) {
//                        recordBO.setStatus("生成订单");
//                    }else if("2".equals(recordBO.getStatus())) {
//                        recordBO.setStatus("待付款");
//                    }else if("3".equals(recordBO.getStatus())) {
//                        recordBO.setStatus("待咨询");
//                    }else if("4".equals(recordBO.getStatus())) {
//                        recordBO.setStatus("待评价");
//                    }else if("5".equals(recordBO.getStatus())) {
//                        recordBO.setStatus("已完成");
//                    }else if("6".equals(recordBO.getStatus())) {
//                        recordBO.setStatus("退款中");
//                    }else if("7".equals(recordBO.getStatus())) {
//                        recordBO.setStatus("已退款");
//                    }else {
//                        recordBO.setStatus("");
//                    }
//                    //0:图文；1：语音；2：视频；3：私人医生；4：院后指导）
//
//                    if("0".equals(recordBO.getConsultation_type())) {
//                        recordBO.setConsultation_type("图文咨询");
//                    }else if("1".equals(recordBO.getConsultation_type())) {
//                        recordBO.setConsultation_type("语音咨询");
//                    }
//                    else if("2".equals(recordBO.getConsultation_type())) {
//                        recordBO.setConsultation_type("视频咨询");
//                    }
//                    else if("3".equals(recordBO.getConsultation_type())) {
//                        recordBO.setConsultation_type("私人医生");
//                    }
//                    else if("4".equals(recordBO.getConsultation_type())) {
//                        recordBO.setConsultation_type("院后指导");
//                    }
//
//                }
//
//            }else {
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","您还没有订单信息");
//                return  map;
//            }
//
//            map.put("code","0");
//            map.put("data",recordList);
//            map.put("message","获取当前用户订单信息成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 添加订单记录
//     * @param recordVO
//     * @return
//     */
//    @RequestMapping("addZxysRecord")
//    @ResponseBody
//    public Map<String, Object> addZxysRecord(@RequestBody ZxysRecordVO recordVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            ZxysRecordBO recordBO = new ZxysRecordBO();
//
//            // 通过token查找当前用户
//            UserInfoBO userInfoBO1 =treatmentService.checkToken(recordVO.getToken());
//            if(userInfoBO1 == null) {
//                return Tools.errMessage();
//            }
//
//            UserInfoBO userInfoBO = treatmentService.findMemberByMemberId(userInfoBO1);
//
//            recordBO.setPatient_id(userInfoBO.getMember_id());
//            recordBO.setPatient_name(userInfoBO.getMember_name());
//            recordBO.setPatient_sex(userInfoBO.getMember_sex());
//            recordBO.setPatient_age(userInfoBO.getMember_age());
//            recordBO.setPatient_area(userInfoBO.getMember_area());
//
////            recordVO.setPatientId(userInfoBO.getMember_id());
////            recordVO.setPatientName(userInfoBO.getMember_truename());
////            recordVO.setPatientSex(userInfoBO.getMember_sex());
////            recordVO.setPatientAge(userInfoBO.getMember_age());
////            recordVO.setPatientArea(userInfoBO.getMember_area());
//
//            // 下单时间
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            recordBO.setOrder_time(sdf.format(new Date()));
//            System.out.println(sdf.format(new Date()));
////            recordVO.setOrderTime(new Date().toString());
//
//            // 收费订单才有的信息
//            if(recordVO.getDoctorId() != null) {
//                // 订单状态
//                recordBO.setStatus("2");
//                ZxysDoctorVO doctorVO = new ZxysDoctorVO();
//
//                doctorVO.setDoctorId(recordVO.getDoctorId());
//                ZxysDoctorBO doctorBO = treatmentService.queryDoctorById(doctorVO);
//
//                recordBO.setDoctor_name(doctorBO.getDoctor_name());
//                recordBO.setOffice(doctorBO.getOffice());
//                recordBO.setDoctor_id(recordVO.getDoctorId());
////                recordVO.setDoctorName(doctorBO.getDoctor_name());
////                recordVO.setOffice(doctorBO.getOffice());
//
//                // 费用  0:图文；1：语音；2：视频；3：私人医生；4：院后指导,5:预约就诊
//                if("图文咨询".equals(recordVO.getConsultationType())) {
//                    recordBO.setFee(doctorBO.getPicture_text_consult());
//                    recordBO.setConsultation_type("0");
//                }else if("电话咨询".equals(recordVO.getConsultationType())) {
//                    recordBO.setFee(doctorBO.getMobile_consult());
//                    recordBO.setConsultation_type("1");
//                }else if("私人医生".equals(recordVO.getConsultationType())) {
//                    recordBO.setFee(doctorBO.getPrivate_doctor_consult());
//                    recordBO.setConsultation_type("2");
//                }else if("预约就诊".equals(recordVO.getConsultationType())) {
//                    recordBO.setFee(doctorBO.getAppointment_consult());
//                    recordBO.setConsultation_type("3");
//                }else if("视频咨询".equals(recordVO.getConsultationType())) {
//                    recordBO.setFee(doctorBO.getVideo_consult());
//                    recordBO.setConsultation_type("4");
//                }else if("院后指导".equals(recordVO.getConsultationType())) {
//                    recordBO.setFee(doctorBO.getHospital_post_consult());
//                    recordBO.setConsultation_type("5");
//                }
//
//                // 医生的职称，医院信息
//                recordBO.setHospital_name(doctorBO.getHospital_name());
//                recordBO.setTitle(doctorBO.getTitle());
////                recordVO.setHospital(doctorBO.getHospital_name());
////                recordVO.setTitle(doctorBO.getTitle());
//
//            }else {// 免费问诊
////                // 随机分配医生（按照科室信息）
////
////                // 上传图片
////                String patientImg = recordVO.getPatientPictures();
////                System.out.println(patientImg+"***************************");
////
////                // 图片保存路径
////                String imgPathDB = "";
////                if(patientImg != null && !"".equals(patientImg)) {
////                    String date = sdf.format(new Date());
////                    // 生成保存图片的文件路径
////                    String path = "d:/patient/order/" + userInfoBO.getMember_id() + "/" + date;
////                    // 文件夹不存在，创建一个
////                    File file = new File(path);
//////                File fileParent = file.getParentFile();
////                    if(!file.exists()){
////                        file.mkdirs();
////                    }
////
////                    // 对数据内容做处理
//////                patientImg = patientImg.substring(23).trim();
//////                patientImg = patientImg.substring(0, patientImg.length()-1);
////                    patientImg = patientImg.replace("\"","");
////
////                    patientImg = patientImg.replaceAll("\\\\", "");
////
////                    // 图片数组
////                    String[] imgArr = patientImg.split(",");
////
////                    for (String img: imgArr){
////                        String picName = Tools.getToken() + ".jpg";
////                        String imgPathDisk = path + "/"+ picName;
////
////                        System.out.println(img + "--------------------------------------");
////
////                        ImageAnd64Binary.generateImage(img, imgPathDisk);
////
////                        imgPathDB = imgPathDB + imgPathDisk + ",";
////                    }
////                    imgPathDB = imgPathDB.substring(0, imgPathDB.length()-1);
////                }
////
//////                recordVO.setPatientPictures(imgPathDB);
////                recordBO.setPatient_pictures(imgPathDB);
//            }
//
//            // 订单号
//            String OutTradeNo = System.currentTimeMillis()/1000 + RandomCode.getChar();
//
//            System.out.print(OutTradeNo);
////            recordVO.setOutTradeNo(OutTradeNo);
//            recordBO.setOut_trade_no(OutTradeNo);
//
////            treatmentService.saveZxysRecord(recordVO);
//            treatmentService.saveZxysRecordNew(recordBO);
//
//            map.put("code", "0");
//            map.put("data", recordBO);
//            map.put("message", MessageTemplate.treatment_CreateOrderSuccess);
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
//    @RequestMapping("addZxysRecordFree")
//    @ResponseBody
//    public Map<String, Object> addZxysRecordFree(@RequestBody ZxysRecordVO recordVO) throws Exception {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//       try {
//           // 通过token查找当前用户
//           UserInfoBO userInfoBO1 =treatmentService.checkToken(recordVO.getToken());
//
//           UserInfoBO userInfoBO = treatmentService.findMemberByMemberId(userInfoBO1);
//           recordVO.setPatientId(userInfoBO.getMember_id());
//           recordVO.setPatientName(userInfoBO.getMember_truename());
//           recordVO.setPatientSex(userInfoBO.getMember_sex());
//           recordVO.setPatientAge(userInfoBO.getMember_age());
//           recordVO.setPatientArea(userInfoBO.getMember_area());
//
//           // 下单时间
//           recordVO.setOrderTime(new Date().toString());
//
//           // 订单状态
//           recordVO.setStatus("2");
//       }catch (Exception e){
//           e.printStackTrace();
//           return Tools.errMessageSystem();
//       }
//
//        return map;
//    }
//
//
//    /**
//     * 返回支付码(新的中孝医生)
//     */
//    @RequestMapping(value = "getAlipayCode")
//    @ResponseBody
//    public Map<String, Object> getAlipayCode(@RequestBody ZxysRecordVO recordVO) throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        UserInfoBO userInfoBO = treatmentService.checkToken(recordVO.getToken());
//        if(userInfoBO == null) {
//            return Tools.errMessage();
//        }
//
//        // 查询订单记录
//        ZxysRecordBO recordBO = treatmentService.queryRecordByOutTradeNo(recordVO);
//        if(recordBO == null) {
//            map.put("data", null);
//            map.put("message", "数据传输错误");
//            map.put("code", "0");
//
//            return map;
//        }
//
//        CommunicateRecordVo communicateRecordVo = new CommunicateRecordVo();
//        communicateRecordVo.setMoney(recordBO.getFee().toString());
//        communicateRecordVo.setCommunicate_id(recordBO.getOut_trade_no());
//
//        System.out.println("------------------------------------" +recordVO.getOutTradeNo() +"===========================================================");
//        System.out.println("========================================" +recordBO.getOut_trade_no() +"===========================================================");
//
//        communicateRecordVo.setCreate_time(recordBO.getOrder_time().substring(0,19));
//        System.out.println(recordBO.getOrder_time().substring(0,19) + "====================================================");
//
////        System.out.println(orderInfo.equals(new String (orderInfo.getBytes("utf-8"), "utf-8")));
//
//        String orderInfo = new String(pay.alipay2(communicateRecordVo).getBytes("gb2312"), "utf-8");
////        orderInfo = URLEncoder.encode(orderInfo,"utf-8");
//
//        map.put("data", orderInfo);
//        map.put("message", "获取支付码成功");
//        map.put("code", "0");
//        return map;
//    }
//
//    /**
//     * 支付宝调用的异步验签回调(新的中孝医生)
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("aliPayPostCallBack")
//    @ResponseBody
//    public String aliPayPostCallBack(HttpServletRequest request ,HttpServletResponse response) throws Exception {
//        //获取支付宝POST过来反馈信息
//
//        Map<String,String> params = new HashMap<String,String>();
//        Map requestParams = request.getParameterMap();
//
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//
//                if ("subject".equals(name)){
//                    //
//                    valueStr=new String(request.getParameter("subject").getBytes(),"UTF-8");
//                    System.out.println(Tools.getEncoding(valueStr));
//                    System.out.println("subject:"+valueStr);
//                    continue;
//                }
//                if ("body".equals(name)){
//                    valueStr=new String(request.getParameter("body").getBytes(),"UTF-8");
//                    System.out.println(Tools.getEncoding(valueStr));
//                    System.out.println("body:"+valueStr);
//                    continue;
//                }
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//            valueStr = new String(valueStr.getBytes(), "utf-8");
//            params.put(name, valueStr);
//        }
//        Collection<String> keyset= params.keySet();
//        List list=new ArrayList<String>(keyset);
//        Collections.sort(list);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("key键---值: "+list.get(i)+","+params.get(list.get(i)));
//        }
//        String sign=new String(request.getParameter("sign").getBytes(),"UTF-8");
//        System.out.println("sign:"+sign);
////        params= AlipayCore.paraFilter(params);
//        Tools.writeObject(params);
//        //获取支付宝的通知返回参数
//        //通知时间
//        String notify_time=new String (request.getParameter("notify_time").getBytes("gb2312"),"UTF-8");
//        //通知类型
//        String notify_type = new String(request.getParameter("notify_type").getBytes("gb2312"),"UTF-8");
//        //通知校验ID
//        String notify_id = new String(request.getParameter("notify_id").getBytes("gb2312"),"UTF-8");
//        //支付宝分配给开发者的应用Id
//        String app_id = new String(request.getParameter("app_id").getBytes("gb2312"),"UTF-8");
//        //编码格式
//        String charset=new String(request.getParameter("charset").getBytes("gb2312"),"UTF-8");
//        //接口版本
//        String version=new String(request.getParameter("version").getBytes("gb2312"),"UTF-8");
//        //支付宝交易号
//        String trade_no = new String(request.getParameter("trade_no").getBytes("gb2312"),"UTF-8");
//        //商户订单号
//        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("gb2312"),"UTF-8");
//        //商户业务号
//        String out_biz_no = null;
////                new String(request.getParameter("out_biz_no").getBytes("gb2312"),"UTF-8");
//        //买家支付宝用户号
//        String buyer_id = new String(request.getParameter("buyer_id").getBytes("gb2312"),"UTF-8");
//        //买家支付宝账号
//        String buyer_logon_id = new String(request.getParameter("buyer_logon_id").getBytes("gb2312"),"UTF-8");
//        // 卖家支付宝用户号
//        String seller_id = new String(request.getParameter("seller_id").getBytes("gb2312"),"UTF-8");
//        //卖家支付宝账号
//        String seller_email = new String(request.getParameter("seller_email").getBytes("gb2312"),"UTF-8");
//        //交易状态
//        String trade_status = new String(request.getParameter("trade_status").getBytes("gb2312"),"UTF-8");
//        //订单金额
//        String total_amount = new String(request.getParameter("total_amount").getBytes("gb2312"),"UTF-8");
//        //实收金额
//        String receipt_amount = new String(request.getParameter("receipt_amount").getBytes("gb2312"),"UTF-8");
//        //开票金额
//        String invoice_amount = new String(request.getParameter("invoice_amount").getBytes("gb2312"),"UTF-8");
//        //付款金额
//        String buyer_pay_amount = new String(request.getParameter("buyer_pay_amount").getBytes("gb2312"),"UTF-8");
//        //集分宝金额
//        String point_amount = new String(request.getParameter("point_amount").getBytes("gb2312"),"UTF-8");
//        //总退款金额
//        String refund_fee = null;
//        //订单标题
//        String subject = new String(request.getParameter("subject").getBytes("gb2312"),"UTF-8");
//        //商品描述
//        String body = new String(request.getParameter("body").getBytes("gb2312"),"UTF-8");
//        //交易创建时间
//        String gmt_create = new String(request.getParameter("gmt_create").getBytes("gb2312"),"UTF-8");
//        //交易付款时间
//        String gmt_payment = new String(request.getParameter("gmt_payment").getBytes("gb2312"),"UTF-8");
//        //交易退款时间
//        String gmt_refund = request.getParameter("gmt_refund")==null?null:new String(request.getParameter("gmt_refund").getBytes("gb2312"),"UTF-8");
//        //交易结束时间
//        String gmt_close = request.getParameter("gmt_close")==null?null:new String(request.getParameter("gmt_close").getBytes("gb2312"),"UTF-8");
//        //支付金额信息
//        String fund_bill_list = request.getParameter("fund_bill_list")==null?null:new String(request.getParameter("fund_bill_list").getBytes("gb2312"),"UTF-8");
//        //回传参数
//        String passback_params = request.getParameter("passback_params")==null?null:new String(request.getParameter("passback_params").getBytes("gb2312"),"UTF-8");
//        //优惠券信息
//        String voucher_detail_list = request.getParameter("voucher_detail_list")==null?null:new String(request.getParameter("voucher_detail_list").getBytes("gb2312"),"UTF-8");
//        AlipayTransactionVo alipayTransactionVo=new AlipayTransactionVo(notify_time,notify_type,notify_id,app_id,trade_no,out_trade_no,out_biz_no,buyer_id,buyer_logon_id,seller_id,seller_email,trade_status,total_amount,receipt_amount,invoice_amount,buyer_pay_amount,point_amount,refund_fee,subject,body,gmt_create,gmt_payment,gmt_refund,gmt_close,fund_bill_list);
////        AlipayTransactionVo alipayTransactionVo=new AlipayTransactionVo(notify_time,notify_type,notify_id,app_id,trade_no,out_trade_no,out_biz_no,buyer_id,buyer_logon_id,seller_id,seller_email,trade_status,total_amount,receipt_amount,invoice_amount,buyer_pay_amount,point_amount,refund_fee,subject,body,gmt_create,gmt_payment,gmt_refund,gmt_close,fund_bill_list
////        )
////        String notify_time, String notify_type, String notify_id, String app_id, String trade_no, String out_trade_no, String out_biz_no, String buyer_id, String buyer_logon_id, String seller_id, String seller_email, String trade_status, String total_amount, String receipt_amount, String invoice_amount, String buyer_pay_amount, String point_amount, String refund_fee, String subject, String body, String gmt_create, String gmt_payment, String gmt_refund, String gmt_close, String fund_bill_list
////        System.out.println("回调参数列表:"+"out_trade_no:"+out_trade_no+
////                "remarks"+remarks+"trade_no"+trade_no+"money"+money+"trade_status"+trade_status);
////        System.out.println("这个是>?:"+params);
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表
////        AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "utf-8")
////        System.out.println(AlipayNotify.verify(params));
//        if(AlipayNotify.getSignVeryfy(params,sign)){//验证成功
//            //////////////////////////////////////////////////////////////////////////////////////////
//            //记录用户的消费记录入库
//            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
//
////            if(trade_status.equals("TRADE_FINISHED")){
////
////                System.out.println("post:TRADE_FINISHED");
////                //注意：
////                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知乙
////            } else if (trade_status.equals("TRADE_SUCCESS")){
////
////                System.out.println("post:TRADE_SUCCESS");
////
////            }
//
//            // 根据订单号查找订单信息
//            ZxysRecordVO recordVO = new ZxysRecordVO();
//            recordVO.setOutTradeNo(alipayTransactionVo.getOut_trade_no());
//            ZxysRecordBO zxysRecordBO = treatmentService.queryRecordByOutTradeNo(recordVO);
//            if (zxysRecordBO == null) {
//                return "failure";
//            } else {
//                if ("pay.APPID".equals(alipayTransactionVo.getApp_id())) {
//                    return "failure";
//                }
//                if ("alipayResultVo.getTotal_amount()".equals(zxysRecordBO.getFee())) {
//                    return "failure";
//                } else {
//                    // 更新交易流水号和订单支付状态
//                    zxysRecordBO.setTrade_no(alipayTransactionVo.getTrade_no());
//                    zxysRecordBO.setPayway("1");
//                    treatmentService.updateTradeNo(zxysRecordBO);
////                            System.out.println("222222222222222222222222222222222222222");
//                    treatmentService.saveAlipayTransaction(alipayTransactionVo);
//                    return "success";
//                }
//            }
//            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//
//            //out.println("fail");
//
//        }else{
//            System.out.println("3333333333333333333333333333333");
//            return "failure";
//        }
//    }
//
//
//    /**
//     * 校验支付是否成功(新的中孝医生使用)（同步校验）
//     */
//    @RequestMapping(value = "checkAlipayResult")
//    @ResponseBody
//    public Map<String, Object> checkAlipayResult(@RequestBody AlipayResultVo alipayResultVo ) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
////            AlipayTransactionVo alipayTransactionVo=new AlipayTransactionVo();
////            alipayTransactionVo.setApp_id(alipayResultVo.getApp_id());
//
////            Map<String, String> param = OrderInfoUtil2_0.getMapInfo(alipayResultVo);
////            boolean signVerified = AlipaySignature.rsaCheckV2(param,pay.ALIPAY_PUBLIC_KEY,"utf-8");
////            if(signVerified) {
////            System.out.println("666666666666666666666666666666");
//            QueryDoctorVo vo = new QueryDoctorVo();
//            vo.setToken(alipayResultVo.getToken());
//            UserInfoBO userInfoBO = treatmentService.checkTokenIsGone(vo);
//
//            if (userInfoBO == null) {
//                return map;
//            }
//
//            ZxysRecordVO recordVO = new ZxysRecordVO();
//            recordVO.setOutTradeNo(alipayResultVo.getOut_trade_no());
//            ZxysRecordBO recordBO = treatmentService.queryRecordByOutTradeNo(recordVO);
//
//            if (recordBO == null) {
//                map.put("data", null);
//                map.put("message", "订单号有误");
//                map.put("code", 1);
//                return map;
//            } else {
//                if ("pay.APPID".equals(alipayResultVo.getApp_id())) {
//                    map.put("data", null);
//                    map.put("message", "密匙有误");
//                    map.put("code", 1);
//                    return map;
//                }
//                if ("alipayResultVo.getTotal_amount()".equals(recordBO.getFee().toString())) {
//                    map.put("data", null);
//                    map.put("message", "订单金额有误");
//                    map.put("code", 1);
//                    return map;
//                } else {
//                    recordBO.setTrade_no(alipayResultVo.getTrade_no());
//                    treatmentService.updateTradeNo(recordBO);
//                    treatmentService.saveAlipayResult(alipayResultVo);
//                    map.put("data", null);
//                    map.put("message", "支付成功");
//                    map.put("code", 0);
//                }
//            }
////            }else{
////                map.put("data", null);
////                map.put("message", "验签失败");
////                map.put("code", 1);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            map=Tools.errMessageSystem();
//            return map;
//        }
//        return map;
//    }
//
//    /**
//     * 通过订单号返回支付是否成功(新的中孝医生使用)(异步校验)
//     */
//    @RequestMapping(value = "checkZxysRecordByOutTradeNo")
//    @ResponseBody
//    public Map<String, Object> checkZxysRecordByOutTradeNo(@RequestBody AlipayResultVo alipayResultVo ) throws Exception {
//        Map<String, Object> map=Tools.errMessage();
////        alipayResultVo.getToken();
////        QueryDoctorVo vo=new QueryDoctorVo();
////        vo.setToken(alipayResultVo.getToken());
//        boolean isPay=false;
//        boolean flag=true;
//
//        try {
//            ZxysRecordVO recordVO = new ZxysRecordVO();
//            recordVO.setOutTradeNo(alipayResultVo.getOut_trade_no());
//            isPay = treatmentService.checkZxysRecordByOutTradeNo(recordVO);
//        } catch (Exception e) {
//            map=Tools.errMessageSystem();
//            e.printStackTrace();
//            return map;
//        }
//
//        if (isPay){
//            map.put("data", null);
//            map.put("message", "支付成功");
//            map.put("code", 0);
//        }else{
//            map.put("data", null);
//            map.put("message", "支付失败");
//            map.put("code", 1);
//        }
//        return map;
//    }
//
//    /**
//     * 发送用户消息
//     * @param messageVo（from_id,to_id,conent,type）
//     * @return
//     */
//    @RequestMapping("sendUserMessage")
//    @ResponseBody
//    public Map<String, Object> sendUserMessage(@RequestBody ZxysMessageVo messageVo) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            if(messageVo == null) {
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","消息不能为空");
//                return map;
//            }
//            // 环信发送
//
//            //本地保存
//            messageVo.setSend_time(new Date());
//            // 默认消息种类是文本类型是文本（type=0）
//
//            boolean result = treatmentService.saveMessage(messageVo);
//            if(result) {
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","消息保存成功");
//            }else {
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","消息保存失败");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 获取对话列表消息
//     * @return
//     */
//    @RequestMapping("queryZxysMessage")
//    @ResponseBody
//    public Map<String, Object> queryZxysMessage(@RequestBody ZxysMessageVo zxysMessageVo) throws Exception {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        try {
//            List<ZxysMessageVo> list = (List<ZxysMessageVo>) treatmentService.queryZxysMessage(zxysMessageVo);
//            if (list != null && list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    int type = list.get(i).getType();
//                    if (type == 2 || type == 3) {
//                        list.get(i).setContent("图片因为隐私问题不能显示");
//                    }
//                }
//                modelMap.put("code", "0");
//                modelMap.put("date", list);
//                modelMap.put("message", "获取对话列表消息成功");
//            } else {
//                modelMap.put("code", "0");
//                modelMap.put("date", null);
//                modelMap.put("message", "暂无对话列表消息");
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
//
////    /**
////     * 更新对话咨询状态（0未结束，1结束）
////     * @return
////     */
////    @RequestMapping("updateIsEnd")
////    @ResponseBody
////    public Map<String, Object> updateIsEnd(@RequestBody ZxysMessageVo zxysMessageVo) throws Exception {
////        Map<String, Object> modelMap = new HashMap<String, Object>();
////        try {
////            treatmentService.updateIsEnd(zxysMessageVo);
////            modelMap.put("code", "0");
////            modelMap.put("date", null);
////            modelMap.put("message", "对话咨询状态已更新");
////        } catch (Exception e) {
////            e.printStackTrace();
////            modelMap.put("message", "系统内部错误");
////            modelMap.put("code", "1");
////            modelMap.put("data", null);
////            return modelMap;
////        }
////        return modelMap;
////    }
//
//    /**
//     * 设置用户和医生的对话是否公开
//     * @param messageVo
//     * @return
//     */
//    @RequestMapping("setIsOpenForMessage")
//    @ResponseBody
//    public Map<String, Object> setIsOpenForMessage(@RequestBody ZxysMessageVo messageVo) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            treatmentService.setIsOpenForMessage(messageVo);
//            map.put("code","0");
//            map.put("data",null);
//            map.put("message","用户消息是否公开设置完成");
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//
//        return map;
//    }
//
//    /**
//     * 会话结束
//     * @param messageVo
//     * @return
//     */
//    @RequestMapping("endSession")
//    @ResponseBody
//    public Map<String, Object> endSession(@RequestBody ZxysMessageVo messageVo) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            // 保存对话
//
//            // 设置回话结束(此处产生session_id)
//            // 查找当前数据库中最大的sessin_id
//            int session_id = treatmentService.queryMaxSessionId();
//            messageVo.setSession_id(session_id+1);
//            treatmentService.setIsCloseForMessage(messageVo);
//
//            // 更新record表中的sesisn_id字段
//            treatmentService.updateSessionIdForRecord(messageVo);
//            // 通过session_Id获取，医生id
//            ZxysRecordBO recordBO = treatmentService.queryZxysRecordBySessionId(messageVo);
//
//            if(recordBO == null) {
//
//                map.put("code","0");
//                map.put("data",null);
//                map.put("message","会话信息不存在");
//                return map;
//            }
//            ZxysDoctorVO doctorVO = new ZxysDoctorVO();
//            doctorVO.setDoctorId(recordBO.getDoctor_id());
//
//            // 更新认证表中的reception_num字段
//            treatmentService.updateReceptionNum(doctorVO);
//
//            // 获取医生的相关信息
//            ZxysDoctorBO doctorBO = treatmentService.queryDoctorById(doctorVO);
//
//            // 获取所用的评价标签列表
//            List<ZxysVariableBO> labelList = treatmentService.queryVariable(1);
//
//            Map<String, Object> resultMap = new HashMap<String, Object>();
//            resultMap.put("doctor", doctorBO);
//            resultMap.put("label", labelList);
//
//            map.put("code","0");
//            map.put("data",resultMap);
//            map.put("message","获取评价页面信息成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message","系统内部错误");
//            return  map;
//        }
//        return map;
//    }
//
//
//    /**
//     * 获取评价页面详情（患者通过订单查询--未评价订单进入）
//     *
//     * @param recordVO
//     * @return
//     */
//    @RequestMapping("queryCommentPageInfo")
//    @ResponseBody
//    public Map<String, Object> queryCommentPageInfo(@RequestBody ZxysRecordVO recordVO){
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//
//            // 通过doctor_id查询医生信息
//            ZxysDoctorVO doctorVO = new ZxysDoctorVO();
//            doctorVO.setDoctorId(recordVO.getDoctorId());
//            ZxysDoctorBO doctorBO = treatmentService.queryDoctorById(doctorVO);
//
//            // 获取所用的评价标签列表
//            List<ZxysVariableBO> labelList = treatmentService.queryVariable(1);
//
//            Map<String, Object> resultMap = new HashMap<String, Object>();
//            resultMap.put("doctor", doctorBO);
//            resultMap.put("label", labelList);
//
//            map.put("code","0");
//            map.put("data",resultMap);
//            map.put("message","获取评价页面信息成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code","1");
//            map.put("data",null);
//            map.put("message", MessageTemplate.common_SystemErr);
//            return  map;
//        }
//        return map;
//    }
//
//    /**
//     * 删除订单
//     * @param recordVO
//     * @return
//     */
//    @RequestMapping("deleteOrder")
//    @ResponseBody
//    public Map<String, Object> deleteOrder(@RequestBody ZxysRecordVO recordVO) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        try {
//            // token校验
//            UserInfoBO userInfoBO = treatmentService.checkToken(recordVO.getToken());
//            if(userInfoBO == null) {
//                return Tools.errMessage();
//            }
//
//            boolean flag = treatmentService.deleteOrderByTradeNo(recordVO);
//            if(flag) {
//                map.put("code", "0");
//                map.put("data", null);
//                map.put("message", "订单删除成功");
//            }else {
//                map.put("code", "0");
//                map.put("data", null);
//                map.put("message", "订单删除失败");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//        return map;
//
//    }
//
//    /**
//     * 通过积分购买
//     * @param recordVO
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("intrgralPay")
//    @ResponseBody
//    public Map<String, Object> intrgralPay(@RequestBody ZxysRecordVO recordVO) throws Exception {
//        Map<String, Object> map = Tools.errMessage();
//        try {
//            // token校验
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setToken(recordVO.getToken());
//            UserInfoBO userInfoBO = treatmentService.findMemberByToken(tokenVo);
//            if(userInfoBO == null) {
//                return map;
//            }
//
//            UserInfoBO userInfo = treatmentService.queryMemberInfoById(userInfoBO);
//            int points = userInfo.getMember_points();
//            // 通过订单号查询订单信息
//            ZxysRecordBO record = treatmentService.queryRecordByOutTradeNo(recordVO);
//            if(record == null) {
//                map.put("code", "0");
//                map.put("message", "订单不存在");
//                map.put("data", null);
//                return map;
//            }else if("3".equals(record.getStatus()) || "4".equals(record.getStatus()) || "5".equals(record.getStatus())) {
//                map.put("code", "0");
//                map.put("message", "该订单已经付过款了，请不要重复付款");
//                map.put("data", null);
//                return map;
//            }
//            // 订单金额转换成积分（int）
//            int fee = record.getFee().setScale(0, BigDecimal.ROUND_UP).intValue();
//            // 校验用户积分是否足够
//            if(points < fee) {
//                map.put("code", "0");
//                map.put("message", "对不起，您的积分不足，请您充值");
//                map.put("data", null);
//                return map;
//            }
//            // 更新用户积分
//            userInfo.setMember_points(points-fee);
//            treatmentService.updateMemberPointInfo(userInfo);
//
//            //更新用户的订单状态
//            ZxysRecordBO recordBO = new ZxysRecordBO();
//            recordBO.setOut_trade_no(recordVO.getOutTradeNo());
//            String payway = recordVO.getPayWay();
//            if(payway == null || "".equals(payway)) {
//                payway = "2";
//            }
//            recordBO.setPayway(payway);
//            treatmentService.updateTradeNo(recordBO);
//
//            map.put("code", "0");
//            map.put("message", "购买成功");
//            map.put("data", null);
//        }catch (Exception e){
//            e.printStackTrace();
//            return Tools.errMessageSystem();
//        }
//
//        return map;
//    }
//
//}
//
//
//
