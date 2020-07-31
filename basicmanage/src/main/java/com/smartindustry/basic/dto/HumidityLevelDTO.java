package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.HumidityLevelPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/31 9:37
 * @description: 温度等级
 * @version: 1.0
 */
@Data
public class HumidityLevelDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long hlid;
    private String hlname;

    public static HumidityLevelPO createPO(HumidityLevelDTO dto, Long userId) {
        HumidityLevelPO po = new HumidityLevelPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static HumidityLevelPO buildPO(HumidityLevelPO po, HumidityLevelDTO dto) {
        po.setHumidityLevelName(dto.getHlname());
        return po;
    }
}
