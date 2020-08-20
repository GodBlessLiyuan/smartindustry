package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.MaterialRecordBO;
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

    public static List<MaterialRecordVO> convert(List<MaterialRecordBO> bos) {
        List<MaterialRecordVO> vos = new ArrayList<>(bos.size());
        for (MaterialRecordBO po : bos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static MaterialRecordVO convert(MaterialRecordBO bo) {
        MaterialRecordVO vo = new MaterialRecordVO();
        vo.setName(bo.getName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
