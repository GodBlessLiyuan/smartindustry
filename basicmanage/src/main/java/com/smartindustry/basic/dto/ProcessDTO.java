package com.smartindustry.basic.dto;

import com.smartindustry.common.pojo.dd.ProcessPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:10 2020/8/13
 * @version: 1.0.0
 * @description:
 */
@Data
public class ProcessDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long prid;

    private String prname;

    public static ProcessPO createPO(ProcessDTO dto) {
        ProcessPO po = new ProcessPO();
        po.setProcessName(dto.getPrname());
        po.setUserId(1L);
        po.setCreateTime(new Date());
        return po;
    }
}
