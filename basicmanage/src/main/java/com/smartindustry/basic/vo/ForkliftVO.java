package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.si.ForkliftPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:20 2020/10/29
 * @version: 1.0.0
 * @description:
 */
@Data
public class ForkliftVO {
    /**
     * 叉车id
     */
    private Long fid;
    /**
     * 叉车编号
     */
    private String fno;
    /**
     * 叉车型号
     */
    private String fmodel;
    /**
     * 工位一体机IMEI号
     */
    private String imeino;
    /**
     * 叉车品牌
     */
    private String fbrand;
    /**
     * 供应商名称
     */
    private String sname;
    /**
     * 作业区域
     */
    private Byte warea;
    /**
     * 当前状态
     */
    private Byte status;

    public static List<ForkliftVO> convert(List<ForkliftPO> bos) {
        List<ForkliftVO> vos = new ArrayList<>(bos.size());
        for (ForkliftPO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static ForkliftVO convert(ForkliftPO bo) {
        ForkliftVO vo = new ForkliftVO();
        vo.setFid(bo.getForkliftId());
        vo.setFno(bo.getForkliftNo());
        vo.setFmodel(bo.getForkliftModel());
        vo.setImeino(bo.getImeiNo());
        vo.setFbrand(bo.getForkliftBrand());
        vo.setSname(bo.getSupplierName());
        vo.setWarea(bo.getWorkArea());
        vo.setStatus(bo.getStatus());
        return vo;
    }
}
