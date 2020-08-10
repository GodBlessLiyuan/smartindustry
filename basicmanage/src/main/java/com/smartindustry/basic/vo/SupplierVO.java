package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.SupplierBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:27
 * @description: 供应商管理
 * @version: 1.0
 */
@Data
public class SupplierVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long sid;
    private String sno;
    private Long sgid;
    private String sgname;
    private Long csid;
    private String csname;
    private Long stid;
    private String stname;
    private Long spid;
    private String spname;
    private Long cid;
    private String cname;
    private String sname;
    private String contact;
    private String phone;
    private String fax;
    private String site;
    private String mail;
    private String area;
    private String addr;
    private String remark;


    public static List<SupplierVO> convert(List<SupplierBO> bos) {
        List<SupplierVO> vos = new ArrayList<>(bos.size());
        for (SupplierBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static SupplierVO convert(SupplierBO bo) {
        SupplierVO vo = new SupplierVO();
        vo.setSid(bo.getSupplierId());
        vo.setSno(bo.getSupplierNo());
        vo.setSgid(bo.getSupplierGroupId());
        vo.setSgname(bo.getSupplierGroupName());
        vo.setCsid(bo.getCertStatusId());
        vo.setCsname(bo.getCertStatusName());
        vo.setStid(bo.getSupplierTypeId());
        vo.setStname(bo.getSupplierTypeName());
        vo.setSpid(bo.getSettlePeriodId());
        vo.setSpname(bo.getSettlePeriodName());
        vo.setCid(bo.getCurrencyId());
        vo.setCname(bo.getContactName());
        vo.setSname(bo.getSupplierName());
        vo.setContact(bo.getContactName());
        vo.setPhone(bo.getPhone());
        vo.setFax(bo.getFax());
        vo.setSite(bo.getSite());
        vo.setMail(bo.getMail());
        vo.setArea(bo.getArea());
        vo.setAddr(bo.getAddress());
        vo.setRemark(bo.getRemark());
        return vo;
    }
}
