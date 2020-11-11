package com.smartindustry.common.bo.si;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:03 2020/11/11
 * @version: 1.0.0
 * @description:
 */
@Data
public class ProductDetailBO {
    /**
     * 物料编号
     */
    private String materialNo;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料类型
     */
    private Byte materialType;
    /**
     * 物料型号
     */
    private String materialModel;

    private String materialDesc;
    /**
     * 栈板rfid号
     */
    private String rfid;
    /**
     * 物料批次
     */
    private String materialBatch;
    /**
     * 栈板入库时间
     */
    private Date storageTime;
    /**
     * 当前栈板物料状态 1 正常 2 锁定
     */
    private Byte materialStatus;
    /**
     * 当前状态 1 在库 2 作业中
     */
    private Byte curStatus;
    /**
     * 所在仓库名称
     */
    private String warehouseName;
    /**
     * 所在储位编号
     */
    private String locationNo;
    /**
     * 当前储位本物料的所有物料数量
     */
    private BigDecimal num;
}
