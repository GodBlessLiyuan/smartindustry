package com.smartindustry.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: xiahui
 * @date: Created in 2020/7/28 18:19
 * @description: 用户权限管理
 * @version: 1.0
 */
@MapperScan({"com.smartindustry.common.mapper"})
@ComponentScan({"com.smartindustry.common", "com.smartindustry.authority"})
@SpringBootApplication
public class AuthorityApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }
}
