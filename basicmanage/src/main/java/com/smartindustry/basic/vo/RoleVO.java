package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.am.RolePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    private Date ctime;

    private String rcode;

    private Byte status;

    private String rdes;


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
        vo.setRcode(po.getRoleCode());
        vo.setCtime(po.getCreateTime());
        vo.setRdes(po.getRoleDesc());
        vo.setStatus(po.getStatus());
        return vo;
    }
}
