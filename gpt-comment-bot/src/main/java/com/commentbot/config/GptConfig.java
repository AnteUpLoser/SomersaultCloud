package com.commentbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 绑定自定义配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gpt")
public class GptConfig {
    private String sk;
    private String apiUrl;
    private Integer proxyPort;
    private String proxyHostname;
}
