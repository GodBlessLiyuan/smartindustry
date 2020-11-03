package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.SupplierRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 11:12
 * @description: 供应商管理
 * @version: 1.0
 */
@Data
public class SupplierRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String name;
    private Date ctime;
    private String type;

    public static List<SupplierRecordVO> convert(List<SupplierRecordBO> bos) {
        List<SupplierRecordVO> vos = new ArrayList<>(bos.size());
        for (SupplierRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static SupplierRecordVO convert(SupplierRecordBO bo) {
        SupplierRecordVO vo = new SupplierRecordVO();
        vo.setName(bo.getName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
