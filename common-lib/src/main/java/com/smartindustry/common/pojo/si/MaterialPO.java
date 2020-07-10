package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * si_material
 *
 * @author
 */
@Data
public class MaterialPO implements Serializable {
    private String materialNo;

    private String materialName;

    /**
     * 1：原料；2：半成品；3：成品
     */
    private Byte materialType;

    private String materialModel;

    private String materialDesc;

    private Byte testType;

    private Date createTime;

    private Date updateTime;

    /**
     * 1：未删除
     * 2：已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}