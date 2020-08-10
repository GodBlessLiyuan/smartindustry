package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.SupplierPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 9:32
 * @description: 供应商 VO
 * @version: 1.0
 */
@Data
public class SupplierBO extends SupplierPO {
    private String supplierGroupName;
    private String certStatusName;
    private String supplierTypeName;
    private String settlePeriodName;
    private String currencyName;
}
