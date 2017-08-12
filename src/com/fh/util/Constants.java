package com.fh.util;

public class Constants {
	
	public static String PICTURE_VISIT_FILE_PATH = "";//图片访问的路径
	
	public static String PICTURE_SAVE_FILE_PATH = "";//图片存放的路径

	
	public static String getPICTURE_VISIT_FILE_PATH() {
		return PICTURE_VISIT_FILE_PATH;
	}

	public static void setPICTURE_VISIT_FILE_PATH(String pICTURE_VISIT_FILE_PATH) {
		PICTURE_VISIT_FILE_PATH = pICTURE_VISIT_FILE_PATH;
	}

	public static String getPICTURE_SAVE_FILE_PATH() {
		return PICTURE_SAVE_FILE_PATH;
	}

	public static void setPICTURE_SAVE_FILE_PATH(String pICTURE_SAVE_FILE_PATH) {
		PICTURE_SAVE_FILE_PATH = pICTURE_SAVE_FILE_PATH;
	}

	public static void main(String[] args) {
//		Constants.setPICTURE_SAVE_FILE_PATH("D:/Tomcat 6.0/webapps/FH/topic/");
//		Constants.setPICTURE_VISIT_FILE_PATH("http://192.168.1.225:8888/FH/topic/");
	}
    //转账类型
	public static String jiaojin = "1";
	public static String zhongxiaojifen = "2";
	public static String zhongxiaoqianbao = "3";
	public static String xiaofeijifen = "4";
	public static String jiangjichi = "5";


    public static String status1 = "1";//未付款
	public static String status2 = "2";//已付款
	public static String status3 = "3";//删除
	public static String status4 = "4";//待审核
	public static String status5 = "5";//已退单








//   手续费
//	奖金提消费积分
	public static Double jiangjinToxiaofeijifen = 0.00;
	//	奖金提消费中孝钱包
	public static Double jiangjinToqianbao =0.00;

	public static Double jifentuidanshouxufei =0.05;

	public static Double jifentuidanguanlifei =0.15;

    //安卓版本
	public static String versionAndroid ="0.23";
//	官网地址
	public static String  url="http://www.zxyl1688.com/download/";
    //
	public static String  phone="13936326828";



	
}
