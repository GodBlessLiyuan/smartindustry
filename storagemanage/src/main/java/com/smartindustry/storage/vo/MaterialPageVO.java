package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.pojo.si.MaterialPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/22 14:53
 * @description: 物料分页信息
 * @version: 1.0
 */
@Data
public class MaterialPageVO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long mid;
    private String mno;
    private Long mtid;
    private String mname;
    private String mmodel;
    private String mdesc;


    /**
     * pos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<MaterialPageVO> convert(List<MaterialBO> bos) {
        List<MaterialPageVO> vos = new ArrayList<>(bos.size());

        for (MaterialBO bo : bos) {
            vos.add(convert(bo));
        }

        return vos;
    }

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */
    public static MaterialPageVO convert(MaterialBO po) {
        MaterialPageVO vo = new MaterialPageVO();
        vo.setMid(po.getMaterialId());
        vo.setMno(po.getMaterialNo());
        vo.setMtid(po.getMaterialTypeId());
        vo.setMname(po.getMaterialName());
        vo.setMmodel(po.getMaterialModel());
        vo.setMdesc(po.getMaterialDesc());
        return vo;
    }
}
