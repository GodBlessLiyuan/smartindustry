package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.MaterialPO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:26
 * @description: 物料管理
 * @version: 1.0
 */
@Data
public class MaterialDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mid;
    private String mno;

    public static MaterialPO createPO(MaterialDTO dto) {
        MaterialPO po = new MaterialPO();

        return buildPO(po, dto);
    }

    public static MaterialPO buildPO(MaterialPO po, MaterialDTO dto) {

        return po;
    }
}
