package com.smartindustry.pda.config;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 18:56 2020/11/10
 * @ Description：rfid映射配置文件实体类
 * @ Modified By：
 * @ Version:     1.0
 */
@Component
@PropertySource(value = "classpath:config.yml")
@ConfigurationProperties(prefix = "lrfid-area")
@Data
public class RfidConfig {
    /**
     * rfid和区域对应的map
     */
    @Value("${maps}")
    private String maps;

    public Map<String,String> parseMap(String maps){
        return JSON.parseObject(this.maps, HashMap.class);
    }

}