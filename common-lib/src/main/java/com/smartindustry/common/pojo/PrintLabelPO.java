package com.smartindustry.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sm_print_label
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
2：打印
     */
    private Byte origin;

    private String materialNo;

    private String locationNo;

    private Long storageId;

    private Long relateLabelId;

    private String relatePackageId;

    private Date createTime;

    /**
     * 1：未废弃
2：已废弃
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}