package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderController {
    public static final String PAYMENT_URL = "http://consul-provider-payment";

    @Value("${server.port}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "consumer/payment/consul")
    public CommonResult<Payment> PaymentZk(){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/consul", CommonResult.class);
    }
}
