package com.smartindustry.basic.vo;

import com.smartindustry.common.constant.ConfigConstant;
import com.smartindustry.common.pojo.si.ConfigPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:57 2020/8/20
 * @version: 1.0.0
 * @description:
 */
@Data
public class ConfigVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 配置id
     */
    private Long cid;

    /**
     * 配置名称
     */
    private String cname;

    /**
     * 配置状态
     */
    private Boolean status;

    public static List<ConfigVO> convert(List<ConfigPO> pos) {
        List<ConfigVO> vos = new ArrayList<>(pos.size());
        for (ConfigPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static ConfigVO convert(ConfigPO po) {
        ConfigVO vo = new ConfigVO();
        vo.setCid(po.getConfigId());
        vo.setCname(po.getConfigKey());
        if(ConfigConstant.V_YES.equals(po.getConfigValue())){
            vo.setStatus(Boolean.TRUE);
        }else{
            vo.setStatus(Boolean.FALSE);
        }
        return vo;
    }
}
