package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.MaterialLevelPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/31 9:37
 * @description: 物料层级
 * @version: 1.0
 */
@Data
public class MaterialLevelDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mlid;
    private String mlname;

    public static MaterialLevelPO createPO(MaterialLevelDTO dto, Long userId) {
        MaterialLevelPO po = new MaterialLevelPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static MaterialLevelPO buildPO(MaterialLevelPO po, MaterialLevelDTO dto) {
        po.setMaterialLevelName(dto.getMlname());
        return po;
    }
}
