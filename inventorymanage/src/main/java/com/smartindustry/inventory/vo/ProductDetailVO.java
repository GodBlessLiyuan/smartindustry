package com.smartindustry.inventory.vo;

import com.smartindustry.common.bo.si.ProductDetailBO;
import com.smartindustry.inventory.constant.InventoryConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:19 2020/11/11
 * @version: 1.0.0
 * @description:
 */
@Data
public class ProductDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 栈板rfid
     */
    private String rfid;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料型号
     */
    private String mmodel;
    /**
     * 物料批次
     */
    private String mbatch;
    /**
     * 入库时间
     */
    private Date stime;
    /**
     * 物料状态
     */
    private Byte mstatus;
    /**
     * 锁定原因
     */
    private String reason;
    /**
     * 当前状态
     */
    private Byte cstatus;
    /**
     * 所在仓库
     */
    private String wname;
    /**
     * 所在货位
     */
    private String lno;
    /**
     * 物料数量
     */
    private BigDecimal num;
    /**
     * 计量单位
     */
    private String muname;

    public static List<ProductDetailVO> convert(List<ProductDetailBO> bos) {
        List<ProductDetailVO> vos = new ArrayList<>(bos.size());
        for (ProductDetailBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static ProductDetailVO convert(ProductDetailBO bo) {
        ProductDetailVO vo = new ProductDetailVO();
        vo.setRfid(bo.getRfid());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMbatch(bo.getMaterialBatch());
        vo.setStime(bo.getStorageTime());
        vo.setMstatus(bo.getMaterialStatus());
        if(bo.getMaterialStatus().equals(InventoryConstant.STATUS_NORMAL)){
            vo.setReason(InventoryConstant.TEXT_NULL);
            vo.setCstatus(InventoryConstant.STATUS_NORMAL);
        }else {
            vo.setReason(InventoryConstant.TEXT_WORKING);
            vo.setCstatus(InventoryConstant.STATUS_LOCK);
        }
        vo.setWname(bo.getWarehouseName());
        vo.setLno(bo.getLocationNo());
        vo.setMuname(bo.getMeasureUnitName());
        vo.setNum(bo.getNum());
        return vo;
    }

}
