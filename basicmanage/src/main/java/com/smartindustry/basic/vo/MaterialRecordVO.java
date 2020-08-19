package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.MaterialRecordPO;
import com.smartindustry.common.util.ServletUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/3 8:46
 * @description: 物料记录
 * @version: 1.0
 */
@Data
public class MaterialRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String name;
    private Date ctime;
    private String type;

    public static List<MaterialRecordVO> convert(List<MaterialRecordPO> pos) {
        List<MaterialRecordVO> vos = new ArrayList<>(pos.size());
        for (MaterialRecordPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static MaterialRecordVO convert(MaterialRecordPO po) {
        UserPO user = ServletUtil.getUserBO().getUser();
        MaterialRecordVO vo = new MaterialRecordVO();
        vo.setName(user.getName());
        vo.setCtime(po.getCreateTime());
        vo.setType(po.getType());
        return vo;
    }
}
