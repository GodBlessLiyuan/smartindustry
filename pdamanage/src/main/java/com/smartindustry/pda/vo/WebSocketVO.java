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

    private List<ListVO> list;
    private DetailVO detail;
    private TitleVO title;

    /**
     * 列表区
     */
    @Data
    private static class ListVO {
        /**
         * 出/入库 ID
         */
        private Long id;
        /**
         * 当前数量
         */
        private BigDecimal cnum;
    }

    /**
     * 详情区
     */
    @Data
    private static class DetailVO {
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
        private List<String> fno;
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

