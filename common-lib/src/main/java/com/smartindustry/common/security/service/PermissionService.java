package com.smartindustry.common.security.service;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.util.ServletUtil;
import com.smartindustry.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * 
 * @author ruoyi
 */
@Service("ss")
public class PermissionService
{
    /** 所有权限标识 */
    private static final String ALL_PERMISSION = "*:*:*";

    /** 管理员角色权限标识 */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    @Autowired
    private TokenService tokenService;

    /**
     * 验证用户是否具备某权限
     * 
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission)
    {
        if (StringUtil.isEmpty(permission))
        {
            return false;
        }
        LoginUserBO loginUserBO = tokenService.getLoginUser(ServletUtil.getRequest());
        if (StringUtil.isNull(loginUserBO) || CollectionUtils.isEmpty(loginUserBO.getPermissions()))
        {
            return false;
        }
        return hasPermissions(loginUserBO.getPermissions(), permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermi(String permission)
    {
        return hasPermi(permission) != true;
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions)
    {
        if (StringUtil.isEmpty(permissions))
        {
            return false;
        }
        LoginUserBO loginUserBO = tokenService.getLoginUser(ServletUtil.getRequest());
        if (StringUtil.isNull(loginUserBO) || CollectionUtils.isEmpty(loginUserBO.getPermissions()))
        {
            return false;
        }
        Set<String> authorities = loginUserBO.getPermissions();
        for (String permission : permissions.split(PERMISSION_DELIMETER))
        {
            if (permission != null && hasPermissions(authorities, permission))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含权限
     * 
     * @param permissions 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtil.trim(permission));
    }
}
