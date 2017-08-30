package com.example.luyan.smartmenu_shop.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by luyan on 5/26/16.
 */
public class DateUtils {
    private static SimpleDateFormat sf = null;

    /* 获取系统时间 格式为："yyyy/MM/dd " */
    public static String getCurrentDate() {
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static String getTomorrowDate(String nowDay){
        Date date=new Date();//取时间
        sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(sf.parse(nowDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(calendar.DATE,1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /* 获取系统时间 格式为："yyyy/MM/dd " */
    public static String getCurrentDateByCN() {
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /* 获取系统时间 格式为："yyyy-MM-dd " */
    public static String getCurrentDateWithNoLine() {
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /* 获取系统时间 格式为："yyyyMMdd " */
    public static String getCurrentDateYYYYMMdd() {
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat("yyyyMMdd");
        return sf.format(d);
    }

    /* 时间戳转换成字符窜 */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /* 将字符串转为时间戳 */
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static void main(String[] args) {
        String TIME = DateUtils.getDateToString(1445875200000l);
        System.out.println("TIME==" + TIME);

        String CURRENTTIME = DateUtils.getCurrentDate();
        System.out.println("CURRENTtime" + CURRENTTIME);
        long CHUO = DateUtils.getStringToDate(CURRENTTIME);
        System.out.println(CHUO);
    }

}
