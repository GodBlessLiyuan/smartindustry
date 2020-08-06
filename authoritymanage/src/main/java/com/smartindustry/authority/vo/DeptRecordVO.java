package com.smartindustry.authority.vo;

import com.smartindustry.common.bo.am.DeptRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:03 2020/8/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class DeptRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String dname;

    private String uname;

    private Date ctime;

    private String type;

    public static List<DeptRecordVO> convert(List<DeptRecordBO> bos) {
        List<DeptRecordVO> vos = new ArrayList<>(bos.size());
        for (DeptRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static DeptRecordVO convert(DeptRecordBO bo) {
        DeptRecordVO vo = new DeptRecordVO();
        vo.setUname(bo.getName());
        vo.setDname(bo.getDeptName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
