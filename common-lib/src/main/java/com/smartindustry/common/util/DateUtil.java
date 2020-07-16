package com.smartindustry.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 19:42
 * @description: 时间工具
 * @version: 1.0
 */
public class DateUtil {
    public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat Y_M_D = new SimpleDateFormat("yyyyMMdd");

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
}
