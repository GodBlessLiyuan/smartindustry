package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.CertStatusPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:34
 * @description: 认证状态
 * @version: 1.0
 */
@Data
public class CertStatusDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long csid;
    private String csname;

    public static CertStatusPO createPO(CertStatusDTO dto, Long userId) {
        CertStatusPO po = new CertStatusPO();
        po.setUserId(userId);
        po.setCreateTime(new Date());
        return buildPO(po, dto);
    }

    public static CertStatusPO buildPO(CertStatusPO po, CertStatusDTO dto) {
        po.setCertStatusName(dto.getCsname());
        return po;
    }
}
