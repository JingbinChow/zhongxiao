package com.fh.util;

import com.bcloud.msg.http.HttpSender;

public class HttpSenderTest {

	public static void main(String[] args) {
		String uri = "http://send.18sms.com/msg/HttpBatchSendSM";//应用地址
		String account = "v9zg3x";//账号
		String pswd = "6XjzFcS3";//密码
		String mobiles = "13936326828";//手机号码，多个号码使用","分割
		String content = "您好！你的验证码为8888";//短信内容  注意内容中的逗号请使用中文状态下的逗号
		boolean needstatus = false;//是否需要状态报告，需要true，不需要false
		String product = "";//产品ID(不用填写)
		String extno = "";//扩展码(不用填写)
		try {
			String returnString = HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product, extno);
			System.out.println(returnString);
			//TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
	}
}

