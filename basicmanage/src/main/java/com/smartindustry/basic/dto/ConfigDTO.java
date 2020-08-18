package com.smartindustry.basic.dto;

import com.smartindustry.common.constant.ConfigConstant;
import com.smartindustry.common.pojo.si.ConfigPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:33 2020/8/17
 * @version: 1.0.0
 * @description:
 */
@Data
public class ConfigDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 流程配置的名称
     */
    private String cname;

    /**
     * 流程配置的状态，默认开启
     */
    private Boolean status;

    public static ConfigPO buildPO(ConfigDTO dto) {
        ConfigPO po = new ConfigPO();
        po.setConfigKey(dto.getCname());
        if(dto.getStatus().equals(Boolean.TRUE)){
            po.setConfigValue(ConfigConstant.V_YES);
        }else {
            po.setConfigValue(ConfigConstant.V_NO);
        }
        return po;
    }

    public static List<ConfigPO> updateList(List<ConfigDTO> dtos){
        List<ConfigPO> pos = new ArrayList<>(dtos.size());
        for(ConfigDTO dto:dtos) {
            pos.add(buildPO(dto));
        }
        return pos;
    }

}
