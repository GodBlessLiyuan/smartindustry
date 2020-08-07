package com.smartindustry.authority.service.impl;

import com.smartindustry.authority.constant.AuthorityConstant;
import com.smartindustry.authority.service.IAuthorityService;
import com.smartindustry.authority.util.StringUtils;
import com.smartindustry.authority.dto.LoginUserDTO;
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

/**
 * @author: jiangzhaojie
 * @date: Created in 16:41 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private IAuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserPO user = userMapper.queryByName(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        else if (AuthorityConstant.USER_DELETE.equals(user.getDr()))
        {
            log.info("登录用户：{} 已被删除.", username);
//            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (AuthorityConstant.USER_DISABLE.equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
//            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }
        return createLoginUser(user);
    }

    // 获得当前用户的信息以及所有权限
    public UserDetails createLoginUser(UserPO user) {
        return new LoginUserDTO(user,authorityService.getMenuPermission(user),authorityMapper.queryPermissionId(user.getUserId()));
    }
}
