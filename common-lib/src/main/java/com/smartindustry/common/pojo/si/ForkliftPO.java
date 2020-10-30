package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
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
     * 供应商
     */
    private String supplierName;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 工业一体机号
     */
    private String imeiNo;

    /**
     * 作业区域 : 1 原材料区
2 生产产区
3 成品区
     */
    private Byte workArea;

    /**
     * 当前状态 : 1 忙碌中
2 空闲中
     */
    private Byte status;

    /**
     * 备注
     */
    private String extra;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}