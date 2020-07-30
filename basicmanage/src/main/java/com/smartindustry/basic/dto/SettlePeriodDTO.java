package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.SettlePeriodPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:39
 * @description: 结算期限
 * @version: 1.0
 */
@Data
public class SettlePeriodDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long spid;
    private String spname;

    public static SettlePeriodPO createPO(SettlePeriodDTO dto, Long userId) {
        SettlePeriodPO po = new SettlePeriodPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static SettlePeriodPO buildPO(SettlePeriodPO po, SettlePeriodDTO dto) {
        po.setSettlePeriodName(dto.getSpname());
        return po;
    }
}
