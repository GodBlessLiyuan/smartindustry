package com.smartindustry.common.bo.sm.bo.ds;

import com.smartindustry.common.pojo.ds.sqlserver.SaleDetailErpPO;
import com.smartindustry.common.pojo.ds.sqlserver.SaleOutboundErpPO;
import lombok.Data;

import java.util.List;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@Data
public class SaleOutboundErpBO extends SaleOutboundErpPO {
    private List<SaleDetailErpPO> sdpos;
}
