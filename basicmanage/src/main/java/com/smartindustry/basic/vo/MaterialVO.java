package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.MaterialBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:27
 * @description: 物料管理
 * @version: 1.0
 */
@Data
public class MaterialVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mid;
    private String mno;

    public static List<MaterialVO> convert(List<MaterialBO> bos) {
        List<MaterialVO> vos = new ArrayList<>(bos.size());
        for (MaterialBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialVO convert(MaterialBO bo) {
        MaterialVO vo = new MaterialVO();
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        return vo;
    }
}
