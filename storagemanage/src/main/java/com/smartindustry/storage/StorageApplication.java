package com.smartindustry.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: xiahui
 * @date: Created in 2020/6/22 19:17
 * @description: 入库管理
 * @version: 1.0
 */
@MapperScan("com.smartindustry.common.mapper")
//@ComponentScan({"com.smartindustry.common.security","com.smartindustry.storage"})
@SpringBootApplication
public class StorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
}
