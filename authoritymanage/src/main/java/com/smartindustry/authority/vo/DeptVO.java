package com.smartindustry.authority.vo;

import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
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

    private String dname;

    private Long uid;

    private String ddesc;

    /**
     * 1 启动
     2 禁用
     */
    private Byte status;

    private Date ctime;

    private Date utime;

    public static List<DeptVO> convert(List<DeptPO> pos) {
        List<DeptVO> vos = new ArrayList<>(pos.size());
        for (DeptPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static DeptVO convert(DeptPO po) {
        DeptVO vo = new DeptVO();
        vo.setCtime(po.getCreateTime());
        vo.setDdesc(po.getDeptDesc());
        vo.setDid(po.getDeptId());
        vo.setDname(po.getDeptName());
        vo.setStatus(po.getStatus());
        vo.setUid(po.getUserId());
        vo.setUtime(po.getUpdateTime());
        vo.setPid(po.getParentId());
        return vo;
    }
}
