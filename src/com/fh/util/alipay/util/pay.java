package com.fh.util.alipay.util;

import com.fh.entity.vo.CommunicateRecordVo;
import com.fh.util.alipay.Base64;
import com.fh.util.alipay.SignUtils;
import com.fh.util.config.AlipayConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

public  class pay {
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016101402164580";
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "2088521057130925";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "zxapp@zxyl1688.com";
	/** 商户私钥，pkcs8格式 */
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMRXZOAVAd5MCAUQgnX/4/fd+QnOMQweNYawN1ncGy/0URzT1ApqzgnM4r1KvSwWlpWaAXYv9pp2f+W4YOyS4DFHUUYP74ks7NUuXR4HsZKB/D9bzkuOWZr+ZlTiCFmmX65x4Mu8qQb0Wxj1G8qjtWGKuviROpl2Y4V/0+CI7s5bAgMBAAECgYEAlUao0GHIaN2/nzIdLl/eUB648EaPLdpt8bhabPyWww/q95Lp/P2VJwhaP3bqevG1jYpooBX5VRjRRrO3JUbKIfhHYI5OCfAC4ho54W0cFclHylj+Bk2u5j9TZGBHxnfEyxVm1hP5cNBRXz29A+W6cCoDa75tLmgD6zCNql+TiZkCQQDlriXdc5m+bhwvoBsLFHamzMYLB3l/PcX+5Wc5XlgjbiJMlKddiHs8Ln4+1RHugdSc9+efabVD4ZF9p2vZd5ytAkEA2tc5QssjU/rYeUJMMzxlVbVLWVWSeGLHqhRk9F5SloRix8uiL7p9dUzLdrxuCrUpaF88PH0i3zzuY/YyNNuwJwJANdO1XgvciSuHAYZVMCiQkhDFjQooaDCS+WTL5z78Cvmhoi7odlwo/Xar8Y+/zxSwPkU/DDYa3Bi+HyOge+uzVQJBAKJ96ICWARdcTNxMw/PPRpcLVHVyup01C3hYSFXCq6QjjI05wKWb3Yf5i6IQa8z7k1FyVXG8oOYNgh0rM0BYV0UCQAk6+L1E4u258LmOTs2Ys7tkqA0QtOdTkVl6u3psS7y0DSw/z7Deo+L1dMXTXr7O580MrxLI8WFtrysz+K2oaMk=";
	/**商户公钥 */

	//public static final String ALIPAY_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	//支付宝公钥
	public static final String ALIPAY_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	public static String alipay(CommunicateRecordVo vo) {
		try {
			Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID  ,vo);
			String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
			String sign = OrderInfoUtil2_0.getSign(params, AlipayConfig.private_key);
			final String orderInfo = orderParam + "&" + sign;

			return  orderInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
           return "";
	}

	/**
	 * 供新的中孝医生使用
	 * @param vo
	 * @return
	 */
	public static String alipay2(CommunicateRecordVo vo) {
		try {
			Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap2(APPID  ,vo);
			String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

			String sign = OrderInfoUtil2_0.getSign(params, AlipayConfig.private_key);

			final String orderInfo = orderParam + "&" + sign;

			return  orderInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
