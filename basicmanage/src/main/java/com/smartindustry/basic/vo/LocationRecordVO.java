package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.LocationRecordPO;
import com.smartindustry.common.util.ServletUtil;
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

    public static List<LocationRecordVO> convert(List<LocationRecordPO> pos) {
        List<LocationRecordVO> vos = new ArrayList<>(pos.size());
        for (LocationRecordPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static LocationRecordVO convert(LocationRecordPO po) {
        UserPO user = ServletUtil.getUserBO().getUser();
        LocationRecordVO vo = new LocationRecordVO();
        vo.setName(user.getName());
        vo.setCtime(po.getCreateTime());
        vo.setType(po.getType());
        return vo;
    }
}
