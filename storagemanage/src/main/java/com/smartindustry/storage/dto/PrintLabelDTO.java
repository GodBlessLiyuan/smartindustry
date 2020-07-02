package com.smartindustry.storage.dto;

import com.smartindustry.common.pojo.PrintLabelPO;
import com.smartindustry.storage.constant.ReceiptConstant;
import com.smartindustry.storage.util.ReceiptNoUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 15:13
 * @description: 标签
 * @version: 1.0
 */
@Data
public class PrintLabelDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 收料单ID
     */
    private Long rbid;

    /**
     * 扫描条形码
     */
    private String scode;
    /**
     * 生产日期
     */
    private Date pdate;
    /**
     * 生产批号
     */
    private String pbatch;
    /**
     * 最小包装物料数
     */
    private Integer mnum;
    /**
     * 最小包装打印份数
     */
    private Integer pnum;

    /**
     * 创建 po
     *
     * @param dto
     * @param num
     * @param date
     * @return
     */
    public static PrintLabelPO createPO(PrintLabelDTO dto, int num, Date date, Byte origin) {
        PrintLabelPO po = new PrintLabelPO();
        po.setReceiptBodyId(dto.getRbid());
        po.setPackageId(ReceiptNoUtil.genLabelNo(null, date, num));
        po.setProduceDate(dto.getPdate());
        po.setProduceBatch(dto.getPbatch());
        po.setNum(dto.getMnum());
        po.setOrigin(origin);
        po.setAddTime(date);
        po.setStatus(ReceiptConstant.LABEL_STORAGE_PENDING);
        po.setDr((byte) 1);

        return po;
    }
}
