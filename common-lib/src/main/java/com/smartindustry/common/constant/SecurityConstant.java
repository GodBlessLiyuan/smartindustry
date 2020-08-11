package com.smartindustry.common.constant;

/**
 * 通用常量信息
 * 
 * @author jiangzhaojie
 */
public class SecurityConstant {
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
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";
    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 1020;

}
