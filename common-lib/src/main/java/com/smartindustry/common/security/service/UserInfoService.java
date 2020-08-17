package com.smartindustry.common.security.service;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.constant.SecurityConstant;
import com.smartindustry.common.mapper.am.AuthorityMapper;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.pojo.am.UserPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:41 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class UserInfoService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPO user = userMapper.queryByName(username);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (SecurityConstant.USER_DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
//            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }

        return createLoginUser(user);
    }

    // 获得当前用户的信息以及所有权限
    private UserDetails createLoginUser(UserPO user) {
        return new LoginUserBO(user, getMenuPermission(user), authorityMapper.queryPermissionId(user.getUserId()));
    }

    private Set<String> getMenuPermission(UserPO user) {
        Set<String> perms = new HashSet<>();
        if (SecurityConstant.SUPER_ADMIN.equals(user.getUsername())) {
            perms.add(SecurityConstant.ALL_PERMISSION);
        } else {
            perms.addAll(selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }

    private Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = authorityMapper.queryPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (!StringUtils.isEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(SecurityConstant.PERMISSION_SEPARATOR)));
            }
        }
        return permsSet;
    }
}
