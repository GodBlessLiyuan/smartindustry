package com.smartindustry.inventory.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/13 14:54
 * @description: 安全库存 DTO
 * @version: 1.0
 */
@Data
public class SafeStockDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<MaterialInventoryDTO> mi;
    private Integer llimit;
    private Byte way;

    @Data
    public static class MaterialInventoryDTO {
        private Long ssid;
        private Long miid;
        private Long mid;
    }
}
