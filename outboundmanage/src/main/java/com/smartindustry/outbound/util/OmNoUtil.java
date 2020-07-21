package com.smartindustry.outbound.util;

import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.util.NoUtil;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:10 2020/7/21
 * @version: 1.0.0
 * @description:
 */
public class OmNoUtil {
    private static final int NUM_LEN = 5;
    private static NoUtil<PrintLabelPO, Long> labelUtil = new NoUtil<>();
    public static int getLabelNum(PrintLabelMapper mapper, String head, Date date) {
        PrintLabelPO po = labelUtil.getPO(mapper, head, date);
        if (null == po) {
            return 0;
        }

        return labelUtil.getNum(po.getPackageId(), NUM_LEN);
    }

    /**
     * 生成标签编号
     *
     * @param head
     * @param date
     * @param num
     * @return
     */
    public static String genLabelNo(String head, Date date, int num) {
        return labelUtil.genNum(head, date, num, NUM_LEN);
    }
}
