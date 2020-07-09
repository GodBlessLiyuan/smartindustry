package com.smartindustry.common.pojo.ba;

import java.io.Serializable;
import lombok.Data;

/**
 * ba_m_user_authority
 * @author 
 */
@Data
public class UserAuthorityPO implements Serializable {
    private Long userAuthorityId;

    private Long userId;

    private Long authorityId;

    private String code;

    private String name;

    private static final long serialVersionUID = 1L;
}