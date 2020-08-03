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
    private Long mtid;
    private String mtname;
    private Long hlid;
    private String hlname;
    private Long mlid;
    private String mlname;
    private Long muid;
    private String muname;
    private Long mvid;
    private String mvname;
    private Long pllid;
    private String pllname;
    private Long lcsid;
    private String lcsname;
    private String mname;
    private Integer ddays;
    private String moq;
    private String mmodel;
    private String mdraw;
    private Long sid;
    private String sname;
    private String mdesc;

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
        vo.setMtid(bo.getMaterialTypeId());
        vo.setMtname(bo.getMaterialTypeName());
        vo.setHlid(bo.getHumidityLevelId());
        vo.setHlname(bo.getHumidityLevelName());
        vo.setMlid(bo.getMaterialLevelId());
        vo.setMlname(bo.getMaterialLevelName());
        vo.setMuid(bo.getMeasureUnitId());
        vo.setMuname(bo.getMeasureUnitName());
        vo.setMvid(bo.getMaterialVersionId());
        vo.setMvname(bo.getMaterialVersionName());
        vo.setPllid(bo.getProduceLossLevelId());
        vo.setPllname(bo.getProduceLossLevelName());
        vo.setLcsid(bo.getLifeCycleStateId());
        vo.setLcsname(bo.getLifeCycleStateName());
        vo.setMname(bo.getMaterialName());
        vo.setDdays(bo.getDeliveryDays());
        vo.setMoq(bo.getMoq());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMdraw(bo.getMaterialDraw());
        vo.setSid(bo.getSupplierId());
        vo.setSname(bo.getSupplierName());
        vo.setMdesc(bo.getMaterialDesc());
        return vo;
    }
}
