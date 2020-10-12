package com.smartindustry.outbound;

import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.config.MultipartConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author: xiahui
 * @date: Created in 2020/7/9 10:34
 * @description: 出库管理
 * @version: 1.0
 */
@MapperScan("com.smartindustry.common.mapper")
@ComponentScan({"com.smartindustry.common.security","com.smartindustry.outbound"})
@SpringBootApplication
@EnableConfigurationProperties({FilePathConfig.class})
public class OutboundApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutboundApplication.class, args);
    }
}
