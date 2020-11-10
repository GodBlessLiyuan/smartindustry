package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.ForkliftPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/10/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class ForkLiftDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 叉车id
     */
    private Long fid;
    /**
     * 叉车编号
     */
    private String fno;
    /***
     * 叉车名
     */
    private String fname;

    /**
     * 叉车型号
     */
    private String fmodel;
    /**
     * 叉车品牌
     */
    private String fbrand;
    /**
     * 联系人
     */
    private String cname;
    /**
     * 联系人电话
     */
    private String cphone;
    /**
     * 工位一体机IMEI号
     */
    private String imeino;
    /**
     * 作业区域
     */
    private Byte warea;
    /**
     * 供应商名称
     */
    private String sname;

    /**
     * 当前状态
     */
    private Byte status;
    /**
     * 备注
     */
    private String extra;

    public static ForkliftPO createPO(ForkLiftDTO dto) {
        ForkliftPO po = new ForkliftPO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static ForkliftPO buildPO(ForkliftPO forkliftPO, ForkLiftDTO dto) {
        forkliftPO.setForkliftNo(dto.getFno());
        forkliftPO.setForkliftName(dto.getFname());
        forkliftPO.setForkliftModel(dto.getFmodel());
        forkliftPO.setForkliftBrand(dto.getFbrand());
        forkliftPO.setSupplierName(dto.getSname());
        forkliftPO.setContact(dto.getCname());
        forkliftPO.setContactPhone(dto.getCphone());
        forkliftPO.setImeiNo(dto.getImeino());
        forkliftPO.setWorkArea(dto.getWarea());
        forkliftPO.setStatus(dto.getStatus());
        forkliftPO.setExtra(dto.getExtra());
        return forkliftPO;
    }
}
