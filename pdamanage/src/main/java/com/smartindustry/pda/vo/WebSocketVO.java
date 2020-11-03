package com.smartindustry.pda.vo;

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
    }

    /**
     * 提示区
     */
    @Data
    private static class TitleVO {
        /**
         * 提示文案
         */
        private String msg;
    }
}

