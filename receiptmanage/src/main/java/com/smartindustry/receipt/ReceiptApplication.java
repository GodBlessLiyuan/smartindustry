package com.smartindustry.receipt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: xiahui
 * @date: Created in 2020/6/22 19:17
 * @description: TODO
 * @version: 1.0
 */
@MapperScan("com.smartindustry.common.mapper")
@SpringBootApplication
public class ReceiptApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReceiptApplication.class, args);
    }
}
