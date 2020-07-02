package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * sm_print_label
 *
 * @author
 */
@Data
public class PrintLabelPO implements Serializable {
    private Long printLabelId;

    private Long receiptBodyId;

    private String packageId;

    private Date produceDate;

    private String produceBatch;

    private Integer num;

    /**
     * 1：扫描
     * 2：打印
     */
    private Byte origin;

    /**
     * 1：良品
     * 2：非良品
     */
    private Byte type;

    /**
     * 1：已入库
     * 2：入库中
     * 3：待入库
     */
    private Byte status;

    private String locationNo;

    private Long relateLabelId;

    private String relatePackageId;

    private Date createTime;

    /**
     * 1：未删除
     * 2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}