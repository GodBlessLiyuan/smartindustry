package com.smartindustry.storage.vo;

import com.smartindustry.common.pojo.ReceiptBodyPO;
import com.smartindustry.common.pojo.ReceiptHeadPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 10:02
 * @description: 收料单信息
 * @version: 1.0
 */
@Data
public class ReceiptVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ReceiptHeadVO head = new ReceiptHeadVO();
    private List<ReceiptBodyVO> body = new ArrayList<>();

    /**
     * po 转 vo
     *
     * @param headPO
     * @param bodyPOs
     * @return
     */
    public static ReceiptVO convert(ReceiptHeadPO headPO, List<ReceiptBodyPO> bodyPOs) {
        ReceiptVO vo = new ReceiptVO();
        vo.setHead(convert(headPO));
        vo.setBody(convert(bodyPOs));
        return vo;
    }

    /**
     * head po 转 vo
     *
     * @param po
     * @return
     */
    public static ReceiptHeadVO convert(ReceiptHeadPO po) {
        ReceiptHeadVO vo = new ReceiptHeadVO();
        vo.setRhid(po.getReceiptHeadId());
        vo.setOno(po.getOrderNo());
        vo.setOdate(po.getOrderDate());
        vo.setSupplier(po.getSupplier());
        vo.setBuyer(po.getBuyer());
        vo.setPdate(po.getPlanDate());
        vo.setLoco(po.getLogisticsCompany());
        vo.setLono(po.getLogisticsNo());
        vo.setWay(po.getReceiptWay());
        vo.setRemark(po.getRemark());
        return vo;
    }

    /**
     * body pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<ReceiptBodyVO> convert(List<ReceiptBodyPO> pos) {
        List<ReceiptBodyVO> vos = new ArrayList<>();
        for (ReceiptBodyPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    /**
     * body po 转 vo
     *
     * @param po
     * @return
     */
    public static ReceiptBodyVO convert(ReceiptBodyPO po) {
        ReceiptBodyVO vo = new ReceiptBodyVO();
        vo.setRbid(po.getReceiptBodyId());
        vo.setRno(po.getReceiptNo());
        vo.setMno(po.getMaterialNo());
        vo.setMtype(po.getMaterialType());
        vo.setMdesc(po.getMaterialDesc());
        vo.setOtotal(po.getOrderTotal());
        vo.setAnum(po.getAcceptNum());
        vo.setAdate(po.getAcceptDate());
        return vo;
    }

    @Data
    public static class ReceiptHeadVO {
        /**
         * 收料表头Id
         */
        private Long rhid;
        /**
         * 来源订单编号
         */
        private String ono;
        /**
         * 采购日期
         */
        private Date odate;
        /**
         * 供应商
         */
        private String supplier;
        /**
         * 采购员
         */
        private String buyer;
        /**
         * 预计到货日期
         */
        private Date pdate;
        /**
         * 物流公司
         */
        private String loco;
        /**
         * 物流单号
         */
        private String lono;
        /**
         * 收货方式
         */
        private Byte way;
        /**
         * 备注
         */
        private String remark;
    }

    @Data
    public static class ReceiptBodyVO {
        /**
         * 收料表体id
         */
        private Long rbid;
        /**
         * 收料单号
         */
        private String rno;
        /**
         * 物料编码
         */
        private String mno;
        /**
         * 物料类型
         */
        private Byte mtype;
        /**
         * 物料描述
         */
        private String mdesc;
        /**
         * 订单总数
         */
        private Integer ototal;
        /**
         * 接受数量
         */
        private Integer anum;
        /**
         * 接受日期
         */
        private Date adate;
    }
}
