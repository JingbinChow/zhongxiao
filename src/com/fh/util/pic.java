package com.fh.util;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import javax.swing.ImageIcon;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class pic {
    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        security("中孝科技");
    }
    public static void security(String username){
        try {
            //1.jpg是你的 主图片的路径
            InputStream is = new FileInputStream("E:\\pic\\pic1.jpg");


            //通过JPEG图象流创建JPEG数据流解码器
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
            //解码当前JPEG数据流，返回BufferedImage对象
            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
            //得到画笔对象
            Graphics g = buffImg.getGraphics();

            //最后一个参数用来设置字体的大小
            Font f = new Font("宋体",Font.PLAIN,25);
            Color mycolor = Color.red;//new Color(0, 0, 255);
            g.setColor(mycolor);
            g.setFont(f);
            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(username,200,135);
            g.dispose();
            OutputStream os;

            //os = new FileOutputStream("d:/union.jpg");
            String shareFileName = "E:\\pic\\" +username+System.currentTimeMillis() + ".jpg";
            os = new FileOutputStream(shareFileName);
            //创键编码器，用于编码内存中的图象数据。
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(buffImg);

            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ImageFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
