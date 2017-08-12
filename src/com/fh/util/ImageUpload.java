package com.fh.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;

/**
 * Created by admin on 2016/12/15.
 */
public class ImageUpload {


    public static void main(String[] args) throws IOException {
//        String imgSrcPath 	 = "e:/123.png";     //生成64编码的图片的路径
//        String imgCreatePath = "D:/abc/123/3652.jpg";     //将64编码生成图片的路径


        String imgSrcPath = "D:/test.txt";
        String imgCreatePath = "D:/123/testbak.txt";

        // 文件夹不存在，创建一个
        File file = new File(imgCreatePath);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }

        imgCreatePath=imgCreatePath.replaceAll("\\\\", "/");
        System.out.println(imgCreatePath);

        byte[] strImg = ImageUpload.getImageStr(imgSrcPath);
        for (byte b :strImg){
            System.out.print(b);
        }
        System.out.println("*****************************");
        System.out.println(strImg);
        ImageUpload.generateImage(strImg, imgCreatePath);
    }

    /**
     * 将图片文件转换成二进制字符串
     * @param imgSrcPath
     * @return
     */
    public static byte[] getImageStr(String imgSrcPath) {
        InputStream in = null;
        byte[] data = null;
        FileImageInputStream input =null;
        try {
            input = new FileImageInputStream(new File(imgSrcPath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;//返回Base64编码过的字节数组字符串
    }



    /**
     * 图片上传
     * @param image 图片的二进制字符串
     * @param imgCreatePath  上传路径
     * @return
     */
    public static boolean generateImage(byte[] image, String imgCreatePath){

        try {
            // 字符串转byte[]

            for(int i=0;i<image.length;++i) {
                if(image[i]<0) {//调整异常数据
                    image[i]+=256;
                }
            }

            OutputStream out = new FileOutputStream(imgCreatePath);
            out.write(image);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
