package com.smartindustry.workbench.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hui.feng
 * @date created in 2020/9/25
 * @description
 */
@Data
public class OperateDTO implements Serializable {

    /**
     * 工作台权限类型
     */
    private Byte btype;

    /**
     * 工作台权限类型
     */
    private Byte bmodule;


}
