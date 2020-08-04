package com.smartindustry.authority.service.impl;

import com.smartindustry.authority.service.IAuthorityService;
import com.smartindustry.authority.util.StringUtils;
import com.smartindustry.common.mapper.am.AuthorityMapper;
import com.smartindustry.common.pojo.am.AuthorityPO;
import com.smartindustry.common.pojo.am.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:44 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService {

    @Autowired
    AuthorityMapper authorityMapper;

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(UserPO user) {
        Set<String> perms = new HashSet<String>();
        if ("admin".equals(user.getUsername())) {
            perms.add("*:*:*");
        }else {
            perms.addAll(selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId)
    {
        List<String> perms = authorityMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }
}
