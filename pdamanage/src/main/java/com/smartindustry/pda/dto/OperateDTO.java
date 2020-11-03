package com.smartindustry.pda.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:11 2020/10/27
 * @version: 1.0.0
 * @description:
 */
@Data
public class OperateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 生产单id
     */
    private Long poid;

    /**
     * 表体的批量删除
     */
    private List<Long> sbids;

    /**
     * 采购拣货单的表头id
     */
    private Long shid;

    /**
     * 出库id
     */
    private Long ohid;
}
