package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * si_client
 * @author 
 */
@Data
public class ClientPO implements Serializable {
    /**
     * 客户id
     */
    private Long clientId;

    /**
     * 客户代码
     */
    private String clientNo;

    /**
     * 客户类型ID
     */
    private Long clientTypeId;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系人性别 : 1：男
2：女
     */
    private Byte sex;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 电话号码
     */
    private String telephone;

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
    private Long creditLevelId;

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 : 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}