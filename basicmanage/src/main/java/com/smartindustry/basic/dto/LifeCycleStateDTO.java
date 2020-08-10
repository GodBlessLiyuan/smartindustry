package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.LifeCycleStatePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/31 9:38
 * @description: 生命周期状态
 * @version: 1.0
 */
@Data
public class LifeCycleStateDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long lcsid;
    private String lcsname;

    public static LifeCycleStatePO createPO(LifeCycleStateDTO dto, Long userId) {
        LifeCycleStatePO po = new LifeCycleStatePO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static LifeCycleStatePO buildPO(LifeCycleStatePO po, LifeCycleStateDTO dto) {
        po.setLifeCycleStateName(dto.getLcsname());
        return po;
    }
}
