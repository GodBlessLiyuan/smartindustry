package com.smartindustry.outbound.vo;

import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.util.DateUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/17 15:29
 * @description: 拣货单详情
 * @version: 1.0
 */
@Data
public class PickDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 拣货单表头ID
     */
    private Long phid;
    /**
     * 拣货单编号
     */
    private String pno;
    /**
     * 来源单号
     */
    private String ono;
    /**
     * 对应项目
     */
    private String cproject;
    /**
     * 计划出库日期
     */
    private String ptime;
    /**
     * 拣货列表
     */
    private List<PickItemVO> items;

    /**
     * 设置表头数据
     *
     * @param po
     */
    public void setPickHeadVO(PickHeadPO po) {
        this.phid = po.getPickHeadId();
        this.pno = po.getPickNo();
        this.ono = po.getOrderNo();
        this.cproject = po.getCorrespondProject();
        if (!StringUtils.isEmpty(po.getPlanTime())) {
            this.ptime = DateUtil.date2Str(po.getPlanTime(), DateUtil.Y_M_D);
        }
    }

    @Data
    public static class PickItemVO {
        /**
         * 拣货单表体数据
         */
        private PickBodyVO body;
        /**
         * 优先推荐
         */
        private List<String> recommend;
        /**
         * 其他库位
         */
        private List<String> other;
    }
}
