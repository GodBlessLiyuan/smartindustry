package com.smartindustry.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author: xiahui
 * @date: Created in 2020/10/9 9:08
 * @description: 大数据
 * @version: 1.0
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BigDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(BigDataApplication.class, args);
    }
}
