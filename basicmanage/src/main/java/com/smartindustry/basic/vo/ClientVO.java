package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.ClientBO;
import com.smartindustry.common.pojo.si.ClientPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:37 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class ClientVO {
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

    public static List<ClientVO> convert(List<ClientPO> bos) {
        List<ClientVO> vos = new ArrayList<>(bos.size());
        for (ClientPO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static ClientVO convert(ClientPO bo) {
        ClientVO vo = new ClientVO();
        vo.setCid(bo.getClientId());
        vo.setCno(bo.getClientNo());
        vo.setCtid(bo.getClientTypeId());
        vo.setCname(bo.getClientName());
        vo.setContact(bo.getContact());
        vo.setSex(bo.getSex());
        vo.setPhone(bo.getPhone());
        vo.setEmail(bo.getEmail());
        vo.setFax(bo.getFax());
        vo.setUrl(bo.getUrl());
        vo.setClid(bo.getCreditLevelId());
        vo.setArea(bo.getArea());
        vo.setAddress(bo.getAddress());
        vo.setRemark(bo.getRemark());
        return vo;
    }
}
