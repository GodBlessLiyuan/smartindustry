package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.si.ClientPO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:55 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class ClientDTO {
    private static final long SerialVersionUID = 1L;
    /**
     * 客户id
     */
    private Long cid;
    /**
     * 客户代码
     */
    private String cno;
    /**
     * 客户类型
     */
    private Long ctid;
    /**
     * 客户类型名称
     */
    private String ctname;
    /**
     * 客户名称
     */
    private String cname;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 性别
     */
    private Byte sex;
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
    /**
     * 信用等级id
     */
    private Long clid;
    /**
     * 信用等级名称
     */
    private String clname;
    /**
     * 地区
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;


    public static ClientPO createPO(ClientDTO dto) {
        ClientPO po = new ClientPO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static ClientPO buildPO(ClientPO po, ClientDTO dto) {
        po.setClientNo(dto.getCno());
        po.setClientTypeId(dto.getCtid());
        po.setClientName(dto.getCname());
        po.setContact(dto.getContact());
        po.setSex(dto.getSex());
        po.setPhone(dto.getPhone());
        po.setEmail(dto.getEmail());
        po.setFax(dto.getFax());
        po.setUrl(dto.getUrl());
        po.setCreditLevelId(dto.getClid());
        po.setArea(dto.getArea());
        po.setAddress(dto.getAddress());
        po.setRemark(dto.getRemark());
        return po;
    }

}
