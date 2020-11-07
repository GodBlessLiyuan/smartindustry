package com.smartindustry.datasynchronize.vo;

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

    private Long pid;

    private String pname;

    private String dname;

    private Long uid;

    private String uname;

    private String ddesc;

    private Boolean disabled;

    /**
     * 存放着从顶级部门到当前部门的id列表
     */
    private List<Long> dcode;

    /**
     * 1 启动
     2 禁用
     */
    private Byte status;

    private Date ctime;

    private Date utime;
    /**
     * 当前部门的子部门列表
     */
    private List<DeptVO> children;

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
        vo.setPname(bo.getParentName());
        vo.setUname(bo.getUsername());
        vo.setStatus(bo.getStatus());
        vo.setDdesc(bo.getDeptDesc());
        vo.setPid(bo.getParentId());
        vo.setUid(bo.getUserId());
        if(bo.getStatus() == 1){
            vo.setDisabled(Boolean.FALSE);
        }else {
            vo.setDisabled(Boolean.TRUE);
        }
        return vo;
    }


}
