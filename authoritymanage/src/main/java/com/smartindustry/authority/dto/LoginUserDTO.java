package com.smartindustry.authority.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartindustry.common.pojo.am.UserPO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:23 2020/8/3
 * @version: 1.0.0
 * @description:
 */
@Data
public class LoginUserDTO implements UserDetails {
    private static final long SerialVersionUID = 1L;
    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登陆时间
     */
    private Long loginTime;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 用户信息
     */
    private UserPO user;
    /**
     * 权限列表
     */
    private Set<String> permissions;
    /**
     * 权限列表ID
     */
    private List<Long> permissionIds;

    public LoginUserDTO(UserPO user, Set<String> permissions, List<Long> permissionIds)
    {
        this.user = user;
        this.permissions = permissions;
        this.permissionIds = permissionIds;
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUsername();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

}
