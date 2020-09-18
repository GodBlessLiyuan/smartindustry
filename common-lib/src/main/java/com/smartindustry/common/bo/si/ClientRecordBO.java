package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.ClientRecordPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:08 2020/9/17
 * @version: 1.0.0
 * @description:
 */
@Data
public class ClientRecordBO extends ClientRecordPO {
    /**
     * 操作人姓名
     */
    private String name;

}
