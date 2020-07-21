package com.smartindustry.outbound.vo;

import com.smartindustry.common.pojo.om.PickHeadPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:06 2020/7/15
 * @version: 1.0.0
 * @description: 查询拣货单表头信息
 */
@Data
public class PickHeadVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long phid;
    /**
     * 拣货单号
     */
    private String pno;
    /**
     * 物料状态
     */
    private Byte mstatus;
    /**
     * 工单号
     */
    private String ono;
    /**
     * 对应项目
     */
    private String cproject;
    /**
     * 计算发货时间
     */
    private Date ptime;
    /**
     * 出库时间
     */
    private Date otime;
    /**
     * 出库情况
     */
    private Byte ostatus;

    public static List<PickHeadVO> convert(List<PickHeadPO> pos) {
        List<PickHeadVO> vos = new ArrayList<>(pos.size());
        for (PickHeadPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static PickHeadVO convert(PickHeadPO po) {
        PickHeadVO vo = new PickHeadVO();
        vo.setPhid(po.getPickHeadId());
        vo.setCproject(po.getCorrespondProject());
        vo.setMstatus(po.getMaterialStatus());
        vo.setOno(po.getOrderNo());
        vo.setOstatus(po.getOutboundStatus());
        vo.setPtime(po.getPlanTime());
        vo.setPno(po.getPickNo());
        vo.setOtime(po.getOutboundTime());
        return vo;
    }
}
