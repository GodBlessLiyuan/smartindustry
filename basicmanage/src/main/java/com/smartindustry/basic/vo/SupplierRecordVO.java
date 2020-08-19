package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.SupplierRecordPO;
import com.smartindustry.common.util.ServletUtil;
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

    public static List<SupplierRecordVO> convert(List<SupplierRecordPO> pos) {
        List<SupplierRecordVO> vos = new ArrayList<>(pos.size());
        for (SupplierRecordPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static SupplierRecordVO convert(SupplierRecordPO po) {
        UserPO user = ServletUtil.getUserBO().getUser();
        SupplierRecordVO vo = new SupplierRecordVO();
        vo.setName(user.getName());
        vo.setCtime(po.getCreateTime());
        vo.setType(po.getType());
        return vo;
    }
}
