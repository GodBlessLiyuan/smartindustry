package com.smartindustry.datasynchronize;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hui.feng
 * @date created in 2020/11/4
 * @description
 */
//@MapperScan("com.smartindustry.common.mapper")
@ComponentScan({"com.smartindustry.common.security", "com.smartindustry.datasynchronize"})
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class DataSynchronizeApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSynchronizeApplication.class, args);
    }
}
