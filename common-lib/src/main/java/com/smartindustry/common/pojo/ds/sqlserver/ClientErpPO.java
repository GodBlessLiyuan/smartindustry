package com.smartindustry.common.pojo.ds.sqlserver;

import java.io.Serializable;
import lombok.Data;

/**
 * 客户
 * @author 
 */
@Data
public class ClientErpPO implements Serializable {
    private Double clientId;

    private String clientCode;

    private String clientName;

    private String address;

    private String telephone;

    private String cellphone;

    private String fax;

    private String homeAddress;

    private String email;

    private String contact;

    private String remark;

    private static final long serialVersionUID = 1L;
}