package com.fh.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 通过银行的Bin号 来获取 银行名称
 * 
 * @author 一介草民
 * 
 */
public class JsonUtil {

	private JsonUtil(){}

	/**
	 * 对象转换成json字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * json字符串转成对象
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	/**
	 * json字符串转成对象
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

}
