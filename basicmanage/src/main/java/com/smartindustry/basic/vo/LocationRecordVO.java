package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.LocationRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 9:05
 * @description: 库位记录
 * @version: 1.0
 */
@Data
public class LocationRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String name;
    private Date ctime;
    private String type;

    public static List<LocationRecordVO> convert(List<LocationRecordBO> bos) {
        List<LocationRecordVO> vos = new ArrayList<>(bos.size());
        for (LocationRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static LocationRecordVO convert(LocationRecordBO bo) {
        LocationRecordVO vo = new LocationRecordVO();
        vo.setName(bo.getName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
