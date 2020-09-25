package com.smartindustry.workbench;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hui.feng
 * @date created in 2020/9/25
 * @description
 */

@MapperScan("com.smartindustry.common.mapper")
@ComponentScan({"com.smartindustry.common.security","com.smartindustry.workbench"})
@SpringBootApplication
public class WorkbenchApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkbenchApplication.class, args);
    }
}
