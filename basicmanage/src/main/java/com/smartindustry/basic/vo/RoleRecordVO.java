package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.am.RoleRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:53 2020/8/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class RoleRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String rname;

    private String uname;

    private Date ctime;

    private String type;

    public static List<RoleRecordVO> convert(List<RoleRecordBO> bos) {
        List<RoleRecordVO> vos = new ArrayList<>(bos.size());
        for (RoleRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static RoleRecordVO convert(RoleRecordBO bo) {
        RoleRecordVO vo = new RoleRecordVO();
        vo.setUname(bo.getName());
        vo.setRname(bo.getRoleName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
