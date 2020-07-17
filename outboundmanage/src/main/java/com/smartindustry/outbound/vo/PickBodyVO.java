package com.smartindustry.outbound.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/17 10:34
 * @description: 拣货单表体
 * @version: 1.0
 */
@Data
public class PickBodyVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 拣货单表体ID
     */
    private Long pbid;
    /**
     * PID
     */
    private String pid;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 数量
     */
    private Integer num;
}
