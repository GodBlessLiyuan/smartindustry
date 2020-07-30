package com.smartindustry.authority.dto;

import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.om.LogisticsRecordPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:34 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class DeptDTO {
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


    public static DeptPO updatePO(DeptDTO dto) {
        DeptPO po = new DeptPO();
        po.setDeptId(dto.getDid());
        po.setUpdateTime(new Date());
        po.setStatus(dto.getStatus());
        return po;
    }

    public static DeptPO insertPO(DeptDTO dto) {
        DeptPO po = new DeptPO();
        Date date = new Date();
        po.setUpdateTime(date);
        po.setCreateTime(date);
        po.setStatus(dto.getStatus());
        po.setDeptDesc(dto.getDdesc());
        po.setParentId(dto.getPid());
        po.setDeptName(dto.getDname());
        po.setUserId(dto.getUid());
        po.setDr((byte)1);
        return po;
    }

    public static List<DeptPO> updateList(List<DeptDTO> dtos){
        List<DeptPO> pos = new ArrayList<>(dtos.size());
        for(DeptDTO dto:dtos) {
            pos.add(updatePO(dto));
        }
        return pos;
    }
}
