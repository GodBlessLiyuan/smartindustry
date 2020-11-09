package com.smartindustry.common.bo.sm.bo.ds;

import com.smartindustry.common.pojo.ds.sqlserver.PurchaseDetailErpPO;
import com.smartindustry.common.pojo.ds.sqlserver.PurchaseErpPO;
import lombok.Data;

import java.util.List;

/**
 * 采购到货
 * @author 
 */
@Data
public class PurchaseErpBO extends PurchaseErpPO {
    List<PurchaseDetailErpPO> pdpos;

}