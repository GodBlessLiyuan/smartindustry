package com.smartindustry.authority.service;

import com.smartindustry.common.pojo.am.UserPO;

import java.util.Set;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:44 2020/8/3
 * @version: 1.0.0
 * @description:
 */
public interface IAuthorityService {

    /**
     * 获取用户菜单数据权限
     * @param userPO
     * @return
     */
    Set<String> getMenuPermission(UserPO userPO);
}
