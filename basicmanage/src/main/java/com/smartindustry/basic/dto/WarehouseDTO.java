package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.WarehousePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:26
 * @description: 仓库管理
 * @version: 1.0
 */
@Data
public class WarehouseDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 仓库ID
     */
    private Long wid;
    /**
     * 仓库编号
     */
    private String wno;
    /**
     * 仓库名称
     */
    private String wname;
    /**
     * 仓库类型 ID
     */
    private Long wtid;
    /**
     * 负责人
     */
    private String prin;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 地区信息
     */
    private String area;
    /**
     * 地址
     */
    private String addr;
    /**
     * 备注
     */
    private String remark;

    public static WarehousePO createPO(WarehouseDTO dto) {
        WarehousePO po = new WarehousePO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static WarehousePO buildPO(WarehousePO po, WarehouseDTO dto) {
        po.setWarehouseNo(dto.getWno());
        po.setWarehouseName(dto.getWname());
        po.setWarehouseTypeId(dto.getWtid());
        po.setPrincipal(dto.getPrin());
        po.setPhone(dto.getPhone());
        po.setArea(dto.getArea());
        po.setAddress(dto.getAddr());
        po.setRemark(dto.getRemark());
        return po;
    }
}
