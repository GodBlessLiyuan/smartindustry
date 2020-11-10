package com.smartindustry.common.bo.im;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/12 11:23
 * @description: 物料库存信息
 * @version: 1.0
 */
@Data
public class MaterialInventoryBO extends MaterialInventoryPO {
    private String materialNo;
    private String materialName;
    private Byte materialType;
    private String materialModel;
    private String materialDesc;
    private String supplierName;
    private List<LocationBO> locations;
    private Long safeStockId;
    private BigDecimal lowerLimit;
    private Byte way;
    private Integer availableNum;
    private String measureUnitName;

    public MaterialInventoryPO updatePO(MaterialInventoryPO po) {
        po.setMaterialInventoryId(this.getMaterialInventoryId());
        po.setMaterialId(this.getMaterialId());
        po.setWayNum((null != this.getWayNum() ? this.getWayNum() : 0) + (null != po.getWayNum() ? po.getWayNum() : 0));
        po.setStorageNum((null != this.getStorageNum() ? this.getStorageNum() : 0) + (null != po.getStorageNum() ? po.getStorageNum() : 0));
        po.setLockNum((null != this.getLockNum() ? this.getLockNum() : 0) + (null != po.getLockNum() ? po.getLockNum() : 0));
        po.setRelateNum((null != this.getRelateNum() ? this.getRelateNum() : 0) + (null != po.getRelateNum() ? po.getRelateNum() : 0));
        po.setAvailableNum(po.getStorageNum() - po.getLockNum() - po.getRelateNum());
        if (this.lowerLimit != null && this.lowerLimit.compareTo(BigDecimal.ZERO) != 0) {
            int warnNum = null != this.way && 1 == this.way ? po.getStorageNum() + po.getWayNum() : po.getStorageNum();
            po.setStatus((byte) (this.lowerLimit.compareTo(new BigDecimal(warnNum)) > 0 ? 2 : 1));
        } else {
            po.setStatus((byte) 1);
        }

        return po;
    }
}
