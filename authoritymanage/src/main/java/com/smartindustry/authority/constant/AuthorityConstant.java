package com.smartindustry.authority.constant;

/**
 * 通用常量信息
 * 
 * @author jiangzhaojie
 */
public class AuthorityConstant {
    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";
    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

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

    /**
     * 状态值， 1 启用 2 禁用
     */
    public static final Byte STATUS_OK = 1;
    public static final Byte STATUS_DISABLE = 2;


    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 菜单标识
     */
    public static final Byte MENUTYPE = 1;

    /**
     * 按钮标识
     */
    public static final Byte BUTTONTYPE = 2;


    /**
     * 操作记录
     */
    public static final String INSERTRECORD = "新增";
    public static final String UPDATERECORD = "修改";
    public static final String USERECORD = "启用";
    public static final String DISABLERECORD = "禁用";
    public static final String DELETERECORD = "删除";

    /**
     * 操作成功
     */
    public static final int SUCCESS = 1000;
    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;


}
