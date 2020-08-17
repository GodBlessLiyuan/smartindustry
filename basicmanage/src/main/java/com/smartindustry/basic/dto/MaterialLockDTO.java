package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.MaterialLockPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/8/17 11:30
 * @description: 物料锁定
 * @version: 1.0
 */
@Data
public class MaterialLockDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mlkid;
    private String mlkname;

    public static MaterialLockPO createPO(MaterialLockDTO dto, Long userId) {
        MaterialLockPO po = new MaterialLockPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static MaterialLockPO buildPO(MaterialLockPO po, MaterialLockDTO dto) {
        po.setMaterialLockName(dto.getMlkname());
        return po;
    }
}
