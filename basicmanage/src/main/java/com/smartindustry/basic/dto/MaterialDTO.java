package com.smartindustry.basic.dto;

import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.si.MaterialSpecificationPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:26
 * @description: 物料管理
 * @version: 1.0
 */
@Data
public class MaterialDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 物料ID
     */
    private Long mid;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料类型
     */
    private Byte mtype;
    /**
     * 物料二级类型ID
     */
    private Long mtid;
    /**
     * 湿度等级
     */
    private Long hlid;
    /**
     * 物料层级
     */
    private Long mlid;
    /**
     * 计量单位
     */
    private Long muid;
    /**
     * 版本
     */
    private Long mvid;
    /**
     * 生产损耗等级
     */
    private Long pllid;
    /**
     * 生命周期状态
     */
    private Long lcsid;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 交期天数（天）
     */
    private Integer ddays;
    /**
     * MOQ
     */
    private String moq;
    /**
     * 规格型号
     */
    private String mmodel;
    /**
     * 图号
     */
    private String mdraw;
    /**
     * 所属供应商
     */
    private Long sid;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 物料规格书
     */
    private List<FileDTO> files;

    public static MaterialPO createPO(MaterialDTO dto) {
        MaterialPO po = new MaterialPO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static MaterialPO buildPO(MaterialPO po, MaterialDTO dto) {
        po.setMaterialNo(dto.getMno());
        po.setMaterialType(dto.getMtype());
        po.setMaterialTypeId(dto.getMtid());
        po.setHumidityLevelId(dto.getHlid());
        po.setMaterialLevelId(dto.getMlid());
        po.setMeasureUnitId(dto.getMuid());
        po.setMaterialVersionId(dto.getMvid());
        po.setProduceLossLevelId(dto.getPllid());
        po.setLifeCycleStateId(dto.getLcsid());
        po.setMaterialName(dto.getMname());
        po.setDeliveryDays(dto.getDdays());
        po.setMoq(dto.getMoq());
        po.setMaterialModel(dto.getMmodel());
        po.setMaterialDraw(dto.getMdraw());
        po.setSupplierId(dto.getSid());
        po.setMaterialDesc(dto.getMdesc());
        return po;
    }

    public static List<MaterialSpecificationPO> createFilePO(MaterialDTO dto, FilePathConfig config) {
        List<MaterialSpecificationPO> pos = new ArrayList<>(dto.getFiles().size());
        Date curDate = new Date();
        for (FileDTO file : dto.getFiles()) {
            MaterialSpecificationPO po = new MaterialSpecificationPO();
            po.setMaterialId(dto.getMid());
            po.setFileName(file.getName());
            po.setFilePath(file.getUrl().split(config.getPublicPath())[1]);
            po.setCreateTime(curDate);
            pos.add(po);
        }

        return pos;
    }

    @Data
    private static class FileDTO {
        private String name;
        private String url;
    }
}
