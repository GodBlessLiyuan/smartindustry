package com.smartindustry.pda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author: xiahui
 * @date: Created in 2020/10/30 9:44
 * @description: PDA管理
 * @version: 1.0
 */
@MapperScan("com.smartindustry.common.mapper")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PdaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdaApplication.class, args);
    }
}