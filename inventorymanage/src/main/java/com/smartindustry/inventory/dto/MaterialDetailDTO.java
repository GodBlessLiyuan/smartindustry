package com.smartindustry.inventory.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/14 11:06
 * @description: 物料明细
 * @version: 1.0
 */
@Data
public class MaterialDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Long> slids;
    private Long mlid;
}
