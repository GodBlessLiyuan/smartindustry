package com.smartindustry.basic;

import com.smartindustry.common.config.FilePathConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: xiahui
 * @date: Created in 2020/7/28 19:35
 * @description: 基础数据管理
 * @version: 1.0
 */
@MapperScan("com.smartindustry.common.mapper")
@ComponentScan({"com.smartindustry.common.security", "com.smartindustry.basic"})
@SpringBootApplication
@EnableConfigurationProperties({FilePathConfig.class})
public class BasicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }
}
