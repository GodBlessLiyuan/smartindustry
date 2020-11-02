package com.smartindustry.common.pojo.wo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * wo_package
 * @author 
 */
@Data
public class PackagePO implements Serializable {
    /**
     * 打包ID
     */
    private Long packageId;

    /**
     * 成品工单ID
     */
    private Long produceOrderId;

    /**
     * RFID
     */
    private String rfid;

    /**
     * 打包时间
     */
    private Date packageTime;

    private static final long serialVersionUID = 1L;
}