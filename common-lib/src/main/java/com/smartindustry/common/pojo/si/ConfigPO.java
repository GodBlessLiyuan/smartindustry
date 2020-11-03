package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import lombok.Data;

/**
 * si_config
 * @author 
 */
@Data
public class ConfigPO implements Serializable {
    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 配置名称
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    private static final long serialVersionUID = 1L;
}