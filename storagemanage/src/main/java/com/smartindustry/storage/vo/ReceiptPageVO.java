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
    private Long rid;
    /**
     * 收料单号
     */
    private String rno;
    /**
     * 物料状态
     */
    private Byte status;
    /**
     * 来源单号：（PO号、样品采购单号、对应工单号）
     */
    private String ono;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料类型
     */
    private Byte mtype;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 订单总数
     */
    private Integer ototal;
    /**
     * 采购日期
     */
    private Date odate;
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
    private Date pdate;
    /**
     * 接受数量
     */
    private Integer anum;
    /**
     * 收料日期
     */
    private Date adate;
    /**
     * 实际入库数
     */
    private Integer snum;

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

        vo.setRid(bo.getReceiptBodyId());
        vo.setRno(bo.getReceiptNo());
        vo.setStatus(bo.getStatus());
        vo.setOno(bo.getOrderNo());
        vo.setMno(bo.getMaterialNo());
        vo.setMtype(bo.getMaterialType());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setOtotal(bo.getOrderTotal());
        vo.setOdate(bo.getOrderDate());
        vo.setSupplier(bo.getSupplier());
        vo.setBuyer(bo.getBuyer());
        vo.setPdate(bo.getPlanDate());
        vo.setAnum(bo.getAcceptNum());
        vo.setAdate(bo.getAcceptDate());
        vo.setSnum(bo.getStockNum());

        return vo;
    }
}
