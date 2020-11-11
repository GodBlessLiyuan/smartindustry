package com.smartindustry.pda.vo;

import com.smartindustry.common.bo.om.OutboundBodyBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/10/31 14:39
 * @description: 成品出库
 * @version: 1.0
 */
@Data
public class OutboundDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String[] COLORS = new String[]{"#FF0000", "#00FF00", "#0000FF"};

    /**
     * 出库单ID
     */
    private Long ohid;
    /**
     * 单号
     */
    private String sno;
    /**
     * 客户名称
     */
    private String cname;
    /**
     * 物料信息
     */
    private List<String> minfos;
    /**
     * 需出库量
     */
    private BigDecimal dnum;
    /**
     * 需出库量
     */
    private BigDecimal dnum2;
    /**
     * 当前排位
     */
    private BigDecimal cnum;
    /**
     * 已出库数
     */
    private BigDecimal onum;
    /**
     * 状态：开始执行；辅助执行；关闭;消失
     */
    private String status;
    /**
     * 储位图
     */
    private List<LocationVO> lvos;

    public static OutboundDetailVO convert(OutboundHeadBO bo) {
        OutboundDetailVO vo = new OutboundDetailVO();
        vo.setOhid(bo.getOutboundHeadId());
        vo.setSno(bo.getSourceNo());
        vo.setDnum(bo.getExpectNum());
        vo.setOnum(bo.getOutboundNum());
        vo.setCname(bo.getClientName());
        BigDecimal dnum2 = new BigDecimal(0);
        if (null != bo.getBodyBOs() && bo.getBodyBOs().size() > 0) {
            List<String> minfos = new ArrayList<>(bo.getBodyBOs().size());
            for (OutboundBodyBO bodyBO : bo.getBodyBOs()) {
                String minfo = bodyBO.getMaterialName() + " " + bodyBO.getMaterialModel();
                minfos.add(minfo);

                dnum2 = dnum2.add(bodyBO.getExpectNum().multiply(bodyBO.getPackageVolume()));
            }
            vo.setMinfos(minfos);
        }
        vo.setDnum2(dnum2);
        return vo;
    }

    @Data
    static class MaterialVO {
        /**
         * 出库物料
         */
        private String mname;
        /**
         * 规格参数
         */
        private String mmodel;
    }

    @Data
    public static class LocationVO {
        /**
         * 物料信息： 物料编码 + 物料规格
         */
        private String minfo;
        /**
         * 颜色
         */
        private String color;
        /**
         * 储位RFID
         */
        private List<String> lrfids = new ArrayList<>();
    }
}
