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
     * 未授权
     */
    public static final int UNAUTHORIZED = 1020;

    /**
     * 用户的状态 1 启用2  禁用
     */
    public static final String USER_OK = "1";
    public static final String USER_DISABLE = "2";
    /**
     * 用户是否被删除
     */
    public static final String USER_NOTDELETE = "1";
    public static final String USER_DELETE = "2";

}
