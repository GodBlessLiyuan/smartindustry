package com.smartindustry.outbound.util;

import com.smartindustry.common.bo.si.StorageLabelBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:50 2020/9/15
 * @version: 1.0.0
 * @description: 将String格式的时间列表进行排序
 */
public class DateSortUtil implements Comparator {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int compare(Object arg0,Object arg1){
        StorageLabelBO bo1 = (StorageLabelBO)arg0;
        StorageLabelBO bo2 = (StorageLabelBO)arg1;
        int flag = 0;
        try {
            flag = sdf.parse(bo1.getProduceDate()).compareTo(sdf.parse(bo2.getProduceDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
