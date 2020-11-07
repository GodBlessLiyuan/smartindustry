package com.smartindustry.datasynchronize.vo;

import com.smartindustry.common.bo.ds.SalesOutboundBO;
import com.smartindustry.common.bo.ds.SalesOutboundDetailBO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hui.feng
 * @date created in 2020/11/6
 * @description
 */
@Data
public class SalesOutboundVO implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 销售出库ID
     */
    private Long soid;

    /**
     * 业务单号
     */
    private String sno;

    /**
     * 开票日期
     */
    private Date sdate;

    /**
     * 客户id
     */
    private Long cid;

    private String cname;

    /**
     * 开票员ID
     */
    private Long uid;

    private String uname;

    private List<SalesOutboundDetailVO> sodvos;

    public static List<SalesOutboundVO> convert(List<SalesOutboundBO> bos) {
        List<SalesOutboundVO> vos = new ArrayList<>(bos.size());
        for (SalesOutboundBO po : bos) {
            vos.add(convert(po));
        }
        return vos;
    }

    private static SalesOutboundVO convert(SalesOutboundBO bo) {
        SalesOutboundVO vo = new SalesOutboundVO();
        vo.setSoid(bo.getSalesOutboundId());
        vo.setSno(bo.getSalesNo());
        vo.setSdate(bo.getSalesDate());
        vo.setCid(bo.getClientId());
        vo.setCname(bo.getClientName());
        vo.setUid(bo.getUserId());
        vo.setUname(bo.getUserName());
        if (bo.getDetails() != null && bo.getDetails().size()> 0) {
            vo.setSodvos(convertDetail(bo.getDetails()));
        }
        return vo;
    }

    private static List<SalesOutboundDetailVO> convertDetail(List<SalesOutboundDetailBO> details) {
        List<SalesOutboundDetailVO> vos = new ArrayList<>(details.size());
        for(SalesOutboundDetailBO bo: details) {
            vos.add(convertDetail(bo));
        }
        return vos;
    }

    private static SalesOutboundDetailVO convertDetail(SalesOutboundDetailBO bo) {
        SalesOutboundDetailVO vo = new SalesOutboundDetailVO();
        vo.setSodid(bo.getSalesOutboundDetailId());
        vo.setMid(bo.getMaterialId());
        vo.setMname(bo.getMaterialName());
        vo.setMno(bo.getMaterialNo());
        vo.setNnum(bo.getNeedNum());
        vo.setUprice(bo.getUnitPrice());
        vo.setTprice(bo.getTotalPrice());
        return vo;
    }

    @Data
    static  class  SalesOutboundDetailVO {
        /**
         * 销售出库明细ID
         */
        private Long sodid;

        /**
         * 销售出库ID
         */
        private Long soid;

        /**
         * 物料ID
         */
        private Long mid;

        private String mno;

        private String mname;

        /**
         * 需求量
         */
        private BigDecimal nnum;

        /**
         * 单价
         */
        private BigDecimal uprice;

        /**
         * 金额
         */
        private BigDecimal tprice;
    }
}
