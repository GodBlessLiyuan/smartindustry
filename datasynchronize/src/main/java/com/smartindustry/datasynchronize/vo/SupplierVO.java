package com.smartindustry.datasynchronize.vo;

import com.smartindustry.common.pojo.si.SupplierPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hui.feng
 * @date created in 2020/11/6
 * @description
 */
@Data
public class SupplierVO implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 供应商ID
     */
    private Long sid;

    /**
     * 供应商编码
     */
    private String sno;

    /**
     * 供应商名称
     */
    private String sname;

    /**
     * 联系人名称
     */
    private String cname;

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
     * 邮箱
     */
    private String mail;


    /**
     * 详细地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long uid;

    /**
     * 创建时间
     */
    private Date ctime;


    /**
     * 是否删除 : 1：未删除
     2：已删除
     */
    private Byte dr;

    public static List<SupplierVO> convert(List<SupplierPO> pos) {
        List<SupplierVO> vos = new ArrayList<>(pos.size());
        for (SupplierPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    private static SupplierVO convert(SupplierPO po) {
        SupplierVO vo = new SupplierVO();
        vo.setSid(po.getSupplierId());
        vo.setSname(po.getSupplierName());
        vo.setSno(po.getSupplierNo());
        vo.setCname(po.getContactName());
        vo.setPhone(po.getPhone());
        vo.setMail(po.getMail());
        vo.setFax(po.getFax());
        vo.setSite(po.getSite());
        vo.setAddress(po.getAddress());
        vo.setRemark(po.getRemark());
        vo.setCtime(po.getCreateTime());
        vo.setDr(po.getDr());
        return vo;
    }
}
