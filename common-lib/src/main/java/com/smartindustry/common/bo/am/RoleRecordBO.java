package com.smartindustry.common.bo.am;

import com.smartindustry.common.pojo.am.RoleRecordPO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 13:17 2020/8/6
 * @version: 1.0.0
 * @description:
 */
@AllArgsConstructor
@Data
public class RoleRecordBO extends RoleRecordPO {
    private static final long SerialVersionUID = 1L;

    private String roleName;

    private String name;
}
