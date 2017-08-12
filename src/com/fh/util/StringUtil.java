package com.fh.util;

public class StringUtil {
	//判断是否为空
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str) || str.isEmpty()) {
			return true;
		}
		return false;
	}

	//判断是否不为空
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}



	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0)
		{
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());

			i++;
		}
		return returnStr;
	}
}
