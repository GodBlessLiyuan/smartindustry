package com.smartindustry.common.util;

import com.smartindustry.common.bo.si.StorageLabelBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 19:42
 * @description: 时间工具
 * @version: 1.0
 */
public class DateUtil implements Comparator {
    public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat Y_M_D = new SimpleDateFormat("yyyy-MM-dd");

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

    @Override
    public int compare(Object arg0,Object arg1){
        StorageLabelBO bo1 = (StorageLabelBO)arg0;
        StorageLabelBO bo2 = (StorageLabelBO)arg1;
        int flag = 0;
        try {
            flag = Y_M_D.parse(bo1.getProduceDate()).compareTo(Y_M_D.parse(bo2.getProduceDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
