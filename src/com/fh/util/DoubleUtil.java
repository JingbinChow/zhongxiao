package com.fh.util;

/**
 *
 * Created by admin on 2016/11/1.
 */
public class DoubleUtil {

    /**
     * 获取小数点后两位
     * @param num1
     * @param num2
     * @return
     */
    public static String countDec(Object num1,Object num2){

        String str = null;

        if(num1 instanceof Double && num2 instanceof Double){
            double douNum1=Double.parseDouble(num1.toString());
            double douNum2=Double.parseDouble(num2.toString());
            double num3=douNum1*douNum2;
            str = "" + num3;
            int i = str.lastIndexOf(".");
            str=str.substring(i+1,i+3);

        }
        if(num1 instanceof Double && num2 instanceof Float){
            double douNum=Double.parseDouble(num1.toString());
            float floNum = Float.parseFloat(num2.toString());
            double num3 = douNum*floNum;
            str = "" + num3;
            int i = str.lastIndexOf(".");
            str=str.substring(i+1,i+3);

        }
        if(num1 instanceof Float && num2 instanceof Double){
            float floNum = Float.parseFloat(num1.toString());
            double douNum=Double.parseDouble(num2.toString());
            double num3 = douNum*floNum;
            str = "" + num3;
            int i = str.lastIndexOf(".");
            str=str.substring(i+1,i+3);
        }
        return str;
    }
}
