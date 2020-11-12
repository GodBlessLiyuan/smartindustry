package com.smartindustry.inventory.dto;

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
     * 入库仓库id
     */
    private Long wid;


}
