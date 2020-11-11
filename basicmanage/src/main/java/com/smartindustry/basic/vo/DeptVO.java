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

    private List<Long> dcode;

    private String code; //部门代码

    private String dmanager;//部门负责人

    private String pDeptName;//上级部分

    private Byte status;//状态

    private String desc;//描述

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
        vo.setPDeptName(bo.getParentName());
        vo.setDmanager(bo.getUsername());//负责人
        vo.setStatus(bo.getStatus());//状态
        vo.setDesc(bo.getDeptDesc());
        vo.setCode(bo.getDeptCode());
        vo.setCtime(bo.getCreateTime());
        return vo;
    }


}
