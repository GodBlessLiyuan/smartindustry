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
    private String sname;
    private String sgname;
    private String phone;
    private String cname;


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
        vo.setSname(bo.getSupplierName());
        vo.setSgname(bo.getSupplierGroupName());
        vo.setPhone(bo.getPhone());
        vo.setCname(bo.getContactName());
        return vo;
    }
}
