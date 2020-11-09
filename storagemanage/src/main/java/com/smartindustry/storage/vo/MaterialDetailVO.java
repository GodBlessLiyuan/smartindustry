package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.MaterialDetailBO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:08 2020/11/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 栈板rfid
     */
    private String mrfid;
    /**
     * 仓库名称
     */
    private String wname;
    /**
     * 储位编号
     */
    private String lno;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料编号
     */
    private String mno;
    /**
     * 包装体积
     */
    private String volume;

    /**
     * 标识是否是备料区 是否是备料区false还是成品区true
     */
    private Boolean flag;

    public static List<MaterialDetailVO> convert(List<MaterialDetailBO> bos) {
        List<MaterialDetailVO> vos = new ArrayList<>(bos.size());
        for (MaterialDetailBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialDetailVO convert(MaterialDetailBO bo) {
        MaterialDetailVO vo = new MaterialDetailVO();
        vo.setMrfid(bo.getRfid());
        vo.setLno(bo.getLocationNo());
        vo.setMname(bo.getMaterialName());
        vo.setVolume(bo.getVolume());
        vo.setWname(bo.getWarehouseName());
        vo.setMno(bo.getMaterialNo());
        return vo;
    }
}
