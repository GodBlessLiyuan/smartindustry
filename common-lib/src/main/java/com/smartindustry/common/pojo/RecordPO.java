package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * sm_record
 *
 * @author
 */
@AllArgsConstructor
@Data
public class RecordPO implements Serializable {
    private Long recordId;

    private Long receiptBodyId;

    private Long userId;

    private String name;

    private String type;

    private Date createTime;

    /**
     * 1：录入标签
     * 5：IQC检测
     * 10：QE检测
     * 15：QE确认
     * 20：物料入库
     * 25：入库完成
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}