package com.smartindustry.storage.dto;

import com.smartindustry.common.pojo.ReceiptHeadPO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/6/24 11:11
 * @description: 物流
 * @version: 1.0
 */
@Data
public class LogisticsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long rbid;
    private String company;
    private String no;
    private Byte way;
    private String remark;

    /**
     * dto 转 po
     *
     * @param po
     * @param dto
     * @return
     */
    public static ReceiptHeadPO buildPO(ReceiptHeadPO po, LogisticsDTO dto) {
        po.setLogisticsCompany(dto.getCompany());
        po.setLogisticsNo(dto.getNo());
        po.setReceiptWay(dto.getWay());
        po.setRemark(dto.getRemark());
        return po;
    }
}
