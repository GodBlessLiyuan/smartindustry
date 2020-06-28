package com.smartindustry.storage.dto;

import com.smartindustry.common.pojo.PrintLabelPO;
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
    private Long rbId;
    /**
     * 来源方式
     */
    private Byte origin;

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
    public static PrintLabelPO createPO(PrintLabelDTO dto, int num, Date date) {
        PrintLabelPO po = new PrintLabelPO();
        po.setReceiptBodyId(dto.getRbId());
        po.setPackageId(ReceiptNoUtil.genLabelNo(null, date, num));
        po.setProduceDate(dto.getPdate());
        po.setProduceBatch(dto.getPbatch());
        po.setNum(dto.getMnum());
        po.setOrigin(dto.getOrigin());
        po.setAddTime(date);
        po.setStatus((byte) 1);
        po.setDr((byte) 1);

        return po;
    }
}