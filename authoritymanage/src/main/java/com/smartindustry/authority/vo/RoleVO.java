package com.smartindustry.authority.vo;

import com.smartindustry.common.pojo.am.RolePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/7/31
 * @version: 1.0.0
 * @description:
 */
@Data
public class RoleVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long rid;

    private String rname;

    private Byte status;

    private String rdesc;

    public static List<RoleVO> convert(List<RolePO> pos) {
        List<RoleVO> vos = new ArrayList<>(pos.size());
        for (RolePO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static RoleVO convert(RolePO po) {
        RoleVO vo = new RoleVO();
        vo.setRid(po.getRoleId());
        vo.setRname(po.getRoleName());
        vo.setStatus(po.getStatus());
        vo.setRdesc(po.getRoleDesc());
        return vo;
    }
}
