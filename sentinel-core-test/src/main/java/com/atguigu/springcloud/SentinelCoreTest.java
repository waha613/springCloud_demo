package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class SentinelCoreTest {

    public static void main(String[] args) {
        SpringApplication.run(SentinelCoreTest.class, args);
    }
}
