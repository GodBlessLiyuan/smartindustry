package com.smartindustry.outbound;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: xiahui
 * @date: Created in 2020/7/9 10:34
 * @description: 出库管理
 * @version: 1.0
 */
@MapperScan("com.smartindustry.common.mapper")
@SpringBootApplication
public class OutboundApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutboundApplication.class, args);
    }
}
