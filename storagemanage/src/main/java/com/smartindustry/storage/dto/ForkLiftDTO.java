package com.smartindustry.storage.dto;

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
    /**
     * 叉车型号
     */
    private String fmodel;
    /**
     * 叉车品牌
     */
    private String fbrand;
    /**
     * 供应商名称
     */
    private String sname;
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

    public static ForkliftPO buildPO(ForkliftPO po, ForkLiftDTO dto) {
//        po
        po.setExtra(dto.getExtra());
        return po;
    }
}
