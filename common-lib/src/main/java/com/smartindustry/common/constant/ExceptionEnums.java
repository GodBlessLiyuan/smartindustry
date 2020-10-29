package com.smartindustry.common.constant;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:34 2020/10/26
 * @version: 1.0.0
 * @description:
 */
public enum ExceptionEnums {

    OK(1000,"正常"),
    NO_EXIST(1002,"数据不存在或已被删除");

    private String msg;
    private int code;

    private ExceptionEnums(int code, String msg)
    {
        this.code=code;
        this.msg=msg;
    }

    public String getMsg()
    {
        return this.msg;
    }
    public int getCode() {
        return this.code;
    }

}
