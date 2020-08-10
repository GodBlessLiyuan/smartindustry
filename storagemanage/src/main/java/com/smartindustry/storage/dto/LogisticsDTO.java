package com.smartindustry.storage.dto;

import com.smartindustry.common.pojo.sm.ReceiptHeadPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/24 11:11
 * @description: 物流
 * @version: 1.0
 */
@Data
public class LogisticsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 物流公司名称
     */
    private String company;
    /**
     * 物流单号
     */
    private String no;
    /**
     * 收货方式
     */
    private Byte way;
    /**
     * 备注（采购员等）
     */
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
        po.setUpdateTime(new Date());
        return po;
    }
}
