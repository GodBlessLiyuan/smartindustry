package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.PickHeadPO;
import lombok.Data;


/**
 * @author: jiangzhaojie
 * @date: Created in 9:40 2020/7/17
 * @version: 1.0.0
 * @description:
 */
@Data
public class PickHeadBO extends PickHeadPO {
    /**
     * 物料编号
     */
    private String materialNo;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料描述
     */
    private String materialDesc;
    /**
     * 以逗号为间隔隔开的PID列表
     */
    private String packageItems;
}
