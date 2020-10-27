package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import lombok.Data;

/**
 * si_forklift
 * @author 
 */
@Data
public class ForkliftPO implements Serializable {
    /**
     * 叉车id
     */
    private Long forkliftId;

    /**
     * 叉车编号
     */
    private String forkliftNo;

    /**
     * 叉车型号
     */
    private String forkliftModel;

    /**
     * 品牌
     */
    private String forkliftBrand;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 是否携带RFID设备
     */
    private Boolean rfid;

    /**
     * 是否携带工业一体机
     */
    private Boolean iom;

    /**
     * 作业区域
     */
    private String workArea;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}