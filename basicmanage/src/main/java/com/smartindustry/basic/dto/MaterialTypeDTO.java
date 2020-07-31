package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.MaterialTypePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/31 9:37
 * @description: TODO
 * @version: 1.0
 */
@Data
public class MaterialTypeDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mtid;
    private String mtname;

    public static MaterialTypePO createPO(MaterialTypeDTO dto, Long userId) {
        MaterialTypePO po = new MaterialTypePO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static MaterialTypePO buildPO(MaterialTypePO po, MaterialTypeDTO dto) {
        po.setMaterialTypeName(dto.getMtname());
        return po;
    }
}
