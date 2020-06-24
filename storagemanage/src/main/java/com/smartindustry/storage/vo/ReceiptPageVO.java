package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.ReceiptBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 15:32
 * @description: 收料单分页VO
 * @version: 1.0
 */
@Data
public class ReceiptPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收料ID
     */
    private Long rId;
    /**
     * 收料单号
     */
    private String rNo;
    /**
     * 物料状态
     */
    private Byte status;
    /**
     * 来源单号：（PO号、样品采购单号、对应工单号）
     */
    private String oNo;
    /**
     * 物料编码
     */
    private String mNo;
    /**
     * 物料类型
     */
    private String mType;
    /**
     * 物料描述
     */
    private String mDesc;
    /**
     * 订单总数
     */
    private Integer oTotal;
    /**
     * 采购日期
     */
    private Date oDate;
    /**
     * 供应商名称
     */
    private String supplier;
    /**
     * 采购员
     */
    private String buyer;
    /**
     * 计划到货时间
     */
    private Date pDate;
    /**
     * 接受数量
     */
    private Integer aNum;
    /**
     * 收料日期
     */
    private Date aDate;
    /**
     * 实际入库数
     */
    private Integer sNum;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<ReceiptPageVO> convert(List<ReceiptBO> bos) {
        List<ReceiptPageVO> vos = new ArrayList<>();
        for (ReceiptBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static ReceiptPageVO convert(ReceiptBO bo) {
        ReceiptPageVO vo = new ReceiptPageVO();

        vo.setRId(bo.getReceiptBodyId());
        vo.setRNo(bo.getReceiptNo());
        vo.setStatus(bo.getStatus());
        vo.setONo(bo.getOrderNo());
        vo.setMNo(bo.getMaterialNo());
        vo.setANum(bo.getAcceptNum());
        vo.setADate(bo.getAcceptDate());
        vo.setSNum(bo.getStockNum());

        return vo;
    }
}
