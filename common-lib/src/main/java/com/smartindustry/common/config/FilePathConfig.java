package com.smartindustry.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: xiahui
 * @date: Created in 2020/7/16 18:54
 * @description: 文件路径配置
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "config.file")
public class FilePathConfig {
    /**
     * 本地访问根目录
     */
    private String localPath;
    /**
     * 网络访问域名
     */
    private String publicPath;
    /**
     * 更改tomcat临时文件
     */
    private String tmpPath;

    /**
     * 项目目录
     */
    private String projectDir = "/industryfile";

    /**
     * 模块目录
     */
    private String outboundDir = "/outbound";
    private String basicDir = "/basic";

    /**
     * 文件目录
     */
    private String logisticsDir = "/logistics/";
    private String materialDir = "/material/";
}
