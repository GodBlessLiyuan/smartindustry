package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * om_logistics_record
 * @author 
 */
@Data
public class LogisticsRecordPO implements Serializable {
    private Long logisticsRecordId;

    private Long pickHeadId;

    private Date shipDate;

    private String logisticsNo;

    /**
     * 1：到付
2：寄付
     */
    private Byte shipWay;

    private String remark;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}