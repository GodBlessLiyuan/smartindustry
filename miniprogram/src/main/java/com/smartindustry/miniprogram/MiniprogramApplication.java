package com.smartindustry.miniprogram;

import com.smartindustry.common.config.FilePathConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
@EnableDiscoveryClient
@AutoConfigurationPackage
@MapperScan("com.smartindustry.common.mapper")
@ComponentScan("com.smartindustry.common")
@ComponentScan("com.smartindustry.miniprogram")
@EnableConfigurationProperties({FilePathConfig.class})
@SpringBootApplication
public class MiniprogramApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniprogramApplication.class, args);
    }

}
