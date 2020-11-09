package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.WarehouseBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:27
 * @description: 仓库管理
 * @version: 1.0
 */
@Data
public class WarehouseVO implements Serializable {
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
     * 仓库类型名称
     */
    private String wtname;
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

    public static List<WarehouseVO> convert(List<WarehouseBO> bos) {
        List<WarehouseVO> vos = new ArrayList<>(bos.size());
        for (WarehouseBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static WarehouseVO convert(WarehouseBO bo) {
        WarehouseVO vo = new WarehouseVO();

        vo.setWid(bo.getWarehouseId());
        vo.setWno(bo.getWarehouseNo());
        vo.setWname(bo.getWarehouseName());
        vo.setWtid(bo.getWarehouseTypeId());
        vo.setWtname(bo.getWarehouseTypeName());
        vo.setPrin(bo.getPrincipal());
        vo.setPhone(bo.getPhone());
        vo.setArea(bo.getArea());
        vo.setAddr(bo.getAddress());
        vo.setRemark(bo.getRemark());

        return vo;
    }
}
