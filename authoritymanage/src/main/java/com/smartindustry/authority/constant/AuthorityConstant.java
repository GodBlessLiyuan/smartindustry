package com.smartindustry.authority.constant;

/**
 * 通用常量信息
 * 
 * @author jiangzhaojie
 */
public class AuthorityConstant {
    /**
     * 状态值， 1 启用 2 禁用
     */
    public static final Byte STATUS_OK = 1;
    public static final Byte STATUS_DISABLE = 2;

    /**
     * 菜单标识
     */
    public static final Byte TYPE_MENU = 1;

    /**
     * 按钮标识
     */
    public static final Byte TYPE_BUTTON = 2;

    /**
     * 操作记录
     */
    public static final String RECORD_INSERT = "新增";
    public static final String RECORD_UPDATE = "修改";
    public static final String RECORD_USE = "启用";
    public static final String RECORD_DISABLE = "禁用";
    public static final String RECORD_DELETE = "删除";

    /**
     * 权限类型名称
     */
    public static final String NAME_MENU= "menu";
    public static final String NAME_BUTTON= "button";

    /**
     * 所有菜单按钮权限列表的过期时间
     */
    public static final Long EXPIRE_TIME = 30L;

}
