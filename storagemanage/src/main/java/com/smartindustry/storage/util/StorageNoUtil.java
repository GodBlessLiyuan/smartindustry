package com.smartindustry.storage.util;

import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.util.NoUtil;

import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 19:52
 * @description: 入库编号生成工具
 * @version: 1.0
 */
public class StorageNoUtil {
    public static final String RECEIPT_HEAD_YP = "PORK";
    private static final int NUM_LEN = 5;

    private static NoUtil<StorageHeadPO, Long> headUtil = new NoUtil<>();


    /**
     * 生成表头编号
     *
     * @param mapper
     * @param head
     * @param date
     * @return
     */
    public static String genStorageHeadNo(StorageHeadMapper mapper, String head, Date date) {
        StorageHeadPO po = headUtil.getPO(mapper, head, date);
        return headUtil.genNum(head, date, null == po ? 1 : headUtil.getNum(po.getStorageNo(), NUM_LEN) + 1, NUM_LEN);
    }
}
