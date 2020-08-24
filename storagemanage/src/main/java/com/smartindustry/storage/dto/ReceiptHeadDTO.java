package com.smartindustry.storage.dto;

import com.smartindustry.common.mapper.sm.ReceiptHeadMapper;
import com.smartindustry.common.pojo.sm.ReceiptHeadPO;
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
    private String ono;
    /**
     * 来源订单类型
     */
    private Byte otype;
    /**
     * 采购日期
     */
    private Date odate;
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
    private Date pdate;
    /**
     * 物流公司
     */
    private String loco;
    /**
     * 物流单号
     */
    private String lono;
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
        if (StringUtils.isEmpty(dto.getOno())) {
            po.setSourceNo(ReceiptNoUtil.genReceiptHeadNo(mapper, ReceiptNoUtil.RECEIPT_HEAD_YP, new Date()));
        } else {
            po.setSourceNo(dto.getOno());
        }
        po.setSourceType(dto.getOtype());
        po.setOrderDate(dto.getOdate());
        po.setSupplier(dto.getSupplier());
        po.setBuyer(dto.getBuyer());
        po.setPlanDate(dto.getPdate());
        po.setLogisticsCompany(dto.getLoco());
        po.setLogisticsNo(dto.getLono());
        po.setReceiptWay(dto.getWay());
        po.setRemark(dto.getRemark());
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return po;
    }
}
