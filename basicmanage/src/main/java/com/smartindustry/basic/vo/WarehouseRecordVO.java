package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.WarehouseRecordBO;
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

    public static List<WarehouseRecordVO> convert(List<WarehouseRecordBO> bos) {
        List<WarehouseRecordVO> vos = new ArrayList<>(bos.size());
        for (WarehouseRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static WarehouseRecordVO convert(WarehouseRecordBO bo) {
        WarehouseRecordVO vo = new WarehouseRecordVO();
        vo.setName(bo.getName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
