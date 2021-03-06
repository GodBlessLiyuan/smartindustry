package com.smartindustry.workbench;

import com.smartindustry.common.config.FilePathConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hui.feng
 * @date created in 2020/9/25
 * @description
 */

@MapperScan("com.smartindustry.common.mapper")
@ComponentScan({"com.smartindustry.common.security", "com.smartindustry.workbench"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableConfigurationProperties({FilePathConfig.class})
public class WorkBenchApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkBenchApplication.class, args);
    }
}
