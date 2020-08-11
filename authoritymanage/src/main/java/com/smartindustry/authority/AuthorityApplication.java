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
@MapperScan({"com.smartindustry.common.mapper","com.smartindustry.common.service"})
//@ComponentScan({"com.smartindustry.common.service","com.smartindustry.common.util"})
@SpringBootApplication(scanBasePackages="com.smartindustry")
public class AuthorityApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }
}
