package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 物品
 * @author 
 */
@Data
public class MaterialErpPO implements Serializable {
    private Double materialId;

    private String materialNo;

    private String materialName;

    private String materialModel;

    private String materialUnit;

    private Double materialType;

    private String remark;

    private static final long serialVersionUID = 1L;
}