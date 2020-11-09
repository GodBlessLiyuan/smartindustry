package com.smartindustry.pda.socket;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/11/2 9:23
 * @description: WebSocket 提示信息
 * @version: 1.0
 */
@Data
public class WebSocketVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private OutboundVO ovo;
    private TitleVO title;

    /**
     * 创建 Title VO
     *
     * @param msg
     * @return
     */
    public static WebSocketVO createTitleVO(String msg, Byte type) {
        WebSocketVO vo = new WebSocketVO();
        TitleVO titleVO = new TitleVO();
        titleVO.setMsg(msg);
        titleVO.setType(type);
        vo.setTitle(titleVO);
        return vo;
    }

    /**
     * 列表/详情区
     */
    @Data
    public static class OutboundVO {
        /**
         * 出/入库 ID
         */
        private Long id;
        /**
         * 当前数量
         */
        private BigDecimal cnum;
        /**
         * 成功出/入库数量
         */
        private BigDecimal snum;
        /**
         * 叉车
         */
        private List<String> fnames;
        /**
         * 状态：1-入库；2-出库
         */
        private Byte status;
        /**
         * 类型：1-待执行， 2-执行中，3-已执行
         */
        private Byte type;
    }

    /**
     * 提示区
     */
    @Data
    public static class TitleVO {
        /**
         * 弹框左上角小标题
         */
        private String tip;
        /**
         * 提示文案
         */
        private String msg;
        /**
         * 提示框类型，1-提示性消息，2-报警性提醒，3-故障性提示，4-弹窗
         */
        private Byte type;

        /**
         * 备料区选择物料返回物料信息VO列表
         */
        private List<MaterialVO> vos;
        /**
         * 备料区入成品区的VO列信息
         */
        private MaterialVO mvo;

    }

    /**
     * 入库备料区选择物料属性
     */

    @Data
    public static class MaterialVO {
        /**
         * 产品id
         */
        private Long mid;
        /**
         * 产品名称
         */
        private String mname;
        /**
         * 产品等级
         */
        private String mlevel;
        /**
         * 产品规格
         */
        private String model;
        /**
         * 栈板ifid
         */
        private String mrfid;
        /**
         * 每栈板的体积
         */
        private String mvolume;
    }
}

