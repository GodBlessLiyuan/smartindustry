package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.si.ClientPO;
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
public class ClientVO implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 客户id
     */
    private Long cid;

    /**
     * 客户代码
     */
    private String cno;


    /**
     * 客户名称
     */
    private String cname;

    /**
     * 联系人
     */
    private String contact;

    private String tphone;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 客户邮箱
     */
    private String email;

    /**
     * 传真
     */
    private String fax;

    /**
     * 网址
     */
    private String url;

    private String area;
    /**
     * 详细地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 是否删除 : 1 未删除
     2 已删除
     */
    private Byte dr;

    public static List<ClientVO> convert(List<ClientPO> pos) {
        List<ClientVO> vos = new ArrayList<>(pos.size());
        for (ClientPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static ClientVO convert(ClientPO po) {
        ClientVO vo = new ClientVO();
        vo.setCid(po.getClientId());
        vo.setCno(po.getClientNo());
        vo.setCname(po.getClientName());
        vo.setContact(po.getContact());
        vo.setPhone(po.getPhone());
        vo.setTphone(po.getTelephone());
        vo.setEmail(po.getEmail());
        vo.setFax(po.getFax());
        vo.setUrl(po.getUrl());
        vo.setAddress(po.getAddress());
        vo.setRemark(po.getRemark());
        vo.setCtime(po.getCreateTime());
        vo.setDr(po.getDr());
        vo.setArea(po.getArea());
        return vo;
    }
}
