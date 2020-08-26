package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.util.DateUtil;
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
     * 来源订单编号
     */
    private String sno;
    /**
     * 来源订单类型
     */
    private Byte stype;
    /**
     * 对应项目
     */
    private String cproject;
    /**
     * 计算发货时间
     */
    private String ptime;
    /**
     * 出库时间
     */
    private Date otime;
    /**
     * 出库情况
     */
    private Byte ostatus;

    /**
     * 调拨订单id
     */
    private Long tid;
    /**
     * 调拨订单编号
     */
    private String tno;
    /**
     * 调拨订单类型
     */
    private Byte ttype;

    /**
     * 计划调拨时间
     */
    private String tptime;

    /**
     * 调拨出库状态
     */
    private Byte tostatus;



    public static PickHeadVO convert(PickHeadPO po) {
        PickHeadVO vo = new PickHeadVO();
        vo.setPhid(po.getPickHeadId());
        vo.setCproject(po.getCorrespondProject());
        vo.setMstatus(po.getMaterialStatus());
        vo.setSno(po.getSourceNo());
        vo.setStype(po.getSourceType());
        vo.setOstatus(po.getOutboundStatus());
        vo.setPtime(DateUtil.date2Str(po.getPlanTime(),DateUtil.Y_M_D));
        vo.setPno(po.getPickNo());
        vo.setOtime(po.getOutboundTime());
        return vo;
    }

    public static List<PickHeadVO> convertBO(List<PickHeadBO> bos) {
        List<PickHeadVO> vos = new ArrayList<>(bos.size());
        for (PickHeadBO bo : bos) {
            vos.add(convertBO(bo));
        }
        return vos;
    }


    public static List<PickHeadVO> convert(List<PickHeadPO> pos) {
        List<PickHeadVO> vos = new ArrayList<>(pos.size());
        for (PickHeadPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static PickHeadVO convertBO(PickHeadBO bo) {
        PickHeadVO vo = new PickHeadVO();
        vo.setPhid(bo.getPickHeadId());
        vo.setCproject(bo.getCorrespondProject());
        vo.setMstatus(bo.getMaterialStatus());
        vo.setSno(bo.getSourceNo());
        vo.setStype(bo.getSourceType());
        vo.setOstatus(bo.getOutboundStatus());
        vo.setPtime(DateUtil.date2Str(bo.getPlanTime(),DateUtil.Y_M_D));
        vo.setPno(bo.getPickNo());
        vo.setOtime(bo.getOutboundTime());
        vo.setTno(bo.getTransferNo());
        vo.setTostatus(bo.getThOutboundStatus());
        vo.setTptime(DateUtil.date2Str(bo.getThPlanTime(),DateUtil.Y_M_D));
        vo.setTtype(bo.getTransferType());
        vo.setTid(bo.getTransferId());
        return vo;
    }
}
