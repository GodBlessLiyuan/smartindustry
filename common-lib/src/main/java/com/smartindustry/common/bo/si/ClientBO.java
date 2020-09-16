package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.ClientPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:12 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class ClientBO extends ClientPO {
    private static final long SerialVersionUID = 1L;

    /**
     * 信用等级名称
     */
    private String creditLevelName;

    /**
     * 客户类型名称
     */
    private String clientTypeName;
}
