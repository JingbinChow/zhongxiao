package com.fh;


import com.fh.util.Files;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SVNFileExports {

    private static final String MODIFY_PREFIX = "Modified : ";
    private static final String ADD_PREFIX = "Added : ";

    private static int i = 0;
    private static int is = 0;
    private static int j = 0;
    private static int js = 0;
    public static void main(String[] args) throws Exception {
        try {
            String projectDir = "D:\\IDEA\\WorkSpace\\java";
            GregorianCalendar gc = new GregorianCalendar();
            Date date = gc.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            String tt = formatter.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String patchDir = sdf.format(date) + "_zhongxiao";

            String pathFile = projectDir + "\\SVNFilePaths.txt";
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile)));
            String lineData = reader.readLine();

            try {
                while (lineData != null) {
                    System.out.println(lineData);
                    lineData = lineData.trim();
                    String filePath = lineData;
                    String fileSrcPath = lineData;

                    boolean isValidFile = false;
                    if (filePath.indexOf("/src/") > -1) {
                        filePath = filePath.substring(filePath.indexOf("/zhongxiao/"));
                        String srcFilePath = projectDir + filePath;
                        srcFilePath = srcFilePath.replace('/', '\\');
                        String srcTargetFilePath = projectDir + "/patch/" + patchDir + filePath.replaceFirst("/zhongxiao", "");
                        srcTargetFilePath = srcTargetFilePath.replace('/', '\\');
                        File tf = new File(srcFilePath);
                        if (tf.isFile()) {
                            Files.copyFile(new File(srcFilePath), new File(srcTargetFilePath));
                        }

                        filePath = filePath.replaceAll(".java", ".class");
                        filePath = filePath.replaceAll("/zhongxiao/src/", "/zhongxiao/WebRoot/WEB-INF/classes/");
                        filePath = filePath.substring(filePath.indexOf("/zhongxiao/"));
                        isValidFile = true;

                    } else {


                        filePath = filePath.substring(filePath.indexOf("/zhongxiao/"));
                        isValidFile = true;

                    }
                    if (filePath.indexOf("/resources/") > -1) {
                        filePath = filePath.substring(filePath.indexOf("/zhongxiao/"));
                        String srcFilePath = projectDir + filePath;
                        srcFilePath = srcFilePath.replace('/', '\\');
                        String srcTargetFilePath = projectDir + "/patch/" + patchDir + filePath.replaceFirst("/zhongxiao", "");
                        srcTargetFilePath = srcTargetFilePath.replace('/', '\\');
                        File tf = new File(srcFilePath);
                        if (tf.isFile()) {
                            Files.copyFile(new File(srcFilePath), new File(srcTargetFilePath));
                        }

                        filePath = filePath.replaceAll(".mxl", ".mxl");
                        filePath = filePath.replaceAll("/zhongxiao/resources/", "/zhongxiao/WebRoot/WEB-INF/classes/");
                        filePath = filePath.substring(filePath.indexOf("/zhongxiao/"));
                        isValidFile = true;

                    } else {


                        filePath = filePath.substring(filePath.indexOf("/zhongxiao/"));
                        isValidFile = true;

                    }
                    if (!isValidFile) {
                        lineData = reader.readLine();
                        continue;
                    }
                    String srcFilePath = projectDir + filePath;
                    srcFilePath = srcFilePath.replace('/', '\\');
                    String srcTargetFilePath = projectDir + "/patch/" + patchDir + filePath.replaceFirst("/zhongxiao", "");
                    srcTargetFilePath = srcTargetFilePath.replace('/', '\\');
                    File tf = new File(srcFilePath);
                    if (tf.isFile()) {
                        Files.copyFile(new File(srcFilePath), new File(srcTargetFilePath));
                        i++;
                    }
                    lineData = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("lineData:" + lineData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
