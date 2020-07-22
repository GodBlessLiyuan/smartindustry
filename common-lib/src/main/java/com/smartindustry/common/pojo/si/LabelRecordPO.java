package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * si_label_record
 *
 * @author
 */
@Data
public class LabelRecordPO implements Serializable {
    private Long labelRecordId;

    private Long printLabelId;

    private Long userId;

    private String name;

    /**
     * 1：入库管理
     * 2：出库管理
     */
    private Byte module;

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