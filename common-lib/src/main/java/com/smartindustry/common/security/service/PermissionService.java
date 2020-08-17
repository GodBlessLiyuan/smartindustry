package com.smartindustry.common.security.service;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.constant.SecurityConstant;
import com.smartindustry.common.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author ruoyi
 */
@Service("ss")
public class PermissionService {

    @Autowired
    private TokenService tokenService;

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }

        LoginUserBO loginUserBO = tokenService.getLoginUser(ServletUtil.getRequest());
        if (null == loginUserBO || CollectionUtils.isEmpty(loginUserBO.getPermissions())) {
            return false;
        }
        return hasPermissions(loginUserBO.getPermissions(), permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions) {
        if (null == permissions) {
            return false;
        }

        LoginUserBO loginUserBO = tokenService.getLoginUser(ServletUtil.getRequest());
        if (null == loginUserBO || CollectionUtils.isEmpty(loginUserBO.getPermissions())) {
            return false;
        }

        Set<String> authorities = loginUserBO.getPermissions();
        for (String permission : permissions.split(SecurityConstant.PERMISSION_SEPARATOR)) {
            if (!StringUtils.isEmpty(permission) && hasPermissions(authorities, permission)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(SecurityConstant.ALL_PERMISSION) || permissions.contains(permission.trim());
    }
}
