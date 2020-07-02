package com.smartindustry.storage.util;

import com.smartindustry.common.mapper.MaterialStorageMapper;
import com.smartindustry.common.mapper.PrintLabelMapper;
import com.smartindustry.common.mapper.ReceiptBodyMapper;
import com.smartindustry.common.mapper.ReceiptHeadMapper;
import com.smartindustry.common.pojo.MaterialStoragePO;
import com.smartindustry.common.pojo.PrintLabelPO;
import com.smartindustry.common.pojo.ReceiptBodyPO;
import com.smartindustry.common.pojo.ReceiptHeadPO;
import com.smartindustry.common.util.NoUtil;

import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 19:52
 * @description: 收料编号生成工具
 * @version: 1.0
 */
public class ReceiptNoUtil {
    public static final String RECEIPT_HEAD_YP = "YPCG";

    public static final String RECEIPT_BODY_PO = "CG";
    public static final String RECEIPT_BODY_YP = "YP";
    public static final String RECEIPT_BODY_RP = "RP";
    public static final String MATERIAL_STORAGE_LPPK = "LPRK";
    public static final String MATERIAL_STORAGE_BLPK = "BLRK";
    private static final int NUM_LEN = 5;

    private static NoUtil<ReceiptHeadPO, Long> headUtil = new NoUtil<>();
    private static NoUtil<ReceiptBodyPO, Long> bodyUtil = new NoUtil<>();
    private static NoUtil<PrintLabelPO, Long> labelUtil = new NoUtil<>();
    private static NoUtil<MaterialStoragePO, Long> storageUtil = new NoUtil<>();

    /**
     * 生成表头编号
     *
     * @param mapper
     * @param head
     * @param date
     * @return
     */
    public static String genReceiptHeadNo(ReceiptHeadMapper mapper, String head, Date date) {
        ReceiptHeadPO po = headUtil.getPO(mapper, head, date);
        return headUtil.genNum(head, date, null == po ? 1 : headUtil.getNum(po.getOrderNo(), NUM_LEN) + 1, NUM_LEN);
    }

    /**
     * 生成表体编号
     *
     * @param head
     * @param date
     * @param num
     * @return
     */
    public static String genReceiptBodyNo(String head, Date date, int num) {
        return bodyUtil.genNum(head, date, num, NUM_LEN);
    }

    /**
     * 获取表体当前编号值
     *
     * @param mapper
     * @return
     */
    public static int getReceiptBodyNum(ReceiptBodyMapper mapper, String head, Date date) {
        ReceiptBodyPO po = bodyUtil.getPO(mapper, head, date);
        if (null == po) {
            return 0;
        }

        return bodyUtil.getNum(po.getReceiptNo(), NUM_LEN);
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

    /**
     * 获取标签当前编号值
     *
     * @param mapper
     * @return
     */
    public static int getLabelNum(PrintLabelMapper mapper, String head, Date date) {
        PrintLabelPO po = labelUtil.getPO(mapper, head, date);
        if (null == po) {
            return 0;
        }

        return labelUtil.getNum(po.getPackageId(), NUM_LEN);
    }

    /**
     * 生成入库单编号
     *
     * @param head
     * @param date
     * @return
     */
    public static String genStorageNo(MaterialStorageMapper mapper, String head, Date date) {
        MaterialStoragePO po = storageUtil.getPO(mapper, head, date);
        return storageUtil.genNum(head, date, null == po ? 1 : storageUtil.getNum(po.getStorageNo(), NUM_LEN) + 1, NUM_LEN);
    }
}
