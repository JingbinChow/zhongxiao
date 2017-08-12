package com.fh.util.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOx4ImWy7ZjS/rGU\n" +
			"AtbN+AltDRCg2Mku45bvnKMPW/T4gPrziN0je0YRTTMXPf6Jv5H5wcFbD7cGSE80\n" +
			"dfhHaZneeGi3OIwZ6x68LvJavV5V/WBPnKZlS83xXvkeDOpXBoY7RwU7/GRCKbRW\n" +
			"ctitZejx07jUjab8mWQf9ufC4FW/AgMBAAECgYACPZ0t04U21oD9U8a3HFqGVFc0\n" +
			"WiHTvjL/4iNc1C9t4dDvD9GzLrvjTRSrMEQxHkMHchNo7qD2fgO/ZGLdyGdyviQq\n" +
			"xHYfZonD/gZqbFBgGBdjTzkg+9nhA/0TPw0tTVIzugtuNYejcmHLWdlwBv9DqlyN\n" +
			"omXZByitS+5+IWo3yQJBAPjAnpP3hjXVhe5vImyNjl/D09T8plvoODjnfLUrHZvv\n" +
			"NETsXAkbKZbJ97h/O2tto7++5h2aboC354i/oSz+tiMCQQDzW+XpLHfO/eFxpuSn\n" +
			"XWcPlbmSxDEZ72N7awWiAjiILY+yRR6z+SYMkBQojQSM4H/jQO8t42FK8jNfpo3w\n" +
			"16W1AkEAwqN1cbIsgdMhiP/Mn8Pjs2s3RWCrix6ZX21CVz/1Wx5Ad+251iY61JIU\n" +
			"E3j6tYSErHRi+qbr8Cv2LzpCk0J6oQJAEGMGxz28T47e6GVBjleGwhDl7xEQ8Vsi\n" +
			"SsQ5G+CkZ98RhwKKf7nuKvhksxpTzRys3+OWd3ORMJfAh9dSRX5v5QJBAOS2SeiV\n" +
			"Z3pUZHNn3y2W16GZF/yMiqKe5p4siFWsXqPetlLscjXJom/xqEduXq9D7T+pZXBD\n" +
			"3vQtOvC6i/7PXT8=";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://106.75.37.114:8081/treatment/aliPostCallBack.do";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

