package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import lombok.Data;

/**
 * si_config
 * @author 
 */
@Data
public class ConfigPO implements Serializable {
    private Long configId;

    private String configName;

    private String configValue;

    private static final long serialVersionUID = 1L;
}