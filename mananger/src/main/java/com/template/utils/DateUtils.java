package com.template.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: DateUtils
 * @Description:  时间工具类
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/11 18:20
 */
public class DateUtils {

    /**
     *
     * 功能描述:
     *
     * @param: 获取当前系统时间 yyyy-MM-dd HH:mm:ss
     * @return:
     * @auther: youqing
     * @date: 2018/5/26 9:59
     */
    public static String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(System.currentTimeMillis());
        return date;
    }

    /**
     *
     * 功能描述:
     *
     * @param: date类 获取当前系统时间 yyyy-MM-dd HH:mm:ss
     * @return:
     * @auther: youqing
     * @date: 2018/5/26 10:39
     */
    public static Date getCurrentDateToDate () {
        DateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String date = df.format(System.currentTimeMillis());
        Date d = null;
        try {
            d = df.parse( date.toString( ) );
        } catch ( ParseException e ) {
            e.printStackTrace( );
        }
        return d;
    }
}
