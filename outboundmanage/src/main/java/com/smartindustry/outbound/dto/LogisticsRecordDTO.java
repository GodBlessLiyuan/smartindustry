package com.smartindustry.outbound.dto;

import com.smartindustry.common.config.FilePathConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/16 19:28
 * @description: 物流信息
 * @version: 1.0
 */
@Data
public class LogisticsRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 出库单ID
     */
    private Long oid;
    /**
     * 物流ID
     */
    private Long lid;
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
     * 创建 po
     *
     * @param dto
     * @return
     */
    public static LogisticsRecordPO createPO(LogisticsRecordDTO dto) {
        LogisticsRecordPO po = updatePO(new LogisticsRecordPO(), dto);
        po.setCreateTime(new Date());
        return po;
    }

    /**
     * 更新 po
     *
     * @param po
     * @param dto
     * @return
     */
    public static LogisticsRecordPO updatePO(LogisticsRecordPO po, LogisticsRecordDTO dto) {
        po.setOutboundId(dto.getOid());
        po.setShipDate(dto.getSdate());
        po.setLogisticsNo(dto.getLno());
        po.setShipWay(dto.getSway());
        po.setRemark(dto.getRemark());
        return po;
    }

    public static List<LogisticsPicturePO> createPicPO(LogisticsRecordDTO dto, FilePathConfig config) {
        List<LogisticsPicturePO> pos = new ArrayList<>(dto.getPicture().size());
        for (String pic : dto.getPicture()) {
            LogisticsPicturePO po = new LogisticsPicturePO();
            po.setLogisticsRecordId(dto.getLid());
            po.setPicture(pic.split(config.getPublicPath())[1]);
            pos.add(po);
        }

        return pos;
    }
}
