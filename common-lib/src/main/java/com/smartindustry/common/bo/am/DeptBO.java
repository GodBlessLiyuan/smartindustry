package com.smartindustry.common.bo.am;

import com.smartindustry.common.pojo.am.DeptPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:44 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class DeptBO extends DeptPO {

    private static final long SerialVersionUID = 1L;
    private String username;
    private String parentName;
    /**
     * 当前部门的子部门列表
     */
    private List<DeptBO> children;

    public static List<DeptBO> convert(List<DeptPO> pos) {
        List<DeptBO> bos = new ArrayList<>(pos.size());
        for (DeptPO po : pos) {
            bos.add(convert(po));
        }
        return bos;
    }

    public static DeptBO convert(DeptPO po) {
        DeptBO bo = new DeptBO();
        bo.setDeptId(po.getDeptId());
        bo.setDeptName(po.getDeptName());
        bo.setUserId(po.getUserId());
        return bo;
    }
}
