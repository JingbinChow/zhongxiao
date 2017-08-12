package com.fh.util;

import com.fh.entity.vo.UpgradePackage;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    /**
     * 随机生成六位数验证码
     *
     * @return
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;//(Math.random()*(999999-100000)+100000)
    }

    /**
     * 获取今日星期几
     */
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        System.out.println(splitRegex);
        return str.split(splitRegex);
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     * @return
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2Date(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 按照参数format的格式，日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 把时间根据时、分、秒转换为时间段
     *
     * @param StrDate
     */
    public static String getTimes(String StrDate) {
        String resultTimes = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now;

        try {
            now = new Date();
            java.util.Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            //sb.append("发表于：");
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTimes;
    }

    /**
     * 写txt里的单行内容
     *
     * @param fileP 文件路径
     * @param content  写入的内容
     */
    public static void writeFile(String fileP, String content) {
        String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
        filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter(filePath));
            pw.print(content);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18([0-3]|[5-9])))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 读取txt里的单行内容
     *
     * @param fileP 文件路径
     */
    public static String readTxtFile(String fileP) {
        try {

            String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
            filePath = filePath.replaceAll("file:/", "");
            filePath = (filePath.trim() + fileP.trim()).trim();

            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {        // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);    // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    return lineTxt;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
        }
        return "";
    }

    public static String getToken() {
        String str = UUID.randomUUID().toString().replace("-", "");//用来生成数据库的主键id非常不错。。
        return str;
    }

    public static String filterNull(String str) {
        String s = "";
        if (str == null || "null".equals(str)) {   //zhangxf,080415 modified
            s = "";
        } else {
            s = str.trim();
        }
        return s;
    }

    public static Map<String, Object> errMessage() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("message", "token失效");
        modelMap.put("code", "2");
        modelMap.put("data", null);
        return modelMap;
    }

    public static Map<String, Object> errMessageSystem() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("message", "系统内部错误");
        modelMap.put("code", "1");
        modelMap.put("data", null);
        return modelMap;
    }


    public static Map<String, Object> errMessageSystemZxlp() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("message", "系统错误,请联系管理员");
        modelMap.put("code", "89");
        modelMap.put("data", null);
        return modelMap;
    }

    public static String getSysDate(String dateFormat) {
        if (dateFormat == null || "".equals(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateStr = sdf.format(date.getTime());
        return dateStr;
    }

    public static String getSysDateString(String dateFormat) {
        if (dateFormat == null || "".equals(dateFormat)) {
            dateFormat = "yyyyMMddHHmmss";
        }
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateStr = sdf.format(date.getTime());
        return dateStr;
    }

    /**
     * 获取前一天
     */
    public static String minusDay() {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前一天
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return defaultStartDate;
    }

    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public static void main(String[] args) {
        //System.out.println(isZero(new BigDecimal("0.00")));
       // System.out.println(compareTo(new BigDecimal("0.005"),new BigDecimal("10")));
        // System.out.println( saveTwo(new BigDecimal("20.5255")));
        // System.out.println(getRandomNum());
        System.out.println(checkidCard("142729199409081235"));
    }

    /**
     * 两个日期相差多少天
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;

        try {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 拆分团队标识并且返回上级所有人的团队标识
     */
    public static List<String> subTeamSign(String teamSign) {
        List<String> list = new ArrayList<String>();
        //获取团队标识的长度
        int length=teamSign.length();
        //计算拆分的节数
        int num=(length/4)-1;
        //最后四位不要
        for(int i=0;i<num;i++){
            list.add(teamSign.substring(4*i,i*4+4)) ;
        }
        //重新拼接上级团队标识
        List<String> result=new ArrayList<String>();
        String team="";
        for (String s : list) {
            team=team+s;
            result.add(team);
        }
        return result;
    }
    /**
     * 补包升级
     */
    public static List<UpgradePackage> filling(int balance,int unit){


        System.out.println(balance);
        //爱心会员
        int level=90000;
        //公益会员
        int level1=300000;
        //慈孝会员
        int level2=900000;
        //慈孝股东
        int level3=3000000;
      /*  List<Integer> levels=new ArrayList<Integer>();
        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
       */
        List<UpgradePackage> list=null;
//老爱心会员特殊情况处理 直接升级公益会员
        if(balance==30000){
            //老爱心会员
            list=new ArrayList<UpgradePackage>();
            UpgradePackage upgrade=null;
            for (int i=0;i<3;i++){
                if (i==0){
                    upgrade= new UpgradePackage((level1-balance)/unit,"公益会员升级包");
                    list.add(upgrade);
                    continue;
                }
                if (i==1){
                    upgrade= new UpgradePackage((level2-balance)/unit,"慈善会员升级包");
                    list.add(upgrade);
                    continue;
                }
                if (i==2){
                    upgrade= new UpgradePackage((level3-balance)/unit,"慈孝股东升级包");
                    list.add(upgrade);
                    continue;
                }
            }
            return list;
        }
        //新会员处理
        balance=(balance/10000)*10000;
//        //原始升级
//        if(balance<level1){
//            list=new ArrayList<UpgradePackage>();
//            UpgradePackage upgrade=null;
//           for (int i=0;i<3;i++){
////               if (i==0){
////                   upgrade= new UpgradePackage((level-balance)/unit,"爱心会员升级包");
////                   list.add(upgrade);
////                   continue;
////               }
//               if (i==0){
//                   upgrade= new UpgradePackage((level1-balance)/unit,"公益会员升级包");
//                   list.add(upgrade);
//                   continue;
//               }
//               if (i==1){
//                   upgrade= new UpgradePackage((level2-balance)/unit,"慈善会员升级包");
//                   list.add(upgrade);
//                   continue;
//               }
//               if (i==2){
//                   upgrade= new UpgradePackage((level3-balance)/unit,"慈孝股东升级包");
//                   list.add(upgrade);
//                   continue;
//               }
//           }
//            return list;
//        }
//爱心升级
        if(balance<level1){

            list=new ArrayList<UpgradePackage>();
            UpgradePackage upgrade=null;
            for (int i=0;i<3;i++){
                if (i==0){
                    upgrade= new UpgradePackage((level1-balance)/unit,"公益会员升级包");
                    list.add(upgrade);
                    continue;
                }
                if (i==1){
                    upgrade= new UpgradePackage((level2-balance)/unit,"慈善会员升级包");
                    list.add(upgrade);
                    continue;
                }
                if (i==2){
                    upgrade= new UpgradePackage((level3-balance)/unit,"慈孝股东升级包");
                    list.add(upgrade);
                    continue;
                }
            }
            return list;
        }
//公益升级
        if(balance<level2&&balance>=level1){
            list=new ArrayList<UpgradePackage>();
            UpgradePackage upgrade=null;
            for (int i=0;i<2;i++){

                if (i==0){
                    upgrade= new UpgradePackage((level2-balance)/unit,"慈善会员升级包");
                    list.add(upgrade);
                    continue;
                }
                if (i==1){
                    upgrade= new UpgradePackage((level3-balance)/unit,"慈孝股东升级包");
                    list.add(upgrade);
                    continue;
                }
            }
        }
        //慈善升级
        if(balance<level3&&balance>=level2){
            list=new ArrayList<UpgradePackage>();
            UpgradePackage upgrade=null;
            upgrade= new UpgradePackage((level3-balance)/unit,"慈孝股东升级包");
            list.add(upgrade);
        }
        return list;
    }
    //打印输出文件
    public static void writeObject(Map<String,String> params) {
        try {
            String line = System.getProperty("line.separator");
            StringBuffer str = new StringBuffer();
            FileWriter fw = new FileWriter("D:\\1.txt", true);
            Set set = params.entrySet();
            Collection<String> keyset= params.keySet();
            List list=new ArrayList<String>(keyset);
            Collections.sort(list);
            Iterator iter = set.iterator();

            while(iter.hasNext()){
                Map.Entry entry = (Map.Entry)iter.next();
                str.append(entry.getKey()+" : "+entry.getValue()).append(line);
            }
            fw.write(str.toString());
            fw.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void writeStr(String str) throws IOException {
        try {
            String line = System.getProperty("line.separator");
            FileWriter fw = new FileWriter("D:\\2.txt", true);
            fw.write(str);
            fw.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //判断String字符集
    public static String getEncoding(String str) {
        String encode = "ISO-8859-1";
        try {
            System.out.print(new String(str.getBytes(encode), encode));
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }
    /**
     * 把gbk转UTF-8
     */
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }
    /**
     * 随机生成一个32位随机数
     */
    public static String getRandom32(){

        Random randomNum = new Random();
        int theNum = randomNum.nextInt();
        return theNum+"";

    }

    /**
     * 检测bigdecimal是否等于0
     * @param s
     */
    public static boolean isZero(BigDecimal s) {

        BigDecimal bg2 = new BigDecimal("0");

        //create int object
        int res;

        res = s.compareTo(bg2); // compare bg1 with bg2

//        String str1 = "相等";
//        String str2 = "大于0";
//        String str3 = "小于0";

        if (res == 0){
            return true;
        }
        else{
            return false;
        }

    }
    /**
     * 比较俩个decimal类型的值大小
     * @param a,b
     * @return 返回数值大的
     */
    public static boolean compareTo(BigDecimal a,BigDecimal b) {


        //create int object
        int res;

        res = a.compareTo(b); // compare bg1 with bg2

//        String str1 = "俩数相等";
//        String str2 = "第一个数大于第二个数";
//        String str3 = "第二个数 大于第一个数";

        if (res == 0 || res == 1){
            return true;
        }
        else {
            return false;
        }

    }

    
    /**
     * @methodName 判断两个BigDecimal类型数值大小  0 相等  1 前面的大 -1  前面的小
     * @Author 刘洋
     * @param 
     * @return 
     * @throws 
     * @Date: 2016-12-01
     * @Time: 18:30
     */
    public static Integer checkbig(BigDecimal a,BigDecimal b){
        Integer res;
        res = a.compareTo(b);
        return res;
    }
    /**
     * 保留decimal类型的值俩位小数
     * @param a
     */
    public static BigDecimal saveTwo(BigDecimal a) {


//        //create int object
//        BigDecimal decimal = new BigDecimal("1.12345");
//        System.out.println(a);
//        //四舍五入，如果是0.015，变为0.01
//        BigDecimal setScale =a .setScale(2,BigDecimal.ROUND_HALF_DOWN);
//        System.out.println(setScale);
//
//        //四舍五入，如果是0.015，变为0.02
//        BigDecimal setScale1 = a.setScale(2,BigDecimal.ROUND_HALF_UP);
//        System.out.println(setScale1);

        //直接删除多余的小数位 ，如果是0.015，变为0.01
        BigDecimal setScale = a.setScale(2,BigDecimal.ROUND_DOWN);
        return  setScale;
    }




    /**
     *
     * 校验银行卡
     * @param bankCrad
     * @return
     */
    public static boolean checkBankCard(String bankCrad) {

        boolean result = false;
        try {
            String reg = "^(\\d{16}|\\d{17}|\\d{19})$";
            result = bankCrad.matches(reg);

        }catch (Exception e){
            e.printStackTrace();
            return result;
        }

        return result;
    }

    //校验身份证号码
    public static boolean checkidCard(String bankCrad) {

        boolean result = false;
        try {
            String reg = "^\\d{15}|^\\d{17}([0-9]|X|x)$";
            result = bankCrad.matches(reg);

        }catch (Exception e){
            e.printStackTrace();
            return result;
        }

        return result;
    }

    /**
     * 返回数组中最小的不为0 的数
     * @param args
     * @return
     */
    public static BigDecimal getMinBigDecimalButZero(BigDecimal[] args) {

        if(args == null) {
            return null;
        }
        Arrays.sort(args);

        for (BigDecimal singal: args){
            if(!singal.equals(new BigDecimal("0.00"))) {
                return singal;
            }
        }

        return new BigDecimal("0.00");
    }

    /**
     * 校验密码的格式
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
            Matcher matcher = regex.matcher(password);
            flag = matcher.matches();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return flag;
    }

    // 将 s 进行 BASE64 编码
    public static String getBASE64(String s) {
        if (s == null) return null;
        return (new sun.misc.BASE64Encoder()).encode( s.getBytes() );
    }
}
