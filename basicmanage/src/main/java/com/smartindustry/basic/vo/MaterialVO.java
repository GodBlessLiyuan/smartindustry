package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.pojo.si.MaterialPO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:11 2020/10/27
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 物料id
     */
    private Long mid;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料型号
     */
    private String mmodel;
    /**
     * 物料计量单位
     */
    private String muname;

    private List<FileVO> files;
    //物料描述
    private String mdes;
    //物料类型
    private Byte mtype;
    //所属供应商
    private String sname;
    private BigDecimal price;

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
        vo.setMname(bo.getMaterialName());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMuname(bo.getMeasureUnitName());
        vo.setMtype(bo.getMaterialType());
        vo.setMdes(bo.getMaterialDesc());
        vo.setPrice(bo.getPrice());
        return vo;
    }


    public static List<MaterialVO> convertPO(List<MaterialPO> pos) {
        List<MaterialVO> vos = new ArrayList<>(pos.size());
        for (MaterialPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static MaterialVO convert(MaterialPO po) {
        MaterialVO vo = new MaterialVO();
        vo.setMid(po.getMaterialId());
        vo.setMno(po.getMaterialNo());
        vo.setMname(po.getMaterialName());
        vo.setMmodel(po.getMaterialModel());
        return vo;
    }

    public static MaterialVO convert(MaterialBO bo, FilePathConfig config) {
        MaterialVO vo = new MaterialVO();
        vo.setMid(bo.getMaterialId());
        vo.setMno(bo.getMaterialNo());
        vo.setMuname(bo.getMeasureUnitName());
        vo.setMname(bo.getMaterialName());
        vo.setMmodel(bo.getMaterialModel());
        vo.setMtype(bo.getMaterialType());
        vo.setMdes(bo.getMaterialDesc());
        vo.setSname(bo.getSupplierName());
        vo.setPrice(bo.getPrice());
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
}
