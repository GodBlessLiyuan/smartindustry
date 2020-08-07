package com.smartindustry.outbound.dto;

import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.outbound.constant.OutboundConstant;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/15 15:18
 * @description: 拣货单
 * @version: 1.0
 */
@Data
public class PickDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private PickHeadDTO head;
    private List<PickBodyDTO> body;

    /**
     * dto 转 po
     *
     * @param dto
     * @return
     */
    public static PickHeadPO convert(PickHeadDTO dto) {
        PickHeadPO headPO = new PickHeadPO();
        headPO.setPickNo(dto.getPno());
        headPO.setOrderNo(dto.getOno());
        headPO.setOrderType(dto.getOtype());
        headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_NOT_RECOMMEND);
        headPO.setCorrespondProject(dto.getCproject());
        headPO.setAcceptCustomer(dto.getAcustomer());
        headPO.setAcceptAddress(dto.getAaddress());
        if (!StringUtils.isEmpty(dto.getPtime())) {
            headPO.setPlanTime(DateUtil.str2Date(dto.getPtime(), DateUtil.Y_M_D));
        }
        headPO.setOutboundStatus(OutboundConstant.PICK_OUTBOUND_WAIT);
        headPO.setCreateTime(new Date());
        headPO.setDr((byte) 1);

        return headPO;
    }

    /**
     * dtos 转 pos
     *
     * @param dtos
     * @return
     */
    public static List<PickBodyPO> convert(PickHeadPO headPO, List<PickBodyDTO> dtos) {
        List<PickBodyPO> bodyPOs = new ArrayList<>();
        for (PickBodyDTO dto : dtos) {
            bodyPOs.add(convert(headPO, dto));
        }
        return bodyPOs;
    }

    /**
     * dto 转 po
     *
     * @param dto
     * @return
     */
    public static PickBodyPO convert(PickHeadPO headPO, PickBodyDTO dto) {
        PickBodyPO po = new PickBodyPO();
        po.setPickHeadId(headPO.getPickHeadId());
        po.setMaterialId(dto.getMid());
        po.setDemandNum(dto.getDnum());
        po.setPickNum(0);
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return po;
    }

    @Data
    public static class PickHeadDTO {
        /**
         * 拣货单编号
         */
        private String pno;
        /**
         * 来源单号
         */
        private String ono;
        /**
         * 来源类型（1：工单；2：销售订单）
         */
        private Byte otype;
        /**
         * 对应项目
         */
        private String cproject;
        /**
         * 接受客户
         */
        private String acustomer;
        /**
         * 接受数量
         */
        private String aaddress;
        /**
         * 计划发货日期
         */
        private String ptime;
    }

    @Data
    public static class PickBodyDTO {
        /**
         * 物料ID
         */
        private Long mid;
        /**
         * 需求数量
         */
        private Integer dnum;
    }
}
