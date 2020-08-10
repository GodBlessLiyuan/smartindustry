package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.LogisticsRecordBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.pojo.om.LogisticsPicturePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/16 20:29
 * @description: 物流记录
 * @version: 1.0
 */
@Data
public class LogisticsRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

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
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static LogisticsRecordVO convert(LogisticsRecordBO bo, FilePathConfig config) {
        LogisticsRecordVO vo = new LogisticsRecordVO();
        vo.setLid(bo.getLogisticsRecordId());
        vo.setLno(bo.getLogisticsNo());
        vo.setSdate(bo.getShipDate());
        vo.setSway(bo.getShipWay());
        vo.setRemark(bo.getRemark());
        List<String> picture = new ArrayList<>(bo.getPicturePOs().size());
        for (LogisticsPicturePO po : bo.getPicturePOs()) {
            picture.add(config.getPublicPath() + po.getPicture());
        }
        vo.setPicture(picture);

        return vo;
    }
}
