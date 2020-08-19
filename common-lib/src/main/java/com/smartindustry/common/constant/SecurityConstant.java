package com.smartindustry.common.constant;

/**
 * 通用常量信息
 *
 * @author jiangzhaojie
 */
public class SecurityConstant {
    /**
     * 所有权限标识
     */
    public static final String ALL_PERMISSION = "*:*:*";
    /**
     * 权限分隔符
     */
    public static final String PERMISSION_SEPARATOR = ",";
    /**
     * 超级管理员
     */
    public static final String SUPER_ADMIN = "admin";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens";
    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 1401;

    /**
     * 用户的状态 1 启用2  禁用
     */
    public static final Byte USER_DISABLE = 2;

    /**
     * session token
     */
    public static final String SESSION_TOKEN = "token";
    /**
     * session user
     */
    public static final String SESSION_USER = "user";
}
