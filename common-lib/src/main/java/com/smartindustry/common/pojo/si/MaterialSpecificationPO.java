package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_material_specification
 * @author 
 */
@Data
public class MaterialSpecificationPO implements Serializable {
    /**
     * 物料规格说明书表
     */
    private Long materialSpecificationId;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}