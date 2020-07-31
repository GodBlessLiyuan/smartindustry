package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.MaterialVersionPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/31 9:38
 * @description: TODO
 * @version: 1.0
 */
@Data
public class MaterialVersionDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mvid;
    private String mvname;

    public static MaterialVersionPO createPO(MaterialVersionDTO dto, Long userId) {
        MaterialVersionPO po = new MaterialVersionPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static MaterialVersionPO buildPO(MaterialVersionPO po, MaterialVersionDTO dto) {
        po.setMaterialVersionName(dto.getMvname());
        return po;
    }
}