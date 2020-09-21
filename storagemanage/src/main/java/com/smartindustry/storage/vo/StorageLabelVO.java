package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.PrintLabelBO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/3 9:24
 * @description: 入库标签 VO
 * @version: 1.0
 */
@Data
public class StorageLabelVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物料入库组ID
     */
    private Long sgid;
    /**
     * 打印标签ID
     */
    private Long plid;

    /**
     * PID
     */
    private String pid;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 入库数量
     */
    private Integer num;
    /**
     * 仓库ID
     */
    private Long whid;
    /**
     * 库位ID
     */
    private Long lid;
    /**
     * 仓库名称
     */
    private String whname;
    /**
     * 库位编号
     */
    private String lno;

    public static StorageLabelVO convert(PrintLabelBO bo, Long sgid) {
        StorageLabelVO vo = new StorageLabelVO();
        vo.setPlid(bo.getPrintLabelId());
        vo.setPid(bo.getPackageId());
        vo.setMno(bo.getMaterialNo());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setNum(bo.getNum());
        vo.setSgid(sgid);
        vo.setWhid(bo.getWarehouseId());
        vo.setWhname(bo.getWarehouseName());
        vo.setLid(bo.getLocationId());
        vo.setLno(bo.getLocationNo());
        return vo;
    }
}
