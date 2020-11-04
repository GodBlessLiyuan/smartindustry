package com.smartindustry.pda.vo;

import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 10:22 2020/11/3
 * @ Description：成品入库VO
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class StorageDetailVO {
    private static final long serialVersionUID = 1L;

    public static final String[] COLORS = new String[]{"#FFFF00", "#FFFAFA", "#FF0000", "#00FF00"};

    /**
     * 入库单ID
     */
    private Long shid;
    /**
     * 来源单号
     */
    private String sno;
    /**
     * 物料
     */
    private List<StorageDetailVO.MaterialVO> mvos;
    /**
     * 需入库量
     */
    private BigDecimal dnum;
    /**
     * 已入库数
     */
    private BigDecimal snum;
    /**
     * 叉车
     */
    private List<String> fnames;
    /**
     * 状态：
     */
    private String status;
    /**
     * 储位图
     */
    private List<StorageDetailVO.LocationVO> lvos;

    public static StorageDetailVO convert(StorageHeadBO bo) {
        StorageDetailVO vo = new StorageDetailVO();
        vo.setShid(bo.getStorageHeadId());
        vo.setSno(bo.getSourceNo());
        vo.setDnum(bo.getExpectNum());
        vo.setSnum(bo.getStorageNum());
        if (null != bo.getBos() && bo.getBos().size() > 0) {
            List<StorageDetailVO.MaterialVO> mvos = new ArrayList<>(bo.getBos().size());
            for (StorageBodyBO bodyBO : bo.getBos()) {
                StorageDetailVO.MaterialVO mvo = new StorageDetailVO.MaterialVO();
                mvo.setMname(bodyBO.getMaterialName());
                mvo.setMmodel(bodyBO.getMaterialModel());
                mvos.add(mvo);
            }
            vo.setMvos(mvos);
        }
        return vo;
    }

    @Data
    private static class MaterialVO {
        /**
         * 入库物料
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