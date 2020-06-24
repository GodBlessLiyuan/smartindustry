package com.smartindustry.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartindustry.common.mapper.ReceiptHeadMapper;
import com.smartindustry.common.pojo.ReceiptHeadPO;
import com.smartindustry.storage.util.ReceiptNoUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 16:33
 * @description: 收料管理表头
 * @version: 1.0
 */
@Data
public class ReceiptHeadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 来源订单编号
     */
    @JsonProperty("oNo")
    private String oNo;
    /**
     * 来源订单类型
     */
    @JsonProperty("oType")
    private Byte oType;
    /**
     * 采购日期
     */
    @JsonProperty("oDate")
    private Date oDate;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 采购员
     */
    private String buyer;
    /**
     * 预计到货日期
     */
    @JsonProperty("pDate")
    private Date pDate;
    /**
     * 物流公司
     */
    private String loCo;
    /**
     * 物流单号
     */
    private String loNo;
    /**
     * 收货方式
     */
    private Byte way;
    /**
     * 备注
     */
    private String remark;

    /**
     * dto 转 po
     *
     * @param dto
     * @return
     */
    public static ReceiptHeadPO createPO(ReceiptHeadMapper mapper, ReceiptHeadPO po, ReceiptHeadDTO dto) {
        if (StringUtils.isEmpty(dto.getONo())) {
            po.setOrderNo(ReceiptNoUtil.genReceiptHeadNo(mapper, ReceiptNoUtil.RECEIPT_HEAD_YP, new Date()));
        } else {
            po.setOrderNo(dto.getONo());
        }
        po.setOrderType(dto.getOType());
        po.setOrderDate(dto.getODate());
        po.setSupplier(dto.getSupplier());
        po.setBuyer(dto.getBuyer());
        po.setArriveDate(dto.getPDate());
        po.setLogisticsCompany(dto.getLoCo());
        po.setLogisticsNo(dto.getLoNo());
        po.setReceiptWay(dto.getWay());
        po.setRemark(dto.getRemark());
        return po;
    }
}
