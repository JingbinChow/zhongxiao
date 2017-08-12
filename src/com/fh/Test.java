package com.fh;

import com.fh.util.Tools;
import com.mysql.fabric.xmlrpc.base.Array;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {


    public static void main(String[] args) throws UnsupportedEncodingException {


        //116.95400, 39.95400, 116.95300, 39.95300
        System.out.println(getDistance(39.95400, 39.95300, 116.95400, 116.95300));
        System.out.println(getDistance(110.11, 110.12, 55.210, 55.210));

        Integer[] arr = {1,5, 8, 4, 32,222, 22222};
        System.out.println(getMid(arr).get("mid")[0]);
        System.out.println(getMid(arr).get("max")[0]);
    }

    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d){
        return d * Math.PI / 180.0;
    }

    public static double getDistance(double eastLongitude, double westLongitude, double southLatitude, double northLatitude) {
        double a, b, d, sa2, sb2;
        southLatitude = rad(southLatitude);
        northLatitude = rad(northLatitude);
        a = southLatitude - northLatitude;
        b = rad(eastLongitude - westLongitude);

        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2   * EARTH_RADIUS
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(southLatitude)
                * Math.cos(northLatitude) * sb2 * sb2));
        return d;
    }


    public static Map<String, Object[]> getMid(Object[] arr) {
        if(arr.length == 0) {
            return null;
        }

        Map<String, Object[]> map = new HashMap<String, Object[]>();
        if(arr.length % 2 == 0) {
            Arrays.sort(arr);
            Object[] max = {arr[arr.length-1]};
            map.put("max", max);
            Object[] mid = {arr[arr.length/2],arr[arr.length/2+1]};
            map.put("mid", mid);

            return map;
        }else {
            Arrays.sort(arr);
            Object[] max = {arr[arr.length-1]};
            map.put("max", max);
            Object[] mid = {arr[arr.length/2]};
            map.put("mid", mid);
            return map;
        }

    }

}