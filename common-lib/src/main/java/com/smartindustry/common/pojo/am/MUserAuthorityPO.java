package com.smartindustry.common.pojo.am;

import java.io.Serializable;
import lombok.Data;

/**
 * am_m_user_authority
 * @author 
 */
@Data
public class MUserAuthorityPO implements Serializable {
    private Long userAuthorityId;

    private Long userId;

    private Long authorityId;

    private String authorityName;

    private static final long serialVersionUID = 1L;
}