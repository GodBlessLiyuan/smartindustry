package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.ProduceLossLevelPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/31 9:38
 * @description: 生产损耗等级
 * @version: 1.0
 */
@Data
public class ProduceLossLevelDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long pllid;
    private String pllname;

    public static ProduceLossLevelPO createPO(ProduceLossLevelDTO dto, Long userId) {
        ProduceLossLevelPO po = new ProduceLossLevelPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static ProduceLossLevelPO buildPO(ProduceLossLevelPO po, ProduceLossLevelDTO dto) {
        po.setProduceLossLevelName(dto.getPllname());
        return po;
    }
}
