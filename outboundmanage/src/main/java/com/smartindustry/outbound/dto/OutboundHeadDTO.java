package com.smartindustry.outbound.dto;



import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.outbound.constant.OutboundConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:48 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class OutboundHeadDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 出库单表头id
     */
    private Long ohid;
    /**
     * 混料单表头id
     */
    private Long mhid;
    /**
     * 来源工单号/混料工单号
     */
    private String sno;
    /**
     * 计划出货时间
     */
    private Date ptime;
    /**
     * 备注
     */
    private String extra;

    /**
     * 标识位：用来标识是确认入库还是暂存， true为确认入库，false为暂存
     */
    private Boolean flag;

    private List<OutboundBodyDTO> body;

    @Data
    public static class OutboundBodyDTO {
        /**
         * 表体id
         */
        private String obid;
        /**
         * 出库数量
         */
        private BigDecimal onum;
        /**
         * 出库时间
         */
        private Date otime;
        /**
         * 物料id
         */
        private Long mid;
    }


    public static OutboundHeadPO createPO(OutboundHeadDTO dto) {
        OutboundHeadPO po = new OutboundHeadPO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static OutboundHeadPO buildPO(OutboundHeadPO po, OutboundHeadDTO dto) {
        po.setSourceNo(dto.getSno());
        po.setSourceType(OutboundConstant.TYPE_INSERT_BY_OURS);
        po.setPlanTime(dto.getPtime());
        po.setExtra(dto.getExtra());
        return po;
    }

    public static List<OutboundBodyPO> convert(OutboundHeadPO po, List<OutboundBodyDTO> dtos) {
        List<OutboundBodyPO> bodyPOs = new ArrayList<>();
        for (OutboundBodyDTO dto : dtos) {
            bodyPOs.add(createPO(po, dto));
        }
        return bodyPOs;
    }


    public static OutboundBodyPO createPO(OutboundHeadPO headPO, OutboundBodyDTO dto) {
        OutboundBodyPO po = new OutboundBodyPO();
        po.setOutboundHeadId(headPO.getOutboundHeadId());
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static OutboundBodyPO buildPO(OutboundBodyPO bodyPO, OutboundBodyDTO dto) {
        bodyPO.setMaterialId(dto.getMid());
        bodyPO.setOutboundNum(dto.getOnum());
        bodyPO.setOutboundTime(dto.getOtime());
        return bodyPO;
    }
}
