package com.smartindustry.pda.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/11/2 9:23
 * @description: WebSocket 提示信息
 * @version: 1.0
 */
@NoArgsConstructor
@Data
public class WebSocketVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ShowVO show;
    private TitleVO title;

    public static WebSocketVO createShowVO(Long hid, Byte status, Byte type) {
        WebSocketVO vo = new WebSocketVO();
        ShowVO showVO = new ShowVO(hid, status, type);
        vo.setShow(showVO);
        return vo;
    }

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
     * 展示VO
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ShowVO {
        /**
         * 出/入库单ID
         */
        private Long hid;
        /**
         * 状态：1-入库；2-出库
         */
        private Byte status;
        /**
         * 列表类型：1-待执行，2-执行中，3-已执行
         */
        private Byte type;
    }

    /**
     * 提示区
     */
    @Data
    public static class TitleVO {
        /**
         * 提示框类型，1-提示性消息，2-报警性提醒，3-故障性提示，4-弹窗
         */
        private Byte type;
        /**
         * 弹框左上角小标题
         */
        private String tip;
        /**
         * 提示文案
         */
        private String msg;
        /**
         * 备料区选择物料返回物料信息VO列表
         */
        private List<MaterialVO> mvos;
    }

    @Data
    public static class MaterialVO {
        /**
         * 物料id
         */
        private Long mid;
        /**
         * 物料信息
         */
        private String minfo;
    }
}

