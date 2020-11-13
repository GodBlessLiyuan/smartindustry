package com.smartindustry.inventory.vo;

import com.smartindustry.common.bo.si.MaterialInventoryBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartindustry.inventory.constant.InventoryConstant.*;


/**
 * @author: jiangchaojie
 * @date: Created in 2020/11/11
 * @description: 物料库存统计
 * @version: 1.0
 */
@Data
public class MaterialInventoryVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 物料id
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
     * 物料型号
     */
    private String mmodel;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 供应商名称
     */
    private String sname;
    /**
     * 仓库名称
     */
    private String wname;
    /**
     * 安全库存上限
     */
    private BigDecimal upper;
    /**
     * 安全库存下限
     */
    private BigDecimal lower;
    /**
     * 库存状态
     */
    private Byte status;
    /**
     * 库存数量
     */
    private BigDecimal num;
    /**
     * 计量单位
     */
    private String muname;

    public static List<MaterialInventoryVO> convert(List<MaterialInventoryBO> bos, Map<BigInteger, Map<Long, BigDecimal>> map) {
        List<MaterialInventoryVO> vos = new ArrayList<>(bos.size());
        for (MaterialInventoryBO bo : bos) {
            vos.add(convert(bo,map));
        }
        return vos;
    }

    public static MaterialInventoryVO convert(MaterialInventoryBO bo,Map<BigInteger, Map<Long, BigDecimal>> map) {
        MaterialInventoryVO vo = new MaterialInventoryVO();
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMtype(bo.getMaterialType());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setSname(bo.getSupplierName());
        vo.setWname(bo.getWarehouseName());
        vo.setUpper(bo.getUpperLimit());
        vo.setLower(bo.getLowerLimit());
        if(bo.getInventoryQuantity()!=null && bo.getPackageVolume()!=null){
            vo.setNum(bo.getInventoryQuantity().multiply(bo.getPackageVolume()));
        }
        for(Map.Entry<BigInteger, Map<Long, BigDecimal>> entry : map.entrySet()){
            if(entry.getKey().longValue() == bo.getMaterialId() && bo.getPackageVolume()!=null){
                BigDecimal curNum = new BigDecimal(String.valueOf(entry.getValue().get(VALUE_FLAG))).multiply(bo.getPackageVolume());
                if(curNum.compareTo(bo.getLowerLimit()) == -1){
                    vo.setStatus(STATUS_LACK);
                }else if(curNum.compareTo(bo.getUpperLimit()) > -1){
                    vo.setStatus(STATUS_FULL);
                }else {
                    vo.setStatus(STATUS_ORDINARY);
                }
            }
        }
        vo.setMuname(bo.getMeasureUnitName());
        return vo;
    }
}
