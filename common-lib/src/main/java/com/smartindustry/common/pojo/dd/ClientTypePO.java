package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_client_type
 * @author 
 */
@Data
public class ClientTypePO implements Serializable {
    private Long clientTypeId;

    private String clientTypeName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}