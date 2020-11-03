package com.smartindustry.common.util;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 19:42
 * @description: 时间工具
 * @version: 1.0
 */
public class DateUtil{
    public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat Y_M_D = new SimpleDateFormat("yyyy-MM-dd");

    public static final Function<String,Date> STRING2DATE = i -> {
        Date date = null;
        try {
            date =  Y_M_D.parse(i);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    };
    /**
     * str 转 date
     *
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, SimpleDateFormat sdf) {
        if (null == str || "".equals(str)) {
            return null;
        }

        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * date 转 str
     *
     * @param date
     * @param sdf
     * @return
     */
    public static String date2Str(Date date, SimpleDateFormat sdf) {
        if (null == date) {
            return "";
        }

        return sdf.format(date);
    }


    /**
     * @param inputDate 要解析的字符串
     * @return 解析出来的日期，如果没有匹配的返回null
     */
    public static Date parseDate(String inputDate) {
        //可能出现的时间格式,可自由添加
        String[] patterns = {
                "yyyy-MM-dd",
                "yyyy/MM/dd",
                "yyyyMMdd",
                "yyyyMM",
                "yyyy"
        };
        SimpleDateFormat df = new SimpleDateFormat();
        for (String pattern : patterns) {
            df.applyPattern(pattern);
            df.setLenient(false);
            ParsePosition pos = new ParsePosition(0);
            Date date = df.parse(inputDate, pos);
            if (date != null) {
                return date;
            }
        }
        return null;
    }
}
