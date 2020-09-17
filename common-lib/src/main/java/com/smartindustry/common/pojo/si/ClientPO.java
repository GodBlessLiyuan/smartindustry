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
    private Long clientId;

    private String clientNo;

    private Long clientTypeId;

    private String clientName;

    private String contact;

    /**
     * 1：男
2：女
     */
    private Byte sex;

    private String phone;

    private String email;

    private String fax;

    private String url;

    private Long creditLevelId;

    private String area;

    private String address;

    private String remark;

    private Date createTime;

    private Date updateTime;

    /**
     * 1 未删除
2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}