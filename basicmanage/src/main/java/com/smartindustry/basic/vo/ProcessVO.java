package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.dd.ProcessPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:01 2020/8/13
 * @version: 1.0.0
 * @description: 物料工序
 */
@Data
public class ProcessVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long prid;

    private String prname;

    public static List<ProcessVO> convert(List<ProcessPO> pos) {
        List<ProcessVO> vos = new ArrayList<>(pos.size());
        for (ProcessPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static ProcessVO convert(ProcessPO po) {
        ProcessVO vo = new ProcessVO();
        vo.setPrid(po.getProcessId());
        vo.setPrname(po.getProcessName());
        return vo;
    }


}
