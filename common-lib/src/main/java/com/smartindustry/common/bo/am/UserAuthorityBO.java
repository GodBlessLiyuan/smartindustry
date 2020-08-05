package com.smartindustry.common.bo.am;

import com.smartindustry.common.pojo.am.MUserAuthorityPO;
import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:49 2020/8/5
 * @version: 1.0.0
 * @description:
 */
@Data
public class UserAuthorityBO extends MUserAuthorityPO {
    private static final long SerialVersionUID = 1L;
    private Long parent_id;
    /**
     * 标识当前权限类型 1 菜单权限 2 按钮权限
     */
    private Byte type;
}
