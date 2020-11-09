package com.smartindustry.common.bo.sm.bo.si;

import com.smartindustry.common.pojo.si.BomRecordPO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:40 2020/8/20
 * @version: 1.0.0
 * @description:
 */
@AllArgsConstructor
@Data
public class BomRecordBO extends BomRecordPO {
    private static final long SerialVersionUID = 1L;

    /**
     * 操作人姓名
     */
    private String name;
}
