package com.fh.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageAnd64Binary {
    public static void main(String[] args){
    	
		String imgSrcPath 	 = "d:/abc/123.jpg";     //生成64编码的图片的路径
//		String imgCreatePath = "E:\\apache-tomcat-6.0.37\\webapps/pro/ueditor2/jsp/upload1/20140318/480ace2bfc6e44608595bd4adbdeb067.jpg";     //将64编码生成图片的路径

        String  imgCreatePath = "D:/1234/test.png";
		imgCreatePath=imgCreatePath.replaceAll("\\\\", "/");
		System.out.println(imgCreatePath);

    	String strImg = getImageStr(imgSrcPath);
    	System.out.println(strImg);

        strImg = "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\n";

        strImg = strImg.replaceAll("\\\\","");
        System.out.print(strImg);

        generateImage(strImg, imgCreatePath);
    }
    
   /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param imgSrcPath 生成64编码的图片的路径
     * @return
     */
    public static String getImageStr(String imgSrcPath){
        InputStream in = null;
        byte[] data = null;
        
        //读取图片字节数组
        try {
            in = new FileInputStream(imgSrcPath);        
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
    
    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param imgStr            转换为图片的字符串
     * @param imgCreatePath     将64编码生成图片的路径
     * @return
     */
    public static boolean generateImage(String imgStr, String imgCreatePath){
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {//调整异常数据
                    b[i]+=256;
                }
            }
            
            OutputStream out = new FileOutputStream(imgCreatePath);    
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
}