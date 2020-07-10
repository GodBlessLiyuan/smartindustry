package com.smartindustry.storage.dto;

import com.smartindustry.common.pojo.si.PrintLabelPO;
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
     * 物料编码
     */
    private String mno;

    /**
     * 扫描条形码
     */
    private String scode;
    /**
     * 生产日期
     */
    private String pdate;
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
     * @return
     */
    public static PrintLabelPO createPO(PrintLabelDTO dto, int num, Byte origin) {
        PrintLabelPO po = new PrintLabelPO();
//        po.setReceiptBodyId(dto.getRbid());
        po.setPackageId(ReceiptNoUtil.genLabelNo(null, new Date(), num));
        po.setProduceDate(dto.getPdate());
        po.setProduceBatch(dto.getPbatch());
        po.setNum(dto.getMnum());
        po.setOrigin(origin);
        po.setMaterialNo(dto.getMno());
        po.setCreateTime(new Date());
        po.setDr((byte) 1);

        return po;
    }
}
