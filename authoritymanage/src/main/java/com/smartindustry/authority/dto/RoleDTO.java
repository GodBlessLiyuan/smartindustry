package com.smartindustry.authority.dto;

import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.RolePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static RolePO buildPO(OperateDTO dto) {
        RolePO po = new RolePO();
        po.setRoleId(dto.getRid());
        po.setStatus(dto.getStatus());
        return po;
    }

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

    public static List<RolePO> updateList(List<OperateDTO> dtos){
        List<RolePO> pos = new ArrayList<>(dtos.size());
        for(OperateDTO dto:dtos) {
            pos.add(buildPO(dto));
        }
        return pos;
    }

}
