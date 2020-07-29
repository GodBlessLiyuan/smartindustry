package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.si.WarehouseRecordPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 15:29
 * @description: 仓库记录
 * @version: 1.0
 */
@Data
public class WarehouseRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String name;
    private Date ctime;
    private String type;

    public static List<WarehouseRecordVO> convert(List<WarehouseRecordPO> pos) {
        List<WarehouseRecordVO> vos = new ArrayList<>();
        for (WarehouseRecordPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static WarehouseRecordVO convert(WarehouseRecordPO po) {
        WarehouseRecordVO vo = new WarehouseRecordVO();
        vo.setName("夏慧");
        vo.setCtime(po.getCreateTime());
        vo.setType(po.getType());
        return vo;
    }
}
