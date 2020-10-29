package com.smartindustry.storage.util;

import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
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
    public static final String OUTBOUND_HEAD_YP = "GDCH";
    private static final int NUM_LEN = 5;

    private static NoUtil<StorageHeadPO, Long> storageUtil = new NoUtil<>();
    private static NoUtil<OutboundHeadPO, Long> outboundheadUtil = new NoUtil<>();

    /**
     * 生成表头编号
     *
     * @param mapper
     * @param head
     * @param date
     * @return
     */
    public static String genStorageHeadNo(StorageHeadMapper mapper, String head, Date date) {
        StorageHeadPO po = storageUtil.getPO(mapper, head, date);
        return storageUtil.genNum(head, date, null == po ? 1 : storageUtil.getNum(po.getStorageNo(), NUM_LEN) + 1, NUM_LEN);
    }

    /**
     * 生成表头编号
     *
     * @param mapper
     * @param head
     * @param date
     * @return
     */
    public static String genOutboundHeadNo(OutboundHeadMapper mapper, String head, Date date) {
        OutboundHeadPO po = outboundheadUtil.getPO(mapper, head, date);
        return outboundheadUtil.genNum(head, date, null == po ? 1 : outboundheadUtil.getNum(po.getOutboundNo(), NUM_LEN) + 1, NUM_LEN);
    }
}
