package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.BomBodyPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:13 2020/8/13
 * @version: 1.0.0
 * @description:
 */
@Data
public class BomBodyDTO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long bbid;
    /**
     * 主BOM清单ID
     */
    private Long bhid;
    private Long mid;
    /**
     * 物料属性ID
     */
    private Long mpid;

    /**
     * 物料需求量
     */
    private Float mdemand;
    /**
     * 损耗
     */
    private Float mloss;
    /**
     * 物料需求量类型
     */
    private Byte mdtype;
    /**
     * 损耗类型
     */
    private Byte mltype;
    /**
     * 工序id
     */
    private Long prid;
    /**
     * 上级物料id
     */
    private Long pid;

    public static BomBodyPO createPO(BomBodyDTO dto) {
        BomBodyPO po = buildPO(dto);
        po.setBomHeadId(dto.getBhid());
        po.setDemandType(dto.getMdtype());
        po.setLossType(dto.getMltype());
        po.setUserId(1L);
        po.setCreateTime(new Date());
        po.setUpdateTime(null);
        po.setDr((byte)1);
        return po;
    }

    public static BomBodyPO buildPO(BomBodyDTO dto) {
        BomBodyPO po = new BomBodyPO();
        po.setBomBodyId(dto.getBbid());
        po.setMaterialId(dto.getMid());
        po.setMaterialPropertyId(dto.getMpid());
        po.setMaterialDemand(dto.getMdemand());
        po.setMaterialLoss(dto.getMloss());
        po.setProcessId(dto.getPrid());
        po.setParentId(dto.getPid());
        po.setUpdateTime(new Date());
        return po;
    }
}
