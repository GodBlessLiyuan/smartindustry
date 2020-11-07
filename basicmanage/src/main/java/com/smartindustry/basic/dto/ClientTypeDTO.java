package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.ClientTypePO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:08 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class ClientTypeDTO {
    private static final long SerialVersionUID = 1L;

    /**
     * 客户类型名称
     */
    private String ctname;

    public static ClientTypePO createPO(ClientTypeDTO dto) {
        ClientTypePO po = new ClientTypePO();
        po.setClientTypeName(dto.getCtname());
        po.setUserId(1L);
        po.setCreateTime(new Date());
        return po;
    }
}
