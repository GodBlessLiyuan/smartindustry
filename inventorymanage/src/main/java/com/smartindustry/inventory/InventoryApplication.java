package com.smartindustry.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 15:55
 * @description: 库存信息
 * @version: 1.0
 */
@MapperScan({"com.smartindustry.common.mapper"})
@ComponentScan({"com.smartindustry.common.security", "com.smartindustry.inventory"})
@SpringBootApplication
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
