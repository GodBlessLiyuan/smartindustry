package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.CurrencyPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 15:45
 * @description: 币种
 * @version: 1.0
 */
@Data
public class CurrencyDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long cid;
    private String cname;

    public static CurrencyPO createPO(CurrencyDTO dto, Long userId) {
        CurrencyPO po = new CurrencyPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static CurrencyPO buildPO(CurrencyPO po, CurrencyDTO dto) {
        po.setCurrencyName(dto.getCname());
        return po;
    }
}
