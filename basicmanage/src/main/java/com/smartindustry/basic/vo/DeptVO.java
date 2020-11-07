package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.am.DeptBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:04 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class DeptVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long did;

    private String dname;

    private Date ctime;

    private String dcode;


    public static List<DeptVO> convert(List<DeptBO> bos) {
        List<DeptVO> vos = new ArrayList<>(bos.size());
        for (DeptBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static DeptVO convert(DeptBO bo) {
        DeptVO vo = new DeptVO();
        vo.setDid(bo.getDeptId());
        vo.setDname(bo.getDeptName());
        vo.setDcode(bo.getDeptCode());
        vo.setCtime(bo.getCreateTime());
        return vo;
    }


}
