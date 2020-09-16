package com.smartindustry.common.util;

import com.smartindustry.common.bo.si.StorageLabelBO;

import java.lang.reflect.Method;
import java.text.ParseException;
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


//    /**
//     * 将时间形式的String串变成Date类型
//     * @param string
//     * @return
//     */
//    public static Function<String, Date> transform(String string){
//        Date date = null;
//        try {
//            date = Y_M_D.parse(string);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }

    /**
     * 数组自定义排序
     * @param targetList  目标数组
     * @param sortField   选中的字段属性排序，
     * @param sortMode    true正序，false逆序
     * @param type        是否要对选中的字段进行格式转换  true转换，false不转换
     * @param function    转换的具体方法，比如 String to Date ,Function<Object,Object> 前者为String后者为Date
     * @param <T>
     */
    @SuppressWarnings("all")
    public static <T> void sort(List<T> targetList, final String sortField,
                                final boolean sortMode, final boolean type, Function<?,?> func) {
        if(targetList == null||targetList.size() < 2||sortField==null||sortField.length() == 0){
            return;
        }
        Collections.sort(targetList, new Comparator() {
            @Override
            public int compare(Object obj1, Object obj2) {
                int retVal = 0;
                try {
                    // 获取getXxx()方法名称
                    String methodStr = "get" + sortField.substring(0, 1).toUpperCase() + sortField.substring(1);
                    // 获得方法
                    Method method1 = ((T) obj1).getClass().getMethod(methodStr,null);
                    Method method2 = ((T) obj2).getClass().getMethod(methodStr,null);
                    if(type){
//                        if (sortMode) {
//                            //将对象的字段属性进行类型转换
//                            retVal = func.apply(method1.invoke(((T) obj1), null)).compareTo(func.apply(method2.invoke(((T) obj2), null)));
//                        } else {
//                            retVal = func.apply(method2.invoke(((T) obj2), null)).compareTo(func.apply(method1.invoke(((T) obj1), null)));
//                        }
                    }else {
                        if (sortMode) {
                            retVal = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString());
                        } else {
                            retVal = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return retVal;
            }
        });
    }

    public static void main(String[] args) {
        StorageLabelBO bo1 = new StorageLabelBO();
        bo1.setProduceDate("2021-05-21");
        StorageLabelBO bo2 = new StorageLabelBO();
        bo2.setProduceDate("2022-04-23");
        StorageLabelBO bo3 = new StorageLabelBO();
        bo3.setProduceDate("2021-05-22");

        List<StorageLabelBO> bos = new ArrayList<StorageLabelBO>();
        bos.add(bo1);
        bos.add(bo2);
        bos.add(bo3);
        DateUtil.sort(bos,"produceDate", true,false,null);
        System.out.println(bos);
    }

}
