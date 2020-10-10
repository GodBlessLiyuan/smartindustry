package com.smartindustry.storage.vo;

import com.smartindustry.common.pojo.si.WarehousePO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hui.feng
 * @date created in 2020/10/10
 * @description
 */
@Data
public class WarehouseVO implements Serializable {

    private String wno;

    private Long whid;

    private String wname;

    /**
     * 仓库是否有库位 true 有 false 没有
     */
    private Boolean flag;

    public static WarehouseVO convert(WarehousePO po) {
        WarehouseVO vo = new WarehouseVO();
        vo.setWhid(po.getWarehouseId());
        vo.setWno(po.getWarehouseNo());
        vo.setWname(po.getWarehouseName());
        return vo;
    }

}
