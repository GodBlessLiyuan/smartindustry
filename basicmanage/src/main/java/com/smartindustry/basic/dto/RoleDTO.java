package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.am.RolePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:46 2020/7/31
 * @version: 1.0.0
 * @description:
 */
@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long rid;
    private String rname;
    private Byte status;
    private String rdesc;

    public static RolePO createPO(RoleDTO dto) {
        RolePO po = new RolePO();
        po.setRoleId(dto.getRid());
        po.setRoleName(dto.getRname());
        po.setRoleDesc(dto.getRdesc());
        po.setStatus(dto.getStatus());
        po.setCreateTime(new Date());
        po.setDr((byte)1);
        return po;
    }


}
