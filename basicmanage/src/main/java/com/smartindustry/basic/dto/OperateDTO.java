package com.smartindustry.basic.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:32
 * @description: 操作 DTO
 * @version: 1.0
 */
@Data
public class OperateDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long wid;
    private Long lid;
    private Long sid;
    private Long mid;
    private Long bhid;

    /**
     * 物料属性名称
     */
    private String mpname;
    /**
     * 物料工序名称
     */
    private String prname;

    /**
     * 批量删除
     */
    private List<Long> bhids;
}
