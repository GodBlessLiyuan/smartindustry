package com.smartindustry.outbound.dto;

import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.pojo.om.LogisticsRecordPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/16 19:28
 * @description: 物流信息
 * @version: 1.0
 */
@Data
public class LogisticsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 出库单ID
     */
    private Long oid;
    /**
     * 物流单号
     */
    private String lno;
    /**
     * 发货日期
     */
    private Date sdate;
    /**
     * 发货方式
     */
    private Byte sway;
    /**
     * 备注
     */
    private String remark;
    /**
     * 图片
     */
    private List<String> picture;

    /**
     * dto 转 po
     *
     * @param dto
     * @return
     */
    public static LogisticsRecordPO createPO(LogisticsDTO dto) {
        LogisticsRecordPO po = new LogisticsRecordPO();
        po.setOutboundId(dto.getOid());
        po.setShipDate(dto.getSdate());
        po.setLogisticsNo(dto.getLno());
        po.setShipWay(dto.getSway());
        po.setRemark(dto.getRemark());
        po.setCreateTime(new Date());
        return po;
    }
}
