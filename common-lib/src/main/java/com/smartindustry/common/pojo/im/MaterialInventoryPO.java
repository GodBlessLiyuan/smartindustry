package com.smartindustry.common.pojo.im;

import java.io.Serializable;
import lombok.Data;

/**
 * im_material_inventory
 * @author 
 */
@Data
public class MaterialInventoryPO implements Serializable {
    private Long materialInventoryId;

    private Long materialId;

    private Integer wayNum;

    private Integer storageNum;

    private Integer lockNum;

    private Integer relateNum;

    private Byte status;

    private static final long serialVersionUID = 1L;
}