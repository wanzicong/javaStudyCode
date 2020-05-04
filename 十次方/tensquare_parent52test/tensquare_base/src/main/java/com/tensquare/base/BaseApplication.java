package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import utils.IdWorker;

/**
 * springBoot启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {
    //入口
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    //定义id生成器的bean
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
