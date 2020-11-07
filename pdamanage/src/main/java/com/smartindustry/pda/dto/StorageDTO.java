package com.smartindustry.pda.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 17:02 2020/11/2
 * @ Description：pda操作
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class StorageDTO implements Serializable {
        private static final long SerialVersionUID = 1L;

        /**
         * 类别类型：1-待执行，2-执行中，3-已完成，4-为完成生产
         */
        private Byte type;
        /**入库单表头ID
         *
         */
        private Long shid;
        /**
         * 物料砧板RFID
         */
        private String mrfid;
        /**
         * 库位RFID
         */
        private String lrfid;
}
