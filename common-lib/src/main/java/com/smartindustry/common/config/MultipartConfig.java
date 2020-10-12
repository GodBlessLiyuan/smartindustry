package com.smartindustry.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:15 2020/10/12
 * @version: 1.0.0
 * @description:
 */
@Configuration
public class MultipartConfig {
    @Autowired
    private FilePathConfig filePathConfig;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        File tmpFile = new File(filePathConfig.getTmpPath());
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(filePathConfig.getTmpPath());
        return factory.createMultipartConfig();
    }
}
