package com.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailConfig {
    //邮箱的各种配置信息
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    private static final String fromPersonal = "筋斗云团队";
    private static final String subject = "[SomersaultCloud]";







}
