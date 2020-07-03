package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/3 9:41
 * @description: 物料入库 DTO
 * @version: 1.0
 */
@Data
public class MaterialStorageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物料入库单ID
     */
    private Long sid;
    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 良品类型：1 良品； 2 非良品
     */
    private Byte type;
    /**
     * 入库详情
     */
    List<StorageDetailDTO> detail;

    @Data
    public static class StorageDetailDTO {
        /**
         * 库位编号
         */
        private String lno;
        /**
         * 打印标签IDs
         */
        private List<Long> plids;
    }
}
