package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.LogisticsPicturePO;
import com.smartindustry.common.pojo.om.LogisticsRecordPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/16 20:11
 * @description: 物流信息
 * @version: 1.0
 */
@Data
public class LogisticsRecordBO extends LogisticsRecordPO {
    List<LogisticsPicturePO> picturePOs = new ArrayList<>();
}
