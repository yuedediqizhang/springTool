package com.example.myspringbootsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@MapperScan("com.example.myspringbootsecurity.dao")
// 开启web安全
@EnableWebSecurity
public class MySpringbootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringbootSecurityApplication.class, args);
    }

}
