package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.OutboundBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/16 15:51
 * @description: 出库单
 * @version: 1.0
 */
@Data
public class OutboundVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 出货单ID
     */
    private Long oid;
    /**
     * 出货单号
     */
    private String ono;

    private Long phid;
    /**
     * 拣货单号
     */
    private String pno;
    /**
     * 对应项目
     */
    private String cproject;
    /**
     * 计划发货时间
     */
    private Date ptime;
    /**
     * 完成出库时间
     */
    private Date otime;
    /**
     * 出货状态（1：已出库；3：未出库）
     */
    private Byte status;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<OutboundVO> convert(List<OutboundBO> bos) {
        List<OutboundVO> vos = new ArrayList<>(bos.size());
        for (OutboundBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static OutboundVO convert(OutboundBO bo) {
        OutboundVO vo = new OutboundVO();
        vo.setOid(bo.getOutboundId());
        vo.setOno(bo.getOutboundNo());
        vo.setPno(bo.getPickNo());
        vo.setPhid(bo.getPickHeadId());
        vo.setCproject(bo.getCorrespondProject());
        vo.setPtime(bo.getPlanTime());
        vo.setOtime(bo.getOutboundTime());
        vo.setStatus(bo.getStatus());
        return vo;
    }
}
