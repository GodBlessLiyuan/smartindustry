package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.config.FilePathConfig;
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
    private Byte mtype;
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
    private List<FileVO> files;
    private MaterialAttributeVO mattribute;

    public static List<MaterialVO> convert(List<MaterialBO> bos, FilePathConfig config) {
        List<MaterialVO> vos = new ArrayList<>(bos.size());
        for (MaterialBO bo : bos) {
            vos.add(convert(bo, config));
        }
        return vos;
    }

    public static MaterialVO convert(MaterialBO bo, FilePathConfig config) {
        MaterialVO vo = new MaterialVO();
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        vo.setMtype(bo.getMaterialType());
        vo.setMtid(bo.getMaterialTypeId());
        vo.setMtname(bo.getMaterialName());
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
        if (null != bo.getFiles() && bo.getFiles().size() > 0) {
            List<FileVO> files = new ArrayList<>(bo.getFiles().size());
            for (MaterialSpecificationPO po : bo.getFiles()) {
                files.add(new FileVO(po.getFileName(), config.getPublicPath() + po.getFilePath()));
            }
            vo.setFiles(files);
        }
        return vo;
    }

    @Data
    public static class FileVO {
        private String name;
        private String url;

        public FileVO(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

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
        vo.setMdesc(bo.getMaterialDesc());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMno(bo.getMaterialNo());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setSname(bo.getSupplierName());
        vo.setMuname(bo.getMeasureUnitName());
        vo.setMname(bo.getMaterialName());
        return vo;
    }
}
