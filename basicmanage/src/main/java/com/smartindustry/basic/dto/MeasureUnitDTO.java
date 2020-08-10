package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.MeasureUnitPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/31 9:37
 * @description: 计量单位
 * @version: 1.0
 */
@Data
public class MeasureUnitDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long muid;
    private String muname;

    public static MeasureUnitPO createPO(MeasureUnitDTO dto, Long userId) {
        MeasureUnitPO po = new MeasureUnitPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static MeasureUnitPO buildPO(MeasureUnitPO po, MeasureUnitDTO dto) {
        po.setMeasureUnitName(dto.getMuname());
        return po;
    }
}