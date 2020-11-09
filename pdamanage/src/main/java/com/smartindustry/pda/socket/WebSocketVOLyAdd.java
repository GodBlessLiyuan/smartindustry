package com.smartindustry.pda.socket;

import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/11/2 9:23
 * @description: WebSocket 提示信息
 * @version: 1.0
 */
@Data
public class WebSocketVOLyAdd extends WebSocketVO {

    private ShowVO svo;
    /**
     * @ Author     ：AnHongxu.
     * @ Date       ：Created in 10:22 2020/11/3
     * @ Description：成品入库VO
     * @ Modified By：
     * @ Version:     1.0
     */
    @Data
    public static class ShowVO {
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
        private List<ShowVO.MaterialVO> mvos;
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
         * 当前排位
         */
        private BigDecimal tvolume;
        /**
         * 当前排位
         */
        private BigDecimal cnum;
        /**
         * 创建时间
         */
        private Date ctime;
        /**
         * 储位图
         */
        private List<ShowVO.LocationVO> lvos;

        public static ShowVO convert(StorageHeadBO bo) {
            ShowVO vo = new ShowVO();
            vo.setShid(bo.getStorageHeadId());
            vo.setSno(bo.getSourceNo());
            vo.setDnum(bo.getExpectNum());
            vo.setSnum(bo.getStorageNum());
            vo.setCtime(bo.getCreateTime());
            BigDecimal tvolume = new BigDecimal(0);
            if (null != bo.getBos() && bo.getBos().size() > 0) {
                List<ShowVO.MaterialVO> mvos = new ArrayList<>(bo.getBos().size());
                for (StorageBodyBO bodyBO : bo.getBos()) {
                    ShowVO.MaterialVO mvo = new ShowVO.MaterialVO();
                    mvo.setMname(bodyBO.getMaterialName());
                    mvo.setMmodel(bodyBO.getMaterialModel());
                    mvos.add(mvo);
                    System.out.println(bodyBO.toString());
                    tvolume = tvolume.add(bodyBO.getExpectNum().multiply(bodyBO.getPackageVolume()));
                }
                vo.setMvos(mvos);
            }
            vo.setTvolume(tvolume);
            return vo;
        }

        @Data
        static class MaterialVO {
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
}

