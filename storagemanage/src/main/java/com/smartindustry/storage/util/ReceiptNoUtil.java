package com.smartindustry.storage.util;

import com.smartindustry.common.mapper.ReceiptBodyMapper;
import com.smartindustry.common.pojo.ReceiptBodyPO;
import com.smartindustry.common.util.NoUtil;

import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 19:52
 * @description: TODO
 * @version: 1.0
 */
public class ReceiptNoUtil {
    private static final String NO_HEAD = "CG";
    private static final int NUM_LEN = 5;

    private static NoUtil<ReceiptBodyPO, Long> util = new NoUtil<>();

    /**
     * 获取当前编号值
     *
     * @param mapper
     * @return
     */
    public static int getReceiptNum(ReceiptBodyMapper mapper, Date date) {
        ReceiptBodyPO po = util.getPO(mapper, NO_HEAD, date);
        if (null == po) {
            return 0;
        }

        return util.getNum(po.getReceiptNo(), NUM_LEN);
    }

    /**
     * 生成编号
     *
     * @param num
     * @return
     */
    public static String genReceiptNo(Date date, int num) {
        return util.genNum(NO_HEAD, date, num, NUM_LEN);
    }
}
