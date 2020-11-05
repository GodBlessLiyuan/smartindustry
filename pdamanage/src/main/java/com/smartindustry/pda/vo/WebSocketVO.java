package com.smartindustry.pda.vo;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

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
    public static WebSocketVO createTitleVO(String msg) {
        WebSocketVO vo = new WebSocketVO();
        TitleVO titleVO = new TitleVO();
        titleVO.setMsg(msg);
        vo.setTitle(titleVO);
        return vo;
    }

    /**
     * 列表区
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
    }

    /**
     * 提示区
     */
    @Data
    public static class TitleVO {
        /**
         * 提示文案
         */
        private String msg;
    }
}

