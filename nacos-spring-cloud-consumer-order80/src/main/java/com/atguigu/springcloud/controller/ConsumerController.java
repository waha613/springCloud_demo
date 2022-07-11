package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.ConsumerService;
import com.atguigu.springcloud.service.EchoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consumer")
@Slf4j
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private EchoService echoService;


    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) throws InterruptedException {
        //return consumerService.getPaymentById(id);
        Thread.sleep(1000);
        return new CommonResult<>(200,"hahahah");
    }

    @GetMapping(value = "/degradeTest")
    public String degradeTest(){
        return "degradeTest";
    }

    @GetMapping(value = "/payment/discovery")
    public String getPaymentList(){
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-service-provider");
        if(ObjectUtils.isEmpty(instances)){
            return null;
        }
        for (ServiceInstance instance : instances) {
            log.info("host:{}",instance.getHost());
            log.info("port:{}",instance.getPort());
            log.info("ServiceId:{}",instance.getServiceId());
            log.info("InstanceId:{}",instance.getInstanceId());
            log.info("uri:{}",instance.getUri());
            log.info("Metadata:{}",instance.getMetadata());
            log.info("Scheme:{}",instance.getScheme());
            log.info("isSecure:{}",instance.isSecure());
        }
        return ""+instances.size() ;
    }

    @GetMapping(value = "/feignTest")
    public String feignTest(){
        return echoService.echo("111");
    }
}
