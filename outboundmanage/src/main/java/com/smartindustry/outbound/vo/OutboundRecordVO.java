package com.smartindustry.outbound.vo;

import com.smartindustry.common.pojo.om.OutboundRecordPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/17 8:59
 * @description: 操作记录
 * @version: 1.0
 */
@Data
public class OutboundRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 操作人
     */
    private String name;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 操作日期
     */
    private Date ctime;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<OutboundRecordVO> convert(List<OutboundRecordPO> pos) {
        List<OutboundRecordVO> vos = new ArrayList<>(pos.size());
        for (OutboundRecordPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */
    public static OutboundRecordVO convert(OutboundRecordPO po) {
        OutboundRecordVO vo = new OutboundRecordVO();
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setCtime(po.getCreateTime());
        return vo;
    }
}
