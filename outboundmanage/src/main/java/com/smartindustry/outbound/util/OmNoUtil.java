package com.smartindustry.outbound.util;

import com.smartindustry.common.mapper.om.OutboundMapper;
import com.smartindustry.common.mapper.si.PrintLabelMapper;
import com.smartindustry.common.mapper.sm.StorageMapper;
import com.smartindustry.common.pojo.om.OutboundPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.sm.StoragePO;
import com.smartindustry.common.util.NoUtil;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:10 2020/7/21
 * @version: 1.0.0
 * @description:
 */
public class OmNoUtil {

    public static final String MATERIAL_STORAGE_QTCK = "QTCK";
    public static final String OUTBOUND = "GDCH";
    private static final int NUM_LEN = 5;
    private static NoUtil<OutboundPO, Long> outboundUtil = new NoUtil<>();
    private static NoUtil<PrintLabelPO, Long> labelUtil = new NoUtil<>();
    private static NoUtil<StoragePO, Long> storageUtil = new NoUtil<>();
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


    public static String getOutboundNo(OutboundMapper mapper, String head, Date date) {
        OutboundPO po = outboundUtil.getPO(mapper, head, date);
        return outboundUtil.genNum(head, date, null == po ? 1 : outboundUtil.getNum(po.getOutboundNo(), NUM_LEN) + 1, NUM_LEN);
    }

    /**
     * 生成入库单编号
     *
     * @param head
     * @param date
     * @return
     */
    public static String genStorageNo(StorageMapper mapper, String head, Date date) {
        StoragePO po = storageUtil.getPO(mapper, head, date);
        return storageUtil.genNum(head, date, null == po ? 1 : storageUtil.getNum(po.getStorageNo(), NUM_LEN) + 1, NUM_LEN);
    }
}
