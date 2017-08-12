package com.fh.util.alipay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.fh.entity.vo.AlipayTransactionVo;
import com.fh.entity.vo.CommunicateRecordVo;
import com.fh.util.Tools;
import com.fh.util.alipay.SignUtils;

public class OrderInfoUtil2_0 {
	
	/**
	 * 构造授权参数列表
	 * 
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildAuthInfoMap(String pid, String app_id, String target_id) {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 商户签约拿到的app_id，如：2013081700024223
		keyValues.put("app_id", app_id);

		// 商户签约拿到的pid，如：2088102123816631
		keyValues.put("pid", pid);

		// 服务接口名称， 固定值
		keyValues.put("apiname", "com.alipay.account.auth");

		// 商户类型标识， 固定值
		keyValues.put("app_name", "mc");

		// 业务类型， 固定值
		keyValues.put("biz_type", "openservice");

		// 产品码， 固定值
		keyValues.put("product_id", "APP_FAST_LOGIN");

		// 授权范围， 固定值
		keyValues.put("scope", "kuaijie");

		// 商户唯一标识，如：kkkkk091125
		keyValues.put("target_id", target_id);

		// 授权类型， 固定值
		keyValues.put("auth_type", "AUTHACCOUNT");

		// 签名类型
		keyValues.put("sign_type", "RSA");

		return keyValues;
	}

	/**
	 * 构造支付订单参数列表
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildOrderParamMap(String app_id ,CommunicateRecordVo vo) throws UnsupportedEncodingException {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);
		String sign="{\"timeout_express\":\"\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""+vo.getMoney()+"\",\"notify_url\":\"http://106.75.37.114:8081/treatment/aliPostCallBack.do\",\"subject\":\"中孝医生\",\"body\":\"中孝医生-订单号-"+vo.getCommunicate_id()+"\",\"out_trade_no\":\"" + vo.getCommunicate_id() +  "\"}";
		sign = new String(sign.getBytes("ISO-8859-1"),"utf-8");
		keyValues.put("biz_content",sign);
		keyValues.put("charset", "utf-8");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type", "RSA");

		keyValues.put("timestamp", vo.getCreate_time()); //订单付款时间

		keyValues.put("version", "1.0");

		return keyValues;
	}

	/**
	 * 构造支付订单参数列表(供新的中孝医生使用)
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildOrderParamMap2(String app_id ,CommunicateRecordVo vo) throws UnsupportedEncodingException {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);
		String sign="{\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""+vo.getMoney()+"\",\"subject\":\"中孝医生\",\"body\":\"中孝医生-订单号-"+vo.getCommunicate_id()+"\",\"out_trade_no\":\"" + vo.getCommunicate_id() +  "\"}";
		// "timeout_express":"",
		// "notify_url":"http://106.75.77.99:81/treatment/aliPayPostCallBack.do",


		System.out.println("**********************************************************" + vo.getCommunicate_id());

		System.out.println(sign);

//		sign = new String(sign.getBytes("ISO-8859-1"),"utf-8");
		keyValues.put("biz_content",sign);
		keyValues.put("charset", "utf-8");
		keyValues.put("notify_url","http://106.75.24.52:81/treatment/aliPayPostCallBack.do");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type", "RSA");

		keyValues.put("timestamp", vo.getCreate_time()); //订单付款时间

		keyValues.put("version", "1.0");

		return keyValues;
	}
	
	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				//UnsupportedEncodingException
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 对支付参数信息进行签名
	 * 
	 * @param map
	 *            待签名授权信息
	 * 
	 * @return
	 */
	public static String getSign(Map<String, String> map, String rsaKey) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}
	
	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	public static Map<String, String> getMapInfo (AlipayTransactionVo alipayTransactionVo){
		Map<String,String> mapInfo=new HashMap<String, String>();
		mapInfo.put("notify_time",alipayTransactionVo.getNotify_time());
		mapInfo.put("notify_type",alipayTransactionVo.getNotify_type());
		mapInfo.put("notify_id",alipayTransactionVo.getNotify_id());
		mapInfo.put("app_id",alipayTransactionVo.getApp_id());
		mapInfo.put("trade_no",alipayTransactionVo.getTrade_no());
		mapInfo.put("out_trade_no",alipayTransactionVo.getOut_trade_no());
		mapInfo.put("out_biz_no",alipayTransactionVo.getOut_biz_no());
		mapInfo.put("buyer_id",alipayTransactionVo.getBuyer_id());
		mapInfo.put("buyer_logon_id",alipayTransactionVo.getBuyer_logon_id());
		mapInfo.put("seller_id",alipayTransactionVo.getSeller_id());
		mapInfo.put("seller_email",alipayTransactionVo.getSeller_email());
		mapInfo.put("trade_status",alipayTransactionVo.getTrade_status());
		mapInfo.put("total_amount",alipayTransactionVo.getTotal_amount());
		mapInfo.put("receipt_amount",alipayTransactionVo.getReceipt_amount());
		mapInfo.put("invoice_amount",alipayTransactionVo.getInvoice_amount());
		mapInfo.put("buyer_pay_amount",alipayTransactionVo.getBuyer_pay_amount());
		mapInfo.put("point_amount",alipayTransactionVo.getPoint_amount());
		mapInfo.put("buyer_pay_amount",alipayTransactionVo.getBuyer_pay_amount());
		mapInfo.put("refund_fee",alipayTransactionVo.getRefund_fee());
		mapInfo.put("subject",alipayTransactionVo.getSubject());
		mapInfo.put("buyer_pay_amount",alipayTransactionVo.getBuyer_pay_amount());
		mapInfo.put("body",alipayTransactionVo.getBody());
		mapInfo.put("gmt_create",alipayTransactionVo.getGmt_create());
		mapInfo.put("gmt_payment",alipayTransactionVo.getGmt_payment());
		mapInfo.put("gmt_refund",alipayTransactionVo.getGmt_refund());
		mapInfo.put("gmt_close",alipayTransactionVo.getGmt_close());
		mapInfo.put("fund_bill_list",alipayTransactionVo.getFund_bill_list());
		return mapInfo;
	}

}
