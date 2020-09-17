package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.CreditLevelPO;
import com.smartindustry.common.pojo.dd.ProcessPO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:01 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class CreditLevelDTO {
    private static final long SerialVersionUID = 1L;
    /**
     * 信用等级
     */
    private String clname;


    public static CreditLevelPO createPO(CreditLevelDTO dto) {
        CreditLevelPO po = new CreditLevelPO();
        po.setCreditLevelName(dto.getClname());
        po.setUserId(1L);
        po.setCreateTime(new Date());
        return po;
    }
}
