package com.smartindustry.common.bo.sm.bo.am;

import com.smartindustry.common.pojo.am.UserPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:06 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class UserBO extends UserPO {
    private static final long SerialVersionUID = 1L;
    private String deptName;
    private String roleName;
}
