package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ll4859332@hotmail.com
 * @version V1.0
 * @title
 * @description
 * @date 2022-07-11 16:09
 */
@FeignClient(name = "cloudalibaba-sentinel-service",url = "http://localhost:8401",
        fallback = EchoServiceFallback.class,configuration = FeignConfiguration.class)
@Service
public interface EchoService {
    @RequestMapping(value = "/testA", method = RequestMethod.GET)
    String echo();
}

class FeignConfiguration {
    @Bean
    public EchoServiceFallback echoServiceFallback() {
        return new EchoServiceFallback();
    }
}

class EchoServiceFallback implements EchoService {
    @Override
    public String echo() {
        System.out.println(this);
        return "echo fallback";
    }
}
