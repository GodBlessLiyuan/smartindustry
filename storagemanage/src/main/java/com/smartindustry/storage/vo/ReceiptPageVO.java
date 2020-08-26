package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.ReceiptBO;
import com.smartindustry.common.util.DateUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

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
     * 收料表体ID
     */
    private Long rbid;
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
     * 物料ID
     */
    private Long mid;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料名称
     */
    private String mname;
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
    private String odate;
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
    private String pdate;
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
        List<ReceiptPageVO> vos = new ArrayList<>(bos.size());
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

        vo.setRbid(bo.getReceiptBodyId());
        vo.setRno(bo.getReceiptNo());
        vo.setStatus(bo.getStatus());
        vo.setOno(bo.getSourceNo());
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMtype(bo.getMaterialType());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setOtotal(bo.getOrderTotal());
        if(!StringUtils.isEmpty(bo.getOrderDate())) {
            vo.setOdate(DateUtil.date2Str(bo.getOrderDate(), DateUtil.Y_M_D));
        }
        vo.setSupplier(bo.getSupplier());
        vo.setBuyer(bo.getBuyer());
        if(!StringUtils.isEmpty(bo.getPlanDate())) {
            vo.setPdate(DateUtil.date2Str(bo.getPlanDate(), DateUtil.Y_M_D));
        }
        vo.setAnum(bo.getAcceptNum());
        vo.setAdate(bo.getAcceptDate());
        vo.setSnum(bo.getStockNum());

        return vo;
    }
}
