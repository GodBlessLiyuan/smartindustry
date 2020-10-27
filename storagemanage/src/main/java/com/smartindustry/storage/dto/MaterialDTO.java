package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:07 2020/10/27
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料型号
     */
    private String mmodel;
    /**
     * 供应商名称
     */
    private String sname;
}
