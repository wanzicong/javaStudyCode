package com.tensquare.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utils.IdWorker;
import utils.JwtUtil;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication {

    /**
     * 启动类
     */

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    /**
     * 使用id生成器
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    /**
     * BCryptPasswordEncoder加密工具类
     */
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JWT授权操作
     */
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}
