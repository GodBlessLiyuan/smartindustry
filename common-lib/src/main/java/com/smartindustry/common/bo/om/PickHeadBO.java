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
     * 当前物料使用了推荐的pid,以逗号隔开
     */
    private String recommendPid;
    /**
     * 当前物料使用了未推荐的pid,以逗号隔开
     */
    private String noRecommendPid;

    /**
     * 异常说明情况
     */
    private String aberrantDesc;
}
