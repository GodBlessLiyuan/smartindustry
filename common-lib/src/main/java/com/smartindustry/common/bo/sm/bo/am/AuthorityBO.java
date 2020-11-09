package com.smartindustry.common.bo.sm.bo.am;

import com.smartindustry.common.pojo.am.AuthorityPO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 8:36 2020/8/5
 * @version: 1.0.0
 * @description:
 */
@Data
public class AuthorityBO extends AuthorityPO {
    private static final long SerialVersionUID = 1L;

    private String parentName;
    /**
     * 子权限列表
     */
    List<AuthorityBO> bos;
}
