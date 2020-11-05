package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.wo.SlurryOrderPO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:26 2020/10/28
 * @version: 1.0.0
 * @description: 混料工单 = 料浆工单
 */
@Data
public class MixHeadBO extends SlurryOrderPO {
    List<MixBodyBO> bos;
}
