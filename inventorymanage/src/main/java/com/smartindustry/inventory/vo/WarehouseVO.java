package com.smartindustry.inventory.vo;

import com.smartindustry.common.pojo.si.WarehousePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:55 2020/11/12
 * @version: 1.0.0
 * @description:
 */
@Data
public class WarehouseVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 仓库id
     */
    private Long wid;
    /**
     * 仓库名称
     */
    private String wname;

    public static List<WarehouseVO> convert(List<WarehousePO> pos) {
        List<WarehouseVO> vos = new ArrayList<>(pos.size());
        for (WarehousePO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static WarehouseVO convert(WarehousePO po) {
        WarehouseVO vo = new WarehouseVO();
        vo.setWid(po.getWarehouseId());
        vo.setWname(po.getWarehouseName());
        return vo;
    }
}
