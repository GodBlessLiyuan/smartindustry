package com.smartindustry.pda;

import com.smartindustry.common.config.FilePathConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: xiahui
 * @date: Created in 2020/10/30 9:44
 * @description: PDA管理
 * @version: 1.0
 */
@MapperScan("com.smartindustry.common.mapper")
@ComponentScan({"com.smartindustry.common.security", "com.smartindustry.pda"})
@SpringBootApplication
@EnableConfigurationProperties({FilePathConfig.class})
public class PdaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdaApplication.class, args);
    }
}