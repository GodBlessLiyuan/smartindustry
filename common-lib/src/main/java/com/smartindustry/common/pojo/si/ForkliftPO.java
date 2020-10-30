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
     * 工业一体机号
     */
    private String imeiNo;

    /**
     * 作业区域
     */
    private Byte workArea;

    /**
     * 当前状态 : 1 忙碌中
2 空闲中
     */
    private Byte status;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}