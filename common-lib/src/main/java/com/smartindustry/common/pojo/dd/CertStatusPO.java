package com.smartindustry.common.pojo.dd;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dd_cert_status
 * @author 
 */
@Data
public class CertStatusPO implements Serializable {
    private Long certStatusId;

    private String certStatusName;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}