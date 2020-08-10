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
    private Long materialSpecificationId;

    private Long materialId;

    private String fileName;

    private String filePath;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}