package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.SupplierPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:26
 * @description: 供应商管理
 * @version: 1.0
 */
@Data
public class SupplierDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 供应商ID
     */
    private Long sid;
    /**
     * 供应商代码
     */
    private String sno;
    /**
     * 供应商组ID
     */
    private Long sgid;
    /**
     * 认证状态ID
     */
    private Long csid;
    /**
     * 类型
     */
    private Long stid;
    /**
     * 供应商名称
     */
    private String sname;
    /**
     * 结算期限ID
     */
    private Long spid;
    /**
     * 币种
     */
    private Long cid;
    /**
     * 联系人名称
     */
    private String contact;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 传真
     */
    private String fax;
    /**
     * 网址
     */
    private String site;
    /**
     * Mail
     */
    private String mail;
    /**
     * 地区
     */
    private String area;
    /**
     * 详细地址
     */
    private String addr;
    /**
     * 备注
     */
    private String remark;

    public static SupplierPO createPO(SupplierDTO dto) {
        SupplierPO po = new SupplierPO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static SupplierPO buildPO(SupplierPO po, SupplierDTO dto) {
        po.setSupplierNo(dto.getSno());
        po.setSupplierName(dto.getSname());
        po.setContactName(dto.getContact());
        po.setPhone(dto.getPhone());
        po.setFax(dto.getFax());
        po.setSite(dto.getSite());
        po.setMail(dto.getMail());
        po.setArea(dto.getArea());
        po.setAddress(dto.getAddr());
        po.setRemark(dto.getRemark());
        return po;
    }
}
