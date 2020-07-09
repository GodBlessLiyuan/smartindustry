package com.smartindustry.storage.vo;

import com.smartindustry.common.pojo.sm.ReceiptHeadPO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/6/24 11:13
 * @description: 物流信息
 * @version: 1.0
 */
@Data
public class LogisticsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流公司名称
     */
    private String company;
    /**
     * 物流单号
     */
    private String no;
    /**
     * 收货方式
     */
    private Byte way;
    /**
     * 备注（采购员等）
     */
    private String remark;

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */
    public static LogisticsVO convert(ReceiptHeadPO po) {
        LogisticsVO vo = new LogisticsVO();

        vo.setCompany(po.getLogisticsCompany());
        vo.setNo(po.getLogisticsNo());
        vo.setWay(po.getReceiptWay());
        vo.setRemark(po.getRemark());

        return vo;
    }
}
