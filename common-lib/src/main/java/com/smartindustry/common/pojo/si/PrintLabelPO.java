package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * si_print_label
 *
 * @author
 */
@Data
public class PrintLabelPO implements Serializable {
    private Long printLabelId;

    private String packageId;

    private String produceDate;

    private String produceBatch;

    private Integer num;

    /**
     * 1：良品
     * 2：非良品
     */
    private Byte type;

    /**
     * 1：扫描
     * 2：打印
     */
    private Byte origin;

    private String materialNo;

    private String locationNo;

    private Long relateLabelId;

    private String relatePackageId;

    private Date createTime;

    /**
     * 1：未废弃
     * 2：已废弃
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}